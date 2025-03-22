package com.ssafy.s12p21d206.achu.domain;

import java.util.List;

public interface BabyRepository {

  Baby save(User user, NewBaby newBaby);

  List<Baby> findByUser(User user);
}
