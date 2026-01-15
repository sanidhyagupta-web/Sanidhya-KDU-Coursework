package com.SpringAOP.SmartLockSecuritySystem.Controller;

import com.SpringAOP.SmartLockSecuritySystem.Service.SmartLockService;
import com.SpringAOP.SmartLockSecuritySystem.Utilities.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.BASE_URL)
public class SmartLockController {

    @Autowired
    private SmartLockService smartLockService;

    @GetMapping(AppConstants.unlockDoor_URL)
    public String unlockDoor(@RequestParam String user) {
        return smartLockService.unlock(user);
    }

    @GetMapping(AppConstants.checkBattery)
    public String checkBattery() throws InterruptedException {
        return smartLockService.checkBattery();
    }
}

