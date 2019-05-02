package cn.boyce.manager.repo;


import cn.boyce.manager.pojo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:19 2019/4/15
 **/
@Repository
public interface ItemDao extends JpaRepository<Item, Long> {

    @Override
    Page<Item> findAll(Pageable pageable);
}
