package org.sunyata.game.store.order;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.order.models.OrderStatusType;
import org.sunyata.game.order.models.Order;

import java.sql.Timestamp;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class OrderDao {
    @Autowired
    OrderMapper orderMapper;


    public Order createOrder(String orderId, Integer businessType, String businessId, String parameterString,
                             Integer orderStatusType, String notes, Timestamp createDateTime, String
                                      orderStatusTypeDesc, String totalPrice) {
        Order order = new Order();
        order.setId(orderId).setBusinessType(businessType).setBusinessId(businessId).setParameterString(parameterString)
                .setOrderStatusType(orderStatusType).setNotes(notes).setCreateDateTime(createDateTime)
                .setOrderStatusTypeDesc(orderStatusTypeDesc).setTotalPrice(totalPrice);
        orderMapper.insertOrder(order);
        return order;
    }


    public Order loadOrder(String orderId) {
        return orderMapper.getOrder(orderId);
    }


    public boolean exists(String orderId) {
        String returnOrderId = orderMapper.getOrderId(orderId);
        return !StringUtils.isEmpty(returnOrderId);
    }


    public void updateOrderStatus(String orderId, OrderStatusType status, String notes) {
        orderMapper.updateOrderStatus(orderId, status.getValue(), "", notes);
    }
}
