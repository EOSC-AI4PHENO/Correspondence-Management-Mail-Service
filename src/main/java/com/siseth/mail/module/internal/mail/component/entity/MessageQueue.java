package com.siseth.mail.module.internal.mail.component.entity;

import com.siseth.mail.module.internal.mail.api.request.CreateUserReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "`queue`", name = "`messages`")
public class MessageQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(name="`sendTo`")
    private String sendTo;

    @Column(name="`realm`")
    private String realm;

    @Column(name="`subject`")
    private String subject;

    @Column(name="`content`")
    private String content;

    @Column(name="`createdAt`")
    private OffsetDateTime createdAt;

    @Column(name="`modifiedAt`")
    private OffsetDateTime modifiedAt;

    @Column(name="`createdBy`")
    private String createdBy;

    @Column(name="`isSent`")
    private Boolean isSent;

    @Column(name="`sentAt`")
    private OffsetDateTime sentAt;

    @Column(name="`messageFromServer`")
    private String messageFromServer;

    @Column(name="`attempts`")
    private Long attempts = 0L;


    public MessageQueue(String content, String subject, String mail, String realm) {
        this.sendTo = mail;
        this.subject = subject;
        this.content = content;
        this.createdAt = OffsetDateTime.now();
        this.modifiedAt = OffsetDateTime.now();
        this.createdBy = "createdBy";
        this.isSent = Boolean.FALSE;
        this.realm = realm;
    }

    public void markAsSend() {
        this.isSent = Boolean.TRUE;
        this.sentAt = OffsetDateTime.now();
    }

    public void changeErrorMessage(String errorMessage) {
        this.messageFromServer = errorMessage;
    }

    public void incrementAttemptCount() {
        this.attempts++;
    }

    public enum EMAIL_TYPE {
        CHANGE_PASSWORD,
        INCORRECT_TIMEZONE,
        IMAGES_DISCOUNTINUITY,
        ACTIVATE_ACCOUNT,
        RESET_PASSWORD,
        INDEX_REGISTER,
        DEFAULT,
        MISSING_FILE,

        LOW_SPACE_DISC

    }

}