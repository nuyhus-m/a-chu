package com.ssafy.s12p21d206.achu.api.controller.example;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import com.ssafy.s12p21d206.achu.test.api.RestDocsUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class ExampleControllerTest extends RestDocsTest {

  private ExampleController controller;

  @BeforeEach
  public void setup() {
    controller = new ExampleController();
    mockMvc = mockController(controller);
  }

  @Test
  public void appendExample() {
    given()
        .contentType(ContentType.JSON)
        .body(new AppendExampleRequest("이름", 20))
        .post("/examples")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-example",
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("생성할 example 이름")
                    .attributes(RestDocsUtils.constraints("2자 이상 10자 이하여야 합니다.")),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("생성할 example 나이")
                    .attributes(RestDocsUtils.constraints("0 이상 150 이하여야 합니다.")))
            ,
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id")
                    .type(JsonFieldType.NUMBER)
                    .description("생성된 example id"))));
  }

  @Test
  public void findExample() {
    given()
        .contentType(ContentType.JSON)
        .get("/examples/{exampleId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-example",
            pathParameters(parameterWithName("exampleId").description("조회하고자 하는 example id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("example id"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("example 이름"),
                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("example 나이"),
                fieldWithPath("data.createdAt")
                    .type(JsonFieldType.STRING)
                    .description("example 생성 시간"),
                fieldWithPath("data.updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("example 수정 시간"))));
  }

  @Test
  public void findExamples() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/examples")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-examples",
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("example id"),
                fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("example 이름"),
                fieldWithPath("data.[].age").type(JsonFieldType.NUMBER).description("example 나이"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("example 생성 시간"),
                fieldWithPath("data.[].updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("example 수정 시간"))));
  }

  @Test
  public void modifyExampleName() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyExampleNameRequest("새이름"))
        .patch("/examples/{exampleId}/name", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-example-name",
            pathParameters(parameterWithName("exampleId").description("조회하고자 하는 example id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("example id"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("example 이름"),
                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("example 나이"),
                fieldWithPath("data.createdAt")
                    .type(JsonFieldType.STRING)
                    .description("example 생성 시간"),
                fieldWithPath("data.updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("example 수정 시간"))));
  }

  @Test
  public void deleteExample() {
    given()
        .contentType(ContentType.JSON)
        .delete("/examples/{exampleId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-example",
            pathParameters(parameterWithName("exampleId").description("조회하고자 하는 example id")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }
}
