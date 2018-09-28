package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.domain.DealinfoDTO;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.usercenter.domain.AppProblemparts;
import com.ifrabbit.nk.usercenter.domain.AppProblempartsDTO;
import com.ifrabbit.nk.usercenter.service.AppProblempartsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("usercenter/appproblemparts")
public class AppProblempartsController
		extends AbstractPageableController<AppProblempartsService, AppProblemparts, AppProblempartsDTO, Long> {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AppProblempartsController.class);


	@Autowired
	private DealinfoService dealinfoService;




	/**
	 * @Auther: lishaomiao
	 * @param processId
	 * @return
	 * @Description: 流程弹窗详情
	 *
	 */
	@RequestMapping("processId")
	public List<Dealinfo> detail(@RequestParam("id") String processId) {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		DealinfoDTO dto = new DealinfoDTO();
		dto.setAppdealTabid(Long.valueOf(processId));
		Dealinfo dealinfo = dealinfoService.findOne(dto);
		if(dealinfo!=null){
			List<Dealinfo> list  = dealinfoService.findTaskDetail(String.valueOf(dealinfo.getAppdealTabletype()));
			if(list.size()>0){
				list.forEach(dealInfo -> {
					String record = dealInfo.getAppdealRecord();
					Integer result = dealInfo.getAppdealResult();
					String type = record.substring(record.length()-2,record.length());
				    if(StringUtils.isNotBlank(record)&&StringUtils.isNotBlank(String.valueOf(result))){
						if(type.equals("M2")||type.equals("M3")||type.equals("M4")||type.equals("M5")){
                            dealInfo.setAppdealVarparamc("已拨打网点");
						}else if(type.equals("M1")&&"11".equals(String.valueOf(result))){
                            dealInfo.setAppdealVarparamc("拨打收件人,已核实");
						}else if(type.contains("M1")&&"12".equals(String.valueOf(result))){
                            dealInfo.setAppdealVarparamc("拨打收件人，未核实");
						}
					}
				});
			}
			return list;
		}
		return  null;
	}
}
