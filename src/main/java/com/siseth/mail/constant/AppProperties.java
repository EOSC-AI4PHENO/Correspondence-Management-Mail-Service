package com.siseth.mail.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    public static Boolean MAIL_SCHEDULE_FLAG;

    public static String CHANGE_PASSWORD_URL;

    @Value("${app.schedule.mail}")
    public void setMailSchedule(Boolean mailSchedule){
        AppProperties.MAIL_SCHEDULE_FLAG = mailSchedule;
    }

    @Value("${app.change-password.url}")
    public void setChangePasswordUrl(String url){
        AppProperties.CHANGE_PASSWORD_URL = url;
    }


}
