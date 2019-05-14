package cn.boyce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:52 2019/5/8
 **/
@SpringBootApplication
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

//    public static void main(String[] args) throws InterruptedException {
//        new SpringApplicationBuilder()
//                .sources(CartServiceApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
//    }
}
