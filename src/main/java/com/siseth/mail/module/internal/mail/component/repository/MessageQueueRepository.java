package com.siseth.mail.module.internal.mail.component.repository;

import com.siseth.mail.module.internal.mail.component.entity.MessageQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageQueueRepository extends JpaRepository<MessageQueue, Long> {

    List<MessageQueue> findAllByIsSentIsFalseAndAttemptsLessThan(Long count);
}
