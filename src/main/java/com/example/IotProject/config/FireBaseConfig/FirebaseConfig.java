package com.example.IotProject.config.FireBaseConfig;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            // Đọc file JSON từ resources
            InputStream serviceAccount = getClass().getResourceAsStream("/serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://botanica-51777.firebaseio.com") // thay <your-project-id> bằng ID của bạn
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Connect Firebase success");
        } catch (Exception ex) {
            // Log lỗi nếu có sự cố trong quá trình khởi tạo Firebase
            ex.printStackTrace();
        }
    }
}