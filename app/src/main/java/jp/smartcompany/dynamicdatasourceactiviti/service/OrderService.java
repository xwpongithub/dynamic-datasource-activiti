package jp.smartcompany.dynamicdatasourceactiviti.service;

import jp.smartcompany.dynamicdatasourceactiviti.dto.PlaceOrderRequest;

public interface OrderService {

    /**
     * 下单
     *
     * @param placeOrderRequest 订单请求参数
     */
    void placeOrder(PlaceOrderRequest placeOrderRequest);
}
