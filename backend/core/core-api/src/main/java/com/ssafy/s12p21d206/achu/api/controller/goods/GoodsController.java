package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import com.ssafy.s12p21d206.achu.domain.support.TradeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GoodsController {

  private final CategoryService categoryService;
  private final GoodsService goodsService;

  private final LikeService likeService;
  private final ChatRoomService chatRoomService;

  private final UserService userService;

  public GoodsController(
      CategoryService categoryService,
      GoodsService goodsService,
      LikeService likeService,
      ChatRoomService chatRoomService,
      UserService userService) {
    this.categoryService = categoryService;
    this.goodsService = goodsService;
    this.likeService = likeService;
    this.chatRoomService = chatRoomService;
    this.userService = userService;
  }

  @GetMapping("/categories")
  public ApiResponse<List<CategoryResponse>> findCategories() {
    List<Category> categories = categoryService.findCategories();
    List<CategoryResponse> responses = CategoryResponse.of(categories);
    return ApiResponse.success(responses);
  }

  @GetMapping("/categories/{categoryId}/goods")
  public ApiResponse<List<GoodsResponse>> findCategoryGoods(
      ApiUser apiUser,
      @PathVariable Long categoryId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<Goods> goods =
        goodsService.findCategoryGoods(apiUser.toUser(), categoryId, offset, limit, sort);
    List<Long> goodsIds = goods.stream().map(Goods::getId).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = chatRoomService.findChatStatus(apiUser.toUser(), goodsIds);
    List<GoodsResponse> responses = GoodsResponse.of(goods, chatStatuses, likeStatuses);
    return ApiResponse.success(responses);
  }

  @GetMapping("/goods")
  public ApiResponse<List<GoodsResponse>> findGoods(
      ApiUser apiUser,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<Goods> goods = goodsService.findGoods(apiUser.toUser(), offset, limit, sort);
    List<Long> goodsIds = goods.stream().map(Goods::getId).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = chatRoomService.findChatStatus(apiUser.toUser(), goodsIds);
    List<GoodsResponse> responses = GoodsResponse.of(goods, chatStatuses, likeStatuses);
    return ApiResponse.success(responses);
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
        new GoodsResponse(5L, "유아 유모차", "goods5_img_url_1", 100000L, createdAt, 1L, 3, true),
        new GoodsResponse(7L, "유아식기", "goods7_img_url_1", 1500000L, createdAt, 0L, 10, false));
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
        new GoodsResponse(11L, "튤립 장난감", "goods11_img_url_1", 3000L, createdAt, 1L, 3, false),
        new GoodsResponse(15L, "장난감 기차", "goods15_img_url_1", 10000L, createdAt, 4L, 3, true));
    return ApiResponse.success(response);
  }

  @GetMapping("/goods/{goodsId}")
  public ApiResponse<GoodsDetailResponse> findGoodsDetail(
      ApiUser apiUser, @PathVariable Long goodsId) {
    GoodsDetail goodsDetail = goodsService.findGoodsDetail(goodsId);

    CategoryId categoryId = goodsService.findCategoryIdByGoodsId(goodsId);
    User userId = goodsService.findUserIdByGoodsId(goodsId);

    LikeStatus likeStatus = likeService.findLikeStatus(apiUser.toUser(), goodsId);

    Category category = categoryService.findCategoryInfo(categoryId.id());
    CategoryResponse categoryResponse = CategoryResponse.from(category);

    Seller seller = userService.findSellerInfo(userId);
    UserResponse sellerResponse = UserResponse.from(seller);

    GoodsDetailResponse response =
        GoodsDetailResponse.of(goodsDetail, likeStatus, categoryResponse, sellerResponse);
    return ApiResponse.success(response);
  }

  @PostMapping("/goods")
  public ApiResponse<DefaultIdResponse> appendGoods(
      ApiUser apiUser,
      @RequestPart("request") AppendGoodsRequest request,
      @RequestPart("Images") MultipartFile[] files) {
    List<String> imgUrls = List.of("goods1-img-url1.jpg", "goods1-img-url2.jpg");
    NewGoods newGoods = request.toNewGoods(imgUrls);
    Long id = goodsService.append(apiUser.toUser(), newGoods);
    return ApiResponse.success(new DefaultIdResponse(id));
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

  @PatchMapping("/trade/{tradeId}/complete")
  public ApiResponse<Void> modifyTradeStatus(Long userId, @PathVariable Long tradeId) {
    return ApiResponse.success();
  }
}
