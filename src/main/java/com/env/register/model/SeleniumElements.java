package com.env.register.model;

public class SeleniumElements {
    public static String EVN_REGISTER_URL  = "https://www.cskh.evnspc.vn/TaiKhoan/DangNhap";
    public static String EVN_REGISTER_TAB_ELEMENT  = "#tab-tab3";
    public static String EVN_CAPTCHA_ELEMENT  = "#frmDangKyTaiKhoan #CaptchaImage";
    public static String EVN_USER_NAME_ELEMENT  = "#frmDangKyTaiKhoan #idTenDangNhap";
    public static String EVN_USER_ID_ELEMENT  = "#frmDangKyTaiKhoan #idMaKhachHang";
    public static String EVN_PHONE_ELEMENT  = "#frmDangKyTaiKhoan #idSoDienThoai";
    public static String EVN_EMAIL_ELEMENT  = "#frmDangKyTaiKhoan #idEmail";
    public static String EVN_PASSWORD_ELEMENT  = "#frmDangKyTaiKhoan #idMatKhau";
    public static String EVN_RE_PASSWORD_ELEMENT  = "#frmDangKyTaiKhoan #idNhapLaiMatKhau";
    public static String EVN_CONFIRM_MAIL_ELEMENT  = "#frmDangKyTaiKhoan #confirm_mail";
    public static String EVN_CAPTCHA_INPUT_ELEMENT  = "#frmDangKyTaiKhoan #CaptchaInputText";
    public static String EVN_BTN_REGISTER_ELEMENT  = "#frmDangKyTaiKhoan input[name='register-btn']";
    public static String EVN_LAST_STEP_ELEMENT  = "form[action='#'] .modal-content-txt";
    public static long EVN_WAIT = 10L;
}
