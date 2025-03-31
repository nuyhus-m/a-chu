package com.ssafy.s12p21d206.achu.api.controller.support;

import com.ssafy.s12p21d206.achu.api.error.CoreApiErrorType;
import com.ssafy.s12p21d206.achu.api.error.CoreApiException;
import com.ssafy.s12p21d206.achu.domain.image.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class FileConverter {

  private FileConverter() {}

  public static File convert(MultipartFile multipartFile) {
    try (InputStream inputStream = multipartFile.getInputStream()) {
      byte[] content = inputStream.readAllBytes();
      return new File(
          multipartFile.getOriginalFilename(),
          multipartFile.getContentType(),
          content,
          multipartFile.getSize());
    } catch (IOException e) {
      throw new CoreApiException(CoreApiErrorType.INVALID_FILE);
    }
  }
}
