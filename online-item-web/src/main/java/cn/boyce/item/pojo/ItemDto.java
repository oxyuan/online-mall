package cn.boyce.item.pojo;

import cn.boyce.manager.pojo.Item;

/**
 * @Author: oxyuan
 * @Date: Created in 13:24 2019/5/7
 **/
public class ItemDto extends Item {

    public String[] getImages() {
        String image1 = this.getImage();
        if (image1 != null && !"".equals(image1)) {
            String[] images = image1.split(",");
            return images;
        }
        return null;
    }

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.setBarcode(item.getBarcode());
        this.setCid(item.getCid());
        this.setCreated(item.getCreated());
        this.setId(item.getId());
        this.setImage(item.getImage());
        this.setNum(item.getNum());
        this.setPrice(item.getPrice());
        this.setSellPoint(item.getSellPoint());
        this.setStatus(item.getStatus());
        this.setTitle(item.getTitle());
        this.setUpdated(item.getUpdated());
    }
}
