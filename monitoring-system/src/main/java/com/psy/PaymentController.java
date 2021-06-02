package com.psy;

import com.psy.dao.IndicationDao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")

public class PaymentController {

    @GetMapping("/")
    public ResponseEntity main() {

        return ResponseEntity.ok("bingo");
    }

    public PaymentController() {
        System.out.println("Connect");
    }

    @RequestMapping(
            value = "/list",
            method = {
                    RequestMethod.GET
            }
    )
    public ResponseEntity list() {

        return ResponseEntity.ok(IndicationDao.getInstance().list());
    }
}