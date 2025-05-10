package com.springboot.letterbackend.notify.dto;

import com.springboot.letterbackend.notify.entity.Notify;
import lombok.*;

public class NotifyDto {
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        String id;
        String name;
        String content;
        String type;
        String createdAt;
        public static Response createResponse(Notify notify) {
            return Response.builder()
                    .content(notify.getContent())
                    .id(notify.getId().toString())
                    .name(notify.getReceiver().getName())
                    .createdAt(notify.getCreatedBy().toString())
                    .build();

        }
    }
}