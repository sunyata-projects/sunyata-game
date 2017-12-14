package org.sunyata.game.order.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leo on 17/11/16.
 */
public class Order implements Serializable {

    /**
     * 订单id
     */
    private String id;
    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 创建时间
     */
    private Timestamp createDateTime;

    /**
     * 订单状态
     */
    private Integer orderStatusType;

    private String orderStatusTypeDesc;

    /**
     * 备注
     */
    private String notes;

    private String parameterString;

    private String totalPrice;
    private String userId;
    private List<OrderItem> orderItems;


    public String getUserId() {
        return userId;
    }

    public Order setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }


    public Integer getBusinessType() {
        return businessType;
    }

    public Order setBusinessType(Integer businessType) {
        this.businessType = businessType;
        return this;
    }


    public String getBusinessId() {
        return businessId;
    }

    public Order setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }


    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public Order setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }


    public Integer getOrderStatusType() {
        return orderStatusType;
    }

    public Order setOrderStatusType(Integer orderStatusType) {
        this.orderStatusType = orderStatusType;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Order setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getParameterString() {
        return parameterString;
    }

    public Order setParameterString(String parameterString) {
        this.parameterString = parameterString;
        return this;
    }


    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }


    public String getOrderStatusTypeDesc() {
        return orderStatusTypeDesc;
    }

    public Order setOrderStatusTypeDesc(String orderStatusTypeDesc) {
        this.orderStatusTypeDesc = orderStatusTypeDesc;
        return this;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}