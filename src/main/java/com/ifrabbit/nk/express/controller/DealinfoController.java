package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.domain.DealinfoDTO;
import com.ifrabbit.nk.express.service.DealinfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("express/dealinfo")
public class DealinfoController extends AbstractPageableController<DealinfoService, Dealinfo, DealinfoDTO, Long> {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(DealinfoController.class);

	@Autowired
	private DealinfoService dealinfoService;


	@RequestMapping("businessId")
	@ResponseBody
	public List<Dealinfo> detail(@RequestParam("appDealTabId") Long businessId) {
		if (businessId == null) {
			return null;
		}
		DealinfoDTO dealinfoDTO = new DealinfoDTO();
		dealinfoDTO.setAppdealProblemid(businessId);
		List<Dealinfo> dealinfos = dealinfoService.findAll(dealinfoDTO);

		Iterator<Dealinfo> iterator = dealinfos.iterator();
		while(iterator.hasNext()){
			Dealinfo next = iterator.next();
			Integer appdealResult = next.getAppdealResult();
			if(appdealResult == 7 || appdealResult == 4 || appdealResult == 0){
				iterator.remove();
			}
		}
		logger.info("==================查询成功====================");
		return dealinfos;
	}



	@RequestMapping("detail")
	@ResponseBody
	public List<Dealinfo> copyOfDetail(@RequestParam("appDealTabId") Long businessId,@RequestParam("taskId")Long taskId) {
		if (businessId == null) {
			return null;
		}
		//根据前端传过来的taskId和businessId来过滤
		DealinfoDTO dealinfoDTO = new DealinfoDTO();
		dealinfoDTO.setAppdealProblemid(businessId);
		dealinfoDTO.setAppdealTaskid(taskId);
		List<Dealinfo> dealinfos = dealinfoService.findAll(dealinfoDTO);

		Iterator<Dealinfo> iterator = dealinfos.iterator();
		while(iterator.hasNext()){
			Dealinfo next = iterator.next();
			Integer appdealResult = next.getAppdealResult();
			if(appdealResult == 7 || appdealResult == 4 || appdealResult == 0 || appdealResult == 3){
				iterator.remove();
			}
		}
		logger.info("==================查询成功====================");
		return dealinfos;
	}
}
