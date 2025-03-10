package com.ssafy.s12p21d206.achu.test.api;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public class RestDocsUtils {

  private static final String SCHEME = "http";
  private static final String HOST = "dev.dukcode.org";

  public static OperationRequestPreprocessor requestPreprocessor() {
    return Preprocessors.preprocessRequest(
        Preprocessors.modifyUris().scheme(SCHEME).host(HOST).removePort(),
        Preprocessors.prettyPrint());
  }

  public static OperationResponsePreprocessor responsePreprocessor() {
    return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
  }
}
