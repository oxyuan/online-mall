package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.ItemParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: oxyuan
 * @Date: Created in 22:20 2019/4/28
 **/
@Repository
public interface ItemParamDao extends JpaRepository<ItemParam, Long> {

    @Override
    Page<ItemParam> findAll(Pageable pageable);

    ItemParam findByItemCatId(Long id);
}
