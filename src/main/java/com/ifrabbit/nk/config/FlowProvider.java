package com.ifrabbit.nk.config;

import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.usercenter.domain.Staff;
import ir.boot.autoconfigure.flow.AbstractFlowProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowProvider extends AbstractFlowProvider {

    @Override
    public String getLoginUser() {
        Staff staff = UserContext.get();
        if (null == staff) {
            return "anonymous";
        }
        return staff.getStaffUsername();
    }

    @Override
    public String getCategoryId() {
        return null;
    }

}
