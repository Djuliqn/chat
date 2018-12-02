package com.chat.server.view;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageView {

    private String username;

    private String text;

    private LocalDate date;
}
