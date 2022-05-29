package com.dc.allo.rank.service.rank.chain;

import com.dc.allo.common.component.chain.AbstractChainNode;
import com.dc.allo.common.component.chain.ChainRegister;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.domain.rank.RankRecordContext;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Slf4j
@Component
public class RankValidCheckProcessor extends AbstractChainNode<RankRecordContext> {
    @Override
    public boolean doProcess(RankRecordContext context) throws Exception {
        if (context == null || context.getRank() == null || context.getRecord() == null) {
            log.warn(Constant.Rank.WARN + "RankValidCheckProcessor 上下文异常 context:{}", context);
            return false;
        }
        if (!checkValidTime(context.getRank(), context.getRecord())) {
            log.warn(Constant.Rank.WARN + "RankValidCheckProcessor 榜单时效性校验不通过 context:{}", context);
            return false;
        }
        return fieldCheck(context.getRank(), context.getRecord());
    }

    /**
     * 榜单时效性校验
     *
     * @param rank
     * @param record
     * @return
     */
    private boolean checkValidTime(Rank rank, RankDataRecord record) {
        Date now = new Date();
        //不允许记录时间或榜单时效范围值为空
        if (record.getTimestamp() == null || rank.getStartTime() == null || rank.getEndTime() == null) {
            return false;
        }
        Date recordTime = new Date(record.getTimestamp());
        Date start = rank.getStartTime();
        Date end = rank.getEndTime();
        //当前时间是否处于榜单时效范围
        boolean nowTimeCheck = ((now.after(start) && now.before(end)) || now.equals(start) || now.equals(end));
        //数据记录时间是否处于榜单时效范围
        boolean recordTimeCheck = ((recordTime.after(start) && recordTime.before(end)) || recordTime.equals(start) || recordTime.equals(end));
        return nowTimeCheck && recordTimeCheck;
    }

    private boolean fieldCheck(Rank rank, RankDataRecord record) {
        //bizId 分割逻辑校验
        if (rank.isDivideByBizId() && StringUtils.isBlank(record.getBizId())) {
            log.warn(Constant.Rank.WARN + "RankValidCheckProcessor 榜单需要分割key 但数据中不存在bizId 不予记录 rank:{} record:{}", rank, record);
            return false;
        }
        //频道类型榜单需要频道id
        if (Constant.Rank.RankMemberType.ROOM.equals(rank.getMemberType())) {
            if (StringUtils.isEmpty(record.getRoomId())) {
                log.warn(Constant.Rank.WARN + "RankValidCheckProcessor 榜单成员类型为[" + rank.getMemberType().name() + "] 但数据中的频道id为空 不予记录 rank:{} record:{}", rank, record);
                return false;
            }
        }
        //分类频道类型榜单需要频道分类id
        if (rank.isSpecifyRoomType()) {
            if (record.getRoomType() == null) {
                log.warn(Constant.Rank.WARN + "RankValidCheckProcessor 榜单需要根据频道分类id过滤 但数据中的频道分类id为空 不予记录 rank:{} record:{}", rank, record);
                return false;
            }
            //只记录与榜单配置的频道分类匹配的数据
            if (!record.getRoomType().equals(rank.getRoomTypeId())) {
                //这种情况属于正常业务，所以打info日志
                log.info(Constant.Rank.INFO + "RankValidCheckProcessor 榜单需要根据频道分类id过滤 但数据中的频道分类id与频道不匹配 不予记录 rank:{} record:{}", rank, record);
                return false;
            }
        }
        //todo 针对某些bizId过滤逻辑

        return true;
    }

    @Override
    public ChainRegister belongTo() {
        return ChainRegister.RANK_DATA_PROCESS_CHAIN;
    }

    @Override
    public Integer priority() {
        return 1;
    }
}
