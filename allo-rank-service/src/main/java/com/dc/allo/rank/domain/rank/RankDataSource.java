package com.dc.allo.rank.domain.rank;

import com.dc.allo.common.component.chain.ChainManager;
import com.dc.allo.common.component.chain.ChainRegister;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@ToString
public class RankDataSource {
    /**
     * 数据源配置
     */
    private RankDatasourceConfig config;
    /**
     * 当前数据源绑定的榜单列表
     */
    private List<Rank> ranks = new ArrayList<>();
    /**
     * 空对象
     */
    public final static RankDataSource NULL = new RankDataSource();

    //编写一个泛型方法对异常进行包装
    static <E extends Exception> void doThrow(Exception e) throws E {
        throw (E) e;
    }

    /**
     * 绑定榜单
     *
     * @param rankList
     */
    public void bindRank(List<Rank> rankList) {
        if (CollectionUtils.isNotEmpty(rankList)) {
            this.ranks.addAll(rankList);
        }
    }

    /**
     * 接收 & 处理数据
     *
     * @param rankDataRecord
     * @param chainManager
     */
    public void acceptData(RankDataRecord rankDataRecord, ChainManager chainManager) throws Exception {
        ranks.parallelStream().forEach((rank) -> {
            if (Rank.isNull(rank)) {
                return;
            }
            RankRecordContext context = RankRecordContext.builder()
                    .rank(rank)
                    .record(rankDataRecord.clone())
                    .build();
            try {
                chainManager.process(ChainRegister.RANK_DATA_PROCESS_CHAIN, context);
            } catch (Exception e) {
                doThrow(e);
            }
        });
    }

    public RankDatasourceConfig getConfig() {
        return config;
    }

    public void setConfig(RankDatasourceConfig config) {
        this.config = config;
    }

    public static boolean isNull(RankDataSource rankDataSource) {
        return rankDataSource == null || RankDataSource.NULL.equals(rankDataSource);
    }

    public static boolean isNotNull(RankDataSource rankDataSource) {
        return !isNull(rankDataSource);
    }

    public static boolean valid(RankDataRecord record, RankDataSource dataSource) {
        if (record != null && isNotNull(dataSource)) {
            //todo 密钥check,md5匹配
            if (dataSource.getConfig() != null && record.getDataSourceSecret().equals(dataSource.getConfig().getSecret())) {
                return true;
            }
            //todo 根据dataSourceType 校验 record里的字段是否满足
        }
        return false;
    }
}
