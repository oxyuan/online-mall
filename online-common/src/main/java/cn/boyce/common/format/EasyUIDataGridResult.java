package cn.boyce.common.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 11:00 2019/4/21
 **/
@Data
public class EasyUIDataGridResult implements Serializable {

    private Long total;

    private List<?> rows;
}
