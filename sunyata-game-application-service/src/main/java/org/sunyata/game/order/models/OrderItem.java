package org.sunyata.game.order.models;

import java.io.Serializable;

/**
 * Created by leo on 17/11/16.
 */
public class OrderItem implements Serializable {

    /**
     * 订单id
     */
    private int id;

    private String orderId;
    /**
     * 业务类型
     */
    private int productId;

    private String price;

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }


    public int getId() {
        return id;
    }

    public OrderItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderItem setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public OrderItem setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public OrderItem setPrice(String price) {
        this.price = price;
        return this;
    }


}