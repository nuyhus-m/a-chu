package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.image.File;
import com.ssafy.s12p21d206.achu.domain.image.ImageService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemoryImageFacade {

  private final MemoryService memoryService;
  private final ImageService imageService;

  public MemoryImageFacade(MemoryService memoryService, ImageService imageService) {
    this.memoryService = memoryService;
    this.imageService = imageService;
  }

  public Memory append(User user, Long babyId, NewMemory newMemory, List<File> imageFiles) {
    ImageUrlsWithThumbnail imageUrlsWithThumbnail =
        imageService.uploadImageUrlsWithThumbnail(imageFiles);
    return memoryService.append(user, babyId, newMemory, imageUrlsWithThumbnail);
  }

  public Memory modifyImages(User user, Long memoryId, List<File> imageFiles) {
    ImageUrlsWithThumbnail imageUrlsWithThumbnail =
        imageService.uploadImageUrlsWithThumbnail(imageFiles);
    return memoryService.modifyImages(user, memoryId, imageUrlsWithThumbnail);
  }
}
