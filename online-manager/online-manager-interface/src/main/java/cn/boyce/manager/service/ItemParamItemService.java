package cn.boyce.manager.service;


import cn.boyce.manager.pojo.ItemParamItem;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oxyuan
 * @Date: Created in 16:58 2019/4/30
 **/
public interface ItemParamItemService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    ItemParamItem getItemParamById(long itemId);
}
