package cn.boyce.manager.service.impl;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.common.util.IDUtils;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.pojo.ItemParamItem;
import cn.boyce.manager.repo.ItemDao;
import cn.boyce.manager.repo.ItemDescDao;
import cn.boyce.manager.repo.ItemParamItemDao;
import cn.boyce.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.Date;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:49 2019/4/9
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Autowired
    ItemDescDao itemDescDao;

    @Autowired
    ItemParamItemDao itemParamItemDao;

    @Override
    public Item getItemById(Long id) {
        return itemDao.findById(id).get();
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {

        //将参数传给这个方法就可以实现物理分页。排序：
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //page=0 为第一页，前端默认所传为 1
        Pageable pageable = new PageRequest(page - 1, rows, sort);
        Page itemPage = itemDao.findAll(pageable);

        // 创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(itemPage.getTotalElements());
        result.setRows(itemPage.getContent());
        return result;
    }


    /**
     * 后台管理添加商品至数据库
     *
     * @param item 商品
     * @param desc 商品描述
     * @return
     */
    @Override
    public R addItem(Item item, String desc, String itemParam) {
        // 1、生成商品 ID
        long itemId = IDUtils.genItemId();
        // 2、补全 Item 对象的属性
        item.setId(itemId);
        // 商品状态 1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 3、向商品表插入数据
        itemDao.saveAndFlush(item);
        // 4、创建一个 ItemDesc 对象
        ItemDesc itemDesc = new ItemDesc();
        // 5、补全 TbItemDesc 的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 6、向商品描述表插入数据
        itemDescDao.saveAndFlush(itemDesc);
        // 7、添加商品规格参数处理
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        itemParamItem.setParamData(itemParam);
        // 8、插入数据
        itemParamItemDao.saveAndFlush(itemParamItem);

        return R.ok();
    }

    @Override
    public void updateItem(Item item) {
        Date date = new Date();
        if (null == item.getCreated()) {
            item.setCreated(date);
        }
        if (null == item.getStatus()) {
            item.setStatus((byte) 1);
        }
        item.setUpdated(date);
        itemDao.saveAndFlush(item);
    }

}
