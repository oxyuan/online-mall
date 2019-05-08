package cn.boyce.item.message;

import cn.boyce.item.pojo.ItemDto;
import cn.boyce.manager.pojo.Item;
import cn.boyce.manager.pojo.ItemDesc;
import cn.boyce.manager.service.ItemDescService;
import cn.boyce.manager.service.ItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 接收后台添加商品消息，生成商品详情页面
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 13:55 2019/5/7
 **/
@Component
public class GeneratePageMessageReceiver {

    @Reference
    private ItemService itemService;

    @Reference
    private ItemDescService itemDescService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Value("${TEMPLATE_NAME}")
    private String TEMPLATE_NAME;

    @Value("${TEMPLATE_FILEPATH}")
    private String TEMPLATE_FILEPATH;

    @JmsListener(destination = "itemAddTopic", containerFactory = "jmsTopicListenerContainerFactory")
    public void itemAddReceiver(Long itemId) {
        try {
            // 0、等待 1s 让 e3-manager-service 提交完事务，商品添加成功
            Thread.sleep(1000);
            // 1、准备商品数据
            Item item = itemService.getItemById(itemId);
            ItemDesc itemDesc = itemDescService.getItemDescById(itemId);
            ItemDto itemDto = new ItemDto(item);
            // 2、构造上下文 (Model)
            Context context = new Context();
            context.setVariable("item", item);
            context.setVariable("itemDesc", itemDesc);
            // 3、生成页面
            FileWriter writer = new FileWriter(TEMPLATE_FILEPATH + itemId + ".html");
            springTemplateEngine.process(TEMPLATE_NAME, context, writer);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

