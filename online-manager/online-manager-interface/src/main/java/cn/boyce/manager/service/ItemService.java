package cn.boyce.manager.service;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oxyuan
 * @Date: Created in 18:40 2019/4/13
 **/
public interface ItemService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    Item getItemById(Long id);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    EasyUIDataGridResult getItemList(int page, int rows);

    @Transactional(propagation = Propagation.REQUIRED)
    R addItem(Item item, String desc, String itemParam);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateItem(Item item);
}
