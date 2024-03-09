package cn.boyce.content.service;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Content;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 13:56 2019/5/3
 **/
public interface ContentService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    EasyUIDataGridResult getContentListByCategoryId(Long categoryId, int page, int rows);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Content> listByCategoryId(Long cid);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    R addContent(Content content);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateById(Content content);
}
