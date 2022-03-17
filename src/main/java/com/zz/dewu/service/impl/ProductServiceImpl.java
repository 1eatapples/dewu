package com.zz.dewu.service.impl;

import com.zz.dewu.dao.ProductDAO;
import com.zz.dewu.dataobject.ProductDO;
import com.zz.dewu.model.Paging;
import com.zz.dewu.model.Product;
import com.zz.dewu.param.BasePageParam;
import com.zz.dewu.service.ProductService;
import com.zz.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public int save(Product product) {

        if (StringUtils.isBlank(product.getId())) {
            product.setId(UUIDUtils.getUUID());
            return productDAO.insert(new ProductDO(product));
        }

        return productDAO.updateByPrimaryKey(new ProductDO(product));
    }


    @Override
    public Product get(String productId) {

        if(StringUtils.isBlank(productId)){
            return null;
        }
        return productDAO.selectByPrimaryKey(productId).convertToModel();
    }

    @Override
    public Paging<Product> pageQueryProduct(BasePageParam param) {

        Paging<Product> result = new Paging<>();
        if (param == null) {
            return result;
        }

        if (param.getPagination() < 0) {
            param.setPagination(0);
        }

        if (param.getPageSize() < 0) {
            param.setPageSize(10);
        }

        //查询数据总数
        int counts = productDAO.selectAllCounts();
        if (counts < 0) {
            return result;
        }

        //组装返回数据
        result.setTotalCount(counts);
        result.setPageSize(param.getPageSize());
        result.setPageNum(param.getPagination());

        int totalPage = (int)Math.ceil(counts / (param.getPageSize() * 1.0));
        result.setTotalPage(totalPage);

        //实际返回的数据
        List<ProductDO> productDOS = productDAO.pageQuery(param);
        List<Product> productList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productDOS)) {
            for (ProductDO productDO : productDOS) {
                productList.add(productDO.convertToModel());
            }
        }

        result.setData(productList);

        return result;
    }
}
