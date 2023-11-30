package com.siseth.mail.module.internal.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeign {

    @GetMapping
    public Object getUserInfo(@RequestParam String userId);

}
