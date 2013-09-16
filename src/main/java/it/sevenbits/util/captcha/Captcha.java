package it.sevenbits.util.captcha;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 16.09.13
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class Captcha {
    private final String captchaString;
    private final byte[] captchaJpegBytes;

    private Captcha(CaptchaBuilder captchaBuilder) {
        this.captchaString = captchaBuilder.getCaptchaText();
        this.captchaJpegBytes = captchaBuilder.getJpegBytes();
    }

    public static Captcha newCaptcha(int width, int height) {
        CaptchaBuilder captchaBuilder = new CaptchaBuilder(width, height);
        return new Captcha(captchaBuilder.build());
    }

    public byte[] getCaptchaData() {
        return this.captchaJpegBytes;
    }

    public String getCaptchaString() {
        return this.captchaString;
    }

    // getters omitted
}
