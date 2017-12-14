package org.sunyata.game.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.order.models.Product;
import org.sunyata.game.store.order.ProductDao;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class ProductDomainService {
    @Autowired
    ProductDao productDao;
    public Product loadProduct(String productCode) {
        return productDao.loadProduct(productCode);
    }

    public Product loadProductById(int productCode) {
        return productDao.loadProductById(productCode);
    }
}
