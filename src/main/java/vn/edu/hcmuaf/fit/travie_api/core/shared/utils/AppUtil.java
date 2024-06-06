package vn.edu.hcmuaf.fit.travie_api.core.shared.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AppUtil {
    @Value("${firebase.storage.bucket}")
    private static String firebaseStorageBucket;

    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static String getImageUrl(String fileName) {
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, firebaseStorageBucket, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
