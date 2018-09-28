package com.ifrabbit.nk.flow.process.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.CallDetailDTO;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.usercenter.domain.UserReport;
import com.ifrabbit.nk.usercenter.domain.UserReportDTO;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/8/23 14:03
 * @Description:
 */
@Component
public class FindDataThread extends Thread {
    @Autowired
    private CallDetailService tempCallDetailService;
    @Autowired
    private UserReportService tempUserReportService;
    private static CallDetailService callDetailService;
    private static UserReportService userReportService;
    public  volatile boolean exit = false;

    @PostConstruct
    public void init() {
        callDetailService = tempCallDetailService;
        userReportService = tempUserReportService;
    }
    @Override
    public void run(){
        while (!exit) {
            fandAll();
            Long interval = Long.valueOf(userReportService.getParameter("sjjg"));//时间间隔系统参数为60s
            long waitTime = interval * 1000;
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void fandAll() {
        // TODO: 2018/8/23 如果你想玩线程池，后面在玩
        UserReportDTO dto = new UserReportDTO();
        dto.setFuzzyCode("IVRid");
        List<UserReport> list = userReportService.findAll(dto);
        CallDetailDTO cond = new CallDetailDTO();
        for (UserReport userReport:list) {
            String IVRid = userReport.getValue();
            cond.setCalldetail_state(0);
            cond.setCalldetail_ivrid(IVRid);
            cond.setMwordlevel_varparama (GetLocalHost.getIp());//新增ip地址筛选条件
            work(cond, IVRid);
        }
    }

    private void work(CallDetailDTO cond, String IVRid) {
        String t5 = userReportService.getParameter("T5");
        Integer startTime = Integer.parseInt(t5.substring(0,2)+t5.substring(3,5));
        Integer endTime = Integer.parseInt(t5.substring(6,8)+t5.substring(9,11));
        List<CallDetail> list = callDetailService.findAllCallTasks(cond, new PageRequest(0, 500));
        if (list.size()> 0) {
            JSONArray array = new JSONArray();
            for (CallDetail callDetail : list) {
                Calendar now = Calendar.getInstance();
                int h = now.get(Calendar.HOUR_OF_DAY);
                int m = now.get(Calendar.MINUTE);
                int num = h * 100 + m;
                if (num >= startTime && num <= endTime) {
                    JSONObject json = new JSONObject();
                    json.put("U_phone", callDetail.getCalldetail_phonenumber());
                    json.put("U_name", callDetail.getCalldetail_name());
                    json.put("U_number", callDetail.getCalldetail_expressnumber());
                    String content = callDetail.getCalldetail_content();
                    String[] split = content.split("/");
                    for (int i = 0; i < split.length; i++) {
                        json.put("U_type" + i, split[i]);
                    }
                    json.put("U_vendor", callDetail.getId());
                    json.put("U_tabid",callDetail.getCalldetail_tableid());
                    json.put("U_dealid", callDetail.getCalldetail_dealid());
                    json.put("U_hostip", GetLocalHost.getIp());
                    callDetail.setCalldetail_state(1);
                    array.add(json);
                }else {
                    callDetail.setCalldetail_state(1);
                    callDetail.setCalldetail_answerid(38);
                }
            }
            String result = CallUtil.duyanKeys(IVRid, array);
            if ("1".equals(result)) {
                for (CallDetail callDetail : list) {
                    callDetailService.updateIgnore(callDetail);
                }
            }
        }
        if (list.size() == 500){
            this.work(cond,IVRid);
        }
    }
}
