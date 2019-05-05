package cn.boyce.content.service.impl;

import cn.boyce.common.format.EasyUITreeNode;
import cn.boyce.common.format.R;
import cn.boyce.content.service.ContentCategoryService;
import cn.boyce.manager.pojo.ContentCategory;
import cn.boyce.manager.repo.ContentCategoryDao;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理service
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 18:03 2019/5/2
 **/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryDao contentCategoryDao;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parantId) {
        // 根据 parentId 查询子节点列表
        List<ContentCategory> catList = contentCategoryDao.findAllByParentId(parantId);
        // 转换成 EasyUITreeNode 的列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (ContentCategory contentCategory : catList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent() ? "closed" : "open");
            // 添加到列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public R addContentCategory(Long parentId, String name) {
        // 接受两个参数: parentId,name，向content_category表中插入数据
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        // 排列序号。表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围：大于零的整数
        contentCategory.setSortOrder(1);
        // 状态。可选值：1 (正常),2 (删除)
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        System.out.println("(before)id:" + contentCategory.getId());
        System.out.println("(before)isParent:" + contentCategory.getIsParent());

        // 向数据库 content_category 表中插入数据
        contentCategoryDao.saveAndFlush(contentCategory);

        System.out.println("(after)id:" + contentCategory.getId());
        System.out.println("(after)isParent:" + contentCategory.getIsParent());

        // 判断父节点的 isparent 是否为 true，不是 true 需要改为 true
        ContentCategory parentNode = contentCategoryDao.findById(parentId).get();
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            // 更新父节点
            contentCategoryDao.saveAndFlush(parentNode);
        }
        // 因前端需要主键返回，所以返回 R ，包装 ContentCategory 对象
        return R.ok(contentCategory);
    }

    @Override
    public R updateContentCategory(Long id, String name) {
        ContentCategory category = contentCategoryDao.findById(id).get();
        if (null == category) {
            return R.error("没有ID对应的数据,更新失败！");
        }
        category.setName(name);
        contentCategoryDao.saveAndFlush(category);
        return R.ok();
    }

    @Override
    public R deleteContentCategory(Long id) {
        ContentCategory category = contentCategoryDao.findById(id).get();
        // 如果该节点有子节点，不允许删除
        if (category.getIsParent()) {
            return R.error("该节点拥有子节点");
        }
        // 找到其父节点
        ContentCategory parent = contentCategoryDao.findById(category.getParentId()).get();
        //删除该节点
        contentCategoryDao.deleteById(id);
        // 如果父节点没有其它子节点，将父节点变为子节点
        int count = contentCategoryDao.countByParentId(parent.getId());
        if (count == 0) {
            parent.setIsParent(false);
            contentCategoryDao.saveAndFlush(parent);
        }
        return R.ok();
    }
}
