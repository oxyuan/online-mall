package cn.boyce.search.service;

import cn.boyce.common.format.R;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: oxyuan
 * @Date: Created in 16:51 2019/5/5
 **/
public interface SearchItemService {

    @Transactional(propagation = Propagation.REQUIRED)
    R importItems();
}
