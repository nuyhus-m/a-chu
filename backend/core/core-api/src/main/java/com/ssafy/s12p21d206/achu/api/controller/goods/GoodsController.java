package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import com.ssafy.s12p21d206.achu.domain.support.TradeType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GoodsController {

  @GetMapping("/categories")
  public ApiResponse<List<CategoryResponse>> findCategories(Long userId) {
    List<CategoryResponse> response = List.of(
        new CategoryResponse(1L, "출산"),
        new CategoryResponse(2L, "육아"),
        new CategoryResponse(3L, "실내"));
    return ApiResponse.success(response);
  }

  @GetMapping("/categories/{categoryId}/goods")
  public ApiResponse<List<GoodsResponse>> findCategoryGoods(
      Long userId,
      @PathVariable Long categoryId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 12, 16, 16);
    List<GoodsResponse> response = List.of(
        new GoodsResponse(1L, "물품1", "img_url1", 5000L, createdAt, 3L, 11L, true),
        new GoodsResponse(3L, "물품3", "img_url3", 10000L, createdAt, 0L, 3L, false),
        new GoodsResponse(10L, "물품10", "img_url10", 0L, createdAt, 7L, 0L, true));
    return ApiResponse.success(response);
  }

  @GetMapping("/goods")
  public ApiResponse<List<GoodsResponse>> findGoods(
      Long userId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 12, 22, 56);
    List<GoodsResponse> response = List.of(
        new GoodsResponse(1L, "물품1", "img_url1", 5000L, createdAt, 3L, 11L, false),
        new GoodsResponse(2L, "물품2", "img_url2", 13000L, createdAt.plusDays(1L), 0L, 5L, true));
    return ApiResponse.success(response);
  }

  @GetMapping("/goods/search")
  public ApiResponse<List<GoodsResponse>> searchGoods(
      Long userId,
      @RequestParam String keyword,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 12, 23, 53);
    List<GoodsResponse> response = List.of(
        new GoodsResponse(5L, "유아 유모차", "goods5_img_url_1", 100000L, createdAt, 1L, 3L, true),
        new GoodsResponse(7L, "유아식기", "goods7_img_url_1", 1500000L, createdAt, 0L, 10L, false));
    return ApiResponse.success(response);
  }

  @GetMapping("/categories/{categoryId}/goods/search")
  public ApiResponse<List<GoodsResponse>> searchCategoryGoods(
      Long userId,
      @PathVariable Long categoryId,
      @RequestParam String keyword,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 13, 13, 19);
    List<GoodsResponse> response = List.of(
        new GoodsResponse(11L, "튤립 장난감", "goods11_img_url_1", 3000L, createdAt, 1L, 3L, false),
        new GoodsResponse(15L, "장난감 기차", "goods15_img_url_1", 10000L, createdAt, 4L, 3L, true));
    return ApiResponse.success(response);
  }

  @GetMapping("/goods/{goodsId}")
  public ApiResponse<GoodsDetailResponse> findGoodsDetail(Long userId, @PathVariable Long goodsId) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 13, 14, 11);
    CategoryResponse category = new CategoryResponse(2L, "의류");
    UserResponse seller = new UserResponse(1L, "재영 맘", "재영맘_img_url");
    GoodsDetailResponse response = new GoodsDetailResponse(
        13L,
        "여아 원피스",
        "8/31에 사고 2번 입은 제품입니다.",
        List.of("goods13_img_url_1", "goods13_img_url_2", "goods13_img_url_3"),
        TradeStatus.SOLD,
        10000L,
        createdAt,
        11L,
        false,
        category,
        seller);
    return ApiResponse.success(response);
  }

  @PostMapping("/goods")
  public ApiResponse<DefaultIdResponse> appendGoods(
      Long userId,
      @RequestPart("request") AppendGoodsRequest request,
      @RequestPart("goodsImages") MultipartFile[] files) {
    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @PatchMapping("/goods/{goodsId}/images")
  public ApiResponse<Void> modifyGoodsImages(
      Long userId, @PathVariable Long goodsId, @RequestParam("goodsImages") MultipartFile[] files) {
    return ApiResponse.success();
  }

  @PutMapping("/goods/{goodsId}")
  public ApiResponse<Void> modifyGoods(
      Long userId, @PathVariable Long goodsId, @RequestBody ModifyGoodsRequest request) {
    return ApiResponse.success();
  }

  @DeleteMapping("/goods/{goodsId}")
  public ApiResponse<Void> deleteGoods(Long userId, @PathVariable Long goodsId) {
    return ApiResponse.success();
  }

  @GetMapping("/trade-history")
  public ApiResponse<List<TradeHistoryResponse>> findGoodsTradeHistory(
      Long userId,
      @RequestParam TradeType tradeType,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<TradeHistoryResponse> response = List.of(
        new TradeHistoryResponse(1L, TradeStatus.SELLING, "나무 장난감", "goods1_img_url", 5000L),
        new TradeHistoryResponse(2L, TradeStatus.SOLD, "유아용 식판", "goods2_img_url", 7000L));
    return ApiResponse.success(response);
  }

  @GetMapping("/goods/liked")
  public ApiResponse<List<TradeHistoryResponse>> findLikedGoods(Long userId) {
    List<TradeHistoryResponse> response = List.of(
        new TradeHistoryResponse(6L, TradeStatus.SOLD, "유아 식기", "goods6_img_url", 5000L),
        new TradeHistoryResponse(10L, TradeStatus.SELLING, "유모차", "goods10_img_url", 10000L));
    return ApiResponse.success(response);
  }

  @PostMapping("/goods/{goodsId}/like")
  public ApiResponse<Void> appendLikedGoods(Long userId, @PathVariable Long goodsId) {
    return ApiResponse.success();
  }

  @DeleteMapping("/goods/{goodsId}/like")
  public ApiResponse<Void> deleteLikedGoods(Long userId, @PathVariable Long goodsId) {
    return ApiResponse.success();
  }

  @PatchMapping("/trade/{tradeId}/complete")
  public ApiResponse<Void> modifyTradeStatus(Long userId, @PathVariable Long tradeId) {
    return ApiResponse.success();
  }
}
