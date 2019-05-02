package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:07 2019/4/28
 **/
@Repository
public interface ItemDescDao extends JpaRepository<ItemDesc,Long> {

}
