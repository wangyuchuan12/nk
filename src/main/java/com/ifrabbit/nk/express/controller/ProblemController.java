package com.ifrabbit.nk.express.controller;

import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.*;


import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.repository.CompanyRepository;
import com.ifrabbit.nk.usercenter.service.*;
import ir.nymph.date.DateTime;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: lishaomiao
 * @Date: 2018/3/14
 * @Description:上报问题件
 */
@RestController
@RequestMapping("/problem")
public class ProblemController extends AbstractPageableController<ProblemService,Problem,ProblemDTO,Long>{
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ProblemController.class);

    @Autowired private ProblemService problemService;
    @Autowired private ProcessService processService;
    @Autowired private TaskService taskService;
    @Autowired private ExpressInfoDetailService expressInfoDetailService;
    @Autowired private CompanyService companyService;
    @Autowired private StaffService staffService;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private ProblemRepository problemRepository;
    @Autowired private UserReportService userReportService;
    @Autowired private DealinfoService dealinfoService;
    @Autowired private SystemLogService systemLogService;
    @Autowired private TableInfoService tableInfoService;


    private final static int PIC_SIZE = 600;//图片大小
    private String time;//创建文件夹时间
    private File file;//文件夹


    /**
     * @Author: lishaomiao
     * @Date: 2018/3/14
     * 新增
     */
    @RequestMapping("/parts")
    @ResponseBody
