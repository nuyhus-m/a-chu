package com.ssafy.s12p21d206.achu.api.controller.goods;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import com.ssafy.s12p21d206.achu.domain.TradeType;
import com.ssafy.s12p21d206.achu.domain.image.ImageService;
import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.test.api.RestDocsTest;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

// sonarqube에서 test에 assertions이 없더라도 code smell로 인식하지 않음
@SuppressWarnings("java:S2699")
class GoodsControllerTest extends RestDocsTest {

  private GoodsController controller;
  private CategoryService categoryService;
  private GoodsService goodsService;

  private LikeService likeService;
  private UserService userService;
  private TradeService tradeService;

  private ImageService imageService;

  private GoodsImageFacade goodsImageFacade;

  @BeforeEach
  void setup() {
    categoryService = mock(CategoryService.class);
    goodsService = mock(GoodsService.class);
    likeService = mock(LikeService.class);
    userService = mock(UserService.class);
    tradeService = mock(TradeService.class);

    imageService = mock(ImageService.class);
    goodsImageFacade = new GoodsImageFacade(goodsService, imageService);

    controller = new GoodsController(
        goodsImageFacade, categoryService, goodsService, likeService, userService, tradeService);
    mockMvc = mockController(controller);
  }

  Goods goods = new Goods(
      1L,
      "장난감 자동차",
      "설명",
      new ImageUrlsWithThumbnail(
          "https://dummy.thumbmail.image", List.of("https://example.com/img1.jpg")),
      TradeStatus.SELLING,
      10000L,
      new DefaultDateTime(LocalDateTime.now(), LocalDateTime.now().minusDays(1)),
      1L,
      new User(1L),
      2L);

