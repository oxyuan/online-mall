package cn.boyce.order.pojo;

import cn.boyce.manager.pojo.Order;
import cn.boyce.manager.pojo.OrderItem;
import cn.boyce.manager.pojo.OrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:07 2019/5/8
 **/
public class OrderInfo extends Order implements Serializable {

    private List<OrderItem> orderItems;

    private OrderShipping orderShipping;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

}