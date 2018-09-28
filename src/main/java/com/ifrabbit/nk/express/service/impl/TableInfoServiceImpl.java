package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.repository.TableInfoRepository;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

/**
 * @Auther: WangYan
 * @Date: 2018/7/5 11:32
 * @Description:
 */
@Service
public class TableInfoServiceImpl extends AbstractCrudService<TableInfoRepository,TableInfo,Long>
            implements TableInfoService{

    @Autowired
    private TableInfoRepository tableInfoRepository;


    @Autowired public TableInfoServiceImpl(TableInfoRepository repository) {
        super(repository);
    }
    @Override
    public void updateTabResult(Long processInstanceId,Integer result){
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_tabid(processInstanceId);
        TableInfo son = repository.findOne(cond);
        son.setTableinfo_result(result);
        repository.update(son);
        cond.setTableinfo_tabid(son.getTableinfo_tabid());
        cond.setTableinfo_result(null);
        TableInfo main = repository.findOne(cond);
        main.setTableinfo_result(result);
        repository.update(main);
    }

    @Override
    public TableInfo findCurrentTask(String processId) { return tableInfoRepository.findCurrentTask(processId); }

    @Override
    public String findReceicerResult(String nodeName, Long upTablId) {
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_dealname(nodeName);
        cond.setTableinfo_uptabid(upTablId);
        TableInfo receicerTableInfo = this.findOne(cond);
        String param = receicerTableInfo.getTableinfo_varparama();
        StringBuffer words = new StringBuffer();
        words.append(param.charAt(0) == '1'?"外包装完好,":"外包装破损,");
        words.append(param.charAt(1) == '1'?"内物完好,":"内物破损,");
        words.append(param.charAt(2) == '1'?"本人签收,":"非本人签收,");
        words.append(param.charAt(3) == '1'?"主动要求快递员放在其他地方,":"未主动要求快递员放在其他地方,");
        words.append(param.charAt(4) == '1'?"委托他人签收,":"未委托他人签收,");
        return  words.toString();
    }
}
