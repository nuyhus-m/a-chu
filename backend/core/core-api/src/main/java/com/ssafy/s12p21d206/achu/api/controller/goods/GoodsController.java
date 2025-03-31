package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.controller.support.FileConverter;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import com.ssafy.s12p21d206.achu.domain.TradeType;
import com.ssafy.s12p21d206.achu.domain.image.File;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GoodsController {

  private final GoodsImageFacade goodsImageFacade;

  private final CategoryService categoryService;
  private final GoodsService goodsService;

  private final LikeService likeService;

  private final UserService userService;

  private final TradeService tradeService;

  public GoodsController(
      GoodsImageFacade goodsImageFacade,
      CategoryService categoryService,
      GoodsService goodsService,
      LikeService likeService,
      UserService userService,
      TradeService tradeService) {
    this.goodsImageFacade = goodsImageFacade;
    this.categoryService = categoryService;
    this.goodsService = goodsService;
    this.likeService = likeService;
    this.userService = userService;
    this.tradeService = tradeService;
  }

  @PostMapping("/goods")
  public ApiResponse<DefaultIdResponse> appendGoods(
      ApiUser apiUser,
      @RequestPart("request") @Validated AppendGoodsRequest request,
      @RequestPart("images") List<MultipartFile> multipartFiles) {
    NewGoods newGoods = request.toNewGoods();
    List<File> imageFiles = multipartFiles.stream().map(FileConverter::convert).toList();
    GoodsDetail goodsDetail = goodsImageFacade.append(apiUser.toUser(), newGoods, imageFiles);
    return ApiResponse.success(new DefaultIdResponse(goodsDetail.goods().id()));
  }

  @PatchMapping("/goods/{goodsId}/images")
  public ApiResponse<Void> modifyGoodsImages(
      ApiUser apiUser,
      @PathVariable Long goodsId,
      @RequestParam("images") List<MultipartFile> multipartFiles) {
    List<File> imageFiles = multipartFiles.stream().map(FileConverter::convert).toList();
    goodsImageFacade.modifyImages(apiUser.toUser(), goodsId, imageFiles);
    return ApiResponse.success();
  }

  @PutMapping("/goods/{goodsId}")
  public ApiResponse<Void> modifyGoods(
      ApiUser apiUser,
      @PathVariable Long goodsId,
      @RequestBody @Validated ModifyGoodsRequest request) {
    ModifyGoods modifyGoods = ModifyGoodsRequest.toModifyGoods(request);
    goodsService.modify(apiUser.toUser(), goodsId, modifyGoods);
    return ApiResponse.success();
  }

  @DeleteMapping("/goods/{goodsId}")
  public ApiResponse<DefaultIdResponse> deleteGoods(ApiUser apiUser, @PathVariable Long goodsId) {
    goodsService.delete(apiUser.toUser(), goodsId);
    return ApiResponse.success(new DefaultIdResponse(goodsId));
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
    List<Long> goodsIds = goods.stream().map(Goods::id).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = List.of();
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
    List<Long> goodsIds = goods.stream().map(Goods::id).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = List.of();
    List<GoodsResponse> responses = GoodsResponse.of(goods, chatStatuses, likeStatuses);
    return ApiResponse.success(responses);
  }

  @GetMapping("/goods/{goodsId}")
  public ApiResponse<GoodsDetailResponse> findGoodsDetail(
      ApiUser apiUser, @PathVariable Long goodsId) {
    GoodsDetail goodsDetail = goodsService.findGoodsDetail(goodsId);

    LikeStatus likeStatus = likeService.status(apiUser.toUser(), goodsId);

    UserDetail userDetail = userService.findSellerInfo(goodsDetail.goods().user());

    CategoryResponse categoryResponse = CategoryResponse.from(goodsDetail.category());
    UserResponse sellerResponse = UserResponse.from(userDetail);

    GoodsDetailResponse response =
        GoodsDetailResponse.of(goodsDetail, likeStatus, categoryResponse, sellerResponse);
    return ApiResponse.success(response);
  }

  @GetMapping("/goods/search")
  public ApiResponse<List<GoodsResponse>> searchGoods(
      ApiUser apiUser,
      @RequestParam String keyword,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {

    List<Goods> goods = goodsService.searchGoods(apiUser.toUser(), keyword, offset, limit, sort);
    List<Long> goodsIds = goods.stream().map(Goods::id).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = List.of();
    List<GoodsResponse> responses = GoodsResponse.of(goods, chatStatuses, likeStatuses);
    return ApiResponse.success(responses);
  }

  @GetMapping("/categories/{categoryId}/goods/search")
  public ApiResponse<List<GoodsResponse>> searchCategoryGoods(
      ApiUser apiUser,
      @PathVariable Long categoryId,
      @RequestParam String keyword,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {

    List<Goods> goods = goodsService.searchCategoryGoods(
        apiUser.toUser(), categoryId, keyword, offset, limit, sort);
    List<Long> goodsIds = goods.stream().map(Goods::id).toList();
    Map<Long, LikeStatus> likeStatuses = likeService.status(apiUser.toUser(), goodsIds);
    List<ChatStatus> chatStatuses = List.of();
    List<GoodsResponse> responses = GoodsResponse.of(goods, chatStatuses, likeStatuses);
    return ApiResponse.success(responses);
  }

  @GetMapping("/trades")
  public ApiResponse<List<TradeResponse>> findTradedGoods(
      ApiUser apiUser,
      @RequestParam TradeType tradeType,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<Goods> goods =
        tradeService.findTradedGoods(apiUser.toUser(), tradeType, offset, limit, sort);
    List<TradeResponse> responses = TradeResponse.of(goods);
    return ApiResponse.success(responses);
  }

  @PostMapping("/goods/{goodsId}/trade/complete")
  public ApiResponse<DefaultIdResponse> completeTrade(
      ApiUser apiUser, @PathVariable Long goodsId, @RequestBody AppendTradeRequest request) {
    Trade trade = tradeService.completeTrade(apiUser.toUser(), goodsId, request.toNewTrade());
    return ApiResponse.success(new DefaultIdResponse(trade.id()));
  }

  @GetMapping("/goods/liked")
  public ApiResponse<List<TradeResponse>> findLikedGoods(Long userId) {
    List<TradeResponse> response = List.of(
        new TradeResponse(6L, TradeStatus.SOLD, "유아 식기", "goods6_img_url", 5000L),
        new TradeResponse(10L, TradeStatus.SELLING, "유모차", "goods10_img_url", 10000L));
    return ApiResponse.success(response);
  }
}
