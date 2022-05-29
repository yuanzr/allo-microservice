package com.dc.allo.user.service.headwear.cache;

import com.dc.allo.rpc.domain.user.headwear.Headwear;

/**
 * description: HeadwearCache
 * date: 2020年04月07日 17:01
 * author: qinrenchuan
 */
public interface HeadwearCache {
    /**
     * 根据id查询
     * @param id
     * @return com.dc.allo.rpc.domain.user.headwear.Headwear
     * @author qinrenchuan
     * @date 2020/4/7/0007 17:20
     */
    Headwear getById(Long id);
}
