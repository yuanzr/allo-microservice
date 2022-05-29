package com.dc.allo.task.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.Constant.RankActType;
import com.dc.allo.common.constants.Constant.SignInTaskType;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.task.constant.TaskBusiStatus;
import com.dc.allo.task.constant.TaskConstant;
import com.dc.allo.task.domain.entity.CommonTaskPrize;
import com.dc.allo.task.domain.entity.CommonTaskType;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.domain.vo.CommonTaskPrizeVO;
import com.dc.allo.task.domain.vo.CommonTaskVO;
import com.dc.allo.task.mapper.CommonTaskPrizeMapper;
import com.dc.allo.task.service.CommonTaskPrizeService;
import com.dc.allo.task.service.CommonTaskService;
import com.dc.allo.task.service.cache.CommonTaskPrizeCache;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * description: CommonTaskPrizeServiceImpl
 *
 * @date: 2020年05月26日 11:32
 * @author: qinrenchuan
 */
@Slf4j
@Service
public class CommonTaskPrizeServiceImpl implements CommonTaskPrizeService {

    @Autowired
    private CommonTaskService taskService;

    @Autowired
    private CommonTaskPrizeCache commonTaskPrizeCache;

    @Autowired
    private CommonTaskPrizeMapper taskPrizeMapper;
    @Autowired
    private RedisUtil redisUtil;


    @Value("${xchat.web.domain}")
    private String xchatApiDomain;

    /**
     * 分页查询
     * @param queryVo
     * @param pageNumber
     * @param pageSize
     * @return com.github.pagehelper.PageInfo<com.dc.allo.task.domain.vo.CommonTaskPrizeVO>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:46
     */
    @Override
    public PageInfo<CommonTaskPrizeVO> listByPage(CommonTaskPrizeVO queryVo, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<CommonTaskPrizeVO> list = taskPrizeMapper.query(queryVo);
        return new PageInfo<>(list);
    }

    /**
     * 任务奖励数据初始化
     * @param actCode
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:47
     */
    @Override
    public void initTaskPrizeConfig(Byte actCode) {
        // 根据任务actCode查询任务
        CommonTaskVO taskVO = taskService.getByCode(actCode);
        if (taskVO == null) {
            throw new DCException(TaskBusiStatus.TASK_NOT_FOUND);
        }

        if (TaskConstant.TaskStatus.ENABLE.equals(taskVO.getStatus())) {
            throw new DCException(TaskBusiStatus.TASK_PRIZE_INIT_FAIL);
        }

        taskPrizeMapper.deleteByTaskId(taskVO.getId());

        // 初始化数据
        List<CommonTaskPrizeVO> prizeVOS = new ArrayList<>();
        for (CommonTaskType taskType : taskVO.getTaskTypes()) {
            for (byte i = 1; i <= taskVO.getLevelNum(); i++) {
                CommonTaskPrizeVO prizeVO = new CommonTaskPrizeVO();
                prizeVO.setTaskId(taskVO.getId());
                prizeVO.setTaskTypeId(taskType.getId());
                prizeVO.setLevel(Byte.valueOf(i));
                prizeVOS.add(prizeVO);
            }
        }

        taskPrizeMapper.insertBatch(prizeVOS);
        taskService.updateTaskStatus(taskVO.getId(), TaskConstant.TaskStatus.ENABLE);
    }

    /**
     * 设置任务奖励礼包
     * @param taskPrizeId
     * @param prizePackageId
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:47
     */
    @Override
    public void setPrizePackage(Long taskPrizeId, Long prizePackageId) {
        taskPrizeMapper.setPrizePackage(taskPrizeId, prizePackageId);
    }

    /**
     * 根据任务code和等级查询奖品
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskPrize>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:19
     */
    @Override
    public List<CommonTaskPrize> getPackageIdByActTypeAndLevel(Byte actCode, Byte level, Byte taskTypeCode) {
        return taskPrizeMapper.getPackageIdByActTypeAndLevel(actCode, level, taskTypeCode);
    }


    /**
     * 根据任务和签到等级查询签到奖励列表
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:29
     */
    @Override
    public List<PrizeRankActItem> getPrizePackageByActTypeAndLevel(Byte actCode, Byte level, Byte taskTypeCode) {
        List<CommonTaskPrize> taskPrizeList = getPackageIdByActTypeAndLevel(actCode, level, taskTypeCode);
        if (taskPrizeList == null || taskPrizeList.size() == 0) {
            return new ArrayList<>();
        }

        Long packageId = taskPrizeList.get(0).getPackageId();
        List<Long> packageIds = new ArrayList<>();
        packageIds.add(packageId);

        Map<Long, List<PrizeRankActItem>> prizePackageMap = queryPrizesByPackageIds(packageIds);
        return prizePackageMap.get(packageId);
    }

