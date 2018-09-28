package com.ifrabbit.nk.express.controller;

import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Messages;
import com.ifrabbit.nk.express.domain.MessagesDTO;
import com.ifrabbit.nk.express.service.MessagesService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: lishaomiao
 * @Date: 2018/7/26
 * @Description:
 */
@RestController
@RequestMapping("express/message")
public class MessagesController extends AbstractPageableController<MessagesService, Messages, MessagesDTO, Long> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    private MessagesService messageService;

    @GetMapping
    @Transactional(readOnly = true)
    protected Page<Messages> list(
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
            MessagesDTO condition) {
        String fuzzyType = condition.getFuzzyType();
        condition.setUser(EnvironmentUtils.getEnvironment().getLoginUser());
        if(fuzzyType != null){
            if(fuzzyType.equals("0")){
                condition.setType(0);
            }else{
                condition.setType(1);
            }
        }else{
            condition.setType(1);
        }
        return super.list(pageable, condition);
    }


    /**
     * @Auther: lishaomiao
     * @Date: 2018/7/26
     * @Description:查询消息数量
     */
    @RequestMapping("number")
    @ResponseBody
    public Long checkDotId(){
        MessagesDTO messageDTO = new MessagesDTO();
        messageDTO.setType(1);
        messageDTO.setUser(EnvironmentUtils.getEnvironment().getLoginUser());
        Long number = messageService.countAll(messageDTO);
        logger.info("success================查询未读消息数量成功");
        return number;
    }


    /**
     * @Auther: lishaomiao
     * @Date: 2018/7/26
     * @Description:查询消息
     */
    @RequestMapping("messagesInfo")
    @ResponseBody
    public Messages detail(@RequestParam("id") String id) {
        if(StringUtils.isNotBlank(id)){
            MessagesDTO dto = new MessagesDTO();
            dto.setId(Long.valueOf(id));
            Messages messages = messageService.findOne(dto);
            logger.info("success================查询未读消息成功");
            return  messages;
        }
        return null;
    }

    /**
     * @Auther: lishaomiao
     * @Date: 2018/7/26
     * @Description:更新消息
     */
    @RequestMapping("updateInfo")
    @ResponseBody
    public boolean updateInfo(@RequestParam("id") String id){
        boolean flag = false;
        if(StringUtils.isNotBlank(id)){
            MessagesDTO dto = new MessagesDTO();
            dto.setId(Long.valueOf(id));
            Messages messages = messageService.findOne(dto);
            if(messages.getType() == 0){
                return true;
            }
            dto.setType(0);
            messageService.updateIgnore(dto);
            logger.info("success================更新未读消息成功");
        }
        return  flag;
    }

}
