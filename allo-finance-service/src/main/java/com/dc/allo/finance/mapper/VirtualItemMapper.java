package com.dc.allo.finance.mapper;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemAppDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Mapper
public interface VirtualItemMapper {

    @Insert("REPLACE into virtual_item_app_def (app,item_id) values (#{app},#{itemId})")
    long addAppDef(@Param("app") String app, @Param("itemId") int itemId);

    @Select("select * from virtual_item_app_def where status = 0 ")
    List<VirtualItemAppDef> queryAllAppDef();

    @Insert("REPLACE into virtual_item_def (name,price,ctime) values (#{name},#{price},now())")
    long addItemDef(@Param("name") String name, @Param("price") int price);

    @Select("select * from virtual_item_def")
    List<VirtualItemDef> queryAllItemDef();

    @Select("select * from virtual_item_def where app = #{app}")
    List<VirtualItemDef> queryAllItemDefByApp(@Param("app") String app);

    @InsertProvider(type = VirtualItemMapperProvider.class, method = "addBill")
    long addBill(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val, @Param("bizId") String bizId, @Param("context") String context, @Param("price") long price, @Param("walletVal") long walletVal);

    @SelectProvider(type = VirtualItemMapperProvider.class, method = "pageBill")
    List<VirtualItemBill> pageBill(@Param("app") String app, @Param("uid") long uid, @Param("month") String month, @Param("id") long id, @Param("pageSize") long pageSize);

    @SelectProvider(type = VirtualItemMapperProvider.class, method = "pageBillByItem")
    List<VirtualItemBill> pageBillByItem(@Param("app")String app,  @Param("uid")long uid, @Param("month")String suffix,@Param("itemId") int itemId,@Param("id")long id,  @Param("pageSize")long pageSize);

    @InsertProvider(type = VirtualItemMapperProvider.class, method = "addWallet")
    long addWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val);

    @UpdateProvider(type = VirtualItemMapperProvider.class, method = "incrWallet")
    long incrWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val);

    @SelectProvider(type = VirtualItemMapperProvider.class, method = "getWallet")
    VirtualItemWallet getWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId);

    @SelectProvider(type = VirtualItemMapperProvider.class, method = "queryWallets")
    List<VirtualItemWallet> queryWallets(@Param("app") String app, @Param("uid") long uid);


    class VirtualItemMapperProvider {
        public String addBill(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val, @Param("bizId") String bizId, @Param("context") String context, @Param("price") long price, @Param("walletVal") long walletVal) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
            sqlBuf.append("insert INTO virtual_item_bill_").append(tableSuffix);
            sqlBuf.append(" (app,uid,item_id,pre_val,val,after_val,biz_id,context,price) VALUES " +
                    "(#{app},#{uid},#{itemId},#{walletVal},#{val},#{walletVal}+#{val},#{bizId},#{context},#{price})");
            return sqlBuf.toString();
        }

        public String pageBill(@Param("app") String app, @Param("uid") long uid, @Param("month") String month, @Param("id") long id, @Param("pageSize") long pageSize) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
            if (StringUtils.isBlank(month)) {
                month = tableSuffix;
            }
            sqlBuf.append("select * from virtual_item_bill_").append(month);
            sqlBuf.append(" where uid = #{uid} and app = #{app} ");
            if (id <= 0) {
                sqlBuf.append(" order by id desc ");
            } else {
                sqlBuf.append(" and id<#{id} order by id desc");
            }
            sqlBuf.append(" limit 0,#{pageSize}");
            return sqlBuf.toString();
        }

        public String pageBillByItem(@Param("app") String app, @Param("uid") long uid, @Param("month") String month,@Param("itemId") int itemId, @Param("id") long id, @Param("pageSize") long pageSize) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
            if (StringUtils.isBlank(month)) {
                month = tableSuffix;
            }
            sqlBuf.append("select * from virtual_item_bill_").append(month);
            sqlBuf.append(" where uid = #{uid} and app = #{app} and item_id =#{itemId}");
            if (id <= 0) {
                sqlBuf.append(" order by id desc ");
            } else {
                sqlBuf.append(" and id<#{id} order by id desc");
            }
            sqlBuf.append(" limit 0,#{pageSize}");
            return sqlBuf.toString();
        }

        public String addWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val) {
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("insert INTO virtual_item_wallet_").append(uid % 100);
            sqlBuf.append(" (app,uid,item_id,val,ctime) VALUES " +
                    "(#{app},#{uid},#{itemId},#{val},now())");
            return sqlBuf.toString();
        }

        public String incrWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId, @Param("val") long val) {
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("update virtual_item_wallet_").append(uid % 100);
            sqlBuf.append(" set pre_val = val, val = val + #{val} where uid=#{uid} and app=#{app} and item_id=#{itemId}");
            return sqlBuf.toString();
        }

        public String queryWallets(@Param("app") String app, @Param("uid") long uid) {
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append(" select w.id, w.uid, w.app, w.item_id, w.val, w.ctime, w.utime, d.name, d.price from virtual_item_wallet_").append(uid % 100).append(" w left join virtual_item_def d");
            sqlBuf.append(" on w.item_id=d.id where w.uid=#{uid} and w.app=#{app} ");
            return sqlBuf.toString();
        }

        public String getWallet(@Param("app") String app, @Param("uid") long uid, @Param("itemId") int itemId) {
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append(" select w.id, w.uid, w.app, w.item_id, w.val, w.ctime, w.utime, d.name, d.price from virtual_item_wallet_").append(uid % 100).append(" w left join virtual_item_def d");
            sqlBuf.append(" on w.item_id=d.id where w.uid=#{uid} and w.app=#{app} and w.item_id=#{itemId}");
            return sqlBuf.toString();
        }
    }


}
