package com.ifrabbit.nk.flow.vo;

import com.ifrabbit.nk.util.beanSax.SaxAttr;
import com.ifrabbit.nk.util.beanSax.SaxEntity;
import lombok.Data;

@Data
public class StartVo {
    @SaxAttr(name="name")
    private String name;
}
