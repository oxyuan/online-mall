package cn.boyce.manager.repo;


import cn.boyce.manager.pojo.ItemCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 0:16 2019/4/18
 **/
@Repository
public interface ItemCatDao extends JpaRepository<ItemCat,Long> {

    List<ItemCat> findByParentId(Long parantId);

}
