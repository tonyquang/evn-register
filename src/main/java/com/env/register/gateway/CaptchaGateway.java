package com.env.register.gateway;

import com.env.register.exceptions.ResolveCaptchaException;

public interface CaptchaGateway {
    String resolveCaptcha(String base64CaptchaImg) throws ResolveCaptchaException;
}
