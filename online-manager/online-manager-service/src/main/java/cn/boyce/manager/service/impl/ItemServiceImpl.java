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
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY;

    @Value("${ITEM_INFO_BASE_KEY}")
    private String ITEM_INFO_BASE_KEY;

    @Value("${ITEM_INFO_DESC_KEY}")
    private String ITEM_INFO_DESC_KEY;

    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;


    @Override
    public Item getItemById(Long id) {
        Item item;
        // 查询缓存
        try {
            item = (Item) redisTemplate.opsForValue().get(ITEM_INFO_KEY + ":" + id + ":" + ITEM_INFO_BASE_KEY);
            if (item != null) {
                System.out.println("read redis item base information...");
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查询数据库
        item = itemDao.findById(id).get();
        try {
            // 把数据保存到缓存
            redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + id + ":" + ITEM_INFO_BASE_KEY, item);
            // 设置缓存的有效期
            redisTemplate.expire(ITEM_INFO_KEY + ":" + id + ":" + ITEM_INFO_BASE_KEY, ITEM_INFO_EXPIRE, TimeUnit.SECONDS);
            System.out.println("write redis item base information...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //将参数传给这个方法就可以实现物理分页。排序：
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //page=0 为第一页，page默认为1
        Pageable pageable = PageRequest.of(page - 1, rows, sort);
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
        // 9、发送消息队列，通知新增商品id
        ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
        jmsMessagingTemplate.convertAndSend(itemAddTopic, item.getId());

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
        //发送消息队列，通知修改商品id
        ActiveMQTopic itemUpdateTopic = new ActiveMQTopic("itemUpdateTopic");
        jmsMessagingTemplate.convertAndSend(itemUpdateTopic, item.getId());

        item.setUpdated(date);
        itemDao.saveAndFlush(item);
    }
}
