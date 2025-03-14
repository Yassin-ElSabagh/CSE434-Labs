package com.assessments.lab2.device;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device savedDevice = deviceService.createDevice(device);
        return deviceService.getDeviceById(savedDevice.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(500).body(null));
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id)
                .map(device -> ResponseEntity.ok().body((Object) device))
                .orElseGet(() -> ResponseEntity.status(404).body("Device not found."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDevice(@PathVariable Long id, @RequestBody Device updatedDevice) {
        Device updated = deviceService.updateDevice(id, updatedDevice);
        return (updated != null)
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(404).body("Device not found.");
    }

    @DeleteMapping("/{id}")
    public boolean deleteDevice(@PathVariable Long id) {
        return deviceService.deleteDevice(id);
    }
}
