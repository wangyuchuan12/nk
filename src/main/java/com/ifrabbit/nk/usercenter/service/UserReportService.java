package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.UserReport;
import org.springframework.data.support.CrudService;

import java.util.List;

/**
* @Author: SunJiaJian
* @Description:
* @Date: Created in 10:52 2018/3/14
*
*/
public interface UserReportService extends CrudService<UserReport, Long> {
    String getParameter(String key);
    void updateBycode(String value,String code);
    public List<UserReport> findAll();
    List<UserReport> findByAnswer();
    List<UserReport> findByUserReport(Long mainId);
    void deleteSysRLtn(Long id);
    @Override
    public void delete(Long id);
}
