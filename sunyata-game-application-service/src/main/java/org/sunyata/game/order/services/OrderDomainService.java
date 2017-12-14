package org.sunyata.game.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.order.models.OrderStatusType;
import org.sunyata.game.order.models.Order;
import org.sunyata.game.order.models.OrderItem;
import org.sunyata.game.store.order.OrderDao;
import org.sunyata.game.store.order.OrderItemDao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class OrderDomainService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderItemDao orderItemDao;

//    @Autowired
//    ProductDao productDao;

    public Order createOrder(String orderId,Integer userId, int businessType, String businessId, String
            parameterString,
                             String notes, OrderStatusType orderStatusType, List<OrderItem> orderItems) throws
            Exception {
        String desc = "";//Utility.getDesc(OrderStatusType.class, orderStatusType);


        //orderItems.stream().mapToDouble(p->new BigDecimal(p.getPrice())).sum();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
//            Product product = productDao.loadProductById(item.getProductId());
//            if (product == null) {
//                throw new Exception("不存在的商品Id:" + item.getProductId());
//            }
            totalPrice = totalPrice.add(new BigDecimal(item.getPrice()));
            orderItemDao.createOrderItem(orderId, item.getProductId(), item.getPrice());//  )
        }
        Order order = orderDao.createOrder(orderId,
                businessType,
                businessId,
                parameterString,
                orderStatusType.getValue(),
                notes,
                new Timestamp(System.currentTimeMillis()), desc, totalPrice.toPlainString());

        order.setOrderItems(orderItems);
        return order;
    }

    public Order loadOrder(String orderId) {
        Order order = orderDao.loadOrder(orderId);
        return order;
    }

    public boolean exist(String orderId) {
        return orderDao.exists(orderId);
    }

    public void updateOrderStatus(String orderId, OrderStatusType status, String notes) {
        orderDao.updateOrderStatus(orderId, status, notes);
    }
}
