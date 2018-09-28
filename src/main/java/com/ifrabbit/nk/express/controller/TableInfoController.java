package com.ifrabbit.nk.express.controller;

import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.domain.DealinfoDTO;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.repository.DealinfoRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.domain.UfloTaskDTO;
import com.ifrabbit.nk.usercenter.service.UfloTaskService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Auther: WangYan
 * @Date: 2018/7/5 11:26
 * @Description:
 */
@RestController
@RequestMapping("express/tableinfo")
public class TableInfoController extends AbstractPageableController<UfloTaskService,UfloTask,UfloTaskDTO,Long> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TableInfoController.class);

    @Autowired
    private TableInfoService tableInfoService;

    @Autowired
    private DealinfoService dealinfoService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private UfloTaskService ufloTaskService;

    @Autowired
    private TaskService taskService;
    @RequestMapping("resultId")
    @ResponseBody
    public String savResult(@RequestParam("tableInfoResult") Long resultId) {
        TableInfo tableInfo  = tableInfoService.get(resultId);
        tableInfo.setTableinfo_result(1);
        tableInfoService.update(tableInfo);
        logger.info("=================流程复制录入成功====================");
        return "ok";
    }



    /**
     * @Author: lishaomiao
     * @Date: 2018/7/13
     * @param pageable
     * @param condition
     * @return
     */
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<UfloTask> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            UfloTaskDTO condition) {
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        if(StringUtils.isNotBlank(loginUser)){
            if(loginUser.equalsIgnoreCase("admin")){
                condition.setFuzzyOwMer(loginUser);
                condition.setFuzzyAssignee("copy");
                condition.setState("Created");
            }else{
                condition.setFuzzyOwMer(loginUser);
                condition.setFuzzyAssignee("copy");
                condition.setState("Created");
            }
                Page<UfloTask> list = super.list(pageable, condition);
                return  list;
        }
       return  null;
    }


    @RequestMapping("robb")
    public String robb(){
        boolean flag = true;//flag为false就不生成任务
        StringBuffer result = new StringBuffer();
        //查询可复制任务
        DealinfoDTO condition = new DealinfoDTO();
        condition.setAppdealVarparamb("0");
        condition.setAppdealDealstate(1);
//        condition.setAppdealDealtype(0);
        condition.setAppdealContent("等待处理");
        List<Dealinfo> allList = dealinfoService.findAll(condition);
//        condition.setAppdealDealtype(4);
        List<Dealinfo> allList2 = dealinfoService.findAll(condition);
        allList.addAll(allList2);
        // 判断任务池是否有任务
        if(allList.isEmpty())
            return "empty";
        List<String> distinctList = new ArrayList<>();

        for (int i = 0; i <allList.size() ; i++) {
            distinctList.add(String.valueOf(allList.get(i).getAppdealTabid()));
        }
        List<String> TaskList = distinctList.stream().distinct().collect(Collectors.toList());

        try{
            for(int i=0; i<TaskList.size(); i++){
                String appdealTabid = TaskList.get(i);

                UfloTaskDTO ut = new UfloTaskDTO();
                ut.setAssignee("copy");
                ut.setBusinessId(String.valueOf(appdealTabid));
                List<UfloTask> all = ufloTaskService.findAll(ut);
                if(all.size() == 0){
                    flag = true;
                    result.append("success");
                }else {
                    flag = false;
                    result.append("empty");
                }

                if(flag){
                    StartProcessInfo info = new StartProcessInfo(EnvironmentUtils.getEnvironment().getLoginUser());
                    info.setBusinessId(appdealTabid);
                    ProcessInstance instance = new ProcessInstance();
                    instance = processService.startProcessByName("流程复制任务", info);
                }
            }
            //判断是否有可以生成的任务，没有就返回empty
            int length = result.length();
            String type = result.substring(0,length);
            if(type.contains("success")){
                return "success";
            }
            return "empty";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    @RequestMapping("complete")
    public String complete(@RequestParam ("businessId") String businessId,@RequestParam ("taskId")String taskId){
        DealinfoDTO dealinfoDTO = new DealinfoDTO();
        Long business_id = Long.valueOf(businessId);
        dealinfoDTO.setAppdealTabid(business_id);
        dealinfoDTO.setAppdealTaskid(Long.valueOf(taskId));
        List<Dealinfo> all = dealinfoService.findAll(dealinfoDTO);
        int dealSize = all.size();
        for(int i=0; i<dealSize; i++){
                Dealinfo dealinfo = all.get(i);
                String appdealVarparamb = dealinfo.getAppdealVarparamb();
                if(!appdealVarparamb.equalsIgnoreCase("1")){
                    return "error";
                }
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);

        Long task_id = Long.valueOf(taskId);

        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();

        try {
            taskService.start(task_id);
            String option = loginUser + "在"  + time + "复制了该条记录";
            TaskOpinion taskOpinion = new TaskOpinion(option);
            taskService.complete(task_id, taskOpinion);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @RequestMapping("update")
    public void update(@RequestParam("updateId")String id){
        if(StringUtils.isNotBlank(id)){
            Dealinfo dealinfo = new Dealinfo();
            dealinfo.setId(Long.valueOf(id));
            dealinfo.setAppdealVarparamb("1");
            dealinfoService.updateIgnore(dealinfo);
            logger.info("=================流程复制更新成功====================");
        }
    }
}
