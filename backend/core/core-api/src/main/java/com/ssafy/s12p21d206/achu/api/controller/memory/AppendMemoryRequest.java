package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.api.validation.MemoryContent;
import com.ssafy.s12p21d206.achu.api.validation.MemoryTitle;
import com.ssafy.s12p21d206.achu.domain.NewMemory;
import jakarta.validation.constraints.NotNull;

public record AppendMemoryRequest(
    @NotNull @MemoryTitle String title, @NotNull @MemoryContent String content) {
  public NewMemory toNewMemory() {
    return new NewMemory(this.title(), this.content());
  }
}
