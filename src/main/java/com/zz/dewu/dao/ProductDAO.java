package com.zz.dewu.dao;

import com.zz.dewu.dataobject.ProductDO;
import com.zz.dewu.param.BasePageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDAO {
    int deleteByPrimaryKey(String id);

    int insert(ProductDO record);

    ProductDO selectByPrimaryKey(String id);

    List<ProductDO> selectAll();

    int updateByPrimaryKey(ProductDO record);

    int selectAllCounts();

    List<ProductDO> pageQuery(BasePageParam param);

}