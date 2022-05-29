package com.dc.allo.rank.mapper.award;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.common.utils.TableUtils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.domain.award.CommonAwardOfPackage;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rank.domain.award.CommonAwardPackage;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Mapper
public interface CommonAwardUtilMapper {

    /**
     * 判断是否存在下月表
     * @return
     */
    @SelectProvider(value = CommonAwardUtilMapperProvider.class,method = "existNextMonthRecordTable")
    int existNextMonthRecordTable();

    /**
     * 创建下月表
     */
    @InsertProvider(value = CommonAwardUtilMapperProvider.class,method = "createNextMonthRecordTable")
    void createNextMonthRecordTable();

    /**
     * 判断是否存在下月表
     * @return
     */
    @SelectProvider(value = CommonAwardUtilMapperProvider.class,method = "existNextMonthDetailTable")
    int existNextMonthDetailTable();

    /**
     * 创建下月表
     */
    @InsertProvider(value = CommonAwardUtilMapperProvider.class,method = "createNextMonthDetailTable")
    void createNextMonthDetailTable();

    /**
     * 新增公共奖品配置
     *
     * @param name
     * @param icon
     * @param releaseType
     * @param releaseId
     * @param price
     * @param priceDisplay
     * @param extend
     * @return
     */
    @Insert("insert into common_award (name,icon,release_type,release_id,price,price_display,extend) " +
            "values(#{name},#{icon},#{releaseType},#{releaseId},#{price},#{priceDisplay},#{extend})")
    long addCommonAward(@Param(value = "name") String name, @Param(value = "icon") String icon, @Param(value = "releaseType") int releaseType,
                        @Param(value = "releaseId") String releaseId, @Param(value = "price") int price,
                        @Param(value = "priceDisplay") int priceDisplay, @Param(value = "extend") String extend);


    /**
     * 更新奖品信息
     * @param id
     * @param name
     * @param icon
     * @param releaseType
     * @param releaseId
     * @param price
     * @param priceDisplay
     * @param extend
     * @return
     */
    @UpdateProvider(value = CommonAwardUtilMapperProvider.class,method = "updateCommonAward")
    long updateCommonAward(@Param(value = "id") long id, @Param(value = "name") String name, @Param(value = "icon") String icon, @Param(value = "releaseType") int releaseType,
                      @Param(value = "releaseId") String releaseId, @Param(value = "price") int price,
                      @Param(value = "priceDisplay") int priceDisplay, @Param(value = "extend") String extend);

    /**
     * 所有奖励
     *
     * @return
     */
    @Select("select * from common_award limit 1000")
    List<CommonAward> queryAllCommonAward();

    @Select("select count(*) from common_award")
    long countCommonAward();

    @Select("select * from common_award limit #{offset},#{pageNum}")
    List<CommonAward> page(@Param(value = "offset") long offset,@Param(value = "pageNum") int pageNum);

    /**
     * 新增礼包
     *
     * @param awardPackage
     * @return
     */
    @Insert("insert into common_award_package (name,icon,need_rate,need_purchase,purchase_type,total_count,release_count,day_count_limit,price) " +
            " values(#{awardPackage.name},#{awardPackage.icon},#{awardPackage.needRate},#{awardPackage.needPurchase}," +
            "#{awardPackage.purchaseType},#{awardPackage.totalCount},#{awardPackage.releaseCount}," +
            "#{awardPackage.dayLimitCount},#{awardPackage.price})")
    long addAwardPackage(@Param(value = "awardPackage") CommonAwardPackage awardPackage);

    /**
     * 更新礼包配置
     * @param awardPackage
     * @return
     */
    @UpdateProvider(value = CommonAwardUtilMapperProvider.class,method = "updateCommonAwardPkg")
    long updateAwardPackage(@Param(value = "awardPackage") CommonAwardPackage awardPackage);

    @Select("select count(*) from common_award_package")
    long countAwardPackage();

    @Select("select id,name,icon,need_rate,need_purchase,purchase_type,total_count,release_count,day_count_limit dayLimitCount,price,enable,ctime,utime from common_award_package limit #{offset},#{pageNum}")
    List<CommonAwardPackage> pageAwardPackage(@Param(value = "offset") long offset,@Param(value = "pageNum") int pageNum);

