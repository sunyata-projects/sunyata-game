package org.sunyata.game.store.currency;

import org.sunyata.core.util.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.currency.models.FundAccountType;
import org.sunyata.game.currency.models.FundBalance;
import org.sunyata.game.currency.models.FundChange;
import org.sunyata.game.currency.models.FundDetail;
import org.sunyata.game.order.exceptions.FundBalanceNotEnoughException;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by leo on 17/11/16.
 */
@Component
public class FundDao {
    protected final Logger LOGGER = LoggerFactory.getLogger(FundDao.class);

    @Autowired
    SqlSessionTemplate sessionTemplate;


    public Integer createFundDetail(String orderId, String account, Integer fundAccountType, Integer fundDetailType,
                                    String amt, String notes, Timestamp createDateTime) {
        try (SqlSession session = sessionTemplate.getSqlSessionFactory().openSession(false)) {
        } catch (Exception ex) {

        }
        return null;
    }


    public Integer createFundChange(Integer detailId, String account, Integer fundAccountType, String
            availableAmtVariation, String actualAmtVariation, String availableBalance, String actualBalance, String
                                            notes, Timestamp createDateTime, Integer fundDetailType) {
        return null;
    }


    public void updateFundBalance(FundBalance fundBalance) {

    }

    public void insertFundBalance(FundBalance balance) {

    }

    public FundBalance getFundBalanceById(Integer balanceId) {
        return null;
    }

    @Autowired
    FundMapper fundMapper;

    public void createFundWithoutLock(String orderId, String account, String accountName, FundAccountType
            fundAccountType, int businessType, String amt, String
                                              actualAmtVariation, String notes) throws Exception {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {
//            FundMapper mapper = session.getMapper(FundMapper.class);
            FundMapper mapper = fundMapper;
            FundBalance balance = mapper.getFundBalance(account, fundAccountType.getValue());
            if (balance == null) {
                balance = new FundBalance()
                        .setAccount(account)
                        .setFundAccountType(fundAccountType.getValue())
                        //.setAvailableBalance(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP))
                        .setActualBalance(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP))
                        .setUpdateDateTime(now)
                        .setAccountName(accountName)
                        .setFunChangeId(-1);
                mapper.insertFundBanalce(balance);
            }
            LOGGER.info("FundBalance Id:{}", balance.getId());
//            //当前可用余额
//            BigDecimal currAvailableBalance = balance.getAvailableBalance();
//            LOGGER.info("orderId:" + orderId + "当前可用余额:" + currAvailableBalance.toPlainString());
            //当前实际余额
            BigDecimal currActualBalance = balance.getActualBalance();
            LOGGER.info("orderId:" + orderId + "当前实际余额:" + currActualBalance.toPlainString());

            //判断余额是否足够
//            BigDecimal availableAmtVariationB = new BigDecimal(availableAmtVariation);
            BigDecimal actualAmtVariationB = new BigDecimal(actualAmtVariation);

//            BigDecimal lastestAvailableBalanceB = currAvailableBalance.add(availableAmtVariationB);
            BigDecimal lastestActualBalanceB = currActualBalance.add(actualAmtVariationB);

            if (Utils.le(lastestActualBalanceB, BigDecimal.ZERO)) {
                throw new FundBalanceNotEnoughException(fundAccountType + "帐户余额不足");
            }

            //资金明细保存
            BigDecimal amtScale = new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP);
            FundDetail fundDetail = new FundDetail();
            fundDetail.setOrderId(orderId).setAccount(account).setFundAccountType(fundAccountType.getValue())
                    .setFundDetailType(businessType).setAmt(amtScale).setNotes(notes).setCreateDateTime(now);
            mapper.createFundDetail(fundDetail);
            LOGGER.info("FundDetail Id:{}", fundDetail.getId());

//            lastestAvailableBalanceB = lastestAvailableBalanceB.setScale(2, BigDecimal.ROUND_HALF_UP);
            lastestActualBalanceB = lastestActualBalanceB.setScale(2, BigDecimal.ROUND_HALF_UP);
            FundChange fundChange = new FundChange();
            fundChange.setDetailId(fundDetail.getId()).setAccount(account).setFundAccountType(fundAccountType
                    .getValue())
                    .setActualAmtVariation(actualAmtVariationB)
                    .setActualBalance(lastestActualBalanceB)
                    .setNotes(notes).setCreateDateTime(now).setFundDetailType(businessType);
            mapper.createFundChange(fundChange);
            LOGGER.info("FundChange Id:{}", fundChange.getId());
            balance.setActualBalance(lastestActualBalanceB)
                    .setUpdateDateTime(now).setFunChangeId(fundChange.getId());
            mapper.updateFundBalance(balance);
            //session.commit();
        } catch (Exception ex) {
            LOGGER.error(ExceptionUtils.getStackTrace(ex));
            throw ex;
        }
    }


}
