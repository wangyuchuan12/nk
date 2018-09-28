package com.ifrabbit.nk.flow.service.impl;
import com.alibaba.druid.util.JdbcUtils;
import com.bstek.uflo.model.ProcessInstance;
import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import com.ifrabbit.nk.flow.service.VariableService;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/5/18 15:38
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class VariableServiceImpl
        extends AbstractCrudService<VariableRepository,TempVariable, Long>
		implements VariableService {

    @Autowired
    private DataSource dataSource;

    public VariableServiceImpl(VariableRepository repository) {
        super(repository);
    }

    public String getPhoneVariable(ProcessInstance processInstance, String variableKey) {
        String variableValue = "";
        try {
            List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, "select * from temp_variable where process_id = ? and t_key = ? ", processInstance.getId(), variableKey);
            if (!result.isEmpty()) {
                variableValue = (String) result.get(0).get("t_value");
            }else{
                variableValue = "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variableValue;
    }
 public String getPhoneVariablebyid(Long id, String variableKey) {
        String variableValue = "";
        try {
            List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, "select * from temp_variable where process_id = ? and t_key = ? ", id, variableKey);
            if (!result.isEmpty()) {
                variableValue = (String) result.get(0).get("t_value");
            }else{
                variableValue = "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variableValue;
    }
}
