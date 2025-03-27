package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Memory;
import com.ssafy.s12p21d206.achu.storage.db.core.converter.ImgUrlListJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

@Table(name = "memory")
@Entity
public class MemoryEntity extends BaseEntity {
  private String title;

  private String content;

  @Convert(converter = ImgUrlListJsonConverter.class)
  @Column(name = "imgUrls", columnDefinition = "json")
  private List<String> imgUrls;

  private Long babyId;

  protected MemoryEntity() {}

  public MemoryEntity(String title, String content, List<String> imgUrls, Long babyId) {
    this.title = title;
    this.content = content;
    this.imgUrls = imgUrls;
    this.babyId = babyId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public List<String> getImgUrls() {
    return imgUrls;
  }

  public Long getBabyId() {
    return babyId;
  }

  public Memory toMemory() {
    return new Memory(
        getId(),
        getTitle(),
        getContent(),
        getImgUrls(),
        getBabyId(),
        getCreatedAt(),
        getUpdatedAt());
  }
}
