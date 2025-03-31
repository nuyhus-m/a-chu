package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  public UserDetail findSellerInfo(User user) {
    /*
     *
     * 주어진 User에 대해 아래 정보를 조회하여 Seller 객체로 반환해야 합니다:
     *
     * 1. 사용자의 id, nickname, imgUrl 정보를 조회
     *    - user.getId()
     *    - user.getNickname()
     *    - user.getImgUrl()
     *
     * 결과는 Seller 레코드로 생성하여 반환해야 합니다:
     *   new Seller(id, nickname, imgUrl)
     */
    return new UserDetail(user.id(), "임시 판매자", "profile-img-placeholder.jpg");
  }
}
