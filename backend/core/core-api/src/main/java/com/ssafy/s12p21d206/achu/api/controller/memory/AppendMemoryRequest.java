package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.domain.NewMemory;
import java.util.List;

public record AppendMemoryRequest(String title, String content) {
  public NewMemory toNewMemory(List<String> imgUrls) {
    return new NewMemory(this.title(), this.content(), imgUrls);
  }
}
