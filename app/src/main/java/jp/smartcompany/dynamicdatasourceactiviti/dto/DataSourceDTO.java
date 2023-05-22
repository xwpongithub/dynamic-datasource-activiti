package jp.smartcompany.dynamicdatasourceactiviti.dto;

import lombok.Data;

@Data
public class DataSourceDTO {

    /**
     * JDBC driver org.h2.Driver
     */
    private String driverClassName = "org.postgresql.Driver";

    /**
     * 连接池名称
     */
    private String poolName;
    /**
     * JDBC url 地址
     */
    private String url;

    /**
     * JDBC 用户名
     */
    private String username;

    /**
     * JDBC 密码
     */
    private String password;

}
