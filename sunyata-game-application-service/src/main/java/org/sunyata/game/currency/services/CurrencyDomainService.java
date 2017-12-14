package org.sunyata.game.currency.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.currency.models.FundAccountType;
import org.sunyata.game.store.currency.FundDao;

/**
 * Created by leo on 17/11/17.
 */
@Component
public class CurrencyDomainService {
    @Autowired
    FundDao fundDao;

    public void createFundWithoutLock(String orderId, String account, String accountName, FundAccountType
            fundAccountType, int businessType, String amt, String
                                              actualAmtVariation, String notes) throws Exception {
        fundDao.createFundWithoutLock(orderId, account, accountName, fundAccountType, businessType, amt,
                actualAmtVariation,"");
    }
}
