package org.sunyata.game.store.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.order.models.OrderItem;

import java.util.List;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class OrderItemDao {
    @Autowired
    OrderItemMapper orderItemMapper;


    public OrderItem createOrderItem(String orderId, int productId, String price) {
        OrderItem order = new OrderItem();
        order.setOrderId(orderId).setProductId(productId).setPrice(price);
        orderItemMapper.insertOrderItem(order);
        return order;
    }

    public OrderItem createOrderItem(OrderItem item) {
        orderItemMapper.insertOrderItem(item);
        return item;
    }


    public List<OrderItem> loadOrderItems(String orderId) {
        return orderItemMapper.loadOrderItems(orderId);
    }



}
