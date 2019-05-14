package cn.boyce.cart.service;

import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Item;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:50 2019/5/8
 **/
public interface CartService {

    R addCart(Long userId, Long itemId, int num);

    R mergeCart(Long userId, List<Item> cookieItemList);

    List<Item> getCartList(Long userId);

    R updateCartNum(Long userId, Long itemId, int num);

    R deleteCartItem(Long userId, Long itemId);

    R clearCartList(Long userId);
}
