package cn.boyce.service.impl;

import cn.boyce.format.EasyUIDataGridResult;
import cn.boyce.format.R;
import cn.boyce.pojo.ItemParam;
import cn.boyce.repo.ItemParamDao;
import cn.boyce.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 商品规格参数模板管理 service
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 22:24 2019/4/28
 **/
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    ItemParamDao itemParamDao;

    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        //将参数传给这个方法就可以实现物理分页。排序：
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page - 1, rows, sort);
        Page itemParamPage = itemParamDao.findAll(pageable);

        // 创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(itemParamPage.getTotalElements());
        result.setRows(itemParamPage.getContent());
        return result;
    }

    @Override
    public R getItemParamByCid(Long cid) {
        ItemParam itemParam = itemParamDao.findByItemCatId(cid);
        if (null != itemParam) {
            return R.ok(itemParam);
        }
        return R.ok();
    }

    @Override
    public R addItemParam(Long cid, String paramData) {
        ItemParam itemParam = new ItemParam();  // 此时 itemParam 的 id 值为 null
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamDao.saveAndFlush(itemParam);
        Long result = itemParam.getId();   // 此时 itemParam 的 id 值为 数据库自增后的值
        if (result != null) {
            return R.ok();
        } else {
            return R.build(400, "新增规格参数失败");
        }
    }

    @Override
    public R deleteParam(String ids) {
        try {
            String[] idsArray = ids.split(",");
            for (String id : idsArray) {
                itemParamDao.deleteById(Long.valueOf(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return R.ok();
    }


}
