package com.youkeda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zz.dewu.api.OrderApi;
import com.zz.dewu.dao.ProductDAO;
import com.zz.dewu.dao.ProductDetailDAO;
import com.zz.dewu.dataobject.ProductDO;
import com.zz.dewu.dataobject.ProductDetailDO;
import com.zz.dewu.model.Order;
import com.zz.dewu.model.OrderStatus;
import com.zz.dewu.model.Result;
import com.zz.dewu.model.User;
import com.zz.dewu.service.OrderService;
import com.zz.dewu.service.UserService;
import com.zz.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YkdTest {
    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    private static String sessionId;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws Exception {
        Result<User> regResult = userService.register("下单测试", "111");

        post("api/user/login?userName=下单测试&pwd=111", new TypeReference<Result<User>>() {
        }, null);

        //插入商品信息
        ProductDO product = new ProductDO();
        product.setId(UUIDUtils.getUUID());
        product.setName("测试商品");
        product.setPrice(20.0);
        product.setPurchaseNum(20);
        int insertSize = productDAO.insert(product);
        if (insertSize < 1) {
            error("插入商品失败！");
            return;
        }

        //插入商品详情信息
        ProductDetailDO productDetailDO = new ProductDetailDO();
        productDetailDO.setPrice(20.0);
        productDetailDO.setProductId(product.getId());
        productDetailDO.setSize(20.0);
        productDetailDO.setId(UUIDUtils.getUUID());
        int insertSize2 = productDetailDAO.insert(productDetailDO);
        if (insertSize2 < 1) {
            error("插入商品详情失败！");
            return;
        }

        //插入订单信息
        Order order = new Order();
        order.setTotalPrice(1.0);
        order.setStatus(OrderStatus.TRADE_PAID_SUCCESS);
        order.setId(UUIDUtils.getUUID());
        order.setProductDetailId(productDetailDO.getId());
        order.setOrderNumber(UUIDUtils.getUUID());
        order.setProductDetail(productDetailDO.convertToModel());

        // 下单
        Result<Order> addResult = post("api/order/add", new TypeReference<Result<Order>>() {
        }, order);

        if (!addResult.isSuccess() || StringUtils.isBlank(addResult.getData().getId())) {
            error("下单失败！");
            return;
        }
    }

    private <T> T post(String url, TypeReference typeReference, Order order) throws Exception {
        String baseUrl = "http://localhost:" + randomServerPort + "/" + url;

        URI uri = new URI(baseUrl);
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri).header("Content-Type", "application/json");

        if (order != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);

            builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        } else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if (sessionId != null) {
            builder.header("Cookie", sessionId);
        }

        HttpRequest request = builder.build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String s = response.headers().firstValue("set-cookie").orElse(null);
        if (s != null) {
            sessionId = s;
        }

        return (T)mapper.readValue(response.body(), typeReference);
    }

}
