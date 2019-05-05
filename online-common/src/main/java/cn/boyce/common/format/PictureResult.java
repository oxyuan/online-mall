package cn.boyce.common.format;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 22:20 2019/4/27
 **/
@Data
public class PictureResult implements Serializable {

    private static final long serialVersionUID = -4813361542496370884L;

    private int error;

    private String url;

    private String message;

}
