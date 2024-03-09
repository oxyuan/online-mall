package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @Author: oxyuan
 * @Date: Created in 18:27 2019/5/7
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SSOWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSOWebApplication.class, args);
    }
}
