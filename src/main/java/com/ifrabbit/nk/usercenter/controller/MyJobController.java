package com.ifrabbit.nk.usercenter.controller;

import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.flow.service.VariableService;
import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.JobListService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import com.ifrabbit.nk.usercenter.service.StaffService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;




/**
 * lishaomiao
 */
@RestController
@RequestMapping("usercenter/myjob")
public class MyJobController extends AbstractPageableController<JobListService, JobList, JobListDTO, Long> {
    private static Logger logger = LoggerFactory.getLogger(MyJobController.class);
    private final static int PIC_SIZE = 600;//图片大小
    private String time;//创建文件夹时间
    private File file;//文件夹


    @Autowired
    private VariableService variableService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DealinfoService dealinfoService;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private JobListService jobListService;



    @GetMapping
    @Transactional(readOnly = true)
    protected Page<JobList> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            JobListDTO condition) {
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        condition.setJoblistOwner(loginUser);
        condition.setJoblistState(2);
        Page<JobList> all = super.list(pageable, condition);
//        Iterator<JobList> iterator = all.iterator();
//        while (iterator.hasNext()) {
//            JobList next = iterator.next();
//            String owner = next.getOwner();
//            String state = next.getState();
//            if ((!owner.equals(loginUser)) || (!state.equalsIgnoreCase("created"))) {
//                iterator.remove();
//            }
//        }
        return all;
    }

    /**
     * @Auther: lishaomiao
     * @Date: 2018/8/2
     * @Description:查询角色编码
     */
    @RequestMapping("getRoleCode")
    public String getLogin() {
        Staff staff = staffService.getByUsername(EnvironmentUtils.getEnvironment().getLoginUser());
        String id = staff.getId();
        if(StringUtils.isNotBlank(id)){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setUserId(id);
            List<Role> role = roleService.findBasicAll(roleDTO);
            if(role.size()>0){
                String code = role.get(0).getCode();
                return code;
            }
        }
        return null;
    }

    @RequestMapping("music")
    public String getMusic(@Param("id") Long id){
        TempVariable v = new TempVariable();
        v.setProcessId(id);
        v.setT_key("call_uuid");
        TempVariable one = variableService.findOne(v);
        String call_uuid = one.getT_value();
        return call_uuid;
    }




    @RequestMapping("sub")
    public void submit(Problem problem) throws Exception{
        String  taskIds = problem.getProblemparts_memo();//接收taskId
        String  parama = problem.getProblempartsVarparamd();//图片一
        String  paramb =  problem.getProblempartsVarparame();//图片二
        String  paramc =  problem.getProblempartsVarparamf();//图片三
        String  paramDetail = problem.getProblemparts_details();//内物详情
        String  paramValue =  problem.getProblemparts_expressvalue();//快件价值
        String  paramDate =  problem.getProblemparts_expressdate();//发件日期
        if(StringUtils.isNotBlank(paramDate)){ paramDate= TimeUtil.timePare2(paramDate); }
        Long taskId = Long.valueOf(taskIds);
        taskService.start(taskId);
        String option = null;
        String formatDate = TimeUtil.getTime(new Date());
        Task task = taskService.getTask(taskId);
        String owner = task.getOwner();//当前任务的所有者
        String dealType_ = problem.getProblempartsVarparama();
        if(StringUtils.isBlank(dealType_)){ return; }
        Long problemId = problem.getId();
        Long instanceId = getInstance(problemId);
        if (dealType_.equals("304")){
            String problempartsVarparamb = problem.getProblempartsVarparamb();
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if(null!=problempartsIntparama){
               if(String.valueOf(problempartsIntparama).equals("10")){
                   option = "客服：" + owner + "在 " + formatDate + "收听了网点留言,记录为：" +problempartsVarparamb+"确认留言正确";
                   updateJobList(taskId);
                   updateDealInfo(taskId,problempartsVarparamb,82);
                   updateTableInfo(instanceId,option,82);
                   completeTask(taskId,option);
               }else if(String.valueOf(problempartsIntparama).equals("20")){
                    option = "客服：" + owner + "在 " + formatDate + "收听了网点留言,记录为：" +problempartsVarparamb+"不确认留言正确";
                    updateJobList(taskId);
                    updateDealInfo(taskId,problempartsVarparamb,83);
                    updateTableInfo(instanceId,option,83);
                    completeTask(taskId,option);
                }
           }
        } else if (dealType_.equals("303")) {
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if(null!=problempartsIntparama){
                if(String.valueOf(problempartsIntparama).equals("10")){
                    option = "客服：" + owner + "在 " + formatDate + "拨打上海中心电话";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,81);
                    updateTableInfo(instanceId,option,81);
                    completeTask(taskId,option);
                }
            }
        } else if (dealType_.equals("302")) {
            option = "客服：" + owner + "在 " + formatDate + " 完成了人工人任务添加网点";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        } else if (dealType_.equals("307")) {
            option = "客服：" + owner + "在 " + formatDate + " 完成了遗失操作";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        }  else if (dealType_.equals("306")) {
            option = "用户：" + owner + "在 " + formatDate + " 完成对人工任务上传包裹信息";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo_(instanceId, parama, paramb, paramc,paramDetail,paramValue,paramDate);
            completeTask(taskId,option);
        } else if(dealType_.equals("305")) {
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if (null != problempartsIntparama) {
                if (String.valueOf(problempartsIntparama).equals("10")) {
                    option = "客服：" + owner + "在 " + formatDate + "机器人收听了网点留言";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,84);
                    updateTableInfo(instanceId,option,84);
                    completeTask(taskId,option);
                } else if (String.valueOf(problempartsIntparama).equals("20")) {
                    option = "客服：" + owner + "在 " + formatDate + "未接通且不为机器人网点留言";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,85);
                    updateTableInfo(instanceId,option,85);
                    completeTask(taskId,option);
                }else if (String.valueOf(problempartsIntparama).equals("30")) {
                    option = "客服：" + owner + "在 " + formatDate + "接通及回复正确结果听了网点留言";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,86);
                    updateTableInfo(instanceId,option,86);
                    completeTask(taskId,option);
                }else if (String.valueOf(problempartsIntparama).equals("40")) {
                    option = "客服：" + owner + "在 " + formatDate + "电话不正确";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,87);
                    updateTableInfo(instanceId,option,87);
                    completeTask(taskId,option);
                }
            }
        }else if(dealType_.equals("308")) {
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if (null != problempartsIntparama) {
                if (String.valueOf(problempartsIntparama).equals("10")) {
                    option = "客服：" + owner + "在 " + formatDate + "确认改地址为正确";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,88);
                    updateTableInfo(instanceId,option,88);
                    completeTask(taskId,option);
                } else if (String.valueOf(problempartsIntparama).equals("20")) {
                    option = "客服：" + owner + "在 " + formatDate + "未改出正确地址";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,89);
                    updateTableInfo(instanceId,option,89);
                    completeTask(taskId,option);
                }
            }
        }else if(dealType_.equals("309")) {
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if (null != problempartsIntparama) {
                if (String.valueOf(problempartsIntparama).equals("10")) {
                    option = "客服：" + owner + "在 " + formatDate + "确认退回";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,90);
                    updateTableInfo(instanceId,option,90);
                    completeTask(taskId,option);
                } else if (String.valueOf(problempartsIntparama).equals("20")) {
                    option = "客服：" + owner + "在 " + formatDate + "未退回";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,91);
                    updateTableInfo(instanceId,option,91);
                    completeTask(taskId,option);
                }
            }
        }else if(dealType_.equals("301")) {
            Integer problempartsIntparama = problem.getProblempartsIntparama();
            if (null != problempartsIntparama) {
                if (String.valueOf(problempartsIntparama).equals("10")) {
                    option = "客服：" + owner + "在 " + formatDate + "确认人工任务复制";
                    updateJobList(taskId);
                    updateDealInfo(taskId,option,81);
                    updateTableInfo(instanceId,option,81);
                    completeTask(taskId,option);
                }
            }
        } else if (dealType_.equals("313")) {
            option = "客服：" + owner + "在 " + formatDate + " 完成了人工任务上报催查件操作";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        }else if(dealType_.equals("311")){
            option = "客服：" + owner + "在 " + formatDate + " 完成了人工任务上报发件拦截";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        }else if(dealType_.equals("310")){
            option = "客服：" + owner + "在 " + formatDate + " 完成了人工任务上报派件拦截";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        }else if (dealType_.equals("312")) {
            option = "客服：" + owner + "在 " + formatDate + " 完成了人工任务登记问题件";
            updateJobList(taskId);
            updateDealInfo(taskId,option,81);
            updateTableInfo(instanceId,option,81);
            completeTask(taskId,option);
        }
    }





  private void completeTask(Long taskId,String option){
      TaskOpinion taskOpinion = new TaskOpinion(option);
      taskService.complete(taskId, taskOpinion);
  }


    private Long getInstance(Long id){
        ProblemDTO dto = new ProblemDTO();
        dto.setId(id);
        Problem problem = problemService.findOne(dto);
        return  problem.getProcessInstanceId();
    }


    private void updateDealInfo(Long taskId,String option,Integer result) {
        DealinfoDTO cond = new DealinfoDTO();
        cond.setAppdealTaskid(taskId);
        Dealinfo dealinfo = dealinfoService.findOne(cond);
        dealinfo.setAppdealContent(option);
        dealinfo.setAppdealDealstate(1);//修改后状态改回正常
        dealinfo.setAppdealResult(result);
        dealinfoService.updateIgnore(dealinfo);
    }

    private void updateTableInfo(Long instanceId,String option,Integer result) {
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_tabid(instanceId);
        TableInfo tableInfo = tableInfoService.findOne(cond);
        Long tableId = tableInfo.getId();
        cond.setId(tableId);
        tableInfo.setTableinfo_content(option);
        tableInfo.setTableinfo_result(result);
        tableInfoService.updateIgnore(cond);
    }


    private void updateJobList(Long taskId) {
        JobListDTO jobListDTO = new JobListDTO();
        jobListDTO.setJoblistTaskid(taskId);
        JobList jobList = jobListService.findOne(jobListDTO);
        Long id = jobList.getId();
        jobListDTO.setId(id);
        jobListDTO.setJoblistState(1);
        jobListService.updateIgnore(jobListDTO);
    }



    private void updateTableInfo_(Long instanceId,String picOne, String picTwo,
                                 String picThree, String paramDetail, String paramValue,
                                 String paramDate){
        TableInfoDTO tableInfoDTO = new TableInfoDTO();
        tableInfoDTO.setTableinfo_tabid(instanceId);
        TableInfo tableInfo = tableInfoService.findOne(tableInfoDTO);
        Long tableId = tableInfo.getId();
        tableInfoDTO.setId(tableId);
        tableInfoDTO.setTableinfo_varparamb(picOne);
        tableInfoDTO.setTableinfo_varparamc(picTwo);
        tableInfoDTO.setTableinfo_varparamd(picThree);
        tableInfoDTO.setTableinfo_varparame(paramDetail);
        tableInfoDTO.setTableinfo_varparamvalue(paramValue);
        tableInfoDTO.setTableinfo_varparamgdate(paramDate);
        tableInfoService.updateIgnore(tableInfoDTO);
    }




    /**
     * @Author: lishaomiao
     * @Date: 2018/3/14
     * @Description:图片上传
     * @throws IOException
     */
    @RequestMapping("pic")
    @ResponseBody
    public String getUploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        //检查是否传入图片
        if (multipartFile.isEmpty() || multipartFile.getBytes().length == 0) {
            String message="必须上传文件";
            logger.info("==============必须上传的图片===="+message);
            return "failNull";
        }
        //检查图片大小小于300KB
        if(checkSize(multipartFile)){
            String errorMsg="图片文件过大";
            logger.info("================上传的图片文件过大===="+errorMsg);
            return "failSize";
        }
        //创建文件夹
        String imgPath_ = decideSystem();
        time = TimeUtil.dateToString(new Date(),"yyyy-MM-dd");
        file = new File(imgPath_+time);
        if(!file.exists()){
            file.mkdirs();
            logger.info("============图片文件夹创建成功=============");
        }
        //存放的路径
        String pathUrl = picPath(imgPath_,time,multipartFile.getOriginalFilename());
        logger.info("存放图片的路径是======================"+pathUrl);
        // 存入图片
        file = new File(pathUrl);
        multipartFile.transferTo(file);
        return pathUrl;
    }

    /**
     * @Author: lishaomiao
     * @Date: 2018/8/31
     * @Description:判断当前系统给出路径
     * @return
     */
    private String decideSystem(){
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        String imgPath = null;
        if(StringUtils.isNotBlank(os)){
            if(os.toLowerCase().indexOf("linux") > -1){
                imgPath = File.separator+"root"+File.separator+"myJob"+File.separator;
            }else{
                imgPath = "D:\\img\\upload\\myJob\\";
            }
            return imgPath;
        }
        return null;
    }

    /**
     * @Author: lishaomiao
     * @Date: 2018/3/14
     * @Description:检验文件的大小
     * @return
     */
    private boolean checkSize(MultipartFile multipartFile){
        boolean flag = false;
        if(multipartFile.getSize()>Long.valueOf(1024 * PIC_SIZE)){
            flag = true;//图片尺寸大于规定尺寸，返回true
        }
        return flag;
    }


    /**
     * @Author: lishaomiao
     * 返回路径
     * @param path
     * @return
     */

    private String picPath(String path,String time,String multipartFile ){
        StringBuilder sb = new StringBuilder();
        sb.append(path).append(time).append(File.separator).append(multipartFile);
        logger.info("返回的图片路径是======================"+sb.toString());
        return sb.toString();
    }


    /**
     * @Author: lishaomiao
     * @Date: 2018/5/9
     * @Description:详情页
     * @param businessId
     * @return
     */
    @RequestMapping("jobId")
    public ExpressInfoRecord detail(@RequestParam("id") String businessId){
        if(StringUtils.isBlank(businessId)){
            return null;
        }
        Problem problem = problemService.get(Long.valueOf(businessId));
        Long expressNumber =problem.getProblemparts_expressnumber();
        if(expressNumber!=null){
            ExpressInfoRecordDTO expressInfoRecord = new ExpressInfoRecordDTO();
            expressInfoRecord.setExpress_number(String.valueOf(expressNumber));
            ExpressInfoRecord expressInfoRecords = expressInfoRecordService.findOne(expressInfoRecord);
            logger.info("======根据businessId查询ExpressInfoRecord成功=======");
            return expressInfoRecords;
        }
        return  null;
    }
}
