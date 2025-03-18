package com.ssafy.s12p21d206.achu.api.controller.baby;

import static com.ssafy.s12p21d206.achu.test.api.RestDocsUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class BabyControllerTest extends RestDocsTest {

  private BabyController controller;

  @BeforeEach
  public void setup() {
    controller = new BabyController();
    mockMvc = mockController(controller);
  }

  @Test
  public void appendBaby() {
    given()
        .contentType(ContentType.JSON)
        .body(
            new AppendBabyRequest("강두식", "여자", "http://test-image.png", LocalDate.of(2025, 3, 20)))
        .post("/babies")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-baby",
            requestFields(
                fieldWithPath("nickname")
                    .type(JsonFieldType.STRING)
                    .description("등록할 자녀의 이름 혹은 별명")
                    .attributes(constraints("2글자 이상 10글자 이하여야 합니다.")),
                fieldWithPath("gender")
                    .type(JsonFieldType.STRING)
                    .description("등록할 자녀의 성별")
                    .attributes(constraints("성별은 남자, 여자, 미정 중 하나여야 합니다.")),
                fieldWithPath("imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("등록할 자녀의 프로필 이미지 주소"),
                fieldWithPath("birth").type(JsonFieldType.ARRAY).description("등록할 자녀의 생년월일")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("등록된 자녀의 id"))));
  }

  @Test
  public void findBaby() {
    given()
        .contentType(ContentType.JSON)
        .get("/babies/{babyId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-baby",
            pathParameters(parameterWithName("babyId").description("조회하고자 하는 자녀 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("자식 id"),
                fieldWithPath("data.nickname")
                    .type(JsonFieldType.STRING)
                    .description("자녀 이름 혹은 별명"),
                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("자녀 성별"),
                fieldWithPath("data.imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("자녀 프로필 이미지 주소"),
                fieldWithPath("data.birth").type(JsonFieldType.STRING).description("자녀 생년월일"))));
  }

  @Test
  public void findBabies() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/babies")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-babies",
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("자녀 id"),
                fieldWithPath("data.[].nickname")
                    .type(JsonFieldType.STRING)
                    .description("자녀 이름 혹은 별명"),
                fieldWithPath("data.[].imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("자녀 프로필 이미지 주소"),
                fieldWithPath("data.[].birth").type(JsonFieldType.STRING).description("자녀 생년월일"))));
  }

  @Test
  public void modifyProfileImage() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyProfileImageRequest("https://modify-test.jpg"))
        .patch("/babies/{babyId}/profile-image", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-profile-image",
            pathParameters(parameterWithName("babyId").description("프로필 이미지를 변경할 자녀의 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("자식 id"),
                fieldWithPath("data.nickname")
                    .type(JsonFieldType.STRING)
                    .description("자녀 이름 혹은 별명"),
                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("자녀 성별"),
                fieldWithPath("data.imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("자녀 프로필 이미지 주소"),
                fieldWithPath("data.birth").type(JsonFieldType.STRING).description("자녀 생년월일"))));
  }

  @Test
  public void modifyBaby() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyBabyRequest("채용수", "남자", LocalDate.of(1997, 3, 20)))
        .patch("/babies/{babyId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-baby",
            pathParameters(parameterWithName("babyId").description("정보를 수정할 자녀의 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("자식 id"),
                fieldWithPath("data.nickname")
                    .type(JsonFieldType.STRING)
                    .description("자녀 이름 혹은 별명"),
                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("자녀 성별"),
                fieldWithPath("data.imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("자녀 프로필 이미지 주소"),
                fieldWithPath("data.birth").type(JsonFieldType.STRING).description("자녀 생년월일"))));
  }

  @Test
  public void deleteBaby() {

    given()
        .contentType(ContentType.JSON)
        .delete("/babies/{babyId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-baby",
            pathParameters(parameterWithName("babyId").description("삭제 할 자녀의 id")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }
}
