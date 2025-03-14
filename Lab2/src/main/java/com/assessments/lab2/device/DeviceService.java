package com.assessments.lab2.device;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device createDevice(Device device) {
        Device savedDevice = deviceRepository.save(device);
        deviceRepository.flush();
        return savedDevice;
    }

    public Device updateDevice(Long id, Device newDeviceData) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setName(newDeviceData.getName());
                    device.setLocation(newDeviceData.getLocation());
                    device.setStatus(newDeviceData.getStatus());
                    return deviceRepository.save(device);
                })
                .orElse(null);
    }

    public boolean deleteDevice(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
