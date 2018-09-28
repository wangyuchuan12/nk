package com.ifrabbit.nk.flow.vo;

import com.ifrabbit.nk.util.beanSax.SaxAttr;
import com.ifrabbit.nk.util.beanSax.SaxEntity;
import lombok.Data;

import java.util.List;

@Data
public class UfloProcessVo {
    @SaxAttr(name="name")
    private String name;
    @SaxAttr(name="task",elementType = TaskVo.class)
    private List<TaskVo> tasks;
    private StartVo start;
    private List<UfloSubProcess> ufloSubProcesses;

}
