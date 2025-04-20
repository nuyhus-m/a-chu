package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.api.validation.MemoryContent;
import com.ssafy.s12p21d206.achu.api.validation.MemoryTitle;
import com.ssafy.s12p21d206.achu.domain.ModifyMemory;
import jakarta.validation.constraints.NotNull;

public record ModifyMemoryRequest(
    @NotNull @MemoryTitle String title, @NotNull @MemoryContent String content) {

  public ModifyMemory toModifyMemory() {
    return new ModifyMemory(title, content);
  }
}
