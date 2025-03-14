package com.ssafy.s12p21d206.achu.api.controller.example;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import com.ssafy.s12p21d206.achu.api.controller.user.AppendUserRequest;
import com.ssafy.s12p21d206.achu.api.controller.user.ModifyNicknameRequest;
import com.ssafy.s12p21d206.achu.api.controller.user.UserController;
import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import com.ssafy.s12p21d206.achu.test.api.RestDocsUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserControllerTest extends RestDocsTest {

  private UserController controller;

  @BeforeEach
  public void setup() {
    controller = new UserController();
    mockMvc = mockController(controller);
  }

  @Test
  public void appendUser() {
    given()
        .contentType(ContentType.JSON)
        .body(new AppendUserRequest("아이디", "비밀번호", "비밀번호 확인", "닉네임", "전화번호"))
        .post("/users")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-user",
            requestFields(
                fieldWithPath("username")
                    .type(JsonFieldType.STRING)
                    .description("생성할 user 아이디")
                    .attributes(RestDocsUtils.constraints("20자 이하")),
                fieldWithPath("password")
                    .type(JsonFieldType.STRING)
                    .description("생성할 user 비밀번호")
                    .attributes(RestDocsUtils.constraints("60자 이하")),
                fieldWithPath("password2")
                    .type(JsonFieldType.STRING)
                    .description("생성할 user 비밀번호 확인")
                    .attributes(RestDocsUtils.constraints("60자 이하")),
                fieldWithPath("nickname")
                    .type(JsonFieldType.STRING)
                    .description("생성할 user 닉네임")
                    .attributes(RestDocsUtils.constraints("36자 이하")),
                fieldWithPath("phoneNumber")
                    .type(JsonFieldType.STRING)
                    .description("생성할 user 전화번호")
                    .attributes(RestDocsUtils.constraints("15자 이하"))),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 user id"))));
  }

  @Test
  public void findUser() {
    given()
        .contentType(ContentType.JSON)
        .get("/users/{userId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-user",
            pathParameters(parameterWithName("userId").description("조회하고자 하는 user id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("user id"),
                fieldWithPath("data.username").type(JsonFieldType.STRING).description("user 아이디"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("user 닉네임"),
                fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("user 프로필"),
                fieldWithPath("data.createdAt")
                    .type(JsonFieldType.STRING)
                    .description("user 생성 시간"),
                fieldWithPath("data.updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("user 수정 시간"))));
  }

  @Test
  public void checkUsernameIsUnique() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("username", "아이디")
        .get("/users/username/is-unique")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "check-username",
            queryParameters(parameterWithName("username")
                .description("중복검사 할 아이디")
                .attributes(RestDocsUtils.constraints("20자 이하"))),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  public void checkNicknameIsUnique() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("nickname", "닉네임")
        .get("/users/nickname/is-unique")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "check-nickname",
            queryParameters(parameterWithName("nickname")
                .description("중복검사 할 닉네임")
                .attributes(RestDocsUtils.constraints("36자 이하"))),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  public void modifyNickname() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyNicknameRequest("새로운 닉네임"))
        .patch("/users/{userId}/nickname", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-nickname",
            pathParameters(parameterWithName("userId").description("변경하고자 하는 user id")),
            requestFields(fieldWithPath("nickname")
                .type(JsonFieldType.STRING)
                .description("새로운 nickname")
                .attributes(RestDocsUtils.constraints("36자 이하"))),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }
}
