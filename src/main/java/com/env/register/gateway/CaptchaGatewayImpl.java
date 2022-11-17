package com.env.register.gateway;

import com.env.register.exceptions.ResolveCaptchaException;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import lombok.AllArgsConstructor;

public class CaptchaGatewayImpl implements CaptchaGateway{
    private String apiKey;

    public CaptchaGatewayImpl(String apiKey) {
       this.apiKey = apiKey;
    }

    @Override
    public String resolveCaptcha(String base64CaptchaImg) throws ResolveCaptchaException {
        Normal captcha = new Normal();
        captcha.setBase64(base64CaptchaImg);
        captcha.setMinLen(4);
        captcha.setMaxLen(6);
        captcha.setCalc(false);
        captcha.setLang("en");
        TwoCaptcha solver = new TwoCaptcha(apiKey);
        solver.setDefaultTimeout(120);
        solver.setRecaptchaTimeout(600);
        solver.setPollingInterval(5);
        try {
            solver.solve(captcha);
            return captcha.getCode();
        } catch (Exception e) {
           throw new ResolveCaptchaException(String.format("Co loi xay ra khi giai captcha, error: %s", e.getMessage()));
        }
    }
}
