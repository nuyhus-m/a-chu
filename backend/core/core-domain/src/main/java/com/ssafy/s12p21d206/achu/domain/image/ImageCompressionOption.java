package com.ssafy.s12p21d206.achu.domain.image;

public record ImageCompressionOption(double compressionQuality, double scale) {

  public static final ImageCompressionOption ORIGINAL = new ImageCompressionOption(0, 0);
  public static final ImageCompressionOption THUMBNAIL_IMAGE = new ImageCompressionOption(0.8, 0.5);
  public static final ImageCompressionOption PROFILE_IMAGE = new ImageCompressionOption(0.6, 0.3);
}
