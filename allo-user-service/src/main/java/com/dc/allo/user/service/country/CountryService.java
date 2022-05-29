package com.dc.allo.user.service.country;

import com.dc.allo.rpc.domain.country.Country;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CountryService
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/21:39
 */
@Service
public interface CountryService {

    Country getCountry(Integer countryId);


}