    @Select("select id,name,icon,need_rate,need_purchase,purchase_type,total_count,release_count,day_count_limit dayLimitCount,price,enable,ctime,utime from common_award_package limit 500")
    List<CommonAwardPackage> queryAllAwardPackage();
    /**
     * 新增礼包内奖品
     *
     * @param awardOfPackage
     * @return
     */
    @Insert("insert into common_award_of_package(package_id,award_id,act_id,rate,display_rate,daily_count_limit,total_count,release_count,award_count,valid_days,remark,enable) " +
            "values(#{awardOfPackage.packageId},#{awardOfPackage.awardId},#{awardOfPackage.actId},#{awardOfPackage.rate},#{awardOfPackage.displayRate}," +
            "#{awardOfPackage.dailyCountLimit},#{awardOfPackage.totalCount},#{awardOfPackage.releaseCount},#{awardOfPackage.awardCount}," +
            "#{awardOfPackage.validDays},#{awardOfPackage.remark},#{awardOfPackage.enable})")
    long addAwardOfPackage(@Param(value = "awardOfPackage") CommonAwardOfPackage awardOfPackage);

    /**
     * 更新礼包奖品配置
     * @param awardOfPackage
     * @return
     */
    @UpdateProvider(value = CommonAwardUtilMapperProvider.class,method = "updateAwardOfPkg")
    long updateAwardOfPackage(@Param(value = "awardOfPackage") CommonAwardOfPackage awardOfPackage);

    @Select("select count(*) from common_award_of_package")
    long countAwardOfPackage();

    @Select("select * from common_award_of_package limit #{offset},#{pageNum}")
    List<CommonAwardOfPackage> pageAwardOfPackage(@Param(value = "offset") long offset,@Param(value = "pageNum") int pageNum);

    @Select("select * from common_award_of_package where package_id = #{packageId}")
    List<CommonAwardOfPackage> getAwardOfPackage(@Param(value = "packageId") long packageId);

    /**
     * 礼包id查处奖励列表
     *
     * @param packageId
     * @return
     */
    @Select("select t1.id,t1.package_id,t1.award_id,t1.act_id,t2.name,t2.icon,t2.release_type,t2.release_id,t2.price,t2.price_display,\n" +
            "t1.rate,t1.display_rate,t1.daily_count_limit,t1.total_count,t1.release_count,t1.award_count,t1.valid_days,t2.extend,t1.remark \n" +
            "from common_award_of_package as t1\n" +
            "left join common_award as t2\n" +
            "on t1.award_id = t2.id\n" +
            "where package_id = #{packageId} and enable = 1")
    @Cached(name = "CommonAwardCache.getAwardList", key = "#packageId", cacheType = CacheType.BOTH, cacheNullValue = true, expire = 1, timeUnit = TimeUnit.MINUTES)
    List<AwardOfPackage> getAwardList(@Param(value = "packageId") long packageId);

    /**
     * 查指定id的奖励
     *
     * @param awardOfPackId
     * @return
     */
    @Select("select t1.id,t1.package_id,t1.award_id,t1.act_id,t2.name,t2.icon,t2.release_type,t2.release_id,t2.price,t2.price_display,\n" +
            "t1.rate,t1.display_rate,t1.daily_count_limit,t1.total_count,t1.release_count,t1.award_count,t1.valid_days,t2.extend,t1.remark \n" +
            "from common_award_of_package as t1\n" +
            "left join common_award as t2\n" +
            "on t1.award_id = t2.id\n" +
            "where t1.id = #{awardOfPackId} and enable = 1")
    AwardOfPackage getAward(@Param(value = "awardOfPackId") int awardOfPackId);

    /**
     * 礼包id查礼包配置
     *
     * @param id
     * @return
     */
    @Select("select * from common_award_package where id=#{id}")
    @Cached(name = "CommonAwardCache.getAwardPackageById", key = "#id", cacheType = CacheType.BOTH, cacheNullValue = true, expire = 1, timeUnit = TimeUnit.MINUTES)
    CommonAwardPackage getAwardPackageById(@Param(value = "id") long id);

    /**
     * 礼包名查礼包配置
     *
     * @param name
     * @return
     */
    @Select("select * from common_award_package where name=#{name}")
    CommonAwardPackage getAwardPackageByName(@Param(value = "name") String name);

