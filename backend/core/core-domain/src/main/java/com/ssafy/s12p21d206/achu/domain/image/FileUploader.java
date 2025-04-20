package com.ssafy.s12p21d206.achu.domain.image;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FileUploader {

  private final FileClient fileClient;

  public FileUploader(FileClient fileClient) {
    this.fileClient = fileClient;
  }

  String uploadFile(File file) {
    return fileClient.uploadFile(file);
  }

  List<String> uploadFiles(List<File> files) {
    return fileClient.uploadFiles(files);
  }
}
