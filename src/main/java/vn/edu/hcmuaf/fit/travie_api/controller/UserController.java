package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileUpdate;
import vn.edu.hcmuaf.fit.travie_api.service.user.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> getProfile() throws BaseException {
        UserProfileDTO data = userService.getProfile();
        return ResponseEntity.ok(HttpResponse.success(data, "Lấy thông tin thành công!"));
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpResponse> updateProfile(@RequestBody UserProfileUpdate userProfileUpdate) throws BaseException {
        UserProfileDTO data = userService.updateProfile(userProfileUpdate);
        return ResponseEntity.ok(HttpResponse.success(data, "Cập nhật thông tin thành công!"));
    }
}
