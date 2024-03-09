package cn.boyce.search.service;

import cn.boyce.common.format.SearchResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oxyuan
 * @Date: Created in 16:55 2019/5/5
 **/
public interface SearchService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    SearchResult search(String keyWord, int page, int rows) throws Exception;
}