    /**
     * 查询所有签到奖品
     * @param actCode
     * @return java.util.Map<java.lang.Byte,java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>>
     * @author qinrenchuan
     * @date 2020/5/27/0027 16:10
     */
    @Override
    public Map<Byte, List<PrizeRankActItem>> getAllPrizesByActCode(Byte actCode) {
        Map<Byte, List<PrizeRankActItem>> result = new HashMap<>();

        List<CommonTaskPrize> commonTaskPrizes = getPackageIdByActTypeAndLevel(
                RankActType.SIGN_IN, null, SignInTaskType.CUMULATIVE);

        Map<Byte, Long> timePackageMap = new HashMap<>(TaskConstant.DIGITAL_SEVEN);


        List<Long> packageIds = new ArrayList<>();
        for (CommonTaskPrize taskPrize : commonTaskPrizes) {
            timePackageMap.put(taskPrize.getLevel(), taskPrize.getPackageId());
            if (!packageIds.contains(taskPrize.getPackageId())) {
                packageIds.add(taskPrize.getPackageId());
            }
        }
        Map<Long, List<PrizeRankActItem>> packagePrizeMap = queryPrizesByPackageIds(packageIds);

        Set<Entry<Byte, Long>> timesEntries = timePackageMap.entrySet();
        for (Entry<Byte, Long> entry : timesEntries) {
            Byte times = entry.getKey();
            Long packageId = entry.getValue();
            result.put(times, packagePrizeMap.get(packageId));
        }
        return result;
    }

    /**
     * 根据packageId查询奖励
     * @param packageIds  礼包id，多个用英文逗号分隔
     * @return java.util.Map<java.lang.Long,java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>>
     * @author qinrenchuan
     * @date 2020/5/28/0028 11:24
     */
    private Map<Long, List<PrizeRankActItem>> queryPrizesByPackageIds(List<Long> packageIds) {
        Map<Long, List<PrizeRankActItem>> packagePrizeMap = new HashMap<>();

        Map<Long, List<PrizeRankActItem>> cachePackagePrizeMap = commonTaskPrizeCache.queryPrizesByPackageIds(packageIds);

        Iterator<Long> iterator = packageIds.iterator();
        while (iterator.hasNext()) {
            Long packageId = iterator.next();
            List<PrizeRankActItem> items = cachePackagePrizeMap.get(packageId);
            if (items != null && items.size() > 0) {
                iterator.remove();
            }
        }


        if (packageIds == null || packageIds.size() == 0) {
            log.info("queryPrizesByPackageIds cache all.");
            return cachePackagePrizeMap;
        }

        try {
            StringBuilder sb = new StringBuilder();
            for (Long packageId : packageIds) {
                sb.append(packageId).append(",");
            }
            Map<String, String> map = new HashMap<>();
            map.put("packageIds", sb.toString());
            Map resultMap = HttpUtils.doGetWithClass(
                    xchatApiDomain + TaskConstant.queryPrizePackageByPackageIds,
                    map, Map.class);

            log.info("queryPrizesByPackageIds resultMap: {}", resultMap);

            if (resultMap == null || resultMap.size() == 0) {
                return packagePrizeMap;
            }

            Set<Entry<Object, Object>> entries = resultMap.entrySet();
            for (Entry<Object, Object> entry : entries) {
                Long packageId = Long.valueOf(entry.getKey().toString());
                List<PrizeRankActItem> prizeRankActItems = JSONObject.parseArray(
                        entry.getValue().toString(), PrizeRankActItem.class);
                packagePrizeMap.put(packageId, prizeRankActItems);
            }
            commonTaskPrizeCache.putPackagePrizes(packagePrizeMap);

            packagePrizeMap.putAll(cachePackagePrizeMap);
            return packagePrizeMap;
        } catch (Exception e) {
            log.error("queryPrizesByPackageIds http error", e);
            return packagePrizeMap;
        }
    }


    @Override
    public List<CommonTaskPrizeVO> listPackageByActCode(Byte actCode,Long uid,Boolean isShowStatus) {
        //1.根据活动code查出task
        CommonTaskVO taskVO = taskService.getTaskDetailByCode(actCode);
        //2.查询根据taskId查询出所有礼包
        CommonTaskPrizeVO queryVo = new CommonTaskPrizeVO();
        queryVo.setTaskId(taskVO.getId());
        PageInfo<CommonTaskPrizeVO> pageInfo = listByPage(queryVo, 1, 20);
        List<CommonTaskPrizeVO> packageList = pageInfo.getList();
        List<Long> packageIds = packageList.stream().map(CommonTaskPrizeVO::getPackageId).collect(Collectors.toList());
        //3.根据礼包ID查询奖品明细
        Map<Long, List<PrizeRankActItem>> packagePrizeMap = queryPrizesByPackageIds(packageIds);
        //4.将奖品明细设置到礼包中
        for (CommonTaskPrizeVO packageVo : packageList) {
            List<PrizeRankActItem> prizeList = packagePrizeMap.get(packageVo.getPackageId());
            packageVo.setPrizeList(prizeList);
            if (isShowStatus && uid != null){
                String uidStr = uid.toString();
                //设置领奖状态
                Byte level = packageVo.getLevel();
                String statusKey = RedisKeyUtil.getKey(RedisKeyUtil.RedisKey.activity_job_user.name(), RankActType.CAMEL_CHBARGE + ":" + level);
                Object jobStatusObj = redisUtil.hGet(statusKey, uidStr);
                String jobStatus = "";
                if (jobStatusObj!= null && Constant.ActJobProgress.PENGDING.equals(jobStatusObj.toString()) ){
                    jobStatus = Constant.ActJobProgress.PENGDING;
                }else{
                    jobStatus = Constant.ActJobProgress.UNFINISH;
                }
                packageVo.setJobStatus(jobStatus);
            }
        }
        packageList.sort(Comparator.comparing(pack -> pack.getPackageId()));
        //6.返回礼包详情列表
        return packageList;
    }
}
