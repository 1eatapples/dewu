package com.zz.dewu.model;


public enum  OrderStatus {
    /**
     * 待付款
     */
    WAIT_BUYER_PAY,

    /**
     * 订单关闭
     */
    TRADE_CLOSED,

    /**
     * 订单已支付成功
     */
    TRADE_PAID_SUCCESS,

    /**
     * 订单支付失败
     */
    TRADE_PAID_FAILED,
}
