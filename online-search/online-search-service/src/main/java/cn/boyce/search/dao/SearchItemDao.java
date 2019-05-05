package cn.boyce.search.dao;

import cn.boyce.common.format.SearchItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 19:38 2019/5/5
 **/
@Repository
public class SearchItemDao {

    @PersistenceContext
    private EntityManager em;

    public List<SearchItem> getItemList() {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT  a.id,	a.title,a.sell_point,a.price,a.image,b.NAME category_name " +
                "FROM	item a	LEFT JOIN item_cat b ON a.cid = b.id " +
                "WHERE	a.status = 1");
        Query query = em.createNativeQuery(sbSQL.toString(), SearchItem.class);
        return query.getResultList();
    }

    public SearchItem getItemById(Long itemId) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT  a.id,	a.title,a.sell_point,a.price,a.image,b.NAME category_name " +
                "FROM	item a	LEFT JOIN item_cat b ON a.cid = b.id " +
                "WHERE	a.status = 1 AND a.id = " + itemId);
        Query query = em.createNativeQuery(sbSQL.toString(), SearchItem.class);
        return (SearchItem) query.getSingleResult();
    }
}
