package com.ssafy.s12p21d206.achu.fcm.domain;

import java.util.Map;

public record FcmMessage(String to, Map<String, String> data) {}
