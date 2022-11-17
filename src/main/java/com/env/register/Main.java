package com.env.register;

//import com.env.register.ui.MainFrame;

import com.env.register.controller.EVNRegisterController;
import com.env.register.model.SeleniumElements;
import com.env.register.ui.MainFrame;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EVNRegisterController evnRegisterController = new EVNRegisterController(mainFrame);
        evnRegisterController.init();

        java.awt.EventQueue.invokeLater(() -> mainFrame.setVisible(true));
//        System.out.println("Chúc mừng abnc kjfhdjksf".contains("Chúc mừng"));
//        WebDriverManager.chromedriver().setup();
//        WebDriver webDriver = new ChromeDriver();
//        webDriver.get("https://www.cskh.evnspc.vn/TaiKhoan/DangNhap");
//        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3L);
//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tab-tab3")));
//        webDriver.findElement(By.cssSelector("#tab-tab3")).click();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#frmDangKyTaiKhoan #CaptchaImage")));
//
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_USER_NAME_ELEMENT)).sendKeys("PB14080064807");
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_USER_ID_ELEMENT)).sendKeys("PB14080064807");
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_PHONE_ELEMENT)).sendKeys("0911330333");
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_EMAIL_ELEMENT)).sendKeys("pb14080064807@gmail.com");
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_PASSWORD_ELEMENT)).sendKeys("123456");
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_RE_PASSWORD_ELEMENT)).sendKeys("123456");
//
//        JavascriptExecutor js = (JavascriptExecutor) webDriver;
//        js.executeScript("arguments[0].checked = true;",  webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CONFIRM_MAIL_ELEMENT)));
//        WebElement webElement1 = webDriver.findElement(By.cssSelector("#frmDangKyTaiKhoan #CaptchaImage"));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement1);
//        WebElement webElement = webDriver.findElement(By.cssSelector("#frmDangKyTaiKhoan #CaptchaImage"));
//        // Get entire page screenshot
//        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(webDriver);
//        BufferedImage fullImg = screenshot.getImage();
//
//        System.out.println(fullImg.getHeight());
//        System.out.println(fullImg.getWidth());
//        // Get the location of element on the page
//        Point point = webElement.getLocation();
//
//        // Get width and height of the element
//        int eleWidth = webElement.getSize().getWidth();
//        int eleHeight = webElement.getSize().getHeight();
//        System.out.println(point.getX() + ":" + point.getY());
//        System.out.println(eleWidth + ":" + eleHeight);
//        // Crop the entire page screenshot to get only element screenshot
//        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
//                eleWidth, eleHeight);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ImageIO.write(eleScreenshot, "png", out);
//        // Copy the element screenshot to disk
//        byte[] encoded = Base64.encodeBase64(out.toByteArray());
//        String base64 =  new String(encoded, StandardCharsets.US_ASCII);
//        System.out.println(base64);
//        Normal captcha = new Normal();
//        captcha.setBase64(base64);
//        captcha.setMinLen(4);
//        captcha.setMaxLen(6);
//        captcha.setCalc(false);
//        captcha.setLang("en");
//        TwoCaptcha solver = new TwoCaptcha("755e69ea2d05c0cae0667ab3d8f64bbc");
//        solver.setDefaultTimeout(120);
//        solver.setRecaptchaTimeout(600);
//        solver.setPollingInterval(5);
//        String resultCapt = "";
//        try {
//            solver.solve(captcha);
//            resultCapt = "captcha.getCode();";
//            System.out.println("Captcha solved: " + captcha.getCode());
//        } catch (Exception e) {
//            System.out.println("Error occurred: " + e.getMessage());
//        }
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_CAPTCHA_INPUT_ELEMENT)).sendKeys(resultCapt);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        webDriver.findElement(By.cssSelector(SeleniumElements.EVN_BTN_REGISTER_ELEMENT)).click();

    }
}
