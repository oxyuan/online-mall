package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:58 2019/5/8
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CartWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartWebApplication.class, args);
    }
}
