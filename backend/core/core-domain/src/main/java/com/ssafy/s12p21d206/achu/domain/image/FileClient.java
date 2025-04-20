package com.ssafy.s12p21d206.achu.domain.image;

import java.util.List;

public interface FileClient {

  String uploadFile(File file);

  List<String> uploadFiles(List<File> files);
}
