package com.zz.dewu.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zz.dewu.model.Paging;

import java.util.Date;

/**
 * @Author jiaoheng
 * @DATE 2020-07-13
 */
public class QueryOrderParam extends Paging {

    /**
     * 商品Id
     */
    private String productDetailId;
    /**
     * 查询近几天的购买记录
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
