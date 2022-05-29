package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.RankScoreNoticeConfig;

/**
 * Created by zhangzhenjun on 2020/5/12.
 */
public interface ScoreNoticeConfigService {

    /**
     * 分值通知
     * @param noticeConfig
     * @return
     */
    long addScoreNotice(RankScoreNoticeConfig noticeConfig);
}
