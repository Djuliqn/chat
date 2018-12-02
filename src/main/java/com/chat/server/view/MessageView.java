package com.chat.server.view;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Builder
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageView {

    private String username;
    
    private List<String> targets;
    
    private MessageType type;

    private String text;

    private LocalDate date;
}
