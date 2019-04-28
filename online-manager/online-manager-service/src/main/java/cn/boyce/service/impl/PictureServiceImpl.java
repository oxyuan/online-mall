package cn.boyce.service.impl;

import cn.boyce.format.PictureResult;
import cn.boyce.service.PictureService;
import cn.boyce.util.FastDFSClient;
import cn.boyce.util.IDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: Yuan Baiyu
 * @Date: Created in 11:44 2019/4/28
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @Override
    public PictureResult uploadFile(MultipartFile uploadFile) {
        PictureResult pictureResult = new PictureResult();
        try {
            //1、取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //2、创建一个 FastDFS 的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fastdfs-client.conf");
            //3、执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //4、拼接返回的 url 和 ip 地址，拼装成完整的 url
            String url = IMAGE_SERVER_URL + path;
            //5. 返回 PictureResult
            pictureResult.setError(0);
            pictureResult.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            // 5. 返回 PictureResult
            pictureResult.setError(1);
            pictureResult.setMessage("图片上传失败");
        }
        return pictureResult;
    }

    private String savePicture(MultipartFile uploadFile) {
        String result = null;
        try {
            // 上传文件功能实现
            // 判断文件是否为空
            if (uploadFile.isEmpty())
                return null;
            // 上传文件以日期为单位分开存放，可以提高图片的查询速度
            String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date()) + "/"
                    + new SimpleDateFormat("MM").format(new Date()) + "/"
                    + new SimpleDateFormat("dd").format(new Date());

            // 取原始文件名
            String originalFilename = uploadFile.getOriginalFilename();
            // 新文件名
            String newFileName = IDUtils.genImageName() + originalFilename.substring(originalFilename.lastIndexOf("."));
            // 转存文件，上传到ftp服务器
//            FtpUtil.uploadFile(FTP_SERVER_IP, FTP_SERVER_PORT, FTP_SERVER_USERNAME, FTP_SERVER_PASSWORD,
//                    FILI_UPLOAD_PATH, filePath, newFileName, uploadFile.getInputStream());
            result = filePath + "/" + newFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
