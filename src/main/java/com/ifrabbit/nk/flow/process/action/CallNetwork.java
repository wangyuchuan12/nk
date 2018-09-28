package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: WangYan
 * @Date: 2018/4/18 14:54
 * @Description: 智能客服拨打快递网点(包含快递停滞网点)执行程序
 */
@Component("呼叫服务网点")
public class CallNetwork implements ActionHandler {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    public ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private UserReportService userReportService;
    @Autowired
    ProblemService problemService;
    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem = problemRepository.findOne(cond);
        String problemparts_expressnumber = String.valueOf(problem.getProblemparts_expressnumber());
        Integer problemparts_type = problem.getProblemparts_type();
        ExpressInfoDetail expressInfoDetail;
        if (problemparts_type == 5 || problemparts_type == 3) {//催单类型，2代表运输中
            expressInfoDetail = expressInfoDetailService.findByType(2, problemparts_expressnumber);
        } else {//其他类，3代表派件中
            expressInfoDetail = expressInfoDetailService.findByType(3, problemparts_expressnumber);
        }
        // TODO: 2018/5/17  expressInfoDetail为空时取不到公司这里需处理
        //抓取网点电话
        Company company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));
//        List<Map<String, Object>> list = companyService.selectTelById(Long.valueOf(expressInfoDetail.getBegin_companyid()));
        String qymndh = userReportService.getParameter("qymndh");
        String tempTime = userReportService.getParameter("cbsjjg");
        Long time;
        if (null == tempTime){
             time = 24 * 60 * 60 * 1000L;
        }else {
             time = Long.valueOf(tempTime)*1000;
        }
        String netWorkPhone = company.getCompany_linktel();
        if ("true".equals(qymndh)) {
            netWorkPhone = problem.getProblemparts_receivephone();
        }
        String companyName = company.getCompany_name();
        String siteid = null;
        String type = problem.getProblemparts_type().toString();
        switch (problem.getProblemparts_type()) {
            case 1:
                siteid = "10000217";
                break;
            case 2:
                siteid = "10000187";
                type = "破损件,非本人签收,内外都破损";
                break;
            case 3:
                siteid = "10000218";
                type = problem.getProblemparts_newreceiveaddress();
                break;
            case 4:
                siteid = "10000221";
                break;
            case 5:
                if ("呼叫停滞中心".equals(processInstance.getCurrentNode())) {
                    siteid = "10000201";
                } else {
                    siteid = "10000203";
                }
                break;
        }

        Map<String, Object> vars = new HashMap<>();
        Integer network_count = (Integer) context.getProcessService().getProcessVariable("network_count", processInstance.getId());
        if (null == network_count) {
            vars.put("network_count", 1);
        } else {
            vars.put("network_count", network_count + 1);
        }
        int i = 0 ;
        context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
        while (i < 3){
            boolean check = HandlerUtil.text01(processInstance, context, netWorkPhone, companyName, problem.getProblemparts_type().toString(),
                    siteid, problem.getProblemparts_expressnumber().toString(),1);
            i++;
            problem.setProblemparts_callcompanycount(i);
            problemService.saveIgnoreNull(problem);
            if (check){
                return;
            }else {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
