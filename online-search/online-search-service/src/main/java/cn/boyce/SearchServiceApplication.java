package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:31 2019/5/5
 **/
@EnableTransactionManagement
@SpringBootApplication
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

//    public static void main(String[] args) throws InterruptedException {
//        new SpringApplicationBuilder()
//                .sources(SearchServiceApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
//    }
}