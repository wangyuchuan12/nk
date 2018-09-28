package com.ifrabbit.nk.express.controller;

import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.utils.KeyGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ProblemWeChatController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;

    @RequestMapping("express/problem")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Problem problem) {
        //生成的TableInfo_TableNo
        String TableInfo_TableNo = KeyGet.setKey(problem.getProblemparts_type());
        problem.setProblemparts_insideitem(problem.getProblemparts_insideitem());
        problemService.insert(problem);
        solveProcess(problem);
    }


    public void solveProcess(Problem problem) {
        Long id = problem.getId();
        StartProcessInfo info = new StartProcessInfo("admin");
        info.setBusinessId(String.valueOf(id));
        processService.startProcessByName("快递退回流程", info);
    }


    @RequestMapping("upload/picture")
    public Map getUploadFile(@RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        System.out.println("1");
        try {
            if (file.isEmpty() || file.getBytes().length == 0) {
                map.put("msg", "提交的图片不能为空");
                System.out.println("2");
            } else {
                request.setCharacterEncoding("utf-8");
                String path = "D:\\";
                File dirs = new File(path, "upload");
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }
                String filePath = dirs.getPath() + "//"
                        + new Date().getTime() + file.getOriginalFilename();
                System.out.println(filePath);
                // 转存文件
                file.transferTo(new File(filePath));
                map.put("msg", "上传成功");
                map.put("url", filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

