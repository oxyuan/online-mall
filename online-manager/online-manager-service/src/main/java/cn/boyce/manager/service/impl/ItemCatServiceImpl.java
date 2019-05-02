package cn.boyce.manager.service.impl;

import cn.boyce.common.format.EasyUITreeNode;
import cn.boyce.manager.pojo.ItemCat;
import cn.boyce.manager.repo.ItemCatDao;
import cn.boyce.manager.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 0:18 2019/4/18
 **/
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    ItemCatDao itemCatDao;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //1、根据 parentId 查询节点列表, 设置查询条件
        List<ItemCat> list = itemCatDao.findByParentId(parentId);
        //2、转换成 EasyUITreeNode 列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (ItemCat itemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent() ? "closed" : "open");
            //添加到列表
            resultList.add(node);
        }
        //3、返回
        return resultList;
    }

}
