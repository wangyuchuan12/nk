package com.ifrabbit.nk.util.beanSax;
import com.ifrabbit.nk.flow.vo.StartVo;
import com.ifrabbit.nk.flow.vo.TaskVo;
import com.ifrabbit.nk.flow.vo.UfloProcessVo;
import com.ifrabbit.nk.flow.vo.UfloSubProcess;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UfloProcessSAXParserHandler extends DefaultHandler {
    public static void main(String[]args)throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        //锟斤拷锟斤拷SAXParserHandler锟斤拷锟斤拷
        UfloProcessSAXParserHandler handler = new UfloProcessSAXParserHandler();
        parser.parse("e://BalanceMapper.xml", handler);
        UfloProcessVo target = (UfloProcessVo)handler.getTarget();
        System.out.println(target.getTasks());
    }
    private UfloProcessVo target;
    private TaskVo thisTask;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public UfloProcessVo getTarget(){
        return target;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        try {
            if (qName.equals("uflo-process")) {
                target = UfloProcessVo.class.newInstance();
                target.setName(attributes.getValue("name"));
            }else if(qName.equals("start")){
                StartVo startVo = new StartVo();
                startVo.setName(attributes.getValue("name"));
                target.setStart(startVo);
            }else if(qName.equals("task")){
                List<TaskVo> tasks = target.getTasks();
                if(tasks==null){
                    tasks = new ArrayList<>();
                }
                TaskVo taskVo = new TaskVo();
                taskVo.setName(attributes.getValue("name"));
                tasks.add(taskVo);
                target.setTasks(tasks);
                this.thisTask = taskVo;
            }else if(qName.equals("subprocess")){
                List<UfloSubProcess> subProcesses = target.getUfloSubProcesses();
                if(subProcesses==null){
                    subProcesses = new ArrayList<>();
                }
                UfloSubProcess subProcess = new UfloSubProcess();
                subProcess.setName(attributes.getValue("name"));
                subProcess.setSubProcessName(attributes.getValue("subprocess-name"));
                subProcesses.add(subProcess);
                target.setUfloSubProcesses(subProcesses);
            }else if(qName.equals("user-data")){
                Map<String,String> map = thisTask.getParamMap();
                if(map==null){
                    map = new HashMap<>();
                }
                map.put(attributes.getValue("key"),attributes.getValue("value"));
                thisTask.setParamMap(map);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
