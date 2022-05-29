package com.dc.allo.roomplay.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigConstant {
    public static String secretaryUid;

    @Value("${SECRETARY_UID}")
    public void setSecretaryUid(String secretaryUid) {
        ConfigConstant.secretaryUid = secretaryUid;
    }
}
