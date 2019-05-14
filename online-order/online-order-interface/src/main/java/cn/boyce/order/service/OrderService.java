package cn.boyce.order.service;

import cn.boyce.common.format.R;
import cn.boyce.order.pojo.OrderInfo;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:09 2019/5/8
 **/
public interface OrderService {

    R createOrder(OrderInfo orderInfo);
}
