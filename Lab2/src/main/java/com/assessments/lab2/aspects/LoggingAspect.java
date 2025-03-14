package com.assessments.lab2.aspects;
import com.assessments.lab2.device.Device;
import com.assessments.lab2.device.DeviceService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Aspect
@Component
class DeviceStatusAspect {

    private final DeviceService deviceService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DeviceStatusAspect(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Before("execution(* com.assessments.lab2.device.DeviceController.getAllDevices(..))")
    public void logBeforeGet() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[" + getTimestamp() + "] [GET] Fetching all smart home devices.");
    }

    @Before("execution(* com.assessments.lab2.device.DeviceController.getDeviceById(..)) && args(deviceId)")
    public void logBeforeGetById(Long deviceId) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[" + getTimestamp() + "] [GET] Fetching device with ID: " + deviceId);
    }

    @Before("execution(* com.assessments.lab2.device.DeviceController.createDevice(..)) && args(device)")
    public void logBeforePost(Device device) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[" + getTimestamp() + "] [POST] Attempting to add a new smart device.");
        System.out.println("Device Details (Before Save): " + formatDevice(device));
    }

    @AfterReturning(pointcut = "execution(* com.assessments.lab2.device.DeviceController.createDevice(..))", returning = "savedDevice")
    public void logDeviceAdded(Device savedDevice) {
        System.out.println("[" + getTimestamp() + "] [POST] A new smart device has been successfully added.");

        Optional<Device> deviceFromDB = deviceService.getDeviceById(savedDevice.getId());
        if (deviceFromDB.isPresent()) {
            System.out.println("Saved Device Details (After Save): " + formatDevice(deviceFromDB.get()));
        } else {
            System.out.println("Warning: Device was saved but could not be retrieved for logging.");
        }
    }

    @Before(value = "execution(* com.assessments.lab2.device.DeviceController.updateDevice(..)) && args(deviceId, updatedDevice)", argNames = "deviceId,updatedDevice")
    public void logBeforePut(Long deviceId, Device updatedDevice) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[" + getTimestamp() + "] [PUT] Updating device ID: " + deviceId);
        System.out.println("Updated Device Data (Before Save): " + formatDevice(updatedDevice));
    }

    @AfterReturning(pointcut = "execution(* com.assessments.lab2.device.DeviceController.updateDevice(..))", returning = "updatedDevice")
    public void logAfterPut(Device updatedDevice) {
        System.out.println("[" + getTimestamp() + "] [PUT] Device has been successfully updated.");
        System.out.println("Updated Device Details (After Save): " + formatDevice(updatedDevice));
    }

    @Before("execution(* com.assessments.lab2.device.DeviceController.deleteDevice(..)) && args(deviceId)")
    public void logBeforeDelete(Long deviceId) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[" + getTimestamp() + "] [DELETE] Attempting to remove device with ID: " + deviceId);
    }

    @AfterReturning(pointcut = "execution(* com.assessments.lab2.device.DeviceController.deleteDevice(..))", returning = "deleted")
    public void logAfterDelete(boolean deleted) {
        if (deleted) {
            System.out.println("[" + getTimestamp() + "] [DELETE] Device was successfully removed.");
        } else {
            System.out.println("[" + getTimestamp() + "] [DELETE] Device could not be found for deletion.");
        }
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    private String formatDevice(Device device) {
        return "{ name: '" + device.getName() + "', " +
                "location: '" + device.getLocation() + "', " +
                "status: '" + device.getStatus() + "' }";
    }
}
