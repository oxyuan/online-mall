package cn.boyce.manager.service;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oxyuan
 * @Date: Created in 22:23 2019/4/28
 **/
public interface ItemParamService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    EasyUIDataGridResult getItemParamList(int page, int rows);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    R getItemParamByCid(Long cid);

    @Transactional(propagation = Propagation.REQUIRED)
    R addItemParam(Long cid, String paramData);

    @Transactional(propagation = Propagation.REQUIRED)
    R deleteParam(String ids);
}
