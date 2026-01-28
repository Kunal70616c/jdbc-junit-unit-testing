package sh.surge.kunal.banking.configurations;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
// ComponentScan is used to scan the packages for the components
@ComponentScan(basePackages = "sh.surge.kunal.banking")
// AspectJAutoProxy is used to enable aspectJ auto proxy
// what is a proxy?
// Proxy is an object that represents another object
// Proxy is used to control access to the object
// Proxy is used to add additional functionality to the object
// Proxy is used to implement the same interface as the object
@EnableAspectJAutoProxy
// PropertySource is used to load the properties from the application.properties file
@PropertySource("classpath:application.properties")
@Data
public class AppConfig {
    @Value("${url}") // @value loads url from the application.properties file
    private String url;
    @Value("${mysqlusername}") // Takes mysqlusername from application.properties file
    private String userName;
    @Value("${mysqlpassword}") // Takes mysqlpassword from application.properties file
    private String password;
    @Value("${driver-class-name}") // Takes driver-class-name from application.properties file
    private String driverClassName;

    @Bean // @Bean is used to create a bean
    // DataSource is used to connect to the database
    // HikariDataSource is a high performance JDBC connection pool
    // Connections pool is a pool of database connections
    // Connection pool is used to manage the connections to the database
    public HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        // Setting JDBC URL
        dataSource.setJdbcUrl(url);
        // Setting JDBC UserName
        dataSource.setUsername(userName);
        // Setting JDBC Password
        dataSource.setPassword(password);
        // Setting JDBC Driver Class Name
        dataSource.setDriverClassName(driverClassName);
        // Setting Maximum Pool Size
        // Pool Size means number of connections that can be created
         dataSource.setMaximumPoolSize(10);
        return dataSource;

    }

    @Bean
    // JDBC Template is used to execute SQL queries
    // It is used to perform database operations
    public JdbcTemplate getJdbcTemplate(HikariDataSource dataSource) {
        // Returns a new JdbcTemplate instance
        return new JdbcTemplate(dataSource);
    }
}
