package cn.boyce.search.service;

import cn.boyce.common.format.SearchResult;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:55 2019/5/5
 **/
public interface SearchService {

    SearchResult search(String keyWord, int page, int rows) throws Exception;
}
