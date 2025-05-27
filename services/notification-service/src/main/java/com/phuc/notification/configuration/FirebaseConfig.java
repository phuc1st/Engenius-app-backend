package com.phuc.notification.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initialize() {
        try {
            Resource resource = new FileSystemResource("F:\\Documents\\LearningMaterials\\Year3_Sem2\\Flutter\\myfcmproject-1d73f-firebase-adminsdk-fbsvc-7bb9435203.json");
            java.io.FileInputStream serviceAccount = new FileInputStream(resource.getFile());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 