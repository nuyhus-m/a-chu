package com.ssafy.s12p21d206.achu.client.sms;

import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationCodeSmsClient;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NurigoSmsClient implements VerificationCodeSmsClient {

  @Value("${achu.sms.sender}")
  private String smsSender;

  private final DefaultMessageService messageService;

  public NurigoSmsClient(DefaultMessageService messageService) {
    this.messageService = messageService;
  }

  @Override
  public void send(String text, Phone phone) {
    Message message = new Message();
    message.setFrom(smsSender);
    message.setTo(phone.number());

    message.setText(text);
    messageService.sendOne(new SingleMessageSendingRequest(message));
  }
}
