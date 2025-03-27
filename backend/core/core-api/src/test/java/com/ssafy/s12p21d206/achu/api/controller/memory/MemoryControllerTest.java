package com.ssafy.s12p21d206.achu.api.controller.memory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.ssafy.s12p21d206.achu.domain.Memory;
import com.ssafy.s12p21d206.achu.domain.MemoryService;
import com.ssafy.s12p21d206.achu.domain.NewMemory;
import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

// sonarqube에서 test에 assertions이 없더라도 code smell로 인식하지 않음
@SuppressWarnings("java:S2699")
class MemoryControllerTest extends RestDocsTest {

  private MemoryController controller;
  private MemoryService memoryService;

  @BeforeEach
  void setup() {
    memoryService = mock(MemoryService.class);
    controller = new MemoryController(memoryService);
    mockMvc = mockController(controller);
  }

  @Test
  void appendMemory() {
    when(memoryService.append(any(User.class), anyLong(), any(NewMemory.class))).thenReturn(1L);
    given()
        .contentType(ContentType.MULTIPART)
        .multiPart("images", "test1.jpg", new byte[0], "image/jpeg") // 파일 파트
        .multiPart("images", "test2.jpg", new byte[0], "image/jpeg") // 파일 파트
        .multiPart("request", new AppendMemoryRequest("제목", "내용"), "application/json") // JSON 파트
        .post("/babies/{babyId}/memories", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-memory",
            pathParameters(parameterWithName("babyId").description("추억을 생성할 자녀의 id")),
            requestParts(
                partWithName("images").description("추억 이미지 파일"),
                partWithName("request").description("추억 생성 요청 데이터")),
            requestPartFields(
                "request",
                fieldWithPath("title").type(JsonFieldType.STRING).description("생성할 추억 제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("생성할 추억 내용")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 추억 id"))));
  }

  @Test
  void findMemory() {
    when(memoryService.findMemory(any(User.class), anyLong()))
        .thenReturn(new Memory(
            1L,
            "제목",
            "내용",
            List.of("https://example.com/img1.jpg"),
            1L,
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now()));
    given()
        .contentType(ContentType.JSON)
        .get("memories/{memoryId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-memory",
            pathParameters(parameterWithName("memoryId").description("조회할 추억 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("추억 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("추억 제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("추억 내용"),
                fieldWithPath("data.imgUrls").type(JsonFieldType.ARRAY).description("추억 사진 주소"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("추억 생성 시간"),
                fieldWithPath("data.updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("추억 수정 시간"))));
  }

  @Test
  void findMemories() {

    when(memoryService.findMemories(
            any(User.class), anyLong(), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(
            new Memory(
                1L,
                "첫 번째 추억",
                "내용 1",
                List.of("https://example.com/img1.jpg"),
                100L,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1)),
            new Memory(
                2L,
                "두 번째 추억",
                "내용 2",
                List.of("https://example.com/img2.jpg"),
                100L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now())));
    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/babies/{babyId}/memories", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-memories",
            pathParameters(parameterWithName("babyId").description("추억을 조회할 자녀 id")),
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("추억 id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("추억 제목"),
                fieldWithPath("data.[].imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("추억 대표 이미지 주소"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("추억 생성 시간"),
                fieldWithPath("data.[].updatedAt")
                    .type(JsonFieldType.STRING)
                    .description("추억 수정 시간"))));
  }

  @Test
  void modifyMemoryImage() {
    given()
        .contentType(ContentType.MULTIPART)
        .multiPart("memoryImages", "modify-test1.jpg", new byte[0], "image/jpeg")
        .multiPart("memoryImages", "modify-test2.jpg", new byte[0], "image/jpeg")
        .multiPart("memoryImages", "modify-test3.jpg", new byte[0], "image/jpeg")
        .patch("/memories/{memoryId}/image", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-memory-image",
            pathParameters(parameterWithName("memoryId").description("사진을 변경할 추억의 id")),
            requestParts(partWithName("memoryImages").description("변경할 추억 사진 파일 목록")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void modifyMemory() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyMemoryRequest("수정 제목", "수정 내용"))
        .patch("/memories/{memoryId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-memory",
            pathParameters(parameterWithName("memoryId").description("정보를 수정할 추억의 id")),
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 추억 제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 추억 내용")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void deleteMemory() {
    when(memoryService.delete(any(User.class), anyLong())).thenReturn(1L);
    given()
        .contentType(ContentType.JSON)
        .delete("/memories/{memoryId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-memory",
            pathParameters(parameterWithName("memoryId").description("삭제 할 추억 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제된 추억 id"))));
  }
}