    /**
     * 更新礼包发放数量（检查库存）
     *
     * @param packageId
     * @return
     */
    @Update("update common_award_package set release_count = release_count+1 where id = #{packageId} and release_count+1 <= total_count")
    int reducePackageStockWithCheck(@Param(value = "packageId") long packageId);

    /**
     * 更新礼包发放数量
     *
     * @param packageId
     * @return
     */
    @Update("update common_award_package set release_count = release_count+1 where id = #{packageId}")
    int reducePackageStockWithoutCheck(@Param(value = "packageId") long packageId);

    /**
     * 新增礼包发放记录
     *
     * @param uid
     * @param package_id
     * @param package_name
     * @param package_icon
     * @param seq_id
     * @param status
     * @param award_back_up
     * @return
     */
    @InsertProvider(type = CommonAwardUtilMapperProvider.class, method = "addNewReleaseRecord")
    long addNewReleaseRecord(@Param(value = "uid") long uid, @Param(value = "package_id") long package_id,
                             @Param(value = "package_name") String package_name, @Param(value = "package_icon") String package_icon,
                             @Param(value = "seq_id") String seq_id, @Param(value = "status") int status, @Param(value = "award_back_up") String award_back_up);

    /**
     * 新增礼包内奖励发放记录
     *
     * @param uid
     * @param seq_id
     * @param award_id
     * @param award_name
     * @param award_icon
     * @param award_release_type
     * @param award_release_id
     * @param award_count
     * @param award_valid_days
     * @param award_price
     * @param award_extend
     * @param status
     * @param remark
     * @param award_of_package_id
     * @return
     */
    @InsertProvider(type = CommonAwardUtilMapperProvider.class, method = "addNewReleaseRecordDetail")
    long addNewReleaseRecordDetail(@Param(value = "uid") long uid, @Param(value = "seq_id") String seq_id,
                                   @Param(value = "award_id") int award_id, @Param(value = "award_name") String award_name, @Param(value = "award_icon") String award_icon,
                                   @Param(value = "award_release_type") int award_release_type, @Param(value = "award_release_id") String award_release_id, @Param(value = "award_count") int award_count,
                                   @Param(value = "award_valid_days") int award_valid_days, @Param(value = "award_price") int award_price, @Param(value = "award_extend") String award_extend,
                                   @Param(value = "status") int status, @Param(value = "remark") String remark, @Param(value = "award_of_package_id") int award_of_package_id);

    @Update("update common_award_of_package set release_count = release_count+1 where id = #{awardOfPackageId} and release_count+1 <= total_count")
    int reduceAwardStockWithCheck(@Param(value = "awardOfPackageId") int awardOfPackageId);

    /**
     * 增加礼包内奖励发放记录
     *
     * @param awardOfPackageId
     * @return
     */
    @Update("update common_award_of_package set release_count = release_count+1 where id = #{awardOfPackageId}")
    int reduceAwardStockWithoutCheck(@Param(value = "awardOfPackageId") int awardOfPackageId);

    @Update("update common_award_of_package set release_count = release_count-1 where id = #{awardOfPackageId}")
    int recoveryAwardStock(@Param(value = "awardOfPackageId") int awardOfPackageId);

    /**
     * 更新礼包发放状态
     *
     * @param packageId
     * @param seqId
     * @param status
     * @param remark
     * @return
     */
    @UpdateProvider(type = CommonAwardUtilMapperProvider.class, method = "updateReleaseRecordStatus")
    int updateReleaseRecordStatus(@Param(value = "packageId") long packageId, @Param(value = "seqId") String seqId, @Param(value = "status") int status, @Param(value = "remark") String remark);

    /**
     * 更新礼包内奖励发放状态
     *
     * @param awardOfPackageId
     * @param seqId
     * @param status
     * @param remark
     * @return
     */
    @UpdateProvider(type = CommonAwardUtilMapperProvider.class, method = "updateReleaseRecordDetailStatus")
    int updateReleaseRecordDetailStatus(@Param(value = "awardOfPackageId") long awardOfPackageId, @Param(value = "seqId") String seqId, @Param(value = "status") int status, @Param(value = "remark") String remark);

    /**
     * 第三方序号重试
     *
     * @param thirdpartySeqId
     * @param awardOfPackageId
     * @param seqId
     * @return
     */
    @UpdateProvider(type = CommonAwardUtilMapperProvider.class, method = "recordThirdpartySeqId")
    int recordThirdpartySeqId(@Param(value = "thirdparty_seq_id") String thirdpartySeqId, @Param(value = "awardOfPackageId") long awardOfPackageId, @Param(value = "seqId") String seqId);

