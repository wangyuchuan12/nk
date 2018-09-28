package com.ifrabbit.nk.usercenter.service.impl;

import com.alibaba.druid.util.JdbcUtils;
import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.redisService.CompanyRedisService;
import com.ifrabbit.nk.redisService.RedisService;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.repository.CompanyRepository;
import com.ifrabbit.nk.usercenter.repository.CompanyRepository;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import ir.nymph.util.RandomUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CompanyServiceImpl
        extends   AbstractCrudService<CompanyRepository,Company,Long>
        implements CompanyService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private CompanyRedisService companyRedisService;

    @Autowired
    private RedisService redisService;

    @Autowired public CompanyServiceImpl(CompanyRepository repository) {
        super(repository);
    }

    private Company GetLay(Company entity){
//        String minLay="10000";
//        if(null==entity.getCompany_parentid())
//            entity.setCompany_parentid(0l);
//        String maxLayrec = getRepository().getMaxLayByParent(entity.getCompany_parentid());
//        if(maxLayrec != null) {
//            String layStart = maxLayrec.substring(0,(entity.getCompany_layno()-1)*5);
//            System.out.println("==========layStart=========="+layStart);
//            String layEnd = maxLayrec.substring((entity.getCompany_layno()-1)*5,maxLayrec.length());
//            System.out.println("==========layEnd=========="+layEnd);
//            entity.setCompany_layrec(layStart+(Integer.valueOf(layEnd)+1));
//        }
//        else {
//            entity.setCompany_layrec(entity.getCompany_parentlayrec()+minLay);
//        }
        //设置company_parentid和company_credit
        String  companyName = entity.getCompany_name();
        String  companyParama = entity.getCompany_varparama();
        BigDecimal originalCredit = entity.getCompany_originalcredit();
        if(companyName != null && companyParama !=null){
            CompanyDTO dto = new CompanyDTO();
            dto.setCompany_name(companyParama);
            Company company = companyService.findOne(dto);
            if(company!=null){
                 Long parenId = company.getId();
                 entity.setCompany_parentid(parenId);
            }
        }
        if(originalCredit != null){
            entity.setCompany_credit(originalCredit);
        }
        return entity;
    }

    @Override
    @Transactional public void insert(Company entity) {
        entity=GetLay(entity);
        entity.setCompany_code(RandomUtil.randomLong(100000000000l,999999999999l));
        String[] temp=entity.getCompanyRegion();
        for (int i=0;i<temp.length;i++){
            if(i==0)
                entity.setCompany_province(temp[i]);
            if(i==1)
                entity.setCompany_city(temp[i]);
            if(i==2)
                entity.setCompany_area(temp[i]);
        }
        super.insert(entity);

        companyRedisService.doCache(entity);
    }



    @Override
    @Transactional public void updateIgnore(Company entity) {
        Long companyId = entity.getId();
        if(companyId != null){
            entity.setId(companyId);
        }
        String[] temp=entity.getCompanyRegion();
        for (int i=0;i<temp.length;i++){
            if(i==0)
                entity.setCompany_province(temp[i]);
            if(i==1)
                entity.setCompany_city(temp[i]);
            if(i==2)
                entity.setCompany_area(temp[i]);
        }
        super.updateIgnore(entity);

        companyRedisService.doCache(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Company getByUsername(String username) {
        CompanyDTO cond = new CompanyDTO();
        cond.setCompany_name(username);
        return getRepository().findOne(cond);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> selectTelById(Long id) {
        List<Map<String, Object>> list = null;
        try {
            list = JdbcUtils.executeQuery(dataSource, "select * from tbase_company where company_id = ? ", id);
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> selcetAllColumnByCompanyid(Long id) {
        List<Map<String, Object>> list = null;
        try {
            list = JdbcUtils.executeQuery(dataSource, "select * from tbase_company where begin_companyid = ? ", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Company findOneByID(Long id) {
        //从缓存获取网点，如果没有再从数据库中获取，并且存入缓存
        Company company = companyRedisService.findOneByCache(id);
        if(company!=null){
            logger.debug("从缓存获取公司：{}",company);
            return company;
        }else {
            company = getRepository().findOne(id);
            if(company!=null) {
                companyRedisService.doFindOneCache(company);
                return company;
            }else{
                //解决缓存击穿漏洞，防止该id再次访问数据库
                company = new Company();
                company.setId(id);
                companyRedisService.doFindOneCache(company);
                return null;
            }
        }
    }



    @Override
    public List<Company> findByName(String companyName) {
        List<Company> list = companyRepository.findByName(companyName);
        return list;
    }

    @Override
    public List<Company> findAll() {
        List<Company> list = companyRepository.findAll();
        return list;
    }

    @Override
    public Company getCompany(String callBody, String expressNumber) {
        ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3,expressNumber);
        Company company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));
        switch (callBody){
            case "M2":case "M12":
                break;
            case "M3":case "M13":
                company = companyService.get(company.getCompany_parentid());
                break;
            case "M4":case "M14":
                Company n33 = companyService.get(company.getCompany_parentid());
                company = companyService.get(n33.getCompany_parentid());
                break;
            case "M5":case "M15":
                Company n333 = companyService.get(company.getCompany_parentid());
                Company n444 = companyService.get(n333.getCompany_parentid());
                company = companyService.get(n444.getCompany_parentid());
                break;
            case "M6":case "M16":
                Company n3333 = companyService.get(company.getCompany_parentid());
                Company n4444 = companyService.get(n3333.getCompany_parentid());
                Company n5555 = companyService.get(n4444.getCompany_parentid());
                company = companyService.get(n5555.getCompany_parentid());
                break;
        }
        return company;
    }

    @Override
    public void delete(Long id){
        companyRepository.delete(id);
        companyRedisService.doDelFindOneCache(id);
    }
}
