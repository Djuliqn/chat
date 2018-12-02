package com.chat.server.view;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chat.server.view.MessageView.MessageViewBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
public class OutputMessageView {

    private String sender;

    private String text;

    private LocalDate date;
}
