package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.service.JobListService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/21
 * Time:16:36
 */
@RestController
@RequestMapping("express/expressInfo")
public class ExpressInfoController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExpressInfoController.class);

    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private DealinfoService dealinfoService;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private JobListService jobListService;


    /**
     * @Auther: lishaomiao
     * @Date: 2018/7/31
     * @Description:查询expressInfo的派件网点
     */
    @RequestMapping("expressInfo")
    @ResponseBody
    public String detail(@RequestParam("businessId") String businessId) {
        if(StringUtils.isBlank(businessId)){
            return null;
        }
        Long expressNumber = getExpressNumber(businessId);
        if(expressNumber!=null){
            ExpressInfoDetailDTO dto = new ExpressInfoDetailDTO();
            dto.setExpress_number(String.valueOf(expressNumber));
            ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3, String.valueOf(expressNumber));
            logger.info("======根据businessId查询ExpressInfoDetail成功=======");
            return expressInfoDetail.getBegin_companyname();
        }
        return  null;
    }

    /**
     * @Auther: lishaomiao
     * @Date: 2018/7/31
     * @Description:查询expressInfo的派件时间
     */
    @RequestMapping("expressInfoTime")
    @ResponseBody
    public String detailByTime(@RequestParam("businessId") String businessId) {
        if(StringUtils.isBlank(businessId)){
            return null;
        }
        Long expressNumber = getExpressNumber(businessId);
        if(expressNumber!=null){
            ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(1, String.valueOf(expressNumber));
            logger.info("======根据businessId查询ExpressInfoDetail成功=======");
            return expressInfoDetail.getExpress_detaildate();
        }
        return null;
    }


    /**
     * @Auther: lishaomiao
     * @Date: 2018/8/3
     * @Description:查询需要核实的网点
     */
    @RequestMapping("expressInfoDot")
    @ResponseBody
    public String checkDot(@RequestParam("businessId") String businessId) {
        if(StringUtils.isBlank(businessId)){
            return null;
        }
        Long expressNumber = getExpressNumber(businessId);
        JobListDTO dto = new JobListDTO();
        dto.setJoblistProblemid(expressNumber);
        JobList jobList = jobListService.findOne(dto);
        Long taskId = null;
        if(jobList != null){
            taskId = jobList.getJoblistDealid();
        }
        String errorNetwork = null;
        if(StringUtils.isNotBlank(String.valueOf(expressNumber))){
            errorNetwork = expressInfoDetailService.expressAnalysis(new ExpressInfoRecordDTO(),String.valueOf(expressNumber),Long.valueOf(businessId),3,taskId);
        }
        logger.info("======根据businessId查询需要核实的网点成功=======");
        return  errorNetwork;
    }

    /**
     * 获取expressNumber运单号
     * @param businessId
     * @return
     */
    private Long getExpressNumber(String businessId){
        if(StringUtils.isNotBlank(businessId)){
            Problem problem = problemService.get(Long.valueOf(businessId));
            Long expressNumber = problem.getProblemparts_expressnumber();
            logger.info("======根据businessId查询运单号成功====="+expressNumber);
            return expressNumber;
        }
       return null;
    }


    /**
     * @Auther: lishaomiao
     * @Date: 2018/8/11
     * @Description:查询需要的留言
     */
    @RequestMapping("Leaving")
    @ResponseBody
    public Dealinfo findLeaving(@RequestParam("businessId") String businessId) {
        if(StringUtils.isNotBlank(businessId)){
            DealinfoDTO cond = new DealinfoDTO();
            cond.setAppdealTabid(Long.valueOf(businessId));
            cond.setAppdealDealstate(2);
            cond.setAppdealResult(8);
            Dealinfo dealinfo = dealinfoService.findOne(cond);
//            String content= one.getAppdealContent();
//            if(StringUtils.isNotBlank(content)) return content;
            return  dealinfo;
        }
        return null;
       }



    /**
     * @Auther: lishaomiao
     * @Date: 2018/9/6
     * @Description:查询需要的人工复制任务
     */
    @RequestMapping("copyContent")
    @ResponseBody
    public List<Dealinfo> copyContent(@RequestParam("businessId") String businessId) {
        if(StringUtils.isNotBlank(businessId)){
            DealinfoDTO dto = new DealinfoDTO();
            dto.setAppdealTabid(Long.valueOf(businessId));
            List<Dealinfo>  dealinfoList = dealinfoService.findAll(dto);
            if(dealinfoList.size()>0){
                return  dealinfoList;
            }
        }
        return null;
    }



    /**
     * @Auther: lishaomiao
     * @Date: 2018/8/3
     * @Description:查询需要核实的网点
     */
    @RequestMapping("picUrl")
    @ResponseBody
    public TableInfo findPicUrl(@RequestParam("businessId") String businessId) {
        if(StringUtils.isNotBlank(businessId)){
            TableInfoDTO tableInfoDTO = new TableInfoDTO();
            tableInfoDTO.setTableinfo_bussinessid(new Long(businessId));
            TableInfo tableInfo = tableInfoService.findOne(tableInfoDTO);
            if(tableInfo!=null) return  tableInfo;
        }
        return null;
    }

    /**
     * @Author: lishaomiao
     * 显示图片
     * @param
     * @return
     */
    @RequestMapping(value="toPic",produces="image/png")
    @ResponseBody
    public void toPic(HttpServletResponse response, @RequestParam("picSrc")String nameSrc) throws IOException {
        String name = URLDecoder.decode(nameSrc, "UTF-8");
        if(StringUtils.isBlank(name)){
            return;
        }
        File file = new File(name);
        FileInputStream fis;
        fis = new FileInputStream(file);
        long size = file.length();
        byte[] temp = new byte[(int) size];
        fis.read(temp, 0, (int) size);
        fis.close();
        byte[] data = temp;
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
    }


    //链接url下载图片
    @RequestMapping(value="downFile")
    public void downFile(@RequestParam("picSrc")String urlList,HttpServletRequest request,HttpServletResponse response) throws IOException {
        URL url = null;
        int imageNumber = 0;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String imageName =  "F:/test.jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

