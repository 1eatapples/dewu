package com.zz.dewu.api;

import com.zz.dewu.model.Paging;
import com.zz.dewu.model.Product;
import com.zz.dewu.model.Result;
import com.zz.dewu.param.BasePageParam;
import com.zz.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
@Controller
public class ProductApi {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品
     *
     */
    @ResponseBody
    @GetMapping("/page")
    @CrossOrigin
    public Result<Paging<Product>> pageQuery(BasePageParam param) {

        Result<Paging<Product>> result = new Result<>();

        result.setSuccess(true);
        result.setData(productService.pageQueryProduct(param));
        return result;
    }
    /**
     * 根据商品Id获取单个商品
     *
     */
    @ResponseBody
    @GetMapping("/get")
    @CrossOrigin
    public Result<Product> get(@RequestParam("productId") String productId) {

        Result<Product> result = new Result<>();

        result.setSuccess(true);
        result.setData(productService.get(productId));
        return result;
    }
}
