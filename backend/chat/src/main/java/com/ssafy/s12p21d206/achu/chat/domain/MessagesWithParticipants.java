package com.ssafy.s12p21d206.achu.chat.domain;

import java.util.List;

public record MessagesWithParticipants(List<Message> messages, Participants participants) {}
