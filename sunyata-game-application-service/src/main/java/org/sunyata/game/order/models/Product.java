package org.sunyata.game.order.models;

import java.util.Date;

public class Product {
    private int id;
    private String productCode;//商品编码
    private String productName;//商品名称
    private int productType;
    private String price;//单价
    private Date beginDate;//上架时间
    private Date endDate;//下架时间
    private int quantity;//库存量
    private int moneyType;//货币类型


    public String getPrice() {
        return price;
    }

    public Product setPrice(String price) {
        this.price = price;
        return this;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public Product setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public int getProductType() {
        return productType;
    }

    public Product setProductType(int productType) {
        this.productType = productType;
        return this;
    }
    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Product setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Product setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public Product setMoneyType(int moneyType) {
        this.moneyType = moneyType;
        return this;
    }
}