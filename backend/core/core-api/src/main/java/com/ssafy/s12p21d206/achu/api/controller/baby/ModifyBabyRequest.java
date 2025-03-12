package com.ssafy.s12p21d206.achu.api.controller.baby;

import java.time.LocalDate;

public record ModifyBabyRequest(String nickname, String gender, LocalDate birth) {}
