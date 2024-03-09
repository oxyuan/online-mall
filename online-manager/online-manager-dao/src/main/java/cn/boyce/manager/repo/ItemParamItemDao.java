package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.ItemParamItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: oxyuan
 * @Date: Created in 23:18 2019/4/29
 **/
@Repository
public interface ItemParamItemDao extends JpaRepository<ItemParamItem, Long> {

    ItemParamItem findItemParamItemByItemId(Long cid);
}
