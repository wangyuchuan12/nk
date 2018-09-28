package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.TmanageBalanceService;
import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;


/**
 * @Author: lishaomiao
 * @Date: 2018/6/1
 * @Description:网点结算单
 */
@RestController
@RequestMapping("express/networkbalance")
public class TmanageBalanceController extends AbstractPageableController<TmanageBalanceService,TmanageBalance,TmanageBalanceDTO,Long>{
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TmanageBalanceController.class);
    @Autowired private CompanyService companyService;
    @Autowired private TmanageBalanceService tmanageBalanceService;



    /**
     * @Author: lishaomiao
     * @Date: 2018/6/5
     * @Description:记录网点数据
     * @param tmanageBalance
     * @return
     */
    @RequestMapping("tmanageBalance")
    public void TmanageBalance(TmanageBalance tmanageBalance) throws ParseException {
        //处理收款日期
        String date = tmanageBalance.getBalance_date();
        if(StringUtils.isNotBlank(date)){
            String date2 = TimeUtil.timePare(date);
            tmanageBalance.setBalance_date(date2);
        }
        //剩余应收款
        Integer balanceOwn = tmanageBalance.getBalance_own();
        //企业信用额
        Integer balanceCredit = tmanageBalance.getBalance_credit();
        //企业ID
        Integer companyId = tmanageBalance.getBalance_companyid();
            //剩余应收款大于企业信用额，企业状态Company_State变成冻结0
         if(balanceOwn != null && balanceCredit != null  && companyId != null ){
                if(balanceOwn > balanceCredit){
                    CompanyDTO companyDTO = new CompanyDTO();
                    Integer companyPaid = tmanageBalance.getBalance_amount()+tmanageBalance.getBalance_originalsum();//已经收款总金额
                    companyDTO.setCompany_paid(new BigDecimal(companyPaid));//已经收款总金额（本次收款+已收款总金额）
                    Integer companySum = tmanageBalance.getBalance_currentown()-tmanageBalance.getBalance_amount();//当前应收金额
                    companyDTO.setCompany_sum(new BigDecimal(companySum));//当前应收金额-本次收款金额
                    Integer credit = balanceOwn-balanceCredit;//剩余应收款减去企业信用额
                    companyDTO.setCompany_credit(new BigDecimal(credit));//网点当前信用额
                    companyDTO.setCompany_state(0);
                    companyDTO.setId(Long.valueOf(companyId));
                    companyService.updateIgnore(companyDTO);
                    logger.info("success================企业状态Company_State变成冻结0");
                }
            }
            tmanageBalance.setBalance_state(1);//网点状态为激活1
            tmanageBalanceService.insert(tmanageBalance);
            logger.info("success================网点结算单数据记录成功");
    }




    /**
     * @Author: lishaomiao
     * @Date: 2018/6/5
     * @Description:查询记录
     * @param dotName
     * @return
     */
    @RequestMapping("dot")
    @ResponseBody
    public Company check(@RequestParam("dotName") String dotName){
        if(StringUtils.isBlank(dotName)){
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        dto.setCompany_name(dotName);
        Company company = companyService.findOne(dto);
        logger.info("success================查询网点名称成功");
        return company;
    }

    /**
     * @Author: lishaomiao
     * @Date: 2018/6/12
     * @Description:查询表单编号
     * @param
     * @return
     */
    @RequestMapping("number")
    @ResponseBody
    public Long checkDotId(){
        TmanageBalanceDTO tmanageBalanceDTO = new TmanageBalanceDTO();
        Long number = tmanageBalanceService.countAll(tmanageBalanceDTO);
        logger.info("success================查询表单编号成功");
        return number;
    }


    /**
     * @Author: lishaomiao
     * @Date: 2018/6/12
     * @Description:历次收款记录
     * @param
     * @return
     */
    @RequestMapping("hisRecord")
    @ResponseBody
    public List<Map<String, Object>> hisRecord(@RequestParam("balanceCompanyname") String balanceCompanyName,
                                               @RequestParam("beginTime") String beginTime,
                                               @RequestParam("endTime") String endTime) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(balanceCompanyName)){
            map.put("balance_companyname",balanceCompanyName);
        }
        if(StringUtils.isNotBlank(beginTime)){
            map.put("beginTime", beginTime);
        }
        if(StringUtils.isNotBlank(endTime) ){
            map.put("endTime",endTime);
        }
        List<Map<String, Object>> tmanageBalance = this.tmanageBalanceService.findBalanceByParams(map);
        logger.info("success======================查询历次收款记录成功");
        return tmanageBalance;
    }



    /**
     * @Author: lishaomiao
     * @Date: 2018/6/25
     * @Description:查询网点名称是否存在
     * @param name
     * @return
     */
    @RequestMapping("checkNetWorkExit")
    @ResponseBody
    public String comapnyExit(@RequestParam("name") String name,@RequestParam(required = false) Long id){
        if(StringUtils.isNotBlank(name)){
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setCompany_name(name);
            Company company = companyService.findOne(companyDTO);
            if(company == null){
                return "ok";
            }
        }
        return  null;
    }



    /**
     * @Author: sunjiajian
     * @Date: 2018/6/13
     * @Description:
     * @param
     * @return
     */
    @RequestMapping("search")
    public List<String> search(@RequestParam("str") String str){
        List<String> list = new LinkedList<>();
        list = tmanageBalanceService.search(str);
        if(list==null){
            list.add("");
            return list;
        }else{
            return list;
        }
    }
}
