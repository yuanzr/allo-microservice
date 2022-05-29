package com.dc.allo.user.service.country.cache;

import com.dc.allo.rpc.domain.country.Country;

/**
 * @ClassName: CountryServiceCache
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/21:39
 */
public interface CountryServiceCache {

    /**
     * 查询缓存
     * @param countryId
     * @return
     */
    Country getCountry(Integer countryId);

    /**
     * 加入缓存
     * @param userInfo
     */
    void addCountry(Country userInfo);

}
