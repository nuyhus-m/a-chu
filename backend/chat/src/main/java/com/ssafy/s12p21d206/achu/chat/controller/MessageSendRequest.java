package com.ssafy.s12p21d206.achu.chat.controller;

/**
 * 메시지 전송 요청 DTO
 */
public record MessageSendRequest(String content, RequestMessageType type) {}
