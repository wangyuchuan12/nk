package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.redisService.UserReportRedisService;
import com.ifrabbit.nk.usercenter.domain.UserReport;
import com.ifrabbit.nk.usercenter.domain.UserReportDTO;
import com.ifrabbit.nk.usercenter.repository.UserReportRepository;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Author: SunJiaJian
* @Description:
* @Date: Created in 10:52 2018/3/14
*
*/ 
@Service
public class UserReportServiceImpl
		extends AbstractCrudService<UserReportRepository, UserReport, Long> implements UserReportService {

	@Autowired
	private UserReportRedisService userReportRedisService;

	@Autowired
	private UserReportRepository userReportRepository;


	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserReportServiceImpl.class);
	@Autowired
	public UserReportServiceImpl(UserReportRepository repository) {
		super(repository);
	}

	@Override
	public void save(UserReport userReport) {

		super.save(userReport);
		userReportRedisService.doCache(userReport);
	}

	@Override
	public void saveIgnoreNull(UserReport userReport) {

		super.saveIgnoreNull(userReport);
		userReportRedisService.doCache(userReport);
	}

	@Override
	public void insert(UserReport userReport) {

		super.insert(userReport);
		userReportRedisService.doCache(userReport);
	}

	@Override
	public void update(UserReport userReport) {

		super.insert(userReport);
		userReportRedisService.doCache(userReport);
	}

	@Override
	public UserReport get(Long id){
		UserReport userReport = userReportRedisService.findOneByCache(id);
		if(userReport==null){
			userReport = getRepository().findOne(id);
			logger.debug("从数据库中获取id为{}的系统参数{}",id,userReport.getValue());
			userReportRedisService.doFindOneCache(userReport);
		}else{
			logger.debug("从缓存中获取id为{}的系统参数{}",id,userReport.getValue());
		}
		return userReport;
	}

	@Override
	public void delete(Long id){
		getRepository().delete(id);
		userReportRedisService.doDelCach(id);
	}


	@Override
	public void updateIgnore(UserReport userReport) {
		super.updateIgnore(userReport);
		userReportRedisService.doCache(userReport);
	}

	@Override
	public List<UserReport> findAll(){

		return getRepository().findAll();
		//暂时不缓存列表
		/*List<UserReport> userReports = userReportRedisService.findAllByCache();
		if(userReports!=null){
			return userReports;
		}else {
			userReports = getRepository().findAll();
			userReportRedisService.doSetFindAllCache(userReports);
			return userReports;
		}*/
	}

	@Override
	public List<UserReport> findByAnswer() { return userReportRepository.findByAnswer(); }

	@Override
	public List<UserReport> findByUserReport(Long mainId) { return userReportRepository.findByUserReport(mainId); }

	@Override
	public void deleteSysRLtn(Long mainId) { userReportRepository.deleteSysRLtnId(mainId); }


	@Override
	public String getParameter(String key) {
		UserReport userReport = userReportRedisService.findOneByCodeCache(key);
		if(userReport!=null){
			return userReport.getValue();
		}
		UserReportDTO cond = new UserReportDTO();
		cond.setCode(key);
		UserReport one = this.findOne(cond);

		logger.debug("从数据库中获取code为{}的userReport{}",key,one);
		//解决缓存击穿漏洞,假如有无意义的code注入，防止他访问数据库
		if(one==null){
			one = new UserReport();
			one.setCode(key);
			//userReportRedisService.doFindOneByCodeCache(one);
			return null;
		}else{
			userReportRedisService.doFindOneByCodeCache(one);
			return  one.getValue();
		}
	}

	@Override
	public void updateBycode(String value, String code) {
		UserReport userReport = userReportRedisService.findOneByCodeCache(code);
		userReport.setValue(value);
		userReportRedisService.doCache(userReport);
		repository.update(userReport);
	}
}
