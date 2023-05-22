package jp.smartcompany.dynamicdatasourceactiviti.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import jp.smartcompany.dynamicdatasourceactiviti.dto.DataSourceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Set;

@RestController
@RequestMapping("ds")
@RequiredArgsConstructor
public class DataSourceController {

    private final DataSource dataSource;
    private final DefaultDataSourceCreator dataSourceCreator;

    @GetMapping
    public Set<String> list() {
        var ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().keySet();
    }

    /**
     * {
     *     "poolName":"testNode1",
     *     "url":"jdbc:postgresql://127.0.0.1:5432/dynamic_datasource_db_01",
     *     "username":"postgres",
     *     "password":"123456"
     * }
     */
    @PostMapping
    public Set<String> add(@RequestBody DataSourceDTO dto) {
        var dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        var ds = (DynamicRoutingDataSource) dataSource;
        var dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPoolName(), dataSource);
        return ds.getDataSources().keySet();
    }


}
