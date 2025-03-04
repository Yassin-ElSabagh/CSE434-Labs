package com.assessments.lab1.aspects;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeviceStatusAspect {

    public DeviceStatusAspect() {
        System.out.println("[SMART HOME] Monitoring system initialized.");
    }

    // Log before a GET request (Fetching all devices)
    @Before("execution(* com.assessments.lab1.controllers.SmartHomeController.getAllDevices(..))")
    public void logBeforeGet() {
        System.out.println("[SMART HOME] Fetching the list of all smart home devices.");
    }

    // Log before a POST request (Adding a new device)
    @Before("execution(* com.assessments.lab1.controllers.SmartHomeController.addDevice(..))")
    public void logBeforePost() {
        System.out.println("[SMART HOME] Attempting to add a new smart device.");
    }

    // Log before a PUT request (Updating device status)
    @Before("execution(* com.assessments.lab1.controllers.SmartHomeController.updateDeviceStatus(..)) && args(deviceId, status)")
    public void logBeforePut(int deviceId, String status) {
        System.out.println("[SMART HOME] Changing status of device ID " + deviceId + " to: " + status);
    }

    // Log before a DELETE request (Removing a device)
    @Before("execution(* com.assessments.lab1.controllers.SmartHomeController.removeDevice(..)) && args(deviceId)")
    public void logBeforeDelete(int deviceId) {
        System.out.println("[SMART HOME] Removing device ID " + deviceId + " from the system.");
    }

    // Log after successfully adding a new device
    @AfterReturning("execution(* com.assessments.lab1.controllers.SmartHomeController.addDevice(..))")
    public void logDeviceAdded() {
        System.out.println("[SMART HOME] A new smart device has been successfully added.");
    }

    // Log after successfully removing a device
    @AfterReturning("execution(* com.assessments.lab1.controllers.SmartHomeController.removeDevice(..))")
    public void logDeviceDeleted() {
        System.out.println("[SMART HOME] A smart device has been successfully removed.");
    }
}
