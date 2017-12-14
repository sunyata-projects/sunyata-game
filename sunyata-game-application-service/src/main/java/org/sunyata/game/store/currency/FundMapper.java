/*
 *
 *
 *  * Copyright (c) 2017 Leo Lee(lichl.1980@163.com).
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  * use this file except in compliance with the License. You may obtain a copy
 *  * of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *  *
 *
 */

package org.sunyata.game.store.currency;

import org.apache.ibatis.annotations.*;
import org.sunyata.core.mapper.MapperInterface;
import org.sunyata.game.currency.models.FundBalance;
import org.sunyata.game.currency.models.FundChange;
import org.sunyata.game.currency.models.FundDetail;

/**
 * Created by leo on 17/3/20.
 */
@Mapper
public interface FundMapper extends MapperInterface {

    @Select("select * from FundBalance where account=#{account} and fundAccountType=#{fundAccountType}")
    FundBalance getFundBalance(@Param("account") String account, @Param("fundAccountType") int fundAccountType);

    @Insert("INSERT INTO FundBalance(account,fundAccountType,actualBalance,updateDateTime," +
            "funChangeId,accountName) " +
            "VALUES(#{account},#{fundAccountType},#{actualBalance},#{updateDateTime}," +
            "#{funChangeId},#{accountName})")
    @Options(keyProperty = "id", keyColumn = "id",useGeneratedKeys = true)
    Integer insertFundBanalce(FundBalance balance);

    @Insert("INSERT INTO FundDetail(orderId,account,fundAccountType,fundDetailType,amt,notes,createDateTime) " +
            "VALUES(#{orderId},#{account},#{fundAccountType},#{fundDetailType},#{amt},#{notes},#{createDateTime})")
    @Options(keyProperty = "id", keyColumn = "id",useGeneratedKeys = true)
    Integer createFundDetail(FundDetail fundDetail);

    @Insert("INSERT INTO FundChange(detailId,account,fundAccountType,availableAmtVariation,actualAmtVariation,actualBalance,notes,createDateTime,fundDetailType) " +
            "VALUES(#{detailId},#{account},#{fundAccountType},#{availableAmtVariation},#{actualAmtVariation}," +
            "#{actualBalance},#{notes},#{createDateTime},#{fundDetailType})")
    @Options(keyProperty = "id", keyColumn = "id",useGeneratedKeys = true)
    Integer createFundChange(FundChange fundChange);

    @Update("UPDATE FundBalance SET actualBalance=#{actualBalance}," +
            "updateDateTime=#{updateDateTime},funChangeId=#{funChangeId} WHERE id=#{id}")
    void updateFundBalance(FundBalance balance);
}