  @Test
  void findCategories() {
    when(categoryService.findCategories())
        .thenReturn(List.of(new Category(1L, "카테고리1"), new Category(2L, "카테고리2")));

    given()
        .contentType(ContentType.JSON)
        .get("/categories")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-categories",
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("category id"),
                fieldWithPath("data.[].name")
                    .type(JsonFieldType.STRING)
                    .description("category 이름"))));
  }

  @Test
  void findCategoryGoods() {
    when(goodsService.findCategoryGoods(
            any(User.class), anyLong(), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));

    when(likeService.status(any(User.class), anyList()))
        .thenReturn(Map.of(goods.id(), new LikeStatus(5, true)));

    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/categories/{categoryId}/goods", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-category-goods",
            pathParameters(parameterWithName("categoryId").description("조회하고자 하는 category id")),
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("물건 가격"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("물건 생성 시간"),
                fieldWithPath("data.[].chatCount")
                    .type(JsonFieldType.NUMBER)
                    .description("채팅 건 사람 수"),
                fieldWithPath("data.[].likedUsersCount")
                    .type(JsonFieldType.NUMBER)
                    .description("찜 누른 사람 수"),
                fieldWithPath("data.[].likedByUser")
                    .type(JsonFieldType.BOOLEAN)
                    .description("로그인한 사용자가 해당 상품을 찜했는지 여부 (true: 찜함, false: 찜 안 함)"))));
  }

  @Test
  void findGoods() {
    when(goodsService.findGoods(any(User.class), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));

    when(likeService.status(any(User.class), anyList()))
        .thenReturn(Map.of(goods.id(), new LikeStatus(5, true)));

    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/goods")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-goods",
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("물건 가격"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("물건 생성 시간"),
                fieldWithPath("data.[].chatCount")
                    .type(JsonFieldType.NUMBER)
                    .description("채팅 건 사람 수"),
                fieldWithPath("data.[].likedUsersCount")
                    .type(JsonFieldType.NUMBER)
                    .description("찜 누른 사람 수"),
                fieldWithPath("data.[].likedByUser")
                    .type(JsonFieldType.BOOLEAN)
                    .description("로그인한 사용자가 해당 상품을 찜했는지 여부 (true: 찜함, false: 찜 안 함)"))));
  }

  @Test
  void searchGoods() {
    when(goodsService.searchGoods(
            any(User.class), anyString(), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));

    when(likeService.status(any(User.class), anyList()))
        .thenReturn(Map.of(goods.id(), new LikeStatus(5, true)));

    given()
        .contentType(ContentType.JSON)
        .queryParam("keyword", "유")
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/goods/search")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "search-goods",
            queryParameters(
                parameterWithName("keyword")
                    .description(
                        "검색 키워드. 입력한 단어가 포함된 상품을 검색합니다. 한 글자 이상 입력하면 연관된 결과를 반환합니다. (예: '유' → '유모차', '유아식기')"),
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("물건 가격"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("물건 생성 시간"),
                fieldWithPath("data.[].chatCount")
                    .type(JsonFieldType.NUMBER)
                    .description("채팅 건 사람 수"),
                fieldWithPath("data.[].likedUsersCount")
                    .type(JsonFieldType.NUMBER)
                    .description("찜 누른 사람 수"),
                fieldWithPath("data.[].likedByUser")
                    .type(JsonFieldType.BOOLEAN)
                    .description("로그인한 사용자가 해당 상품을 찜했는지 여부 (true: 찜함, false: 찜 안 함)"))));
  }

  @Test
  void searchCategoryGoods() {
    when(goodsService.searchCategoryGoods(
            any(User.class), anyLong(), anyString(), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));
    when(likeService.status(any(User.class), anyList()))
        .thenReturn(Map.of(goods.id(), new LikeStatus(5, true)));

    given()
        .contentType(ContentType.JSON)
        .queryParam("keyword", "장난감")
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/categories/{categoryId}/goods/search", 7L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "search-category-goods",
            pathParameters(parameterWithName("categoryId").description("검색하고자 하는 category id")),
            queryParameters(
                parameterWithName("keyword")
                    .description(
                        "검색 키워드. 입력한 단어가 포함된 상품을 검색합니다. 한 글자 이상 입력하면 연관된 결과를 반환합니다. (예: '장난감' → '튤립 장난감', '장난감 기차')"),
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("물건 가격"),
                fieldWithPath("data.[].createdAt")
                    .type(JsonFieldType.STRING)
                    .description("물건 생성 시간"),
                fieldWithPath("data.[].chatCount")
                    .type(JsonFieldType.NUMBER)
                    .description("채팅 건 사람 수"),
                fieldWithPath("data.[].likedUsersCount")
                    .type(JsonFieldType.NUMBER)
                    .description("찜 누른 사람 수"),
                fieldWithPath("data.[].likedByUser")
                    .type(JsonFieldType.BOOLEAN)
                    .description("로그인한 사용자가 해당 상품을 찜했는지 여부 (true: 찜함, false: 찜 안 함)"))));
  }

  @Test
  void findGoodsDetail() {
    when(goodsService.findGoodsDetail(anyLong()))
        .thenReturn(new GoodsDetail(goods, new Category(1L, "카테고리명1")));
    when(likeService.status(any(User.class), anyLong())).thenReturn(new LikeStatus(1, true));

    when(userService.findSellerInfo(any(User.class)))
        .thenReturn(new UserDetail(1L, "닉네임", "img.jpg"));
    given()
        .contentType(ContentType.JSON)
        .get("/goods/{goodsId}", 2L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-goods-detail",
            pathParameters(parameterWithName("goodsId").description("조회하고자 하는 goods id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.description").type(JsonFieldType.STRING).description("물건 설명"),
                fieldWithPath("data.imgUrls")
                    .type(JsonFieldType.ARRAY)
                    .description("물건 이미지 리스트 (최소 1장 ~ 최대 5장)"),
                fieldWithPath("data.tradeStatus")
                    .type(JsonFieldType.STRING)
                    .description("물건 거래 상태 (SELLING or SOLD)"),
                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("물건 가격"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("물건 생성 시간"),
                fieldWithPath("data.likedByUser")
                    .type(JsonFieldType.BOOLEAN)
                    .description("로그인한 사용자가 해당 상품을 찜했는지 여부 (true: 찜함, false: 찜 안 함)"),
                fieldWithPath("data.likedUsersCount")
                    .type(JsonFieldType.NUMBER)
                    .description("찜 누른 사람 수"),
                fieldWithPath("data.category").type(JsonFieldType.OBJECT).description("물건 카테고리 정보"),
                fieldWithPath("data.category.id").type(JsonFieldType.NUMBER).description("카테고리 id"),
                fieldWithPath("data.category.name")
                    .type(JsonFieldType.STRING)
                    .description("카테고리 이름"),
                fieldWithPath("data.seller").type(JsonFieldType.OBJECT).description("판매자 정보"),
                fieldWithPath("data.seller.id")
                    .type(JsonFieldType.NUMBER)
                    .description("판매자의 user id"),
                fieldWithPath("data.seller.nickname")
                    .type(JsonFieldType.STRING)
                    .description("판매자의 닉네임"),
                fieldWithPath("data.seller.imgUrl")
                    .type(JsonFieldType.STRING)
                    .description("판매자의 프로필 이미지"))));
  }

  @Test
  void appendGoods() {
    when(goodsService.append(any(), any(), any()))
        .thenReturn(new GoodsDetail(goods, new Category(1L, "카테고리명1")));
    given()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .multiPart(
            "request", new AppendGoodsRequest("제목1", "설명1", 5000L, 1L, 1L), "application/json")
        .multiPart("images", "goods1-image1.jpg", new byte[0], "image/jpeg")
        .multiPart("images", "goods1-image2.jpg", new byte[0], "image/jpeg")
        .post("/goods")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "append-goods",
            requestParts(
                partWithName("request").description("등록할 상품 정보(JSON)"),
                partWithName("images").description("첨부 이미지 파일들 (최소 1장 ~ 최대 5장)")),
            requestPartFields(
                "request",
                fieldWithPath("title").type(JsonFieldType.STRING).description("생성할 물건 제목"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("생성할 물건 설명"),
                fieldWithPath("price").type(JsonFieldType.NUMBER).description("생성할 물건 가격"),
                fieldWithPath("categoryId")
                    .type(JsonFieldType.NUMBER)
                    .description("생성할 물건 카테고리 id"),
                fieldWithPath("babyId")
                    .type(JsonFieldType.NUMBER)
                    .description("물건 주인(아기)의 baby id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id")
                    .type(JsonFieldType.NUMBER)
                    .description("생성된 물건 goods id"))));
  }

  @Test
  void modifyGoodsImages() {
    given()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .multiPart("images", "modify-test.jpg", new byte[0], "image/jpeg")
        .patch("/goods/{goodsId}/images", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-goods-images",
            pathParameters(parameterWithName("goodsId").description("이미지 수정할 물건 id")),
            requestParts(partWithName("images").description("수정할 물건 이미지 파일들 (최소 1장 ~ 최대 5장)")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void modifyGoods() {
    given()
        .contentType(ContentType.JSON)
        .body(new ModifyGoodsRequest("수정된 제목", "수정된 설명", 2000L, 1L, 2L))
        .put("/goods/{goodsId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "modify-goods",
            pathParameters(parameterWithName("goodsId").description("수정하고자 하는 물건 id")),
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 물건 제목"),
                fieldWithPath("description").type(JsonFieldType.STRING).description("수정할 물건 설명"),
                fieldWithPath("price").type(JsonFieldType.NUMBER).description("수정할 물건 가격"),
                fieldWithPath("categoryId")
                    .type(JsonFieldType.NUMBER)
                    .description("수정할 물건 카테고리 id"),
                fieldWithPath("babyId")
                    .type(JsonFieldType.NUMBER)
                    .description("수정할 물건 주인(아기)의 id")),
            responseFields(fieldWithPath("result")
                .type(JsonFieldType.STRING)
                .description("성공 여부 (예: SUCCESS 혹은 ERROR)"))));
  }

  @Test
  void deleteGoods() {
    when(goodsService.delete(any(), any())).thenReturn(1L);
    given()
        .contentType(ContentType.JSON)
        .delete("/goods/{goodsId}", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "delete-goods",
            pathParameters(parameterWithName("goodsId").description("삭제하고자 하는 물품 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제된 물품 id"))));
  }

  @Test
  void findTradedGoods() {
    when(tradeService.findTradedGoods(
            any(User.class), any(TradeType.class), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));
    given()
        .contentType(ContentType.JSON)
        .queryParam("tradeType", TradeType.SALE)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/trades")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-goods-trade",
            queryParameters(
                parameterWithName("tradeType").description("거래 조회 유형 (PURCHASE or SALE)"),
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].tradeStatus")
                    .type(JsonFieldType.STRING)
                    .description("물건 거래 상태 (SELLING or SOLD)"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("거래 가격"))));
  }

  @Test
  void findLikedGoods() {
    when(likeService.findLikedGoods(any(User.class), anyLong(), anyLong(), any(SortType.class)))
        .thenReturn(List.of(goods));
    given()
        .contentType(ContentType.JSON)
        .queryParam("offset", 0)
        .queryParam("limit", 20)
        .queryParam("sort", "LATEST")
        .get("/goods/liked")
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "find-liked",
            queryParameters(
                parameterWithName("offset").description("결과 목록의 시작 인덱스를 나타냅니다. (예: 0)"),
                parameterWithName("limit").description("한 페이지에 반환할 최대 데이터 수를 지정합니다. (예: 20)"),
                parameterWithName("sort").description("데이터 정렬 기준을 지정합니다. (예: LATEST 혹은 OLDEST)")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("물건 id"),
                fieldWithPath("data.[].tradeStatus")
                    .type(JsonFieldType.STRING)
                    .description("거래 상태 (SELLING or SOLD)"),
                fieldWithPath("data.[].title").type(JsonFieldType.STRING).description("물건 제목"),
                fieldWithPath("data.[].imgUrl").type(JsonFieldType.STRING).description("물건 대표 이미지"),
                fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("판매 가격"))));
  }

  @Test
  void completeTrade() {
    when(tradeService.completeTrade(any(User.class), anyLong(), any(NewTrade.class)))
        .thenReturn(new Trade(1L, 2L, 3L, 7L));

    given()
        .contentType(ContentType.JSON)
        .body(new AppendTradeRequest(1L))
        .post("/goods/{goodsId}/trade/complete", 1L)
        .then()
        .status(HttpStatus.OK)
        .apply(document(
            "complete-trade",
            requestFields(fieldWithPath("buyerId").description("구매자 id")),
            pathParameters(parameterWithName("goodsId").description("거래 완료할 물건 id")),
            responseFields(
                fieldWithPath("result")
                    .type(JsonFieldType.STRING)
                    .description("성공 여부 (예: SUCCESS 혹은 ERROR)"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 거래내역 id"))));
  }
}
