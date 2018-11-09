package com.chat.server.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTokenState {
    private String access_token;
    private Long expires_in;
}
