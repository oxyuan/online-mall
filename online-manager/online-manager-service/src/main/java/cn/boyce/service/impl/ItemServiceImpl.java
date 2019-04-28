package cn.boyce.service.impl;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.pojo.Item;
import cn.boyce.repo.ItemDao;
import cn.boyce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:49 2019/4/9
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Override
    public java.util.Optional<Item> getItemById(Long id) {
        return itemDao.findById(id);

    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {

        //将参数传给这个方法就可以实现物理分页。排序：
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, rows, sort);
        Page itemPage = itemDao.findAll(pageable);

        // 创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(itemPage.getTotalElements());
        result.setRows(itemPage.getContent());
        return result;
    }

}
