package com.ssafy.s12p21d206.achu.auth.domain.image;

public record AuthFile(String fileName, String contentType, byte[] content, long size) {}
