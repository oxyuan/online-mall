package cn.boyce.common.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: oxyuan
 * @Date: Created in 16:49 2019/5/5
 **/
@Data
public class SearchResult implements Serializable {

    private static final long serialVersionUID = -4813361542496370884L;

    private List<SearchItem> itemList;

    private int totalPages;

    private int recourdCount;

}
