package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:16 2019/5/8
 **/
@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, String> {

}
