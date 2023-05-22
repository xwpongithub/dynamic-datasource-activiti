package jp.smartcompany.dynamicdatasourceactiviti.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    AbstractJdbcDataSourceProvider dynamicDataSourceProvider() {
        return  new AbstractJdbcDataSourceProvider("org.postgresql.Driver", "jdbc:postgresql://127.0.0.1:5432/dynamic_ds_master", "postgres", "123456") {
            @Override
            protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
                var map = new HashMap<String,DataSourceProperty>();
                var rs = statement.executeQuery(
                   """
                     select * from m_customer
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

//    @Bean
//    ProcessEngineConfigurationImpl processEngineConfiguration(TenantInfoHolder tenantInfoHolder, DataSource dataSource) {
//        var processEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(tenantInfoHolder);
//        processEngineConfiguration.setAsyncExecutorActivate(true);
//        processEngineConfiguration.setAsyncExecutor(new ExecutorPerTenantAsyncExecutor(tenantInfoHolder));
//        processEngineConfiguration.setDatabaseType(MultiSchemaMultiTenantProcessEngineConfiguration.DATABASE_TYPE_POSTGRES);
//        processEngineConfiguration.setDatabaseSchemaUpdate(MultiSchemaMultiTenantProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
//        processEngineConfiguration.setIdGenerator(IdWorker::getIdStr);
//        var ds = (DynamicRoutingDataSource) dataSource;
//        ds.getDataSources().forEach(processEngineConfiguration::registerTenant);
////        processEngineConfiguration.getDbSqlSessionFactory().setIdGenerator(IdWorker::getIdStr);
//        return processEngineConfiguration;
//    }

}
