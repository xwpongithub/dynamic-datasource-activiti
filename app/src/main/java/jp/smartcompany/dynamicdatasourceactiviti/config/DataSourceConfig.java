package jp.smartcompany.dynamicdatasourceactiviti.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    AbstractJdbcDataSourceProvider jdbcDynamicDataSourceProvider() {
        return new AbstractJdbcDataSourceProvider("org.postgresql.Driver", "jdbc:postgresql://127.0.0.1:5432/dbmanager_test", "postgres", "123456") {
            @Override
            protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
                var map = new HashMap<String,DataSourceProperty>();
                var rs = statement.executeQuery(
                   """
                     select * from t_datasource
                   """);
                while (rs.next()) {
                    var name = rs.getString("name");
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var url = rs.getString("url");
                    var driver = rs.getString("driver");
                    var property = new DataSourceProperty();
                    property.setUsername(username);
                    property.setPassword(password);
                    property.setUrl(url);
                    property.setDriverClassName(driver);
                    map.put(name, property);
                }
                return map;
            }
        };
    }

    @Bean
    ProcessEngineConfigurationImpl processEngineConfiguration(TenantInfoHolder tenantInfoHolder, DataSource dataSource) {
        var processEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(tenantInfoHolder);
        processEngineConfiguration.setAsyncExecutorActivate(true);
        processEngineConfiguration.setAsyncExecutor(new ExecutorPerTenantAsyncExecutor(tenantInfoHolder));
        processEngineConfiguration.setDatabaseType(MultiSchemaMultiTenantProcessEngineConfiguration.DATABASE_TYPE_POSTGRES);
        processEngineConfiguration.setDatabaseSchemaUpdate(MultiSchemaMultiTenantProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        processEngineConfiguration.setIdGenerator(IdWorker::getIdStr);
        var ds = (DynamicRoutingDataSource) dataSource;
        ds.getDataSources().forEach(processEngineConfiguration::registerTenant);
//        processEngineConfiguration.getDbSqlSessionFactory().setIdGenerator(IdWorker::getIdStr);
        return processEngineConfiguration;
    }

}
