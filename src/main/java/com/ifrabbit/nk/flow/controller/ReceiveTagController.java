package com.ifrabbit.nk.flow.controller;

import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import ir.nymph.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/5/11 13:49
 * @Description:接收拨打电话回传的tag信息
 */
@RestController
@RequestMapping("phone")
public class ReceiveTagController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HandlerUtil.class);
    @Autowired
    private VariableRepository variableRepository;

    @GetMapping("posun")
    @Transactional
    public String get(HttpServletRequest request) {
        String queryString = request.getQueryString();
        System.out.println(queryString);
        String[] params = queryString.split("&");
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        String u_vendor = request.getParameter("U_vendor");
        TempVariable variable = new TempVariable();
        variable.setProcessId(Long.valueOf(u_vendor));
        List<TempVariable> list = variableRepository.findAll(variable);
        list.forEach(tempVariable -> {
            vars.put(tempVariable.getT_key(), tempVariable);
        });
        String message = "1";
        for (int i = 0; i < params.length; i++) {
            String[] s = params[i].split("=");
            switch (s[0]) {
                case "U_items":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_outside":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_inside":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_selflf":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_qianshou":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_change":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_request":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_asked":
                    message = this.check(vars, s, parameters);
                    break;
                case "U_line":
                    this.update(vars, s, parameters);
                    break;
                case "U_number":
                    this.update(vars, s, parameters);
                    break;
                case "U_data":
                    this.update(vars, s, parameters);
                    break;
                case "U_date":
                    this.update(vars, s, parameters);
                    break;
                case "U_recall":
                    this.update(vars, s, parameters);
                    break;
                case "U_liuyan":
                    if (s[1].equals("1")){
                        this.update(vars,s ,parameters);
                    }else{
                        message = "2";
                    }
                    break;
                case "U_return":
                     message = this.check(vars, s, parameters);
                case "U_message":
                    switch (s[1]) {
                        //预置网点选择留言  返回1
                        case "1":
                            this.update(vars,s,parameters);
                            break;
                        //留言 返回 4
                        case "2":
                            this.update(vars,s,parameters);
                            message="4";
                            break;
                        //重听  返回3
                        case "0":
                            message = "0";
                            break;
                        //其他错误选项返回
                        default: message = "2";
                    }
                    break;
            }
        }
        for (String key : parameters.keySet()) {
            variable.setT_key(key);
            variable.setT_value((String) parameters.get(key));
            variableRepository.insert(variable);
        }
        JSONObject json = new JSONObject();
        json.put("U_status", message);
        System.out.println(json.toString());
        return json.toString();
    }

    //电话按键容错
    private String  check(Map vars, String[] s, Map parameters) {
        switch (s[1]) {
            case "1":
                this.update(vars,s,parameters);
                return "1";
            case "2":
                this.update(vars,s,parameters);
                return "2";
            case "3":
                return "3";
            case "4":
                return "4";
            case "0":
                return "0";
            case "*":
                return "last";
            default:
                return "6";
        }
    }
    //数据库已存在该参数 即进行比较若有变动更改 否则保存
    private void update(Map vars, String[] s, Map parameters) {
        if (vars.keySet().contains(s[0])) {
            TempVariable temp = (TempVariable) vars.get(s[0]);
            if (!s[1].equals(temp.getT_value())) {
                temp.setT_value(s[1]);
                variableRepository.update(temp);
            }
        } else {
            parameters.put(s[0], s[1]);
        }
    }
    @GetMapping("tag2")
    @Transactional
    public String get2(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String[] params = queryString.split("&");
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        String u_vendor = request.getParameter("U_vendor");
        TempVariable variable = new TempVariable();
        variable.setProcessId(Long.valueOf(u_vendor));
        List<TempVariable> list = variableRepository.findAll(variable);
        list.forEach(tempVariable -> {
            vars.put(tempVariable.getT_key(), tempVariable);
        });
        String message = "1";
        for (int i = 0; i < params.length; i++) {
            String[] s = params[i].split("=");
            if ("U_qianshou".equals(s[0])){
                switch (s[1]) {
                    case "1":
                        this.update(vars,s,parameters);
                        break;
                    case "2":
                        message = "2";
                        this.update(vars,s,parameters);
                        break;
                    case "0":
                        message="0";
                        break;
                    case "4":
                        message="4";
                        break;
                    default:
                        message="3";
                        break;
                }
            }
        }
        for (String key : parameters.keySet()) {
            variable.setT_key(key);
            variable.setT_value((String) parameters.get(key));
            variableRepository.insert(variable);
        }
        JSONObject json = new JSONObject();
        json.put("U_status", message);
        System.out.println(json.toString());
        return json.toString();

    }

