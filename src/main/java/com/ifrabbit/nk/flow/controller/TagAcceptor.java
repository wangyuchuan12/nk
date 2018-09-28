package com.ifrabbit.nk.flow.controller;

import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import ir.nymph.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/7/14 11:14
 * @Description:
 */
public class TagAcceptor {
    @Autowired
    private VariableRepository variableRepository;
//    @GetMapping("posun")
    @Transactional
    public String get(HttpServletRequest request) {
        //拿到所有的数据
        //格式转换=>
        //机制:库中只有该通电话的所有参数
        //先取出,然后集合删除
        String queryString = request.getQueryString();
        String u_vendor = request.getParameter("U_vendor");
        TempVariable variable = new TempVariable();
        variable.setProcessId(Long.valueOf(u_vendor));
        List<TempVariable> list = variableRepository.findAll(variable);
        String[] params = queryString.split("&");
        List<String> paramsList = Arrays.asList(params);
        paramsList.forEach(paramsString->{
            list.forEach(tempVariable -> {
                if (paramsString.contains(tempVariable.getT_key())){
                    paramsList.remove(paramsString);
                }
            });
        });
        String message = "1";
        for (int i = 0;i<paramsList.size();i++ ) {
            String[] s = paramsList.get(i).split("=");
            switch (s[0]) {
                case "U_items":
                    message = this.check(s,u_vendor);
                    break;
                case "U_outside":
                    message = this.check(s,u_vendor);
                    break;
                case "U_inside":
                    message = this.check(s,u_vendor);
                    break;
                case "U_selflf":
                    message = this.check(s,u_vendor);
                    break;
                case "U_qianshou":
                    message = this.check(s,u_vendor);
                    break;
                case "U_change":
                    message = this.check(s,u_vendor);
                    break;
                case "U_line":
                    if ("0".equals(s[1])){
                        message = "0";
                    }else {
                        storegeData(s,u_vendor);
                    }
                    break;
                case "U_number":
                    storegeData(s,u_vendor);
                    break;
                case "U_date":
                    storegeData(s,u_vendor);
                    break;
                case "U_recall":
                    storegeData(s,u_vendor);
                    break;
                case "U_liuyan":
                    if (s[1].equals("1")){
                        storegeData(s,u_vendor);
                    }else{
                        message = "2";
                    }
                    break;
                case "U_return":
                    message = this.check(s,u_vendor);
                case "U_message":
                    switch (s[1]) {
                        //预置网点选择留言  返回1
                        case "1":
                            storegeData(s,u_vendor);
                            break;
                        //留言 返回 4
                        case "2":
                            storegeData(s,u_vendor);
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
        JSONObject json = new JSONObject();
        json.put("U_status", message);
        System.out.println(json.toString());
        return json.toString();
    }

    //电话按键容错
    private String  check(String[] s, String processInstanceId) {
        switch (s[1]) {
            case "1":
            case "2":
                storegeData(s,processInstanceId);
                return "1";
            case "3":
                return "3";
            case "4":
                return "4";
            case "0":
                return "0";
            default:
                return "2";
        }
    }
    //插入数据
    private void storegeData(String[] s,String processInstanceId){
        TempVariable variable = new TempVariable();
        variable.setProcessId(Long.valueOf(processInstanceId));
        variable.setT_key(s[0]);
        variable.setT_value(s[1]);
        variableRepository.insert(variable);
    }
}
