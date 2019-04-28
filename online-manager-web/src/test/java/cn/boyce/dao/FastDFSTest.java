package cn.boyce.dao;

import cn.boyce.util.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 20:23 2019/4/26
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("D:\\IntelliJ_Idea-Pro\\onlineMalls\\online-manager-web\\src\\main\\resources\\conf\\fastdfs-client.conf");
        String file = fastDFSClient.uploadFile("E:\\Pictures\\ScreenToGif\\7fd54a81ly1fkgg00wn2hg208g04r7wi.gif");
        System.out.println(file);
    }

}
