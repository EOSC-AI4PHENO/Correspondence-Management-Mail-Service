package com.siseth.mail.module.internal.feign.fedora;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fedora-management-service")
public interface FedoraFeign {

    @GetMapping("/api/assets/fedora/getPublicFile/{path}")
    byte[] getPublicFile(@PathVariable String path);

}