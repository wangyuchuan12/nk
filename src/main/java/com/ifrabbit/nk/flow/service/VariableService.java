package com.ifrabbit.nk.flow.service;

import com.bstek.uflo.model.ProcessInstance;
import com.ifrabbit.nk.express.domain.TempVariable;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.data.support.CrudService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/5/18 15:34
 * @Description:
 */
public interface VariableService extends CrudService<TempVariable,Long> {
    String getPhoneVariable(ProcessInstance processInstance, String variableKey);
    String getPhoneVariablebyid(Long id, String variableKey);

}
