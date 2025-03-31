package com.ssafy.s12p21d206.achu.domain.image;

public enum ImageCompressionOption {
  ORIGINAL(1, 1),
  THUMBNAIL_IMAGE(0.8, 0.5),
  PROFILE_IMAGE(0.6, 0.3);

  private final double compressionQuality;
  private final double scale;

  ImageCompressionOption(double compressionQuality, double scale) {
    this.compressionQuality = compressionQuality;
    this.scale = scale;
  }

  public double compressionQuality() {
    return compressionQuality;
  }

  public double scale() {
    return scale;
  }
}
