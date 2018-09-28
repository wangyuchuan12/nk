package com.ifrabbit.nk.usercenter.controller;

import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.repository.JobListRepository;
import com.ifrabbit.nk.usercenter.service.JobListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("usercenter/jobpool")
public class JobPoolController extends AbstractPageableController<JobListService, JobList, JobListDTO, Long> {

    private static TaskOpinion taskOpinion = null;
    private static Logger logger = LoggerFactory.getLogger(JobPoolController.class);
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Date date = new Date();


    @Autowired private ProblemService problemService;
    @Autowired private ProcessService processService;
    @Autowired private TaskService taskService;
    @Autowired private JobListRepository joblistRepository;
    @Autowired private JobListService jobListService;

    @Override
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<JobList> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            JobListDTO condition) {
        condition.setJoblistOwner("null");
        Page<JobList> list = super.list(pageable, condition);
//        Iterator<JobList> iterator = list.iterator();
//        while(iterator.hasNext()){
//            String owner = iterator.next().getOwner();
//            if(!owner.equals("null")){
//                iterator.remove();
//            }
//        }
        return list;
    }


    //一键抢单
    @RequestMapping("robb")
    public String robb(JobListDTO condition){
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        condition.setJoblistOwner("null");
        List<JobList> all = jobListService.findAll(condition);
        String reuslt = "";
        if(all.isEmpty()){
            reuslt =  "empty";//如果代办列表是空的就返回空的提示
        }else{
            try{
                int size = all.size() - 1;
                if(size < 20){//如果代办数量小于20，就按照数量大小给他
                    for(int i=0; i<all.size(); i++){
                        JobList jobList = all.get(i);
                        jobList.setJoblistOwner(loginUser);
                        jobList.setJoblistState(2);//表示工单已被抢，正在处理中
                        jobListService.updateIgnore(jobList);
                    }
                    reuslt = "success";
                }else{
                    for(int i=0; i<21; i++){//如果代办数量大于20，就按照指定的数量给他
                        JobList jobList = all.get(i);
                        jobList.setJoblistOwner(loginUser);
                        jobList.setJoblistState(2);//表示工单已被抢，正在处理中
                        jobListService.updateIgnore(jobList);
                    }
                    reuslt = "success";
                }
            }catch (Exception e){
                e.printStackTrace();
                return "error";
            }
        }
        return reuslt;
    }

    @RequestMapping("robbOfCopy")
    public String robbOfCopy(JobListDTO condition){
        System.out.println("11111111111111111111111111111111");
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        condition.setJoblistOwner("copy");
        condition.setJoblistState(0);
        List<JobList> all = jobListService.findAll(condition);

        String reuslt = "";
        if(all.isEmpty()){
            reuslt =  "empty";//如果代办列表是空的就返回空的提示
        }else{
            try{
                int size = all.size();
                if(size < 20){//如果代办数量小于20，就按照数量大小给他
                    for(int i=0; i<size; i++){
                        JobList jobList = all.get(i);
                        jobList.setJoblistOwner(loginUser);
                        jobListService.update(jobList);
                    }
                    reuslt = "success";
                }else{
                    for(int i=0; i<21; i++){//如果代办数量大于20，就按照指定的数量给他
                        JobList jobList = all.get(i);
                        jobList.setJoblistOwner(loginUser);
                        jobListService.update(jobList);
                    }
                    reuslt = "success";
                }
            }catch (Exception e){
                e.printStackTrace();
                return "error";
            }
        }
        return reuslt;
    }

    @RequestMapping("sub")
     public void robbing(JobList jobList, Problem problem){

        logger.info("+++++++++++++++++++++"+problem.getId()+"+++++++++++++++++++++++++");
        logger.info("+++++++++++++++++++++"+problem.getProblempartsVarparamc()+"+++++++++++++++++++++++++");
        //BusinessId
        Long id = problem.getId();
        String problempartsVarparamc = problem.getProblempartsVarparamc();
        jobList.setId(Long.valueOf(problempartsVarparamc));
        JobList one = jobListService.findOne(jobList);
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        JobList one1 = joblistRepository.findOne(Long.valueOf(problempartsVarparamc));
        one1.setJoblistOwner(loginUser);
        try{
            JobList update = joblistRepository.update(one1);
        }catch (Exception e){
            logger.error("更新操作人失败");
            e.printStackTrace();
        }
        //TODO 抢单大致操作:1、先获取到被抢的任务id和当前操作人name,在根据id 更新该id的处理人名字
    }
    @RequestMapping("get")
    public void getTask(@Param("id")Long id,@Param("bussinessId")String bussinessId){
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.processInstanceId(id).businessId(bussinessId);
        List<Task> list = taskQuery.list();
        Task task = list.get(0);
        JobList jobList = joblistRepository.findOne(id);
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        jobList.setJoblistOwner(loginUser);
        try{
            JobList update = joblistRepository.update(jobList);
        }catch (Exception e){
            logger.error("更新操作人失败");
            e.printStackTrace();
        }
    }
}
