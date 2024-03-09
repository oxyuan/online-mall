package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: oxyuan
 * @Date: Created in 18:07 2019/5/7
 **/
@SpringBootApplication
public class SSOServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSOServiceApplication.class, args);
    }

//    public static void main(String[] args) throws InterruptedException {
//        new SpringApplicationBuilder()
//                .sources(SSOServiceApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
//    }
}
