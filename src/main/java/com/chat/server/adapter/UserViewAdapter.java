package com.chat.server.adapter;

import com.chat.server.model.User;
import com.chat.server.view.UserView;

public class UserViewAdapter {

    private UserViewAdapter() {

    }

    public static UserView adapt(User user) {
        return UserView.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User adapt(UserView userView) {
        return User.builder()
                .id(userView.getId())
                .username(userView.getUsername())
                .password(userView.getPassword())
                .role(userView.getRole())
                .build();
    }
}
