package com.ifrabbit.nk.reports.controller;

import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.html.HtmlReport;
import com.bstek.ureport.provider.report.file.FileReportProvider;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Authod: chenyu
 * @Date 2018/5/21 16:02
 * Content:
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ExportManager exportManager;

    @Autowired
    private FileReportProvider fileReportProvider;

    @Autowired
    private UserReportService userReportService;

    @GetMapping("test")
    String test(HttpServletRequest request, ModelMap model){
        Map<String, Object> parameters = request.getParameterMap().keySet().stream().collect(Collectors.toMap(k -> k, k -> request.getParameter(k)));
        HtmlReport htmlReport = exportManager.exportHtml(request.getParameter("_u"), "/", parameters);
        model.put("style",htmlReport.getStyle());
        model.put("content",htmlReport.getContent());
        model.put("file",request.getParameter("_u"));
        return "reports/test";
    }

    @GetMapping("testByCode")
    String testByCode(HttpServletRequest httpServletRequest,ModelMap model){
        String code = httpServletRequest.getParameter("code");
        return userReportService.getParameter(code);
    }

    /**
     * @author lishaomiao
     */
    @GetMapping("del")
     String detele(@RequestParam("fileName") String name){
        if(StringUtils.isNotBlank(name)){
            fileReportProvider.deleteReport(name);
            return  "ok";
        }
       return  null;
    }

}
