package cn.boyce.service;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.pojo.Item;

import java.util.Optional;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 18:40 2019/4/13
 **/
public interface ItemService {

    Optional<Item> getItemById(Long id);

    EasyUIDataGridResult getItemList(int page,int rows);

}
