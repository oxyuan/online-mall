package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: oxyuan
 * @Date: Created in 19:15 2019/5/8
 **/
@Repository
public interface OrderDao extends JpaRepository<Order, String> {

}
