package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.TableInfo;
import org.springframework.data.support.CrudService;

/**
 * @Auther: WangYan
 * @Date: 2018/7/5 11:27
 * @Description:
 */
public interface TableInfoService  extends CrudService<TableInfo,Long > {
    void updateTabResult(Long processInstanceId,Integer result);

    TableInfo findCurrentTask(String processId);
    String findReceicerResult(String nodeName,Long upTablId);
}
