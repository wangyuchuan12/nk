package com.ifrabbit.nk.util.beanSax;

import com.ifrabbit.nk.flow.vo.TaskVo;
import com.ifrabbit.nk.flow.vo.UfloProcessVo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SAXParserHandler extends DefaultHandler{
    public static void main(String[]args)throws Exception{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        //锟斤拷锟斤拷SAXParserHandler锟斤拷锟斤拷
        SAXParserHandler handler = new SAXParserHandler(UfloProcessVo.class);
        parser.parse("e://BalanceMapper.xml", handler);
        UfloProcessVo target = (UfloProcessVo)handler.getTarget();
        System.out.println(target.getTasks());
    }

    private Class<?> beanType;
    private Object target;
    private Object parentTarget;
    private Class<?> thisBeanType;
    private Object thisTarget;
    private int isEnd = 0;
    public SAXParserHandler(Class<?> beanType){
        this.beanType = beanType;
    }
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        try{
            thisBeanType = beanType;
            thisTarget = thisBeanType.newInstance();
            target = thisBeanType.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public Object getTarget(){
        return target;
    }


    private void setValue(Field field,Object target,Object value){
        field.setAccessible(true);
        try {
            field.set(target,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void addListValue(Field field,Object target,Object value){
        field.setAccessible(true);
        try {
            List list = (List) field.get(target);
            if(list == null){
                list = new ArrayList();
            }
            list.add(value);
            field.set(target,list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);
        try {
            if(parentTarget!=null){
                Class<?> parentClass = parentTarget.getClass();
                for(Field field:parentClass.getDeclaredFields()){
                    SaxAttr saxAttr = field.getAnnotation(SaxAttr.class);
                    if(saxAttr!=null&&saxAttr.name().equals(qName)){
                        thisBeanType = field.getType();
                        if(List.class.isAssignableFrom(thisBeanType)){
                            Class<?> elementType = saxAttr.elementType();
                            thisTarget = elementType.newInstance();
                            thisBeanType = elementType;
                            addListValue(field,parentTarget,thisTarget);
                        }else {
                            thisTarget = thisBeanType.newInstance();
                        }

                    }
                }
                if(thisTarget==null){
                    return;
                }
                for(Field field:parentClass.getDeclaredFields()) {
                    SaxAttr saxAttr = field.getAnnotation(SaxAttr.class);
                    if (saxAttr != null && saxAttr.name().equals(qName)) {
                        if(List.class.isAssignableFrom(field.getType())){
                            /*System.out.println("进来了");
                            List list = (List)field.get(parentTarget);
                            list.add(thisTarget);
                            field.setAccessible(true);
                            field.set(parentTarget, list);*/
                        }else {
                            setValue(field,parentTarget,thisTarget);
                        }
                    }
                }
            }else{
                thisTarget = thisBeanType.newInstance();
                target = thisTarget;
            }


            Field[] fields = thisBeanType.getDeclaredFields();
            for(Field field:fields){
                SaxAttr saxAttr = field.getAnnotation(SaxAttr.class);
                if(saxAttr!=null) {
                    String value = attributes.getValue(saxAttr.name());
                    field.setAccessible(true);
                    field.set(thisTarget, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
