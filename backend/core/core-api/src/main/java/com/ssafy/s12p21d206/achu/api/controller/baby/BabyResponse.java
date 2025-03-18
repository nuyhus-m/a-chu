package com.ssafy.s12p21d206.achu.api.controller.baby;

import java.time.LocalDate;

public record BabyResponse(Long id, String nickname, String imgUrl, LocalDate birth) {}
