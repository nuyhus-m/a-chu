package com.ssafy.s12p21d206.achu.api.controller.goods;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class LikeControllerTest extends RestDocsTest {

  private LikeController controller;
  private LikeService likeService;

  @BeforeEach
  void setup() {
    likeService = mock(LikeService.class);
    controller = new LikeController(likeService);
    mockMvc = mockController(controller);
  }

  @Test
  void like() {
    given()
        .contentType(ContentType.JSON)
        .body(new LikeRequest(123L))
        .post("/goods/{goodsId}/like", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "like",
            pathParameters(parameterWithName("goodsId").description("찜할 물건 id")),
            requestFields(
                fieldWithPath("babyId").type(JsonFieldType.NUMBER).description("좋아요 시 아기 ID")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void deleteLike() {
    given()
        .contentType(ContentType.JSON)
        .delete("/goods/{goodsId}/like", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-like",
            pathParameters(parameterWithName("goodsId").description("찜 취소할 물건 id")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }
}
