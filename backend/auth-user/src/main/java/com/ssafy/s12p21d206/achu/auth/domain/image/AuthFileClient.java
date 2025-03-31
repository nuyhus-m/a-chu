package com.ssafy.s12p21d206.achu.auth.domain.image;

import java.util.List;

public interface AuthFileClient {

  String uploadFile(AuthFile file);

  List<String> uploadFiles(List<AuthFile> files);
}
