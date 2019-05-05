package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 13:54 2019/5/3
 **/
@Repository
public interface ContentDao extends JpaRepository<Content, Long>,
        JpaSpecificationExecutor<Content> {

    //写下SQL是因为自己表间映射错了，改了半天bug才发现
//    @Query(value = "SELECT * FROM content WHERE category_id = ?1 ORDER BY ?#{#pageable}",
//            countQuery = "SELECT count(*) FROM content WHERE category_id = ?1",
//            nativeQuery = true)
    Page<Content> findByCategoryId(Long categoryId, Pageable pageable);

    List<Content> findByCategoryId(Long categoryId);
}
