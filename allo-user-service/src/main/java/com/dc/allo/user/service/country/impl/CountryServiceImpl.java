package com.dc.allo.user.service.country.impl;

import com.dc.allo.rpc.domain.country.Country;
import com.dc.allo.user.mapper.country.CountryMapper;
import com.dc.allo.user.service.country.CountryService;
import com.dc.allo.user.service.country.cache.CountryServiceCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CountryServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/21:44
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryServiceCache countryServiceCache;

    @Override
    public Country getCountry(Integer countryId) {
        Country country = countryServiceCache.getCountry(countryId);
        if (country == null) {
             country = countryMapper.getByCountryId(countryId);
            if (country != null) {
                countryServiceCache.addCountry(country);
            }
        }
        return country;
    }
}
