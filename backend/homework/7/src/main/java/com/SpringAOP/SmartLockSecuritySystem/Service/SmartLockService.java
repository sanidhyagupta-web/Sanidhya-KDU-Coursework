package com.SpringAOP.SmartLockSecuritySystem.Service;

import com.SpringAOP.SmartLockSecuritySystem.Utilities.AppConstants;
import org.springframework.stereotype.Service;

@Service
public class SmartLockService {

    public String unlock(String user) {
        return AppConstants.unlock;
    }

    public String checkBattery() throws InterruptedException {
        Thread.sleep(1000);
        return AppConstants.batteryCheck;
    }
}
