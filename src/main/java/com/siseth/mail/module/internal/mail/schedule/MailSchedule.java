package com.siseth.mail.module.internal.mail.schedule;

import com.siseth.mail.constant.AppProperties;
import com.siseth.mail.module.internal.mail.component.entity.MessageQueue;
import com.siseth.mail.module.internal.mail.component.repository.MessageQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailSchedule {

    private final MessageQueueRepository repository;
    private final JavaMailSender javaMailSender;

    private final Long ATTEMPT_COUNT = 5L;

    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void sendMessage() {
        if (!AppProperties.MAIL_SCHEDULE_FLAG) return;

        log.debug("Send messages, schedule is enable");

        List<MessageQueue> messages = repository.findAllByIsSentIsFalseAndAttemptsLessThan(ATTEMPT_COUNT);
        messages.forEach(this::sendEmail);
    }

    private void sendEmail(MessageQueue message) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        log.debug("Send message by id {}", message.getId());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
            helper.setTo(message.getSendTo());
            helper.setFrom("ai4pheno-info@seth.software");
            helper.setSubject(message.getSubject());
            helper.setText(message.getContent(), true);

            helper.addInline("logo-green-id", new ClassPathResource("static/images/logo_green_school_Lxa.png"));
            helper.addInline("logo-seth-software", new ClassPathResource("static/images/logosethsoftware_j72.png"));
            helper.addInline("logo-eu-id", new ClassPathResource("static/images/logoeu.jpg"));
            helper.addInline("base-logo-id", new ClassPathResource("static/images/base_logo_g9y.png"));

            javaMailSender.send(mail);
            message.markAsSend();
        } catch (Exception e) {
            log.error("Error with send mail by id {}", message.getId());
            log.error(e.getMessage());
            message.incrementAttemptCount();
            message.changeErrorMessage(e.getMessage());
        } finally {
            repository.save(message);
        }
    }
}
