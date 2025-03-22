package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import com.ssafy.s12p21d206.achu.domain.support.Sex;
import java.time.LocalDate;

public class Baby {

  private final Long id;

  private final Long userId;

  private String nickname;

  private Sex gender;

  private String imageUrl;

  private LocalDate birth;

  private final DefaultDateTime defaultDateTime;

  public Baby(
      Long id,
      Long userId,
      String nickname,
      Sex gender,
      String imageUrl,
      LocalDate birth,
      DefaultDateTime defaultDateTime) {
    this.id = id;
    this.userId = userId;
    this.nickname = nickname;
    this.gender = gender;
    this.imageUrl = imageUrl;
    this.birth = birth;
    this.defaultDateTime = defaultDateTime;
  }

  public Long getId() {
    return id;
  }

  public String getNickname() {
    return nickname;
  }

  public Sex getGender() {
    return gender;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public LocalDate getBirth() {
    return birth;
  }
}
