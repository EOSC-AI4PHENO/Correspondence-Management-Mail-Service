package com.siseth.mail.module.internal.mail.controller;

import com.siseth.mail.module.internal.mail.api.request.CreateEmailsReqDTO;
import com.siseth.mail.module.internal.mail.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/correspondence/mail")
@Tag(name = "Email Controller", description = "Endpoints responsible for sending emails")
public class MessageQueueInternalController {
    private final EmailService service;


    @PostMapping("/createEmail")
    @Operation(summary = "Create email", description = "Create email for the given template type")
    public ResponseEntity<String> create(@RequestBody Map<String, String> map) {
        return ResponseEntity.ok(service.create(map));
    }


    @PostMapping("/createEmails")
    @Operation(summary = "Create email", description = "Create email for the given template type")
    public ResponseEntity<String> create(@RequestBody CreateEmailsReqDTO dto) {
        return ResponseEntity.ok(service.createEmails(dto));
    }

}