//    @Transactional(readOnly = true)
    public String insertProblemParts(Problem problem) {
        if(null != problem){
            Long problemPartsExpressNumber = problem.getProblemparts_expressnumber();
            try{
                if(problemPartsExpressNumber == null){
                    if (JudgeSignNetwork(problemPartsExpressNumber)) return "fail";//未签约
                }
                if(checkNumber(problem.getProblemparts_expressnumber(),problem.getProblemparts_type()) == false){
                    return "exist";
                }
                UpdateStaff(problem);
                String result = CreateProblemFile(problem);//创建问题件
                if(result.equalsIgnoreCase("invalid")){
                    return "invalid";//如果返回值是 “失效”那就跳出
                }
            }catch (ProblemException e){
                SystemLog systemLog = new SystemLog();
                systemLog.setContent(e.toString());
                systemLog.setExpressNumber(String.valueOf(problemPartsExpressNumber));
                systemLog.setCreateTime(new DateTime().toString());
                systemLogService.insert(systemLog);
                logger.error("message=====上报问题件出现异常===",e);
            }catch (Exception e){
                SystemLog systemLog = new SystemLog();
                systemLog.setContent(e.toString());
                systemLog.setExpressNumber(String.valueOf(problemPartsExpressNumber));
                systemLog.setCreateTime(new DateTime().toString());
                systemLogService.insert(systemLog);
                logger.error("error=====上报问题件出现异常====================",e);
                e.printStackTrace();
            }
        }
        return  null;
    }
    //创建问题件
    private String CreateProblemFile(Problem problem) {
        problem.setProblemparts_createdate(TimeUtil.getTime(new Date()));//创建时间
        problem.setProblemparts_source(Constant.SYSTEM);//系统10
        problem.setProblemparts_uncollecteddays(0);
        problem.setProblemparts_callrecipientscount(0);
        problem.setProblemparts_callcompanycount(0);
        problem.setProblemparts_dutystate(0);
        problem.setProblemparts_obtainstate(0);
        problem.setProblemparts_tasktype(0);
        problemService.insert(problem);

        logger.info("=====================添加问题件成功====================");
        //开始启动流程
        Long id = problem.getId();
        insertDealInfo (id,"收件人姓名:"+problem.getProblemparts_receivename()+" 电话:"+problem.getProblemparts_receivephone()+" 地址:"+problem.getProblemparts_receiveaddress(),problem.getProblemparts_type());
        insertDealInfo (id,"内物:"+ problem.getProblemparts_insideitem(),problem.getProblemparts_type());
        if(problem.getProblemparts_type() == 3){
            insertDealInfo(id,"新收件人姓名:"+problem.getProblemparts_newreceivename()+" 新收件人电话:"+problem.getProblemparts_newreceivephone()+" 新收件人地址:"+problem.getProblemparts_newreceiveaddress(),problem.getProblemparts_type());
        }

        StartProcessInfo info = new StartProcessInfo(EnvironmentUtils.getEnvironment().getLoginUser());
        info.setBusinessId(String.valueOf(id));
        //判断网点状态
        // TODO: 2018/7/25 看这边是否还需要
//        String qymnwl = userReportService.getParameter("qymnwl");
//        if (!"true".equals(qymnwl))
//            expressInfoDetailService.parseJSON(String.valueOf(problem.getProblemparts_expressnumber()), id); //使用接口解析该运单号信息

        String qycxwdzt = userReportService.getParameter("qycxwdzt");
        if (!"true".equals(qycxwdzt)) {
            List<Problem> problems = problemRepository.findByNumber(id);
            int problemSize = problems.size() - 1;
            Problem problem1 = problems.get(problemSize);
            String problemparts_companyid = problem1.getProblemparts_companyid();
            List<Company> companyList = companyRepository.findById(problemparts_companyid);
            int companySize = companyList.size() - 1;
            Company company = companyList.get(companySize);
            Integer company_state = company.getCompany_state();
            //1=生效，9=失效，2=待审核，3=冻结。系统默认值=0
            if (company_state == 3 || company_state == 2 || company_state == 9) {
                /**
                 * 表单状态 10=待确认，20=审核不通过，1=流程正常结束，4=作废
                 */
                problem.setProblemparts_state(20);
                problemService.insert(problem);
                logger.warn("==============================网点状态为： 失效=========================");
                return "invalid";
            } else {
                logger.warn("==============================网点状态为： 正常，继续上报问题件=========================");
                return ReportProblem(problem, info);
            }
        }
        return ReportProblem(problem, info);
    }

    //重复单号校验
    @RequestMapping("checkType")
    @ResponseBody
    private Boolean checkNumber(Long expressNumber,Integer problemType){
        String ydhcfxjy = userReportService.getParameter("ydhcfxjy");
        if("true".equals(ydhcfxjy)){
            ProblemDTO pto = new ProblemDTO();
            pto.setProblemparts_expressnumber(expressNumber);
            pto.setProblemparts_type(problemType);
            System.out.print(pto);
            List<Problem> pbm = problemService.findAll(pto);
            if(pbm.size() == 0){
                return true;
            }
            return false;
        }
        return true;
    }


    private String ReportProblem(Problem problem, StartProcessInfo info) {
        ProcessInstance instance = new ProcessInstance();
        if (Constant.NOT_RECEIVED_RECEIPT.equals(String.valueOf(problem.getProblemparts_type()))) {
            String qswsdlc = userReportService.getParameter("QSWSDLC");
            instance = processService.startProcessByName(qswsdlc, info);
        } else if (Constant.BE_DAMAGED.equals(String.valueOf(problem.getProblemparts_type()))) {
            String pslc= userReportService.getParameter("PSLC");
            instance = processService.startProcessByName(pslc, info);
        } else if (Constant.MODIFY_ADDRESS.equals(String.valueOf(problem.getProblemparts_type()))) {
            String gdzlc = userReportService.getParameter("GDZLC");
            instance = processService.startProcessByName(gdzlc, info);
        } else if (Constant.BE_REMINDER.equals(String.valueOf(problem.getProblemparts_type()))) {
            String cdlc = userReportService.getParameter("CDLC");
            instance = processService.startProcessByName(cdlc, info);
        } else {
            String thlc = userReportService.getParameter("THLC");
            instance = processService.startProcessByName(thlc, info);
        }
        problem.setProcessInstanceId(instance.getId());
        service.saveIgnoreNull(problem);
        return "effective";
    }

    //更新用户的客户，内物，发件人
    private void UpdateStaff(Problem problem) {
        String staffName = problem.getProblemparts_staffname();
        String inSideItem =  problem.getProblemparts_insideitem();
        String sendName = problem.getProblemparts_sendname();
        String sendPhone = problem.getProblemparts_sendphone();
        String sendAdd = problem.getProblemparts_sendaddress();
        Staff staffInfo = staffService.getByUsername(staffName);
        String staffId = staffInfo.getId();
        StaffDTO staff = new StaffDTO();
        staff.setId(staffId);
        staff.setStaffNameId(staffName);
        staff.setStaffInsideItem(inSideItem);
        staff.setStaffSendName(sendName);
        staff.setStaffSendPhone(sendPhone);
        staff.setStaffSendAdd(sendAdd);
        staff.setStaffIntparama(1);
        logger.info("==============何处执行删除方法？=====================");
        staffService.updateIgnore(staff);
        logger.info("===========客户，内物，发件人同步保存到用户表里=============");
//        SysRltnDTO sysRltnDTO = new SysRltnDTO();
//        sysRltnDTO.setSysrltn_type(1);
//        sysRltnDTO.setSysrltn_mainid(1);
//        sysRltnDTO.setSysrltn_assistid(Integer.valueOf(staffId));
//        sysRltnDTO.setSysrltn_state(1);
//        sysRltnService.insert(sysRltnDTO);
//        logger.info("===========更新菜单栏=============");
    }

    //判断网点是否签约
    private boolean JudgeSignNetwork(Long problemparts_expressnumber) {
        ExpressInfoDetailDTO expressInfoDetailDTO = new ExpressInfoDetailDTO();
        expressInfoDetailDTO.setExpress_number(String.valueOf(problemparts_expressnumber));
        ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(1, String.valueOf(problemparts_expressnumber));
        List<Map<String, Object>> list = companyService.selectTelById(Long.valueOf(expressInfoDetail.getBegin_companyid()));
        Integer  Iscontract = (Integer) list.get(0).get("company_iscontract");//网点是否签约
        if (Iscontract != 1){
            logger.info("==================发件网点未签约====================");
            return true;
        }
        return false;
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
                imgPath = "img"+File.separator;
            }else{
                imgPath = "D:\\img\\upload\\";
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


    public void insertDealInfo (Long business_id_,String option,Integer problemPartsType) {
        Dealinfo dealinfo = new Dealinfo();
        dealinfo.setAppdealProblemid(business_id_);
        dealinfo.setAppdealDealstate(1);//state 0:未处理 1:已处理
        dealinfo.setAppdealContent(option);
//        dealinfo.setAppdealDealtype(0);
        dealinfo.setAppdealProblemtype(problemPartsType);
        dealinfo.setAppdealDealername("zhinengkefu");
        dealinfo.setAppdealVarparamb("0");
//        dealinfo.setAppdealRecorderid(taskId);
        dealinfo.setAppdealResult(10);
        dealinfo.setAppdealResulttext("初始记录添加");
        dealinfo.setAppdealDealcreatedate(new DateTime().toTimestamp());
        dealinfoService.insert(dealinfo);
    }


    /**
     * @Author: lishaomiao
     * @Date: 2018/5/9
     * @Description:详情页
     * @param businessId
     * @return
     */
    @RequestMapping("businessId")
    @ResponseBody
    public Problem detail(@RequestParam("id") Long businessId){
        if(businessId == null){
            return null;
        }
        Problem problem = problemService.getByProblemId(businessId);
        logger.info("======根据businessId查询详情页成功=======");
        return problem;
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
     * @param expressNum
     * @return
     */
    @RequestMapping("checkNum")
    public String checkExpressNum(@RequestParam("expressNum")Long expressNum,@RequestParam(required = false) Long id){
        if (expressNum == null){
            return null;
        }
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setProblemparts_expressnumber(expressNum);
        Problem problem = problemService.findOne(problemDTO);
        if(problem != null){
            if (null != id && id.equals(problem.getId())) {
                logger.info("id已经存在=====================");
                return "false";
            }
            Long expressnumber = problem.getProblemparts_expressnumber();
            if (expressNum.equals(expressnumber)) {
                logger.info("运单号已存在=====================");
                return "ok";
            }
        }
        return null;
    }


    /**
     * 根据用户名查询内物和当前发件人
     * @Author: lishaomiao
     */
    @RequestMapping("checkSome")
    public Staff check(@RequestParam("sendName")String sendName){
        if(StringUtils.isNotBlank(sendName)){
            StaffDTO dto = new StaffDTO();
            dto.setStaffSendName(sendName);
            dto.setStaffNameId(EnvironmentUtils.getEnvironment().getLoginUser());
            Staff staff = staffService.findOne(dto);
            if(staff!=null){
              return staff;
            }
        }
        return  null;
    }


    /**
     * 根据用户
     * @Author: lishaomiao
     */
    @RequestMapping("checkUser")
    public Staff checkUser(){
        StaffDTO dto = new StaffDTO();
        dto.setStaffUsername(EnvironmentUtils.getEnvironment().getLoginUser());
        Staff staff = staffService.findOne(dto);
        return staff;
    }



    /**
     * 查询问题件上报时间范围
     * @Author: lishaomiao
     * @param
     * @return
     */
    @RequestMapping("checkSystem")
    public String checkSystem(){return userReportService.getParameter("wtjsbsjfw");}


    /**
     * 根据用户名查询问题件总数量
     * @Author: lishaomiao
     * @param
     * @return
     */
    @RequestMapping("checkAll")
    @ResponseBody
    public List<Map<String, Object>> weekly(){
        Integer staffId = getStaffId();
        if(staffId!=null){
            List<Map<String, Object>> list = problemService.findCountNum(staffId);
            if(list.size()>0)return list;
        }
        return null;
    }

    /**
     * 根据用户名按天查询问题件总数量
     * @Author: lishaomiao
     * @param
     * @return
     */
    @RequestMapping("checkDay")
    @ResponseBody
    public List<Map<String, Object>> checkDay(){
        Integer staffId = getStaffId();
        if(staffId!=null){
            List<Map<String, Object>> list = problemService.findCountByDayTime(staffId);
            if(list.size()>0)return list;
        }
        return null;
     }

    /**
     * 根据用户名按月查询问题件总数量
     * @Author: lishaomiao
     * @param
     * @return
     */
    @RequestMapping("checkMonth")
    @ResponseBody
    public List<Map<String, Object>> checkMonth(){
        Integer staffId = getStaffId();
        if(staffId!=null){
            List<Map<String, Object>> list = problemService.findCountByDayMonth(staffId);
            if(list.size()>0) return list;
        }
        return null;
    }


    /**
     * 获取当前的用户ID
     */
      private int getStaffId(){
          Staff staff = staffService.getByUsername(EnvironmentUtils.getEnvironment().getLoginUser());
          return new Integer(staff.getId());//获取当前用户的staffID
      }





    /**
     * @Author: chenyu
     * @Date: 2018/4/25
     * @param pageable
     * @param condition
     * @return
     */
    @Override
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<Problem> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            ProblemDTO condition) {
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        Staff staff = staffService.getByUsername(loginUser);
        Integer staffId = Integer.valueOf(staff.getId());//获取当前用户的staffID
        Integer fuzzyTaskType = condition.getFuzzyTaskType();
        if(fuzzyTaskType == null){
            condition.setProblemparts_tasktype(0);
        }else if(fuzzyTaskType == 2){
            condition.setFuzzyTaskType(null);
        }
        if(!"admin".equals(loginUser)){
            condition.setProblemparts_staffid(staffId);
        }
        Page<Problem> list = super.list(pageable, condition);
        if (list.hasContent()) {
            list.forEach(problem -> {
                Long processInstanceId = problem.getProcessInstanceId();
                if (null != processInstanceId) {
                    TableInfo tableInfo = tableInfoService.findCurrentTask(String.valueOf(processInstanceId));
                    if(null != tableInfo){
                        Integer result = tableInfo.getTableinfo_result();
                        if(result!=null){
                            String node = String.valueOf(result);
                                if(node.equals("11")||node.equals("12")){
                                    problem.setProblemparts_currenttask("核实完收件人，下一步核实网点");
                                }else if(node.equals("21")||node.equals("22")){
                                    problem.setProblemparts_currenttask("核实完网点，下一步核实收件人");
                                }else{
                                    problem.setProblemparts_currenttask("未成功核实收件人，继续核实中");
                                }
                    }
                    List<Task> tasks = taskService.createTaskQuery()
                            .processInstanceId(problem.getProcessInstanceId())
                            .addTaskState(TaskState.Created).addTaskState(TaskState.Ready)
                            .assignee(EnvironmentUtils.getEnvironment().getLoginUser())
                            .businessId(String.valueOf(problem.getId())).list().stream()
                            .map(t -> {
                                Task task = new Task();
                                task.setId(t.getId());
                                task.setNodeName(t.getNodeName());
                                task.setAssignee(t.getAssignee());
                                task.setTaskName(t.getTaskName());
                                task.setUrl(t.getUrl());
                                task.setBusinessId(t.getBusinessId());
                                return task;
                            }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tasks)) {
                        problem.setTask(tasks.get(0));
                    }
                }
            }});
        }
        return list;
    }
}
