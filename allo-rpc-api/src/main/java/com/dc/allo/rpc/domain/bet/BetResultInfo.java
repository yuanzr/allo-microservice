package com.dc.allo.rpc.domain.bet;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.domain.resource.SvgaImage;
import com.dc.allo.rpc.domain.resource.SvgaInfo;
import com.dc.allo.rpc.domain.resource.SvgaText;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
public class BetResultInfo implements Serializable {
    //投注精灵id
    private long spiritId;
    private long winSpiritId;
    //是否胜方（0败方；1胜方）
    private int isWin;
    //此轮总支付额
    private long payTotalAmount;
    //奖励总金额
    private long awardAmount;
    private String awards;
    private List<BetUserAwardInfo> userAwards;
    private SvgaInfo svgaInfo;
    private String animations;
    Map<Long, BetAnimationData> animationMap;
    private long ctime;

    public Map<Long, BetAnimationData> getAnimationMap() {
        if (StringUtils.isNotBlank(animations)) {
            BetAnimationInfo animationInfo = JsonUtils.fromJson(animations, BetAnimationInfo.class);
            if (animationInfo != null) {
                return animationInfo.getAnimations();
            }
        }
        return null;
    }

    public List<BetUserAwardInfo> getUserAwards() {
        if (StringUtils.isBlank(awards) || "{}".equals(awards)) {
            return null;
        }
        this.userAwards = JsonUtils.fromJson(awards, new TypeReference<List<BetUserAwardInfo>>() {
        });
        return this.userAwards;
    }

    /**
     * 暂时写死，后续考虑配置方式
     *
     * @return
     */
    public SvgaInfo getSvgaInfo() {
        SvgaInfo svgaInfo = null;
        if (StringUtils.isBlank(awards) || "{}".equals(awards)) {
            return svgaInfo;
        }
        String svgaUrl = "https://img.scarllet.cn/bet/svga/bet_loss2.svga";
        String txtColor = "#FFFFFF";
        int txtSize = 18;
        if (isWin == 1) {
            txtColor = "#FFCA3C";
            txtSize = 35;
            svgaUrl = "https://img.scarllet.cn/rankprize_1594806333829.svga?imageslim";
        }
        this.userAwards = JsonUtils.fromJson(awards, new TypeReference<List<BetUserAwardInfo>>() {
        });
        if (CollectionUtils.isNotEmpty(userAwards)) {
            svgaInfo = new SvgaInfo();
            List<SvgaText> svgaTexts = new ArrayList<>();
            List<SvgaImage> svgaImages = new ArrayList<>();
            SvgaText svgaText = null;
            SvgaImage svgaImage = null;
            for (BetUserAwardInfo userAward : userAwards) {
                svgaText = new SvgaText();
                svgaText.setText(String.valueOf(userAward.getAwardCount()));
                svgaText.setAlignment(1);
                svgaText.setKey("image_ming_cheng");
                svgaText.setSize(txtSize);
                svgaText.setColor(txtColor);
                svgaTexts.add(svgaText);
                svgaImage = new SvgaImage();
                svgaImage.setValue(userAward.getIcon());
                svgaImage.setKey("image_jian_pin");
                svgaImages.add(svgaImage);
            }
            svgaInfo.setTexts(svgaTexts);
            svgaInfo.setImages(svgaImages);
        }
        svgaInfo.setUrl(svgaUrl);
        return svgaInfo;
    }
}
