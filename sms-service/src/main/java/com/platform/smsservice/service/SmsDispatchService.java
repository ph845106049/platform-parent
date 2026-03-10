package com.platform.smsservice.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.platform.smsservice.model.SmsSendContext;
import com.platform.smsservice.model.request.SmsNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类说明：SmsDispatchService 负责解析手机号国家信息并路由到对应短信模板实现。
 */
@Service
@RequiredArgsConstructor
public class SmsDispatchService {

    private final List<CountrySmsSender> senders;

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Value("${platform.sms.default-region:CN}")
    private String defaultRegion;

    public void dispatch(SmsNotifyRequest request) {
        SmsSendContext context = buildContext(request);
        CountrySmsSender sender = senders.stream()
                .filter(s -> s.supports(context.getCountryCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("未找到可用短信发送器, countryCode=" + context.getCountryCode()));
        sender.send(context);
    }

    private SmsSendContext buildContext(SmsNotifyRequest request) {
        try {
            Phonenumber.PhoneNumber phone = phoneNumberUtil.parse(request.getTargetMobile(), defaultRegion);
            if (!phoneNumberUtil.isValidNumber(phone)) {
                throw new IllegalArgumentException("手机号格式不合法: " + request.getTargetMobile());
            }
            int countryCode = phone.getCountryCode();
            String e164 = phoneNumberUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.E164);
            String regionCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCode);

            return SmsSendContext.builder()
                    .targetUserId(request.getTargetUserId())
                    .targetMobile(request.getTargetMobile())
                    .e164Mobile(e164)
                    .countryCode(countryCode)
                    .regionCode(regionCode)
                    .content(request.getContent())
                    .build();
        } catch (NumberParseException ex) {
            throw new IllegalArgumentException("手机号解析失败: " + request.getTargetMobile(), ex);
        }
    }
}
