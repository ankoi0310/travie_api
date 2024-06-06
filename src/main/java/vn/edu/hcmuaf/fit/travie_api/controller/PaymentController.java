package vn.edu.hcmuaf.fit.travie_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @RequestMapping
    public ResponseEntity<Void> pay(@RequestParam("provider") String provider) throws BaseException {
        switch (provider) {
            case "momo":
                return null;
        }

        return null;
    }
}
