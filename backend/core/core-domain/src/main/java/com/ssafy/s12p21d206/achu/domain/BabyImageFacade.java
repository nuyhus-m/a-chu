package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.image.File;
import com.ssafy.s12p21d206.achu.domain.image.ImageCompressionOption;
import com.ssafy.s12p21d206.achu.domain.image.ImageService;
import org.springframework.stereotype.Service;

@Service
public class BabyImageFacade {

  private final BabyService babyService;
  private final ImageService imageService;

  public BabyImageFacade(BabyService babyService, ImageService imageService) {
    this.babyService = babyService;
    this.imageService = imageService;
  }

  public Baby modifyImageUrl(User user, Long id, File imageFile) {
    String imageUrl = imageService.uploadImage(imageFile, ImageCompressionOption.PROFILE_IMAGE);
    return babyService.modifyImageUrl(user, id, imageUrl);
  }

  public Long append(User user, File imageFile, NewBaby newBaby) {
    String imageUrl = imageService.uploadImage(imageFile);
    return babyService.append(user, newBaby, imageUrl);
  }

  public Long append(User user, NewBaby newBaby) {
    return babyService.append(user, newBaby, null);
  }
}
