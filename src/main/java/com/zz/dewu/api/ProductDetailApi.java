package com.zz.dewu.api;

import com.zz.dewu.model.ProductDetail;
import com.zz.dewu.model.Result;
import com.zz.dewu.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author zr
 * @date 2020/7/14, 周二
 */
@Controller
@RequestMapping("/api/productdetail")
public class ProductDetailApi {

    @Autowired
    private ProductDetailService productDetailService;
    /**
     * 根据商品Id获取商品详情
     *
     */
    @GetMapping("/productId")
    @ResponseBody
    @CrossOrigin
    public Result<List<ProductDetail>> getProductDetails(@RequestParam("productId") String productId) {

        Result<List<ProductDetail>> result = new Result<>();

        List<ProductDetail> productDetails = productDetailService.getByProductId(productId);

        result.setData(productDetails);
        result.setSuccess(true);
        return result;

    }
}