    class CommonAwardUtilMapperProvider {

        public String existNextMonthRecordTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer(" select count(1)as cnt from information_schema.TABLES where table_name = 'common_award_release_record_").append(tableSuffix).append("'");
            return sqlBuf.toString();
        }

        public String createNextMonthRecordTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer("create table common_award_release_record_").append(tableSuffix).append(" like common_award_release_record");
            return sqlBuf.toString();
        }

        public String existNextMonthDetailTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer(" select count(1)as cnt from information_schema.TABLES where table_name = 'common_award_release_record_detail_").append(tableSuffix).append("'");
            return sqlBuf.toString();
        }

        public String createNextMonthDetailTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer("create table common_award_release_record_detail_").append(tableSuffix).append(" like common_award_release_record_detail");
            return sqlBuf.toString();
        }

        public String updateCommonAward(@Param(value = "id") long id, @Param(value = "name") String name, @Param(value = "icon") String icon, @Param(value = "releaseType") int releaseType,
                                        @Param(value = "releaseId") String releaseId, @Param(value = "price") int price,
                                        @Param(value = "priceDisplay") int priceDisplay, @Param(value = "extend") String extend) {
            StringBuffer sqlBuf = new StringBuffer("update common_award set utime = now() ");
            if (StringUtils.isNotBlank(name)) {
                sqlBuf.append(" ,name = #{name}");
            }
            if (StringUtils.isNotBlank(icon)) {
                sqlBuf.append(" ,icon = #{icon}");
            }
            if (StringUtils.isNotBlank(releaseId)) {
                sqlBuf.append(" ,release_id = #{releaseId}");
            }
            if (releaseType > 0) {
                sqlBuf.append(" ,release_type = #{releaseType}");
            }
            if (price >= 0) {
                sqlBuf.append(" ,price = #{price}");
            }
            if (priceDisplay >= 0) {
                sqlBuf.append(" ,price_display = #{priceDisplay}");
            }
            if (StringUtils.isNotBlank(extend)) {
                sqlBuf.append(" ,extend = #{extend}");
            }
            sqlBuf.append(" where id = #{id}");
            return sqlBuf.toString();
        }

        public String updateCommonAwardPkg(@Param(value = "awardPackage") CommonAwardPackage awardPackage) {
            StringBuffer sqlBuf = new StringBuffer("update common_award_package set utime = now() ");
            if (awardPackage == null || awardPackage.getId() <= 0) {
                return sqlBuf.toString();
            }
            if (StringUtils.isNotBlank(awardPackage.getName())) {
                sqlBuf.append(" ,name = #{awardPackage.name}");
            }
            if (StringUtils.isNotBlank(awardPackage.getIcon())) {
                sqlBuf.append(" ,icon = #{awardPackage.icon}");
            }
            sqlBuf.append(" ,need_rate = #{awardPackage.needRate}")
                    .append(" ,need_purchase = #{awardPackage.needPurchase}")
                    .append(" ,enable = #{awardPackage.enable}");
            if (awardPackage.getPurchaseType() >= 1) {
                sqlBuf.append(" ,purchase_type = #{awardPackage.purchaseType}");
            }
            if (awardPackage.getDayLimitCount() >= -1) {
                sqlBuf.append(" ,day_count_limit = #{awardPackage.dayLimitCount}");
            }
            if (awardPackage.getPrice() >= 0) {
                sqlBuf.append(" ,price = #{awardPackage.price}");
            }
            if (awardPackage.getTotalCount() >= 1) {
                sqlBuf.append(" ,total_count = #{awardPackage.totalCount}");
            }

            sqlBuf.append(" where id = #{awardPackage.id}");
            return sqlBuf.toString();
        }

        public String updateAwardOfPkg(@Param(value = "awardOfPackage") CommonAwardOfPackage awardOfPackage){
            StringBuffer sqlBuf = new StringBuffer("update common_award_of_package set utime = now() ");
            if(awardOfPackage.getRate()>=0){
                sqlBuf.append(" ,rate = #{awardOfPackage.rate}");
            }
            if(awardOfPackage.getDisplayRate()>0){
                sqlBuf.append(" ,display_rate = #{awardOfPackage.displayRate}");
            }
            if(awardOfPackage.getDailyCountLimit()>=0){
                sqlBuf.append(" ,daily_count_limit = #{awardOfPackage.dailyCountLimit}");
            }
            if(awardOfPackage.getTotalCount()>=-1){
                sqlBuf.append(" ,total_count = #{awardOfPackage.totalCount}");
            }
            if(awardOfPackage.getAwardCount()>0){
                sqlBuf.append(" ,award_count = #{awardOfPackage.awardCount}");
            }
            if(awardOfPackage.getValidDays() >=0){
                sqlBuf.append(" ,valid_days = #{awardOfPackage.validDays}");
            }
            sqlBuf.append(" ,enable = #{awardOfPackage.enable}")
                     .append(" where id = #{awardOfPackage.id}");
            return sqlBuf.toString();
        }


        public String addNewReleaseRecord(@Param(value = "uid") long uid, @Param(value = "package_id") long package_id,
                                          @Param(value = "package_name") String package_name, @Param(value = "package_icon") String package_icon,
                                          @Param(value = "seq_id") String seq_id, @Param(value = "status") int status, @Param(value = "award_back_up") String award_back_up) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append("insert into common_award_release_record_").append(tableSuffix);
            sqlBuf.append(" (uid,package_id,package_name,package_icon,seq_id,status,award_back_up,remark)" +
                    " values (#{uid},#{package_id},#{package_name},#{package_icon},#{seq_id},#{status},#{award_back_up},'初始化') ");
            return sqlBuf.toString();
        }

        public String updateReleaseRecordStatus(@Param(value = "packageId") long packageId, @Param(value = "seqId") String seqId, @Param(value = "status") int status, @Param(value = "remark") String remark) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append("update common_award_release_record_").append(tableSuffix);
            sqlBuf.append(" set status = #{status},remark = concat(remark,'|',#{remark}) where seq_id = #{seqId} and package_id = #{packageId}");
            return sqlBuf.toString();
        }

        public String addNewReleaseRecordDetail(@Param(value = "uid") long uid, @Param(value = "seq_id") String seq_id,
                                                @Param(value = "award_id") int award_id, @Param(value = "award_name") String award_name, @Param(value = "award_icon") String award_icon,
                                                @Param(value = "award_release_type") int award_release_type, @Param(value = "award_release_id") String award_release_id, @Param(value = "award_count") int award_count,
                                                @Param(value = "award_valid_days") int award_valid_days, @Param(value = "award_price") int award_price, @Param(value = "award_extend") String award_extend,
                                                @Param(value = "status") int status, @Param(value = "remark") String remark, @Param(value = "award_of_package_id") int award_of_package_id) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append("insert into common_award_release_record_detail_").append(tableSuffix);
            sqlBuf.append(" (uid,seq_id,award_id,award_name,award_icon,award_release_type,award_release_id,award_count,award_valid_days,award_price,award_extend,status,remark,award_of_package_id)" +
                    " values (#{uid},#{seq_id},#{award_id},#{award_name},#{award_icon},#{award_release_type},#{award_release_id},#{award_count},#{award_valid_days},#{award_price},#{award_extend},#{status},#{remark},#{award_of_package_id}) ");
            return sqlBuf.toString();
        }

        public String updateReleaseRecordDetailStatus(@Param(value = "awardOfPackageId") long awardOfPackageId, @Param(value = "seqId") String seqId, @Param(value = "status") int status, @Param(value = "remark") String remark) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" update common_award_release_record_detail_").append(tableSuffix);
            sqlBuf.append(" set status = #{status},remark = concat(remark,'|',#{remark}) where seq_id = #{seqId} and award_of_package_id = #{awardOfPackageId}");
            return sqlBuf.toString();
        }

        public String recordThirdpartySeqId(@Param(value = "thirdparty_seq_id") String thirdpartySeqId, @Param(value = "awardOfPackageId") long awardOfPackageId, @Param(value = "seqId") String seqId) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" update common_award_release_record_detail_").append(tableSuffix);
            sqlBuf.append(" set thirdparty_seq_id = #{thirdparty_seq_id} where seq_id = #{seqId} and award_of_package_id = #{awardOfPackageId}");
            return sqlBuf.toString();
        }


    }
}
