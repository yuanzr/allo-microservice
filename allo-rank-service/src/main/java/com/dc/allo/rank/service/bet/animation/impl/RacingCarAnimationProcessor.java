package com.dc.allo.rank.service.bet.animation.impl;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rank.service.bet.BetFacadeService;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rank.service.bet.animation.AbstractAnimationProcessor;
import com.dc.allo.rpc.domain.bet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by zhangzhenjun on 2020/6/16.
 */
@Service
@Slf4j
public class RacingCarAnimationProcessor extends AbstractAnimationProcessor {

    @Autowired
    BetGameRoundService gameRoundService;

    @Autowired
    BetFacadeService facadeService;

    @Override
    public long doGenAnimation(BetActInfo actInfo, long gameId, long winner) throws Exception {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        if (gameId <= 0) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        if (winner <= 0) {
            return BetConstants.GAME_ROUND_ERR.NO_WINNER;
        }
        int animationDuration = actInfo.getAnimationDuration();
        if(animationDuration<=0){
            return BetConstants.GAME_ROUND_ERR.ANIMATION_DURATION_ERR;
        }
        int totalDistance = 100;
        int perDistance = totalDistance/animationDuration;

        BetGameRoundInfo gameRound = facadeService.getCurBetGameRound(actInfo.getActId(), 0);
        if(gameRound == null || CollectionUtils.isEmpty(gameRound.getSpirits())){
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        List<BetGameSpiritInfo> spirits = gameRound.getSpirits();
        BetAnimationInfo animationInfo = genAnimationData(gameId,spirits, winner, perDistance, animationDuration);
        String animations = JsonUtils.toJson(animationInfo);
        if(MapUtils.isNotEmpty(animationInfo.getAnimations())){
            log.info(BetConstants.INFO_LOG + " animations:{}", animations);
            return gameRoundService.updateAnimations(gameId,animations);
        }
        return 0;
    }

    @Override
    public BetConstants.AnimationModelType getAnimationModelType() {
        return BetConstants.AnimationModelType.RACINGCAR;
    }

    /**
     * 生成动画数据
     * @param spirits
     * @param winner
     * @param perDistance
     * @param duration
     * @return
     */
    private BetAnimationInfo genAnimationData(long gameId,List<BetGameSpiritInfo> spirits,long winner,int perDistance,int duration){
        BetAnimationInfo animationInfo = new BetAnimationInfo();
        animationInfo.setAnimationModelType(BetConstants.AnimationModelType.RACINGCAR.val());
        Map<Long,BetAnimationData> animationMap = new HashMap<>(spirits.size());
        for(BetGameSpiritInfo spirit:spirits){
            animationMap.put(spirit.getSpiritId(),genData(perDistance, duration, spirit.getSpiritId() == winner,spirit.getSpiritId(),gameId));
        }
        if(MapUtils.isNotEmpty(animationMap)){
            animationInfo.setAnimations(animationMap);
        }
        return animationInfo;
    }

    /**
     * 生成数据
     * @param perDistance
     * @param duration
     * @param isWin
     * @return
     */
    private BetAnimationData genData(int perDistance,int duration,boolean isWin,long spiritId,long gameId){
        BetAnimationData animationData = new BetAnimationData();
        List<BetRacingCarAnimationData> datas = new ArrayList<>(duration);
        BetRacingCarAnimationData data = null;
        int animationDuration = duration;
        int distance = 0;
        int stepTime = 0;
        while(duration>0){
            data = new BetRacingCarAnimationData();
            int randomTime = randomStepTime();
            stepTime += randomTime;
            duration -= randomTime;
            if(duration <= 0){
                if(isWin){
                    data.setTime(animationDuration);
                }else{
                    data.setTime(computeFinalTime(animationDuration));
                }
                gameRoundService.setGameRoundRank(gameId,spiritId,data.getTime());
                data.setDistance(100);
            }else{
                data.setTime(stepTime);
                data.setDistance(computeRandomDistance(distance, perDistance,randomTime, isWin));
            }
            distance = data.getDistance();
            datas.add(data);
        }
        animationData.setRacingCarAnimations(datas);
        return animationData;
    }

    private int randomStepTime(){
        return new Random().nextInt(5-3+1)+3;
    }

    /**
     * 计算失败组跑完全程最终时间
     * @param duration
     * @return
     */
    private int computeFinalTime(int duration){
        return duration+new Random().nextInt(2)+1;
    }

    /**
     * 计算每次距离
     * @param distance
     * @param perDistance
     * @param isWin
     * @return
     */
    private int computeDistance(int distance ,int perDistance,int randomTime,boolean isWin){
        if(isWin){
            distance += new Random().nextInt(perDistance*randomTime-3*randomTime+1)+3*randomTime;
        }else{
            distance += new Random().nextInt(perDistance*randomTime-2*randomTime+1)+2*randomTime;
        }
        return distance;
    }

    private int computeRandomDistance(int distance ,int perDistance,int randomTime,boolean isWin){
        if(isWin){
            distance = new Random().nextInt(75-35+1)+35;
        }else{
            distance = new Random().nextInt(70-30+1)+30;
        }
        return distance;
    }

}
