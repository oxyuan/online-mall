package cn.boyce.manager.service.impl;

import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.service.ItemDescService;
import cn.boyce.manager.repo.ItemDescDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: oxyuan
 * @Date: Created in 23:49 2019/4/30
 **/
@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    ItemDescDao itemDescDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY;

    @Value("${ITEM_INFO_BASE_KEY}")
    private String ITEM_INFO_BASE_KEY;

    @Value("${ITEM_INFO_DESC_KEY}")
    private String ITEM_INFO_DESC_KEY;

    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;


    @Override
    public ItemDesc getItemDescById(Long itemId) {
        ItemDesc itemDesc;
        // 查询缓存
        try {
            itemDesc = (ItemDesc) redisTemplate.opsForValue().get(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY);
            if (itemDesc != null) {
                System.out.println("read redis item desc information...");
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查询数据库
        itemDesc = itemDescDao.findById(itemId).get();
        // 把数据保存到缓存
        try {
            redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY, itemDesc);
            redisTemplate.expire(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY, ITEM_INFO_EXPIRE, TimeUnit.HOURS);
            System.out.println("write redis item desc information...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    @Override
    public void updateItemDesc(ItemDesc itemDesc) {
        itemDesc.setUpdated(new Date());
        itemDescDao.saveAndFlush(itemDesc);
    }
}
