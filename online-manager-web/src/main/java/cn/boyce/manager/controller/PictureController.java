package cn.boyce.manager.controller;

import cn.boyce.common.util.FastDFSClient;
import cn.boyce.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 21:33 2019/4/27
 **/
@RestController
@RequestMapping("/pic")
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public String fileUpload(MultipartFile uploadFile) {
        try {
            //1、取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : null;
            //2、创建一个 FastDFS 的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fastdfs-client.conf");
            //3、执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //4、拼接返回的 url 和 ip 地址，拼装成完整的 url
            String url = IMAGE_SERVER_URL + path;
            //5、返回 map
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            //5、返回 map
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", " 图片上传失败 ");
            //手动转化为 json 数据
            return JsonUtils.objectToJson(result);
        }
    }
}