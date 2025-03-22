package com.ssafy.s12p21d206.achu.client.sms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ssafy.s12p21d206.achu.auth.domain.Phone;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class NurigoSmsClientTest {

  private NurigoSmsClient sut;
  private DefaultMessageService messageService;

  @BeforeEach
  void setUp() {
    messageService = mock(DefaultMessageService.class);
    sut = new NurigoSmsClient(messageService);
  }

  @Test
  void 핸드폰_번호로_메세지를_전송_할_수_있다() {
    // given
    String text = "example message";
    Phone phone = new Phone("01012341234");

    // when
    sut.send(text, phone);

    // then
    ArgumentCaptor<SingleMessageSendingRequest> captor =
        ArgumentCaptor.forClass(SingleMessageSendingRequest.class);
    verify(messageService, times(1)).sendOne(captor.capture());

    SingleMessageSendingRequest capturedRequest = captor.getValue();
    assertThat(capturedRequest.getMessage().getText()).isEqualTo(text);
    assertThat(capturedRequest.getMessage().getTo()).isEqualTo(phone.number());
  }
}
