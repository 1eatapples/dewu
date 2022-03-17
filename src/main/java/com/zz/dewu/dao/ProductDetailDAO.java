package com.zz.dewu.dao;

import com.zz.dewu.dataobject.ProductDetailDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailDAO {
    List<ProductDetailDO> selectByIds(List<String> productId);
    int deleteByPrimaryKey(String id);

    int insert(ProductDetailDO record);

    ProductDetailDO selectByPrimaryKey(String id);

    List<ProductDetailDO> selectAll();

    List<ProductDetailDO> selectByProductId(String productId);

    int updateByPrimaryKey(ProductDetailDO record);
}
