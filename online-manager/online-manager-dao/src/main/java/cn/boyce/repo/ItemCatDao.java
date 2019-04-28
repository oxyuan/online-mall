package cn.boyce.repo;

import cn.boyce.pojo.ItemCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:16 2019/4/18
 **/
@Repository
public interface ItemCatDao extends JpaRepository<ItemCat,Long> {

    List<ItemCat> findByParentId(Long parantId);

}
