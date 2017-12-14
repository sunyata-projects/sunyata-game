package org.sunyata.game.store.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.order.models.Product;

/**
 * Created by leo on 17/11/15.
 */
@Component
public class ProductDao {
    @Autowired
    ProductMapper orderMapper;

    public Product loadProduct(String productCode) {
        return orderMapper.getProduct(productCode);
    }

    public Product loadProductById(int productCode) {
        return orderMapper.getProductById(productCode);
    }
}
