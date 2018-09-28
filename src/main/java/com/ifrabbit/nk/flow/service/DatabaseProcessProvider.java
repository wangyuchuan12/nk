package com.ifrabbit.nk.flow.service;

import com.alibaba.druid.util.JdbcUtils;
import com.bstek.uflo.console.provider.ProcessFile;
import com.bstek.uflo.console.provider.ProcessProvider;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.service.ProcessService;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.DealType;
import com.ifrabbit.nk.express.domain.DealTypeDTO;
import com.ifrabbit.nk.express.service.DealTypeService;
import com.ifrabbit.nk.flow.vo.TaskVo;
import com.ifrabbit.nk.flow.vo.UfloProcessVo;
import com.ifrabbit.nk.flow.vo.UfloSubProcess;
import com.ifrabbit.nk.usercenter.domain.Menu;
import com.ifrabbit.nk.usercenter.domain.MenuDTO;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.service.MenuService;
import com.ifrabbit.nk.util.beanSax.SAXParserHandler;
import com.ifrabbit.nk.util.beanSax.UfloProcessSAXParserHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@EnableAspectJAutoProxy
@Component
@Slf4j
public class DatabaseProcessProvider implements ProcessProvider {

    public String prefix = "db:";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DealTypeService dealTypeService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;

    @Override
    public InputStream loadProcess(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }

        try {
            List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource,
                    "select content from uc_flow where name = ?", file);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }

            String content = (String) list.get(0).get("content");
            return new ByteArrayInputStream(content.getBytes());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<ProcessFile> loadAllProcesses() {

        try {
            List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource,
                    "select * from uc_flow order by updateDate desc");
            if (CollectionUtils.isEmpty(list)) {
                return Collections.emptyList();
            }

            return list.stream().map(m -> new ProcessFile((String) m.get("name"),
                    (Date) m.get("updateDate"))).collect(Collectors.toList());

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }


    private UfloProcessVo getProcess(String content){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            //锟斤拷锟斤拷SAXParserHandler锟斤拷锟斤拷
            UfloProcessSAXParserHandler handler = new UfloProcessSAXParserHandler();
            InputStream inputStream = new ByteArrayInputStream(content.getBytes());
            parser.parse(inputStream, handler);
            UfloProcessVo ufloProcess = handler.getTarget();
            return ufloProcess;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void saveToDealType(ProcessDefinition processDefinition){
        try {
            List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource,
                    "select uc.content from uc_flow uc where uc.name = ?", processDefinition.getName()+".uflo.xml");

            for(Map<String,Object> map:list){
                String content = (String)map.get("content");
                this.saveToDealType(content,processDefinition.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doSaveMenu(String content,Menu parentMenu,boolean isSub)throws Exception{
        UfloProcessVo ufloProcess = getProcess(content);
        MenuDTO menuDTO = new MenuDTO();
        if(!isSub) {
            menuDTO.setCode("flowMenu_" + ufloProcess.getName());
            Menu processMenu = menuService.findOne(menuDTO);
            if (processMenu == null) {
                processMenu = new Menu();
                processMenu.setPid(parentMenu.getId());
                processMenu.setName(ufloProcess.getName());
                processMenu.setCode("flowMenu_" + ufloProcess.getName());
                processMenu.setRgt(0L);
                processMenu.setLft(0L);
                processMenu.setIcon("menu");
                menuService.save(processMenu);
            }
            List<UfloSubProcess> ufloSubProcesses = ufloProcess.getUfloSubProcesses();
            for(UfloSubProcess ufloSubProcess:ufloSubProcesses){
                List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource,
                        "select uc.content from uc_flow uc where uc.name = ?", ufloSubProcess.getSubProcessName()+".uflo.xml");
                for(Map<String,Object> map:list){
                    String content2 = (String)map.get("content");
                    doSaveMenu(content2,processMenu,true);

                }
            }
            for(TaskVo taskVo:ufloProcess.getTasks()){
                menuDTO.setCode("flowMenu_"+ufloProcess.getName()+"_t_"+taskVo.getName());
                Menu taskMenu = menuService.findOne(menuDTO);
                if(taskMenu==null){
                    taskMenu = new Menu();
                    taskMenu.setCode(menuDTO.getCode());
                    taskMenu.setName(taskVo.getName());
                    taskMenu.setPid(processMenu.getId());
                    taskMenu.setRgt(0L);
                    taskMenu.setLft(0L);
                    menuService.save(taskMenu);
                }
            }
        }else{
            for(TaskVo taskVo:ufloProcess.getTasks()){
                menuDTO.setCode("flowMenu_"+parentMenu.getName()+"_s_"+taskVo.getName());
                Menu taskMenu = menuService.findOne(menuDTO);
                if(taskMenu==null){
                    taskMenu = new Menu();
                    taskMenu.setCode(menuDTO.getCode());
                    taskMenu.setName(taskVo.getName());
                    taskMenu.setPid(parentMenu.getId());
                    taskMenu.setRgt(0L);
                    taskMenu.setLft(0L);
                    menuService.save(taskMenu);
                }
            }
        }
    }

    private void saveToMenu(String content){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setCode("flowMenu");
        Menu menu = menuService.findOne(menuDTO);
        if(menu==null){
            menu = new Menu();
            menu.setName("流程菜单");
            menu.setCode("flowMenu");
            menu.setHidden(false);
            menu.setIcon("menu");
            menu.setRgt(0L);
            menu.setLft(0L);
            menu.setPid(0L);
            menuService.save(menu);
        }
        try {
            doSaveMenu(content, menu,false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //每一次部署都产生新的processId以及一份新的记录
    private void saveToDealType(String content,Long processId){
        UfloProcessVo ufloProcess = getProcess(content);
        List<TaskVo> taskVos = ufloProcess.getTasks();
        if(taskVos!=null) {
            for (TaskVo taskVo : taskVos) {
                DealType dealType = new DealType();
                dealType.setDealDealName(taskVo.getName());
                dealType.setDealState(DealType.VALID_STATE);
                dealType.setDealTabTypeId(processId);
                dealType.setDealTaskNo(0);
                dealType.setDealType(taskVo.getName());
                dealType.setDealTabname(ufloProcess.getName() + "-" + processId);
                dealTypeService.save(dealType);
            }
        }
    }


    @AfterReturning(value = "execution(public * com.bstek.uflo.service.impl.DefaultProcessService.deployProcess(..))",returning = "processDefinition")
    public void deployProcessAfter(JoinPoint joinPoint, ProcessDefinition processDefinition){
        this.saveToDealType(processDefinition);
    }


    @Override
    public void saveProcess(String file, String content) {
        System.out.println("............content:"+content);
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "update uc_flow set content=?,updateDate=? where name=?");
            ps.setString(1, content);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, file);
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                ps = conn.prepareStatement(
                        "insert into uc_flow(name,content,updateDate) values(?,?,?)");
                ps.setString(1, file);
                ps.setString(2, content);
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            }
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            JdbcUtils.close(ps);
            JdbcUtils.close(conn);
        }

    }

    @Override
    public void deleteProcess(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        try {
            JdbcUtils.execute(dataSource, "delete uc_flow where name=?", file);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return "数据库存储";
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean support(String fileName) {
        return fileName.startsWith(prefix);

    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
