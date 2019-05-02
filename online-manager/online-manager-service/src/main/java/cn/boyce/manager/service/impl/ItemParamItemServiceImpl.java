package cn.boyce.manager.service.impl;

import cn.boyce.manager.pojo.ItemParamItem;
import cn.boyce.manager.repo.ItemParamItemDao;
import cn.boyce.manager.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:58 2019/4/30
 **/
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

    @Autowired
    ItemParamItemDao itemParamItemDao;

    @Override
    public ItemParamItem getItemParamById(long itemId) {
        return itemParamItemDao.findItemParamItemByItemId(itemId);
    }

//    @Override
//    public String getItemParamById(long itemId) {
//        ItemParamItem itemParamItem = itemParamItemDao.findItemParamItemByItemId(itemId);
//        if (null == itemParamItem) {
//            return "";
//        }
//        // 获取规格参数
//        String paramData = itemParamItem.getParamData();
//        //把json数据转换成java对象
//        List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
//        //将参数信息转换成html
//        StringBuilder sb = new StringBuilder();
////        sb.append("<div>");
//        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
//        sb.append("    <tbody>\n");
//        assert paramList != null;
//        for (Map map : paramList) {
//            sb.append("        <tr>\n");
//            sb.append("            <th class=\"tdTitle\" colspan=\"2\">").append(map.get("group")).append("</th>\n");
//            sb.append("        </tr>\n");
//            List<Map> params = (List<Map>) map.get("params");
//            for (Map map2 : params) {
//                sb.append("        <tr>\n");
//                sb.append("            <td class=\"tdTitle\">").append(map2.get("k")).append("</td>\n");
//                sb.append("            <td>" + map2.get("v") + "</td>\n");
//                sb.append("        </tr>\n");
//            }
//        }
//        sb.append("    </tbody>\n");
//        sb.append("</table>");
////        sb.append("</div>");
//        return sb.toString();
//     }


}
