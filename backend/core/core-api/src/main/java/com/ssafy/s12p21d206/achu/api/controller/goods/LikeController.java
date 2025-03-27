package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.domain.LikeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

  private final LikeService likeService;

  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  @PostMapping("/goods/{goodsId}/like")
  public ApiResponse<Void> like(ApiUser apiUser, @PathVariable Long goodsId) {
    likeService.like(apiUser.toUser(), goodsId);
    return ApiResponse.success();
  }

  @DeleteMapping("/goods/{goodsId}/like")
  public ApiResponse<Void> deleteLike(ApiUser apiUser, @PathVariable Long goodsId) {
    likeService.deleteLike(apiUser.toUser(), goodsId);
    return ApiResponse.success();
  }
}
