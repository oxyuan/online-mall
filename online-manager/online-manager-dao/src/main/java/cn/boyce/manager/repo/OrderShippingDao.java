package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.OrderShipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:17 2019/5/8
 **/
@Repository
public interface OrderShippingDao extends JpaRepository<OrderShipping,String> {

}
