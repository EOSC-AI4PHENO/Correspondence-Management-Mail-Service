package com.siseth.mail.module.internal.mail.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.siseth.mail.constant.AppProperties;
import com.siseth.mail.module.internal.mail.api.request.CreateEmailsReqDTO;
import org.apache.commons.io.FileUtils;
import com.siseth.mail.module.internal.feign.fedora.FedoraFeign;
import com.siseth.mail.module.internal.mail.api.request.CreateUserReqDTO;
import com.siseth.mail.module.internal.mail.component.entity.MessageQueue;
import com.siseth.mail.module.internal.mail.component.repository.MessageQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MessageQueueRepository messageQueueRepository;

    private final TemplateEngine templateEngine;

    private final FedoraFeign fedoraFeign;


    static final Map<MessageQueue.EMAIL_TYPE, String> MAP_TO_TEMPLATE = Map.of(
            MessageQueue.EMAIL_TYPE.INCORRECT_TIMEZONE, "/incorrectTimezone.html",
            MessageQueue.EMAIL_TYPE.MISSING_FILE, "/incorrectTimezone.html",
            MessageQueue.EMAIL_TYPE.LOW_SPACE_DISC, "/incorrectTimezone.html",
            MessageQueue.EMAIL_TYPE.CHANGE_PASSWORD, "/createPassword.html",
            MessageQueue.EMAIL_TYPE.ACTIVATE_ACCOUNT, "/ActivateAccount.html",
            MessageQueue.EMAIL_TYPE.RESET_PASSWORD, "/ResetPassword.html",
            MessageQueue.EMAIL_TYPE.INDEX_REGISTER, "/indexRegister.html",
            MessageQueue.EMAIL_TYPE.IMAGES_DISCOUNTINUITY, "/imagesDiscontinuity.html",
            MessageQueue.EMAIL_TYPE.DEFAULT, "/Default.html"
    );


    @SneakyThrows
    public String create(Map<String, String> map) {
        Map<String, String> mutated = mutate(map);
        String mail = mutated.get("email");
        Context context = new Context();
        context.setVariable("dto", mutated);

        MessageQueue.EMAIL_TYPE type = MessageQueue.EMAIL_TYPE.valueOf(map.get("type"));

        MessageQueue messageQueue =
                new MessageQueue(
                            templateEngine.process(MAP_TO_TEMPLATE.get(type), context),
                            map.getOrDefault("subject", "Activate Your Account!"),
                            mail,
                            map.get("realm"));

        messageQueueRepository.save(messageQueue);
        return "Message of id: " + messageQueue.getId() + " was added to the queue";

    }

    @SneakyThrows
    public String createEmails(CreateEmailsReqDTO dto) {
        Map<String, String> mutated = mutate(dto.getMap());
        Context context = new Context();
        context.setVariable("dto", mutated);
        MessageQueue.EMAIL_TYPE type = MessageQueue.EMAIL_TYPE.valueOf(dto.getMap().get("type"));

        List<MessageQueue> messageQueues = dto.getEmails().stream().map(email -> new MessageQueue(
                templateEngine.process(MAP_TO_TEMPLATE.get(type), context),
                dto.getMap().getOrDefault("subject", "default"),
                email,
                dto.getMap().get("realm"))).collect(Collectors.toList());



        messageQueueRepository.saveAll(messageQueues);
        return messageQueues.size() + " messages was added to the queue";

    }


    private Map<String, String> mutate(Map<String, String> map) {
        if(map == null)
            return new HashMap<>();
        Optional
                .ofNullable(map.get("token"))
                .ifPresent(x -> map.putIfAbsent("url", AppProperties.CHANGE_PASSWORD_URL + "?token=" + x +"&email=" + map.get("email")));

        return map;
    }

}
