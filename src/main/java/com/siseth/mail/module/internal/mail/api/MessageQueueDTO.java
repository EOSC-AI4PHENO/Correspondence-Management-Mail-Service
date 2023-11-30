package com.siseth.mail.module.internal.mail.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageQueueDTO {

    private String sendTo;

    private String subject;

    private String content;

    private String createdAt;

}