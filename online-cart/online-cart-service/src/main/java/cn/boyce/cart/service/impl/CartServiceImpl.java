package cn.boyce.cart.service.impl;

import cn.boyce.cart.service.CartService;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.repo.ItemDao;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 *
 * @Author: oxyuan
 * @Date: Created in 16:54 2019/5/8
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ItemDao itemDao;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;


    @Override
    public R addCart(Long userId, Long itemId, int num) {
        Boolean hasItem = redisTemplate.opsForHash().hasKey(REDIS_CART_PRE + ":" + userId, itemId + "");
        if (hasItem) {
            // 商品存在，数量相加
            Item item = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId + "");
            item.setNum(item.getNum() + num);
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId + "", item);
            return R.ok();
        } else {
            // 商品不存在，查询数据库添加商品
            Item item = itemDao.findById(itemId).get();
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId + "", item);
            return R.ok();
        }
    }

    @Override
    public R mergeCart(Long userId, List<Item> cookieItemList) {
        for (Item item : cookieItemList) {
            addCart(userId, item.getId(), item.getNum());
        }
        return R.ok();
    }

    @Override
    public List<Item> getCartList(Long userId) {
        List<Object> results = redisTemplate.opsForHash().values(REDIS_CART_PRE + ":" + userId);
        List<Item> items = new ArrayList<>();
        for (Object result : results) {
            items.add((Item) result);
        }
        return items;
    }

    @Override
    public R updateCartNum(Long userId, Long itemId, int num) {
        Item item = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId + "");
        item.setNum(item.getNum() + num);
        redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId + "", item);
        return R.ok();
    }

    @Override
    public R deleteCartItem(Long userId, Long itemId) {
        redisTemplate.opsForHash().delete(REDIS_CART_PRE + ":" + userId, itemId + "");
        return R.ok();
    }

    /**
     * 清除购物车
     *
     * @param userId
     * @return
     */
    @Override
    public R clearCartList(Long userId) {
        redisTemplate.delete(REDIS_CART_PRE + ":" + userId);
        return R.ok();
    }
}