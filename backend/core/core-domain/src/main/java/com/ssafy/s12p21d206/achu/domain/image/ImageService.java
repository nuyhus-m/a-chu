package com.ssafy.s12p21d206.achu.domain.image;

import com.ssafy.s12p21d206.achu.domain.ImageUrlsWithThumbnail;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  private final FileUploader fileUploader;
  private final ImageValidator authImageValidator;
  private final ImageCompressor authImageCompressor;

  public ImageService(
      FileUploader fileUploader,
      ImageValidator authImageValidator,
      ImageCompressor authImageCompressor) {
    this.fileUploader = fileUploader;
    this.authImageValidator = authImageValidator;
    this.authImageCompressor = authImageCompressor;
  }

  public String uploadImage(File file, ImageCompressionOption option) {
    authImageValidator.validate(file);
    File compressedFile = authImageCompressor.compressFile(file, option);
    return fileUploader.uploadFile(compressedFile);
  }

  public String uploadImage(File file) {
    return uploadImage(file, ImageCompressionOption.ORIGINAL);
  }

  public List<String> uploadImages(List<File> files, ImageCompressionOption option) {
    List<File> compressedFiles = files.stream()
        .map(file -> authImageCompressor.compressFile(file, option))
        .collect(Collectors.toList());
    return fileUploader.uploadFiles(compressedFiles);
  }

  public List<String> uploadImages(List<File> files) {
    return uploadImages(files, ImageCompressionOption.ORIGINAL);
  }

  public ImageUrlsWithThumbnail uploadImageUrlsWithThumbnail(List<File> imageFiles) {
    String thumbnailImageUrl =
        uploadImage(imageFiles.get(0), ImageCompressionOption.THUMBNAIL_IMAGE);
    List<String> imageUrls = uploadImages(imageFiles);
    return new ImageUrlsWithThumbnail(thumbnailImageUrl, imageUrls);
  }
}
