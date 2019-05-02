package cn.boyce.manager.service.impl;

import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.service.ItemDescService;
import cn.boyce.manager.repo.ItemDescDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.Date;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 23:49 2019/4/30
 **/
@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    ItemDescDao itemDescDao;

    @Override
    public ItemDesc getItemDescById(Long itemId) {
        return itemDescDao.findById(itemId).get();
    }

    @Override
    public void updateItemDesc(ItemDesc itemDesc) {
        itemDesc.setUpdated(new Date());
        itemDescDao.saveAndFlush(itemDesc);
    }
}
