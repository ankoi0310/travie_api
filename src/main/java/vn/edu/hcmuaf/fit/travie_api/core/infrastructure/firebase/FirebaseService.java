package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {
    @Value("${firebase.storage.bucket}")
    private String firebaseStorageBucket;

    @PostConstruct
    public void init() throws IOException {
        // Check if the Firebase app has already been initialized
//        if (!FirebaseApp.getApps().isEmpty()) {
//            return;
//        }

        FileInputStream serviceAccount =
                new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                                                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                                 .setStorageBucket(firebaseStorageBucket)
                                                 .build();

        FirebaseApp.initializeApp(options);
    }
}
