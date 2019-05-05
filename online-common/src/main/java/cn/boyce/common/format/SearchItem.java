package cn.boyce.common.format;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:48 2019/5/5
 **/
@Data
@Entity
public class SearchItem implements Serializable {

    @Transient
    private static final long serialVersionUID = -4813361542496370884L;

    @Id
    private String id;

    private String title;

    private String sell_point;

    private long price;

    private String image;

    private String category_name;

    public String[] getImages() {
        if(this.image == null) {
            return null;
        } else {
            return this.image.split(",");
        }
    }
}
