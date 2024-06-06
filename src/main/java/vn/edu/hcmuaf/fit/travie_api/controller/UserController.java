package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie_api.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile() throws BaseException {
        UserProfileDTO userProfile = userService.getProfile();
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileRequest userProfileRequest) throws BaseException {
        UserProfileDTO userProfile = userService.updateProfile(userProfileRequest);
        return ResponseEntity.ok(userProfile);
    }
}
