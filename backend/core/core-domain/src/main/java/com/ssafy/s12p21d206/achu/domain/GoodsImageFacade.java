package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.image.File;
import com.ssafy.s12p21d206.achu.domain.image.ImageService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoodsImageFacade {

  private final GoodsService goodsService;
  private final ImageService imageService;

  public GoodsImageFacade(GoodsService goodsService, ImageService imageService) {
    this.goodsService = goodsService;
    this.imageService = imageService;
  }

  public GoodsDetail append(User user, NewGoods newGoods, List<File> imageFiles) {
    ImageUrlsWithThumbnail imageUrlsWithThumbnail =
        imageService.uploadImageUrlsWithThumbnail(imageFiles);
    return goodsService.append(user, newGoods, imageUrlsWithThumbnail);
  }

  public Goods modifyImages(User user, Long goodsId, List<File> imageFiles) {
    ImageUrlsWithThumbnail imageUrlsWithThumbnail =
        imageService.uploadImageUrlsWithThumbnail(imageFiles);
    return goodsService.modifyImages(user, goodsId, imageUrlsWithThumbnail);
  }
}
