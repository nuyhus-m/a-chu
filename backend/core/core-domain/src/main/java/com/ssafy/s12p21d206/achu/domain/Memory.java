package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;

public record Memory(
    Long memoryId,
    String title,
    String content,
    ImageUrlsWithThumbnail imageUrlsWithThumbnail,
    Long babyId,
    DefaultDateTime defaultDateTime) {}
