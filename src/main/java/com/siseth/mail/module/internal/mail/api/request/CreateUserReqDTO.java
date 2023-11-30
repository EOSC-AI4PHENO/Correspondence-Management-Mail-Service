package com.siseth.mail.module.internal.mail.api.request;

import com.siseth.mail.constant.AppProperties;
import lombok.Getter;

@Getter
public class CreateUserReqDTO {
    private String email;
    private String token;

    public String getUrlWithToken() {
        return AppProperties.CHANGE_PASSWORD_URL +"?token="+ token;
    }

}
