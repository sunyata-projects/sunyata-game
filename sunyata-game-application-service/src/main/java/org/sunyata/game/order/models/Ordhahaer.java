package org.sunyata.game.order.models;

import java.util.Date;

/**
 * Created by leo on 17/11/15.
 */
public class Ordhahaer {
    private String id;
    //private String serial_no;
    private int status;
    private String user_id;
    private Date create_date;
    private String total_price;

    public static class OrderItem {
        private int id;
        private String order_id;
        private int product_id;
        private int price;

    }

    public static class Product {
        private int id;
        private String product_code;//商品编码
        private String product_name;//商品名称
        private String money;//单价
        private Date begin_date;//上架时间
        private Date end_data;//下架时间
        private int quantity;//库存量
        private int money_type;//货币类型
    }
}

