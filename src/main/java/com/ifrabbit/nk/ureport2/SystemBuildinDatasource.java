package com.ifrabbit.nk.ureport2;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.bstek.ureport.provider.report.ReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Authod: chenyu
 * @Date 2018/5/16 15:09
 * Content:
 */
@RestController
public class SystemBuildinDatasource implements BuildinDatasource{
    @Autowired
    private DataSource dataSource;
    public String name() {
        return "内置数据源demo";
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}