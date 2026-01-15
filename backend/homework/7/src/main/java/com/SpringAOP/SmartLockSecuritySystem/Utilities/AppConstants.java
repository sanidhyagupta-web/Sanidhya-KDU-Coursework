package com.SpringAOP.SmartLockSecuritySystem.Utilities;

public class AppConstants {
    public final static String BASE_URL = "/smartlock";
    public final static String unlockDoor_URL = "/unlock";
    public final static String checkBattery = "/battery";
    public final static String secureUnlockPath = "execution(* com.SpringAOP.SmartLockSecuritySystem.Service.SmartLockService.unlock(..))";
    public final static String hardwareAlert = "HARDWARE ALERT : User cannot be empty";
    public final static String hardwareFailureException = "Hardware Failure , user cannot be empty";
    public final static String unknown = "Unknown";
    public final static String securityAlert = "SECURITY ALERT: Unauthorized access blocked!";
    public final static String unauthorizedAccessException = "Unauthorized user attempted access";
    public final static String accessAttempt = "ACCESS ATTEMPT: User is approaching the door";
    public final static String success = "SUCCESS: User has entered the building";
    public final static String measureBatteryCheckTime = "execution(* com.SpringAOP.SmartLockSecuritySystem.Service.SmartLockService.checkBattery(..))";
    public final static String triggerAlarm = "execution(* com.SpringAOP.SmartLockSecuritySystem.Service.SmartLockService.*(..))";
    public final static String unlock = "Unlock request processed";
    public final static String batteryCheck = "Battery check completed";
}
