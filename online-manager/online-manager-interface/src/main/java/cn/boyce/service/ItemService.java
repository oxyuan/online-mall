package cn.boyce.service;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.format.R;
import cn.boyce.pojo.Item;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:40 2019/4/13
 **/
public interface ItemService {

    Item getItemById(Long id);

    EasyUIDataGridResult getItemList(int page,int rows);

    R addItem(Item item, String desc);
}
