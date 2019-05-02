package cn.boyce.manager.service;


import cn.boyce.manager.pojo.ItemDesc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 23:35 2019/4/30
 **/
public interface ItemDescService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    ItemDesc getItemDescById(Long itemId);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateItemDesc(ItemDesc itemDesc);

}
