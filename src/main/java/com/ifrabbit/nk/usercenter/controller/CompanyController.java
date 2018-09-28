package com.ifrabbit.nk.usercenter.controller;

import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Author: lishaomiao
 * @Date: 2018/3/14
 * @Description:企业入口
 */
@RestController
@RequestMapping("usercenter/company")
public class CompanyController extends AbstractPageableController<CompanyService,Company,CompanyDTO, Long> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired private CompanyService companyService;


    /**
     * @param companyName
     * @return
     * Date:2018/4/20
     * @Author: lishaomiao
     * 检验公司名称是否重复
     */
    @RequestMapping("check")
    @ResponseBody
    public String check(@RequestParam("companyName") String companyName,@RequestParam(required = false) Long id) {
        Company company = companyService.getByUsername(companyName);
        if (company != null) {
            if (null != id && id.equals(company.getId())) {
                logger.info("id已经存在=====================");
                return "false";
            }
            String selCompanyName = company.getCompany_name();
            if (companyName.equals(selCompanyName)) {
                logger.info("网点名称已存在=====================");
                return "ok";
            }
        }
        return null;
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getParameterMap().keySet());
        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        System.out.println(".......................**********************************1111111111111111111111111");
        companyService.delete(id);
    }

    /**
     * @Author: lishaomiao
     * @Date: 2018/6/19
     * @Description:查询企业
     * @param companyName
     * @return
     */
    @RequestMapping("checkCompany")
    @ResponseBody
    public List<Company> check(@RequestParam("companyName") String companyName){
        if(StringUtils.isBlank(companyName)){
            return  null;
        }
        List<Company> company = companyService.findByName(companyName);
        logger.info("success================查询网点名称成功");
        return company;
    }

    /**
     * @Author: lishaomiao
     * @Date: 2018/6/19
     * @Description:查询企业名字是否存在
     * @param name
     * @return
     */
    @RequestMapping("checkCompanyExit")
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
     * @Author: lishaomiao
     * @Date: 2018/7/3
     * @param pageable
     * @param condition
     * @return
     */
    @Override
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<Company> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            CompanyDTO condition) {
        String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
        String[] companyRegion = condition.getFuzzyCompanyRegion();
        if(loginUser != null){
            if(!"admin".equals(loginUser)){
                condition.setCompany_adddate(null);
                condition.setCompany_parentid(null);
                condition.setCompany_layno(null);
                condition.setCompany_state(null);
                if(companyRegion!=null && companyRegion.length>0 ){
                    condition.setCompany_province(companyRegion[0]);
                    condition.setCompany_city(companyRegion[1]);
                }
                condition.setCompany_varparamb(loginUser);
            }
           if(companyRegion!=null && companyRegion.length>0 ){
               condition.setCompany_province(companyRegion[0]);
               condition.setCompany_city(companyRegion[1]);
           }
        }
        Page<Company> list = super.list(pageable, condition);
        return list;
    }
}
