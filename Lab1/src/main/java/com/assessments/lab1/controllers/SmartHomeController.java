package com.assessments.lab1.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/devices")
public class SmartHomeController {

    private static final List<Map<String, Object>> devices = new ArrayList<>();
    private static int deviceIdCounter = 1;  // Auto-incrementing Device ID

    // Add a new device (POST)
    @PostMapping
    public ResponseEntity<String> addDevice(@RequestBody Map<String, Object> deviceData) {
        deviceData.put("deviceId", deviceIdCounter++);
        deviceData.putIfAbsent("status", "OFF"); // Default status is OFF
        devices.add(deviceData);
        return ResponseEntity.ok("Device added successfully.");
    }

    // Get all devices (GET)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllDevices() {
        return ResponseEntity.ok(devices);
    }

    // Update device status (PUT)
    @PutMapping("/{deviceId}/status")
    public ResponseEntity<String> updateDeviceStatus(@PathVariable int deviceId, @RequestParam String status) {
        status = status.trim(); // ✅ Trim whitespace & newlines

        for (Map<String, Object> device : devices) {
            if ((int) device.get("deviceId") == deviceId) {
                device.put("status", status);
                return ResponseEntity.ok("Device status updated.");
            }
        }
        return ResponseEntity.status(404).body("Device not found.");
    }

    // Delete a device (DELETE)
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<String> removeDevice(@PathVariable int deviceId) {
        Iterator<Map<String, Object>> iterator = devices.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> device = iterator.next();
            if ((int) device.get("deviceId") == deviceId) {
                iterator.remove();
                return ResponseEntity.ok("Device removed successfully.");
            }
        }
        return ResponseEntity.status(404).body("Device not found.");
    }
}