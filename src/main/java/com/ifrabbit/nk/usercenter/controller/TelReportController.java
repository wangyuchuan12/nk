package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.SysRltnService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Auther: lishaomiao
 */
@RestController
@RequestMapping("usercenter/systemtel")
public class TelReportController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TelReportController.class);

    @Autowired
    private SysRltnService sysRltnService;

    @Autowired
    private UserReportService userReportService;


    @GetMapping
     Page<UserReport> list(
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
            UserReportDTO condition) {
        Page<UserReport> page = userReportService.findAll(pageable, condition);
        if (page.hasContent()) {
            page.getContent().stream().forEach(userReport -> {
                List<UserReport> userReports = userReportService.findByUserReport(userReport.getId());
                userReport.setUserReports(userReports);
            });
        }
        return page;
    }


    /**
     * @param
     * @return
     * @Author: lishaomiao
     * 新增
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    void create(@RequestBody SysRltn sysRltn) {
        insertSysRltn(sysRltn);
    }



    /**
     * @param
     * @return
     * @Author: lishaomiao
     * 更新
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(@PathVariable("id") String id, @RequestBody SysRltn sysRltn) {
        userReportService.deleteSysRLtn(Long.valueOf(id));
        insertSysRltn(sysRltn);
    }



    /**
     * @param
     * @return
     * @Author: lishaomiao
     * 删除
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     void delete(@PathVariable("id") Long id) {
        userReportService.delete(id);
    }



    /**
     * @param answerName
     * @return
     * @Author: lishaomiao
     * 检验系统名称是否存在
     */
    @RequestMapping("check")
    public String check(@RequestParam("answerName") String answerName) {
        if(StringUtils.isNotBlank(answerName)){
            UserReport userReport = checkUserReport(answerName);
            if (userReport != null) {
                String selName = userReport.getName();
                if (answerName.equals(selName)) {
                    logger.info("系统名称已存在=====================");
                    return "ok";
                }
            }
        }
        return null;
    }

    @RequestMapping("checkAnswer")
    public List<UserReport> checkAnswer() {
        return userReportService.findByAnswer();
    }


    private UserReport checkUserReport(String name){
        UserReportDTO dto = new UserReportDTO();
        dto.setName(name);
        UserReport userReport = userReportService.findOne(dto);
        return userReport;
    }

    private void insertSysRltn(SysRltn sysRltn) {
        String[] str = sysRltn.getAnswer();
            for (int i = 0; i <= str.length; i++) {
                String answer = str[i];
                sysRltn.setSysRLtnState(1);
                sysRltn.setSysRLtnType(3);
                if (StringUtils.isNotBlank(answer)) {
                    UserReport userReport = checkUserReport(answer);
                    if (userReport != null) {
                        sysRltn.setSysRLtnMainId(userReport.getId());//答案的ID，mainId
                    }
                }
                String paraMc = sysRltn.getSysrltn_intparama();
                if (StringUtils.isNotBlank(paraMc)) {
                    UserReport userReport = checkUserReport(paraMc);
                    if (userReport != null) {
                        sysRltn.setSysRLtnAssistId(userReport.getId());//问题的ID，sysRLtnAssistId
                    }
                }
                sysRltnService.insert(sysRltn);
            }
    }
}
