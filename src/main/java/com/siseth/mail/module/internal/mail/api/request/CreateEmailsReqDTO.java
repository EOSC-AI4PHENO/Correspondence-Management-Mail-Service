package com.siseth.mail.module.internal.mail.api.request;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CreateEmailsReqDTO {
    private Map<String, String> map;
    private List<String> emails;
}
