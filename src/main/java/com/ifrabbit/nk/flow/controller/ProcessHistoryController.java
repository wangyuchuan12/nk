package com.ifrabbit.nk.flow.controller;

import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.domain.DealinfoDTO;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.express.utils.SortClass;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/5/2 18:53
 * @Description:
 */
@RestController
@RequestMapping("flow/processHistoryInstance")
public class ProcessHistoryController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessHistoryController.class);
    private final static String url = "https://open.duyansoft.com/api/v1/call/recording?apikey=bBjhoF3DdLWYm5buafF0Fi4Gv5a0aGtA&call_uuid=";
    private Player player;
    @Autowired
    private DealinfoService dealinfoService;

    @Autowired
    private TableInfoService tableInfoService;


    @GetMapping
    @Transactional(readOnly = true)
    protected Page<TableInfo> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            TableInfoDTO condition) {
        return tableInfoService.findAll(pageable, condition);
    }




    /**
     * @Auther: lishaomiao
     * @param dealInfoId
     * @return
     * @Description: dealInfo弹窗详情
     */
    @RequestMapping("dealInfoId")
    @ResponseBody
    public List<Dealinfo> detail(@RequestParam("id") Long dealInfoId) {
        if (dealInfoId == null) {
            return null;
        }
        DealinfoDTO dto = new DealinfoDTO();
        dto.setAppdealTabid(dealInfoId);
        List<Dealinfo> dealInfo = this.dealinfoService.findAll(dto);
        SortClass sort = new SortClass();
        Collections.sort(dealInfo,sort);//按照时间和ID排序
        logger.info("==================查询dealInfo信息成功====================");
        return dealInfo;
    }



    /**
     * @Auther: lishaomiao
     * @param
     * @return
     * @Description: 音乐播放
     */
    @RequestMapping("audio")
    @ResponseBody
    public void music(@RequestParam("hisid") String id,@RequestParam("processInstanceId") String processInstanceId,@RequestParam("type") String type) throws FileNotFoundException, JavaLayerException {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(type)){
            return;
        }
        if(type.equals("1")){
//            List<HistoryTask> historyTasks = historyService.getHistoryTasks(Long.valueOf(processInstanceId));
//            for (HistoryTask historyTask :historyTasks ) {
//                if (historyTask.getId() == Long.valueOf(id)){
//                    HandlerUtil.sendPhone(url, historyTask.getSubject()))
//                    File music = new File("https://resource.duyansoft.com/recording/100176/2018-06-27/9f3cdce4-04cf-4c56-a926-52dc21a12b93.oga");
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("https://resource.duyansoft.com/recording/100176/2018-06-27/9f3cdce4-04cf-4c56-a926-52dc21a12b93.oga"));
            player = new Player(buffer);
            player.play();
//                }
//            }
        }else if (type.equals("2")){
            player.close();
        }
    }

}






