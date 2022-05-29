package com.dc.allo.biznotice.mapper.banner;

import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: BannerMapper
 * date: 2020年05月06日 18:20
 * author: qinrenchuan
 */
@Mapper
public interface BannerMapper {

    /**
     * 通用banner查询
     * @param bannerType
     * @param filterType
     * @param language
     * @return java.util.List<com.erban.main.vo.BannerVo>
     * @author qinrenchuan
     * @date 2020/5/6/0006 18:23
     */
    @Select({"<script>"
                + " SELECT "
                    + " banner_id bannerId, banner_name bannerTitle, "
                    + " banner_pic bannerPic, sub_name bannerSubTitle, "
                    + " skip_type actionType, skip_uri actionUrl , "
                    + " seq_no seqNo "
                + " FROM banner "
                + " WHERE is_del=0 "
                    + " AND banner_type=#{bannerType} "
                    + " AND (language=#{language} OR language='all')"
                    + " AND banner_status=1"
            + "</script>"})
    List<CommonBannerItem> queryBanners(@Param("bannerType") Byte bannerType,
            @Param("language") String language);
}
