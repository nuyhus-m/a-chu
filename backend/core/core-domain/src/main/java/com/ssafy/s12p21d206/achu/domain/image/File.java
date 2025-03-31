package com.ssafy.s12p21d206.achu.domain.image;

public record File(String fileName, String contentType, byte[] content, long size) {}
