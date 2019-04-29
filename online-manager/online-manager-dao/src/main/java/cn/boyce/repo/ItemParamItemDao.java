package cn.boyce.repo;

import cn.boyce.pojo.ItemParamItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 23:18 2019/4/29
 **/
@Repository
public interface ItemParamItemDao extends JpaRepository<ItemParamItem, Long> {
}
