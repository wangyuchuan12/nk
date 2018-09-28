package com.ifrabbit.nk.flow.vo;

import com.ifrabbit.nk.util.beanSax.SaxAttr;
import com.ifrabbit.nk.util.beanSax.SaxEntity;
import lombok.Data;

import java.util.Map;

@Data
public class TaskVo {
    @SaxAttr(name="name")
    private String name;
    private Map<String,String> paramMap;
}
