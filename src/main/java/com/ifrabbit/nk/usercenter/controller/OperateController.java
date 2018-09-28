package com.ifrabbit.nk.usercenter.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifrabbit.nk.usercenter.domain.Operate;
import com.ifrabbit.nk.usercenter.domain.OperateDTO;
import com.ifrabbit.nk.usercenter.service.OperateService;
import ir.nymph.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("usercenter/operate")
public class OperateController {

    @Autowired
    private OperateService operateService;

    @GetMapping
    Page<Operate> list(@PageableDefault(size = 20) Pageable pageable,
                       OperateDTO condition) {
        return operateService.findAll(pageable, condition);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Long create(
            @RequestBody Operate operate) {
        operateService.insert(operate);
        return operate.getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(
            @PathVariable("id") Long id, @RequestBody Operate operate) {
        operate.setId(id);
        operateService.updateIgnore(operate);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable("id") Long id) {
        operateService.delete(id);
    }

    @GetMapping("export")
    List<Operate> exportMenus() {
        return this.operateService.findAll(new OperateDTO());
    }

    @PostMapping("/import")
    public Map<String, List<Operate>> importMenus(
            @RequestParam("file") MultipartFile[] files) {
        Map<String, List<Operate>> resMap = new HashMap<>();
        if (files != null && files.length > 0) {
            List<Operate> allOperate = new ArrayList<>();
            //循环获取file数组中得文件
            try {
                BufferedReader reader = null;
                for (int i = 0; i < files.length; i++) {
                    String jsonString = "";
                    MultipartFile file = files[i];
                    InputStreamReader inputStreamReader = new InputStreamReader(
                            file.getInputStream(), "UTF-8");
                    reader = new BufferedReader(inputStreamReader);
                    String tempString = null;
                    while ((tempString = reader.readLine()) != null) {
                        jsonString += tempString;
                    }
                    List<Operate> operates = new ObjectMapper()
                            .readValue(jsonString, new TypeReference<List<Operate>>() {
                            });
                    allOperate.addAll(operates);
                }
                reader.close();
                List<Operate> failed = this.operateService.importOperate(allOperate);
                if (CollectionUtil.isEmpty(failed)) {
                    resMap.put("OK", failed);
                } else {
                    resMap.put("warn", failed);
                }
                return resMap;
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof RuntimeException) {
                    resMap.put("error", new ArrayList<>());
                    return resMap;
                }
                resMap.put("error", new ArrayList<>());
                return resMap;
            }
        } else {
            resMap.put("error", new ArrayList<>());
            return resMap;
        }

    }
}
