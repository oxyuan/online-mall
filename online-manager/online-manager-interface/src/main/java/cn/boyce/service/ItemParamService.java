package cn.boyce.service;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.format.R;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 22:23 2019/4/28
 **/
public interface ItemParamService {

    EasyUIDataGridResult getItemParamList(int page, int rows);

    R getItemParamByCid(Long cid);
}
