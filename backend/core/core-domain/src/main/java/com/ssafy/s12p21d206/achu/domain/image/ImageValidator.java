package com.ssafy.s12p21d206.achu.domain.image;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
class ImageValidator {

  private final Tika tika = new Tika();

  public void validate(File file) {

    String detectedMimeType = tika.detect(file.content());
    if (!detectedMimeType.equals(file.contentType())) {
      throw new CoreException(CoreErrorType.INCORRECT_MIME_TYPE);
    }

    if (!detectedMimeType.startsWith("image/")) {
      throw new CoreException(CoreErrorType.INVALID_IMAGE);
    }
  }
}
