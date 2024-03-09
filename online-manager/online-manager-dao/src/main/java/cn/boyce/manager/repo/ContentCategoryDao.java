package cn.boyce.manager.repo;

import cn.boyce.manager.pojo.ContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 18:00 2019/5/2
 **/
@Repository
public interface ContentCategoryDao extends JpaRepository<ContentCategory, Long> {

    List<ContentCategory> findAllByParentId(Long parentId);

    int countByParentId(Long parentId);
}
