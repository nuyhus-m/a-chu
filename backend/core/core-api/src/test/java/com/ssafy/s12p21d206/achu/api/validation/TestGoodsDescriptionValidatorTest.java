package com.ssafy.s12p21d206.achu.api.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGoodsDescriptionValidatorTest {

  private GoodsDescriptionValidator sut;

  @BeforeEach
  void setup() {
    sut = new GoodsDescriptionValidator();
  }

  @Test
  void 앞_공백이_존재하면_검증에_실패한다() {
    String value = " aa";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(valid).isFalse();
  }

  @Test
  void 앞_개행이_존재하면_검증에_실패한다() {
    String value = "\naa";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(valid).isFalse();
  }

  @Test
  void 뒤_공백이_존재하면_검증에_실패한다() {
    String value = "aa ";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(valid).isFalse();
  }

  @Test
  void 뒤_개행이_존재하면_검증에_실패한다() {
    String value = "aa\n";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(valid).isFalse();
  }

  @Test
  void 이모지가_존재해도_검증에_성공한다() {
    String value = "😗";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(value.length()).isEqualTo(2);
    Assertions.assertThat(valid).isTrue();
  }

  @Test
  void 복합이모지가_존재해도_검증에_성공한다() {
    String value = "🤷‍♀️👯‍♀️👩‍❤️‍👨";

    boolean valid = sut.isValid(value, null);
    Assertions.assertThat(valid).isTrue();
  }
}
