package cn.boyce.service;

import cn.boyce.format.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 11:43 2019/4/28
 **/
public interface PictureService {

    PictureResult uploadFile(MultipartFile uploadFile) throws Exception;
}
