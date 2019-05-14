package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:52 2019/5/8
 **/
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


//    public static void main(String[] args) throws InterruptedException {
//        new SpringApplicationBuilder()
//                .sources(OrderServiceApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
//    }
}
