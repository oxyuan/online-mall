package cn.boyce.search.message;

import cn.boyce.common.format.SearchItem;
import cn.boyce.search.dao.SearchItemDao;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息队列接收者
 *
 * @Author: oxyuan
 * @Date: Created in 16:24 2019/5/6
 **/
@Component
public class ItemMessageReceiver {

    @Autowired
    private SearchItemDao searchItemDao;

    @Autowired
    private SolrClient solrClient;


    /**
     * 根据商品 ID 新增索引
     *
     * @param msg
     */
    @JmsListener(destination = "itemAddTopic", containerFactory = "jmsTopicListenerContainerFactory")
    public void itemAddReceiver(Long msg) {
        try {
            // 0、等待 1s 让 online-manager-service 提交完事务，商品添加成功
            Thread.sleep(1000);
            // 1、根据商品id查询商品信息
            SearchItem searchItem = searchItemDao.getItemById(msg);
            // 2、创建SolrInputDocument对象。
            SolrInputDocument document = new SolrInputDocument();
            // 3、使用SolrServer对象写入索引库。
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            // 5、向索引库中添加文档。
            solrClient.add(document);
            solrClient.commit();

        } catch (SolrServerException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据商品 ID 修改索引
     *
     * @param msg
     */
    @JmsListener(destination = "itemUpdateTopic", containerFactory = "jmsTopicListenerContainerFactory")
    public void itemUpdateReceiver(Long msg) {
        try {
            solrClient.deleteById(msg.toString());
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        itemAddReceiver(msg);
    }
}
