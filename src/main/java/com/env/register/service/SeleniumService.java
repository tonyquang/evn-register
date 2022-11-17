package com.env.register.service;

import com.env.register.exceptions.ResolveCaptchaException;
import com.env.register.gateway.CaptchaGateway;
import com.env.register.log.MyLogger;
import com.env.register.model.Account;
import com.env.register.model.LogLevel;
import com.env.register.model.SeleniumElements;
import com.env.register.ui.MainFrame;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class SeleniumService implements Runnable{
    private final MainFrame mainFrame;
    private final MyLogger myLogger;
    private final CaptchaGateway captchaGateway;
    private final Account account;

    private boolean isStop;
    private WebDriver webDriver;

    @Override
    public void run() {
        webDriver = new ChromeDriver();
        try {
            webDriver.get(SeleniumElements.EVN_REGISTER_URL);
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, SeleniumElements.EVN_WAIT);
            JavascriptExecutor js = (JavascriptExecutor) webDriver;

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SeleniumElements.EVN_REGISTER_TAB_ELEMENT)));
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_REGISTER_TAB_ELEMENT)).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SeleniumElements.EVN_CAPTCHA_ELEMENT)));

            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_USER_NAME_ELEMENT)).sendKeys(this.account.getUserName());
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_USER_ID_ELEMENT)).sendKeys(this.account.getUserName());
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_PHONE_ELEMENT)).sendKeys(this.account.getPhoneNumber());
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_EMAIL_ELEMENT)).sendKeys(this.account.generateEmail());
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_PASSWORD_ELEMENT)).sendKeys(this.account.getPassword());
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_RE_PASSWORD_ELEMENT)).sendKeys(this.account.getPassword());
            js.executeScript("arguments[0].checked = true;",  webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CONFIRM_MAIL_ELEMENT)));
            WebElement webElement1 = webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CAPTCHA_ELEMENT));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement1);
            WebElement webElement = webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CAPTCHA_ELEMENT));
            // Get entire page screenshot
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(webDriver);
            BufferedImage fullImg = screenshot.getImage();
            // Get the location of element on the page
            Point point = webElement.getLocation();

            // Get width and height of the element
            int eleWidth = webElement.getSize().getWidth();
            int eleHeight = webElement.getSize().getHeight();

            // Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                    eleWidth, eleHeight);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(eleScreenshot, "png", out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Copy the element screenshot to disk
            byte[] encoded = Base64.encodeBase64(out.toByteArray());
            String base64 =  new String(encoded, StandardCharsets.US_ASCII);
            String resultCapt = "";
            try {
                resultCapt = captchaGateway.resolveCaptcha(base64);
            } catch (ResolveCaptchaException e) {
                myLogger.log(LogLevel.ERROR, this.account.getUserName(), "Resolve Captcha", e.getMessage());
                return;
            }
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CAPTCHA_INPUT_ELEMENT)).sendKeys(resultCapt);
            webDriver.findElement(By.cssSelector(SeleniumElements.EVN_BTN_REGISTER_ELEMENT)).click();

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SeleniumElements.EVN_LAST_STEP_ELEMENT)));
           // String rs = webElement.findElement(By.cssSelector(SeleniumElements.EVN_LAST_STEP_ELEMENT)).getText();
            if (webDriver.findElements(By.cssSelector("#login-btn")).size() > 0 ) {
                myLogger.log(LogLevel.INFO, this.account.getUserName(), "Last Step", "Done");
                this.mainFrame.addOutput(this.account);
            }else{
                this.mainFrame.addOutputAccError(this.account);
                myLogger.log(LogLevel.WARN, this.account.getUserName(), "Last Step", "Dang ky that bai");
            }
        }catch (Exception e) {
            if (!isStop) {
                myLogger.log(LogLevel.ERROR, this.account.getUserName(), "Input Account Info", e.getMessage());
                e.printStackTrace();
            }
        }finally {
            try {
                if (webDriver != null)
                    webDriver.quit();
            }catch (Exception ex){}
        }
    }

    public void stop() {
        if (webDriver == null) return;
        isStop = true;
        try {
            new Thread(() -> webDriver.quit()).start();
        }catch (Exception e){}
    }
}
