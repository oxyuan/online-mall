package cn.boyce.common.format;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 22:39 2019/4/20
 **/
@Data
public class EasyUITreeNode implements Serializable {

    private static final long serialVersionUID = -4813361542496370884L;

    private long id;

    private String text;

    private String state;

}
