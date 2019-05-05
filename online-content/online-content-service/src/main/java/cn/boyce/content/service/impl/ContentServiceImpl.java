package cn.boyce.content.service.impl;

import cn.boyce.common.format.EasyUIDataGridResult;
import cn.boyce.common.format.R;
import cn.boyce.manager.pojo.Content;
import cn.boyce.manager.repo.ContentDao;
import cn.boyce.content.service.ContentService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 13:58 2019/5/3
 **/
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentDao contentDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
        //将参数传给这个方法就可以实现物理分页。排序：
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //page=0 为第一页，前端默认所传为 1
        Pageable pageable = PageRequest.of(page - 1, rows, sort);

        Page<Content> contentPage;
        if (categoryId == 0L) {
            contentPage = contentDao.findAll(pageable);
        } else {
            //带条件的分页查询
            contentPage = contentDao.findByCategoryId(categoryId, pageable);
        }
        // 创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(contentPage.getTotalElements());
        result.setRows(contentPage.getContent());

        return result;
    }

    @Override
    public List<Content> listByCategoryId(Long cid) {
        // 查询缓存
        try {
            List<Content> contents = (List<Content>) redisTemplate.opsForHash().get(CONTENT_KEY, cid.toString());
            System.out.println("read redis catch data...");
            if (contents != null && !contents.isEmpty()) {
                return contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据 cid 查询内容列表
        List<Content> list = contentDao.findByCategoryId(cid);
        // 向缓存中插入数据
        try {
            redisTemplate.opsForHash().put(CONTENT_KEY, cid.toString(), list);
            System.out.println("write redis catch data...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(Long id) {
        if (null == id) {
            return;
        }
        // 缓存同步：删除缓存中相应的数据
        redisTemplate.opsForHash().delete(CONTENT_KEY, contentDao.findById(id).get().getCategoryId().toString());
        contentDao.deleteById(id);
    }

    @Override
    public R addContent(Content content) {
        // 补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        // 插入属性
        contentDao.saveAndFlush(content);
        // 缓存同步：删除缓存中相应的数据
        redisTemplate.opsForHash().delete(CONTENT_KEY, content.getCategoryId().toString());
        return R.ok();
    }

    @Override
    public void updateById(Content content) {
        content.setUpdated(new Date());
        // 缓存同步：删除缓存中相应的数据
        redisTemplate.opsForHash().delete(CONTENT_KEY, content.getCategoryId().toString());
        contentDao.saveAndFlush(content);
    }
}
