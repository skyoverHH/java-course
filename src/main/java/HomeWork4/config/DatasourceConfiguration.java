package HomeWork4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DatasourceConfiguration {

    @Value("${datasource.url}")
    private String jdbcUrl;

    @Value("${datasource.schema}")
    private String schema;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setSchema(schema);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }
}