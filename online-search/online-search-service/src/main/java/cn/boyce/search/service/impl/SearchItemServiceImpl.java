package cn.boyce.search.service.impl;

import cn.boyce.common.format.R;
import cn.boyce.common.format.SearchItem;
import cn.boyce.search.dao.SearchItemDao;
import cn.boyce.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 索引库 导入
 *
 * @Author: oxyuan
 * @Date: Created in 19:37 2019/5/5
 **/
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemDao itemDao;

    @Autowired
    private SolrClient solrClient;

    @Override
    public R importItems() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemDao.getItemList();
            //导入索引库
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                //写入索引库
                solrClient.add(document);
            }
            //提交
            solrClient.commit();
            //返回成功
            return R.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return R.build(500, "商品导入失败");
        }
    }

}