//        @GetMapping("posun")
        @Transactional
        public String posun(HttpServletRequest request) {
            //得到所有参数  遍历取值  插入
            //各流程可以共用部分
            //在同一通电话中 后面的请求 需要把先前的tag进行覆盖掉 处理方法: 先查出来 / 然后修改
            String u_vendor = request.getParameter("U_vendor");
            Map<String, Object> vars = new HashMap<String, Object>();
            Map<String, Object> parameters = new HashMap<String, Object>();
            String[] params = getParamsArray(request, vars);
            String message = "";
            for (int i = 0; i < params.length; i++) {
                String[] s = params[i].split("=");
                //根据不同的参数进行解析判断
                switch (s[0]) {
                    case "U_qianshou":
                        message = check(s, vars, parameters);
                        break;
                    case "U_selflf":
                        message = check(s, vars, parameters);
                        break;
                    case "U_items":
                        message = check(s, vars, parameters);
                        break;
                    case "U_outside":
                        message = check(s, vars, parameters);
                        break;
                    case "U_inside":
                        message = check(s, vars, parameters);
                        break;
                    case "U_message":
                        switch (s[1]) {
                            //预置网点选择留言  返回1
                            case "1": case"2": case"3":
                                if (!vars.keySet().contains(s[0])) {
                                    parameters.put(s[0], s[1]);
                                    message = "1";
                                }
                                break;
                            //留言 返回 4
                            case "4":
                                if (!vars.keySet().contains(s[0])) {
                                    parameters.put(s[0], s[1]);
                                    message="2";
                                }
                                break;
                            //重听  返回3
                            case "0":
                                message = "3";
                                break;
                            //其他错误选项返回
                            default: message = "4";
                        }
                        break;
                    case "U_line" :
                        switch (s[1]){
                            case "0":
                                message = "2";
                                break;
                            default:
                                parameters.put(s[0],s[1]);
                                message = "1";
                        }
                        break;
                }
            }
            inserteData(u_vendor,parameters);
            JSONObject json = new JSONObject();
            json.put("U_status", message);
            return json.toString();
        }
        //入库
        public void inserteData(String processInstanceId,Map<String, Object> parameters){
            TempVariable variable = new TempVariable();
            variable.setProcessId(Long.valueOf(processInstanceId));
            for (String key : parameters.keySet()) {
                variable.setT_key(key);
                variable.setT_value((String) parameters.get(key));
                variableRepository.insert(variable);
            }
        }
        //check 12  3  wrong
        private String check(String[] s,Map<String, Object> vars,Map<String, Object> parameters){
            String message = "";
            switch (s[1]){
                case "1" : case "2" :
                    //对其进行判断数据库 是否有  如果有就不变  没有 就插入
                    if (!vars.keySet().contains(s[0])) {
                        parameters.put(s[0], s[1]);
                        return message = "1";
                    }
                case "0":
                    return  message = "2";
                default:
                    return   message = "3";
            }
        }
        private String[] getParamsArray(HttpServletRequest request, Map<String, Object> vars){
            String queryString = request.getQueryString();
            String[] params = queryString.split("&");
            String u_vendor = request.getParameter("U_vendor");
            TempVariable variable = new TempVariable();
            variable.setProcessId(Long.valueOf(u_vendor));
            List<TempVariable> list = variableRepository.findAll(variable);
            list.forEach(tempVariable -> {
                vars.put(tempVariable.getT_key(), tempVariable);
            });
            return  params;
        }
    }
