package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import java.util.List;

public record Memory(
    Long memoryId,
    String title,
    String content,
    List<String> imgUrls,
    Long babyId,
    DefaultDateTime defaultDateTime) {}
