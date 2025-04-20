package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.image.AuthFile;
import com.ssafy.s12p21d206.achu.auth.domain.image.AuthImageCompressionOption;
import com.ssafy.s12p21d206.achu.auth.domain.image.AuthImageService;
import org.springframework.stereotype.Service;

@Service
public class AuthUserImageFacade {

  private final AuthUserService authUserService;
  private final AuthImageService authImageService;

  public AuthUserImageFacade(AuthUserService authUserService, AuthImageService authImageService) {
    this.authUserService = authUserService;
    this.authImageService = authImageService;
  }

  public AuthUser modifyProfileImage(Long id, AuthFile profileImageFile) {
    String profileImageUrl =
        authImageService.uploadImage(profileImageFile, AuthImageCompressionOption.PROFILE_IMAGE);
    return authUserService.modifyProfileImage(id, profileImageUrl);
  }
}
