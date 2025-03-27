package com.ssafy.s12p21d206.achu.domain.error;

public enum CoreErrorType {
  DATA_NOT_FOUND(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1000, "데이터를 찾을 수 없습니다.", CoreErrorLevel.INFO),
  CANNOT_ACCESS_BABY(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1001, "해당 아기에 접근 권한이 없습니다.", CoreErrorLevel.INFO),
  CANNOT_REGISTER_MORE_BABIES(
      CoreErrorKind.CLIENT_ERROR,
      CoreErrorCode.E1002,
      "최대 15명의 아기까지만 등록할 수 있습니다.",
      CoreErrorLevel.WARN),

  CANNOT_ACCESS_GOODS(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1003, "해당 물건에 접근 권한이 없습니다.", CoreErrorLevel.INFO),
  GOODS_ALREADY_SOLD(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1004, "이미 판매 완료된 상품엔 상품입니다.", CoreErrorLevel.INFO),

  SELLER_CANNOT_BE_BUYER(
      CoreErrorKind.CLIENT_ERROR,
      CoreErrorCode.E1005,
      "판매자와 구매자는 동일할 수 없습니다.",
      CoreErrorLevel.INFO),
  INVALID_TRADE_TYPE(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1006, "지원하지 않는 거래 타입입니다.", CoreErrorLevel.INFO),
  INVALID_PAGING_OFFSET(
      CoreErrorKind.CLIENT_ERROR,
      CoreErrorCode.E1007,
      "offset 값은 0 이상이어야 합니다.",
      CoreErrorLevel.INFO),
  INVALID_PAGING_LIMIT(
      CoreErrorKind.CLIENT_ERROR,
      CoreErrorCode.E1008,
      "limit 값은 1 이상이어야 합니다.",
      CoreErrorLevel.INFO);
  private final CoreErrorKind kind;
  private final CoreErrorCode code;
  private final String message;
  private final CoreErrorLevel level;

  CoreErrorType(CoreErrorKind kind, CoreErrorCode code, String message, CoreErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public CoreErrorKind getKind() {
    return kind;
  }

  public CoreErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public CoreErrorLevel getLevel() {
    return level;
  }
}
