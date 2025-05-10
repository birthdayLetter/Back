package com.springboot.letterbackend.notify.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.notify.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/notify")
public class NotifyController {

    private final NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @GetMapping(value = "/subscribe",produces ="text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal User user,  @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        return notifyService.subscribe(principal.getUsername(), lastEventId);

    }
}
