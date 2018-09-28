package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.express.domain.DealType;
import com.ifrabbit.nk.express.domain.DealTypeDTO;
import com.ifrabbit.nk.express.service.DealTypeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: lishaomiao
 */
@RestController
@RequestMapping("express/dealtype")
public class DealTypeController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(DealTypeController.class);

    @Autowired
    private DealTypeService dealTypeService;

    @GetMapping
    Page<DealType> list(
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
            DealTypeDTO condition) {
        return dealTypeService.findAll(pageable, condition);
    }


    /**
     * @param
     * @return
     * @Author: lishaomiao
     * 更新
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(@PathVariable("id") String id, @RequestBody DealType dealType) {
        dealType.setId(Long.valueOf(id));
        dealTypeService.updateIgnore(dealType);
    }

    /**
     * @param
     * @return
     * @Author: lishaomiao
     * 删除
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") Long id) {
        dealTypeService.delete(id);
    }



}
