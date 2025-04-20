package com.ssafy.s12p21d206.achu.api.controller.baby;

import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.BabyImageFacade;
import com.ssafy.s12p21d206.achu.domain.BabyService;
import com.ssafy.s12p21d206.achu.domain.Sex;
import com.ssafy.s12p21d206.achu.domain.image.ImageService;
import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

// sonarqube에서 test에 assertions이 없더라도 code smell로 인식하지 않음
@SuppressWarnings("java:S2699")
class BabyControllerTest extends RestDocsTest {

  private BabyController controller;
  private BabyService babyService;
  private ImageService imageService;
  private BabyImageFacade babyImageFacade;

  @BeforeEach
  void setup() {
    babyService = mock(BabyService.class);
    imageService = mock(ImageService.class);
    babyImageFacade = new BabyImageFacade(babyService, imageService);
    controller = new BabyController(babyService, babyImageFacade);
    mockMvc = mockController(controller);
  }

  @Test
  void appendBaby() {
    when(babyService.append(any(), any(), any())).thenReturn(1L);
    when(imageService.uploadImage(any())).thenReturn("http://dummy.image.url");

    given()
        .contentType(ContentType.MULTIPART)
        .multiPart("profileImage", "test.jpg", new byte[0], "image/jpeg")
        .multiPart(
            "request",
            new AppendBabyRequest("강두식", Sex.FEMALE, LocalDate.of(2025, 3, 20)),
            "application/json")
        .post("/babies")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-baby",
            requestParts(
                partWithName("profileImage").description("프로필 이미지 파일").optional(),
                partWithName("request").description("자녀 등록 요청 데이터")),
            requestPartFields(
                "request",
                fieldWithPath("nickname")
                    .type(JsonFieldType.STRING)
                    .description("등록할 자녀의 이름 혹은 별명"),
                fieldWithPath("gender").type(JsonFieldType.STRING).description("등록할 자녀의 성별"),
                fieldWithPath("birth").type(JsonFieldType.ARRAY).description("등록할 자녀의 생년월일")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("등록된 자녀의 id"))));
  }

  @Test
  void findBaby() {

    DefaultDateTime now = new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now());
    Baby baby =
        new Baby(1L, 1L, "강두식", Sex.MALE, "https://test-image1.png", LocalDate.of(2025, 1, 1), now);
    when(babyService.findBaby(any(), any())).thenReturn(baby);

    given()
        .contentType(ContentType.JSON)
        .get("/babies/{id}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-baby",
            pathParameters(parameterWithName("id").description("조회하고자 하는 자녀 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("자녀 id"),
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
  void findBabies() {

    DefaultDateTime now = new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now());

    Baby baby1 =
        new Baby(1L, 1L, "강두식", Sex.MALE, "https://test-image1.png", LocalDate.of(2025, 1, 1), now);
    Baby baby2 = new Baby(
        2L, 1L, "강삼식", Sex.FEMALE, "https://test-image2.png", LocalDate.of(2025, 3, 1), now);

    when(babyService.findBabies(any())).thenReturn(List.of(baby1, baby2));

    given()
        .contentType(ContentType.JSON)
        .get("/babies")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-babies",
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("자녀 id"),
                fieldWithPath("data.[].nickname")
                    .type(JsonFieldType.STRING)
                    .description("자녀 이름 혹은 별명"),
                fieldWithPath("data.[].gender")
                    .type(JsonFieldType.STRING)
                    .description("자녀 성별: MALE or FEMALE"),
                fieldWithPath("data.[].imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("자녀 프로필 이미지 주소"),
                fieldWithPath("data.[].birth").type(JsonFieldType.STRING).description("자녀 생년월일"))));
  }

  @Test
  void modifyProfileImage() {
    given()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .multiPart("profileImage", "modify-test.jpg", new byte[0], "image/jpeg")
        .patch("/babies/{babyId}/profile-image", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-baby-profile-image",
            pathParameters(parameterWithName("babyId").description("프로필 이미지를 변경할 자녀의 id")),
            requestParts(partWithName("profileImage").description("프로필 이미지 파일")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void modifyBabyNickname() {

    DefaultDateTime now = new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now());
    Baby baby =
        new Baby(1L, 1L, "강두식", Sex.MALE, "https://test-image1.png", LocalDate.of(2025, 1, 1), now);
    when(babyService.modifyNickname(any(), any(), any())).thenReturn(baby);

    given()
        .contentType(ContentType.JSON)
        .body(new ModifyBabyNicknameRequest("새로운닉네임"))
        .patch("/babies/{id}/nickname", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-baby-nickname",
            pathParameters(parameterWithName("id").description("정보를 수정할 자녀의 id")),
            requestFields(fieldWithPath("nickname")
                .type(JsonFieldType.STRING)
                .description("자녀의 수정할 이름 혹은 별명")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void modifyBabyBirth() {

    DefaultDateTime now = new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now());
    Baby baby =
        new Baby(1L, 1L, "강두식", Sex.MALE, "https://test-image1.png", LocalDate.of(2025, 2, 1), now);
    when(babyService.modifyBirth(any(), any(), any())).thenReturn(baby);

    given()
        .contentType(ContentType.JSON)
        .body(new ModifyBabyBirthRequest(LocalDate.of(2025, 1, 1)))
        .patch("/babies/{id}/birth", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-baby-birth",
            pathParameters(parameterWithName("id").description("정보를 수정할 자녀의 id")),
            requestFields(
                fieldWithPath("birth").type(JsonFieldType.ARRAY).description("자녀의 수정할 생년월일")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void modifyBabyGender() {
    DefaultDateTime now = new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now());
    Baby baby =
        new Baby(1L, 1L, "강두식", Sex.MALE, "https://test-image1.png", LocalDate.of(2025, 2, 1), now);
    when(babyService.modifyGender(any(), any(), any())).thenReturn(baby);

    given()
        .contentType(ContentType.JSON)
        .body(new ModifyBabyGenderRequest(Sex.FEMALE))
        .patch("/babies/{id}/gender", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-baby-gender",
            pathParameters(parameterWithName("id").description("정보를 수정할 자녀의 id")),
            requestFields(fieldWithPath("gender")
                .type(JsonFieldType.STRING)
                .description("자녀의 수정할 성별: MALE or FEMALE")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void deleteBaby() {

    when(babyService.delete(any(), any())).thenReturn(1L);

    given()
        .contentType(ContentType.JSON)
        .delete("/babies/{id}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-baby",
            pathParameters(parameterWithName("id").description("삭제 할 자녀의 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제된 자녀의 id"))));
  }
}
