package com.example.IotProject.service.AuthService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.IotProject.dto.EmailDTO;
import com.example.IotProject.model.RuleModel;
import com.example.IotProject.response.RuleResponse.ConditionRuleResponse;
import com.example.IotProject.response.RuleResponse.RuleResponse;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String fromEmail;

    public void sendEmail(EmailDTO email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(fromEmail, "Bontanica App"));
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email");
        }
    }

    public String changeFormatDay(LocalDateTime input) {
        return input.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void sendOTPEmail(String fullName,String toEmail, String otp) {
        String subject = "Mã xác thực OTP Bontanica";
        String body = """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
            <meta charset="UTF-8">
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                }
                .email-container {
                background-color: #ffffff;
                max-width: 600px;
                margin: 40px auto;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                }
                h2 {
                color: #2c3e50;
                }
                p {
                font-size: 16px;
                color: #333333;
                line-height: 1.6;
                }
                .otp-code {
                font-size: 28px;
                font-weight: bold;
                color: #e74c3c;
                text-align: center;
                margin: 20px 0;
                }
                .footer {
                margin-top: 30px;
                font-size: 14px;
                color: #777;
                border-top: 1px solid #ddd;
                padding-top: 15px;
                }
                .footer p {
                margin: 4px 0;
                }
            </style>
            </head>
            <body>
            <div class="email-container">
                <h2>
                Kính gửi """ +" "+fullName + ","+"""
                </h2>
                <p>Bạn (hoặc ai đó) vừa yêu cầu đặt lại mật khẩu cho tài khoản Bontanica của bạn.</p>

                <p>Mã xác thực (OTP) để đặt lại mật khẩu của bạn là:</p>

                <div 
                class="otp-code"> """+ otp + """
                </div>
                <p><strong>Lưu ý:</strong> Vui lòng không chia sẻ mã này cho bất kỳ ai để đảm bảo an toàn cho tài khoản của bạn.</p>

                <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ.</p>

                <div class="footer">
                <p>Trân trọng,</p>
                <p><strong>Đội ngũ Bontanica</strong></p>
                <p>Email: bontanica.app.hcmut@gmail.com</p>
                <p>Hotline: 0766909533</p>
                </div>
            </div>
            </body>
            </html>
            """;

        EmailDTO email = new EmailDTO(toEmail, subject, body);
        sendEmail(email);
    }

    public void sendMailArletTemperature(String fullName, String toEmail, String data, ConditionRuleResponse res, RuleModel rule) {
        String subject = "Cảnh báo nhiệt độ Bontanica";
        String body = """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
            <meta charset="UTF-8">
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                }
                .email-container {
                background-color: #ffffff;
                max-width: 600px;
                margin: 40px auto;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                }
                h2 {
                color: #2c3e50;
                }
                p {
                font-size: 16px;
                color: #333333;
                line-height: 1.6;
                }
                .alert {
                background-color: #fce4e4;
                color: #c0392b;
                padding: 15px;
                border-radius: 6px;
                margin: 20px 0;
                }
                .recommendation {
                background-color: #ecf0f1;
                padding: 15px;
                border-radius: 6px;
                margin-top: 20px;
                }
                ul {
                padding-left: 20px;
                }
                .footer {
                margin-top: 30px;
                font-size: 14px;
                color: #777;
                border-top: 1px solid #ddd;
                padding-top: 15px;
                }
                .footer p {
                margin: 4px 0;
                }
            </style>
            </head>
            <body>
            <div class="email-container">
                <h2>
                Kính gửi """ +" "+fullName + ","+"""
                </h2>
                
                <p>Hệ thống <strong>Bontanica</strong> vừa ghi nhận nhiệt độ tại khu vực trồng khoai lang của bạn đã vượt quá mức an toàn:</p>
                
                <div class="alert">
                <ul>
                    <li>
                        <strong>Khu vực:</strong> """ +" "+ rule.getDevice().getZone().getName() + """
                    </li>
                    <li>
                        <strong>Nhiệt độ hiện tại:</strong> """ + " " +data+"°C" + """
                    </li>
                    <li>
                        <strong>Ngưỡng tối đa cho phép:</strong> """ + res.getMinValue()+ "°C"+  " - " + res.getMaxValue()+ "°C" + """
                    </li>
                </ul>
                </div>
                
                <p>Nhiệt độ quá cao có thể gây ảnh hưởng đến sự phát triển và năng suất của cây khoai lang, đặc biệt là trong giai đoạn sinh trưởng.</p>
                
                <div class="recommendation">
                <p><strong>Khuyến nghị:</strong></p>
                <ul>
                    <li>Kiểm tra hệ thống tưới mát hoặc che nắng nếu có</li>
                    <li>Theo dõi thêm các chỉ số độ ẩm đất và ánh sáng</li>
                    <li>Cập nhật thông tin thời tiết trong ứng dụng để chủ động phòng ngừa</li>
                </ul>
                </div>
                
                <p>Mọi biến động sẽ tiếp tục được theo dõi và thông báo đến bạn kịp thời.</p>

                <div class="footer">
                <p>Trân trọng,</p>
                <p><strong>Đội ngũ Bontanica</strong></p>
                <p>Email: bontanica.app.hcmut@gmail.com</p>
                <p>Hotline: 0766909533</p>
                </div>
            </div>
            </body>
            </html>
            """;
        EmailDTO email = new EmailDTO(toEmail, subject, body);
        sendEmail(email);
    }

    public void sendMailAlertLight(String fullName, String toEmail, String data, ConditionRuleResponse res, RuleModel rule) {
        String subject = "Cảnh báo ánh sáng Bontanica";
        String body = """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
            <meta charset="UTF-8">
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                }
                .email-container {
                background-color: #ffffff;
                max-width: 600px;
                margin: 40px auto;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                }
                h2 {
                color: #2c3e50;
                }
                p {
                font-size: 16px;
                color: #333333;
                line-height: 1.6;
                }
                .alert {
                background-color: #fff7e6;
                color: #e67e22;
                padding: 15px;
                border-radius: 6px;
                margin: 20px 0;
                }
                .recommendation {
                background-color: #ecf0f1;
                padding: 15px;
                border-radius: 6px;
                margin-top: 20px;
                }
                ul {
                padding-left: 20px;
                }
                .footer {
                margin-top: 30px;
                font-size: 14px;
                color: #777;
                border-top: 1px solid #ddd;
                padding-top: 15px;
                }
                .footer p {
                margin: 4px 0;
                }
            </style>
            </head>
            <body>
            <div class="email-container">
                <h2>Kính gửi """ + fullName + "," + """
                </h2>
                
                <p>Hệ thống <strong>Bontanica</strong> vừa ghi nhận mức ánh sáng tại khu vực trồng khoai lang của bạn đã vượt quá mức an toàn:</p>
                
                <div class="alert">
                <ul>
                    <li>
                        <strong>Khu vực:</strong> """ + rule.getDevice().getZone().getName() + """
                    </li>
                    <li>
                        <strong>Cường độ ánh sáng hiện tại:</strong> """ + data + " lux" + """
                    </li>
                    <li>
                        <strong>Ngưỡng an toàn cho phép:</strong> """ + res.getMinValue() + " lux - " + res.getMaxValue() + " lux" + """
                    </li>
                </ul>
                </div>
                
                <p>Ánh sáng không phù hợp có thể ảnh hưởng đến khả năng quang hợp và sự phát triển của cây khoai lang.</p>
                
                <div class="recommendation">
                <p><strong>Khuyến nghị:</strong></p>
                <ul>
                    <li>Kiểm tra hệ thống che nắng hoặc chiếu sáng nhân tạo (nếu có)</li>
                    <li>Theo dõi thêm các chỉ số nhiệt độ và độ ẩm</li>
                    <li>Cập nhật tình hình thời tiết và ánh sáng tại khu vực qua ứng dụng</li>
                </ul>
                </div>
                
                <p>Mọi biến động sẽ tiếp tục được theo dõi và thông báo đến bạn kịp thời.</p>
    
                <div class="footer">
                <p>Trân trọng,</p>
                <p><strong>Đội ngũ Bontanica</strong></p>
                <p>Email: bontanica.app.hcmut@gmail.com</p>
                <p>Hotline: 0766909533</p>
                </div>
            </div>
            </body>
            </html>
            """;
        EmailDTO email = new EmailDTO(toEmail, subject, body);
        sendEmail(email);
    }
    
    public void sendMailAlertSoilMoisture(String fullName, String toEmail, String data, ConditionRuleResponse res, RuleModel rule) {
        String subject = "Cảnh báo độ ẩm đất Bontanica";
        String body = """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
            <meta charset="UTF-8">
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                }
                .email-container {
                background-color: #ffffff;
                max-width: 600px;
                margin: 40px auto;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                }
                h2 {
                color: #2c3e50;
                }
                p {
                font-size: 16px;
                color: #333333;
                line-height: 1.6;
                }
                .alert {
                background-color: #e8f6ef;
                color: #1abc9c;
                padding: 15px;
                border-radius: 6px;
                margin: 20px 0;
                }
                .recommendation {
                background-color: #ecf0f1;
                padding: 15px;
                border-radius: 6px;
                margin-top: 20px;
                }
                ul {
                padding-left: 20px;
                }
                .footer {
                margin-top: 30px;
                font-size: 14px;
                color: #777;
                border-top: 1px solid #ddd;
                padding-top: 15px;
                }
                .footer p {
                margin: 4px 0;
                }
            </style>
            </head>
            <body>
            <div class="email-container">
                <h2>Kính gửi """ + fullName + "," + """
                </h2>
                
                <p>Hệ thống <strong>Bontanica</strong> vừa ghi nhận độ ẩm đất tại khu vực trồng khoai lang của bạn đã vượt quá mức an toàn:</p>
                
                <div class="alert">
                <ul>
                    <li>
                        <strong>Khu vực:</strong> """ + rule.getDevice().getZone().getName() + """
                    </li>
                    <li>
                        <strong>Độ ẩm đất hiện tại:</strong> """ + data + "%"+ """
                    </li>
                    <li>
                        <strong>Ngưỡng an toàn cho phép:</strong> """ + res.getMinValue() + "% - " + res.getMaxValue() + "%" + """
                    </li>
                </ul>
                </div>
                
                <p>Độ ẩm đất không phù hợp có thể làm giảm khả năng hấp thụ chất dinh dưỡng, ảnh hưởng đến sự phát triển và năng suất của cây khoai lang.</p>
                
                <div class="recommendation">
                <p><strong>Khuyến nghị:</strong></p>
                <ul>
                    <li>Kiểm tra hệ thống tưới tiêu tự động hoặc bổ sung nước nếu cần</li>
                    <li>Tránh tưới quá nhiều gây ngập úng</li>
                    <li>Theo dõi thêm các chỉ số thời tiết và ánh sáng trong ứng dụng</li>
                </ul>
                </div>
                
                <p>Mọi biến động sẽ tiếp tục được theo dõi và thông báo đến bạn kịp thời.</p>
    
                <div class="footer">
                <p>Trân trọng,</p>
                <p><strong>Đội ngũ Bontanica</strong></p>
                <p>Email: bontanica.app.hcmut@gmail.com</p>
                <p>Hotline: 0766909533</p>
                </div>
            </div>
            </body>
            </html>
            """;
        EmailDTO email = new EmailDTO(toEmail, subject, body);
        sendEmail(email);
    }
    
    public void sendMailAlertHumidity(String fullName, String toEmail, String data, ConditionRuleResponse res, RuleModel rule) {
        String subject = "Cảnh báo độ ẩm không khí Bontanica";
        String body = """
            <!DOCTYPE html>
            <html lang="vi">
            <head>
            <meta charset="UTF-8">
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                }
                .email-container {
                background-color: #ffffff;
                max-width: 600px;
                margin: 40px auto;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                }
                h2 {
                color: #2c3e50;
                }
                p {
                font-size: 16px;
                color: #333333;
                line-height: 1.6;
                }
                .alert {
                background-color: #e6f0fa;
                color: #2980b9;
                padding: 15px;
                border-radius: 6px;
                margin: 20px 0;
                }
                .recommendation {
                background-color: #ecf0f1;
                padding: 15px;
                border-radius: 6px;
                margin-top: 20px;
                }
                ul {
                padding-left: 20px;
                }
                .footer {
                margin-top: 30px;
                font-size: 14px;
                color: #777;
                border-top: 1px solid #ddd;
                padding-top: 15px;
                }
                .footer p {
                margin: 4px 0;
                }
            </style>
            </head>
            <body>
            <div class="email-container">
                <h2>Kính gửi """ + fullName + "," + """
                </h2>
                
                <p>Hệ thống <strong>Bontanica</strong> vừa ghi nhận độ ẩm không khí tại khu vực trồng khoai lang của bạn đã vượt quá mức an toàn:</p>
                
                <div class="alert">
                <ul>
                    <li>
                        <strong>Khu vực:</strong> """ + rule.getDevice().getZone().getName() + """
                    </li>
                    <li>
                        <strong>Độ ẩm không khí hiện tại:</strong> """ + data + "%" + """
                    </li>
                    <li>
                        <strong>Ngưỡng an toàn cho phép:</strong> """ + res.getMinValue() + "% - " + res.getMaxValue() + "%" + """
                    </li>
                </ul>
                </div>
                
                <p>Độ ẩm không khí không ổn định có thể ảnh hưởng đến sự thoát hơi nước và quá trình hô hấp của cây trồng, đặc biệt trong giai đoạn sinh trưởng mạnh.</p>
                
                <div class="recommendation">
                <p><strong>Khuyến nghị:</strong></p>
                <ul>
                    <li>Kiểm tra hệ thống thông gió hoặc tạo ẩm nếu có</li>
                    <li>Theo dõi thêm các chỉ số nhiệt độ và ánh sáng</li>
                    <li>Sử dụng lưới che hoặc máy tạo ẩm tùy điều kiện thực tế</li>
                </ul>
                </div>
                
                <p>Mọi biến động sẽ tiếp tục được theo dõi và thông báo đến bạn kịp thời.</p>
    
                <div class="footer">
                <p>Trân trọng,</p>
                <p><strong>Đội ngũ Bontanica</strong></p>
                <p>Email: bontanica.app.hcmut@gmail.com</p>
                <p>Hotline: 0766909533</p>
                </div>
            </div>
            </body>
            </html>
            """;
        EmailDTO email = new EmailDTO(toEmail, subject, body);
        sendEmail(email);
    }
    
    
}