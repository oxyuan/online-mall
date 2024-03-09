package cn.boyce.order.service.impl;

import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.OrderItem;
import cn.boyce.manager.pojo.OrderShipping;
import cn.boyce.manager.repo.OrderDao;
import cn.boyce.manager.repo.OrderItemDao;
import cn.boyce.manager.repo.OrderShippingDao;
import cn.boyce.order.pojo.OrderInfo;
import cn.boyce.order.service.OrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: oxyuan
 * @Date: Created in 19:11 2019/5/8
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderShippingDao orderShippingDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_ID_BEGIN}")
    private Integer ORDER_ID_BEGIN;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public R createOrder(OrderInfo orderInfo) {
        // 1、接收表单的数据
        // 2、生成订单 id
        if (!redisTemplate.hasKey(ORDER_GEN_KEY)) {
            // 设置初始值
            redisTemplate.opsForValue().set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
        }
        String orderId = redisTemplate.opsForValue().increment(ORDER_GEN_KEY, 1L).toString();
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);
        // 3、向订单表插入数据。
        orderDao.save(orderInfo);
        // 4、向订单明细表插入数据
        List<OrderItem> orderItems = orderInfo.getOrderItems();
        for (OrderItem order : orderItems) {
            // 生成明细 id
            Long orderItemId = redisTemplate.opsForValue().increment(ORDER_ITEM_ID_GEN_KEY, 1L);
            order.setId(Objects.requireNonNull(orderItemId).toString());
            order.setOrderId(orderId);
            // 插入数据
            orderItemDao.save(order);
        }
        // 5、向订单物流表插入数据。
        OrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingDao.save(orderShipping);
        // 6、返回 R。
        return R.ok(orderId);
    }

}
