package com.example.IotProject.service.DeviceDataService;

import com.example.IotProject.model.DeviceDataModel;
import com.example.IotProject.model.DeviceModel;
import com.example.IotProject.repository.DeviceDataRepository;
import com.example.IotProject.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceDataService implements IDeviceDataService {
    private final DeviceDataRepository deviceDataRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceDataService(
            DeviceDataRepository deviceDataRepository,
            DeviceRepository deviceRepository
    ) {
        this.deviceDataRepository = deviceDataRepository;
        this.deviceRepository = deviceRepository;
    }
    @Override
    public DeviceDataModel saveData(Timestamp time, Float value, String feedname){
        DeviceModel device = deviceRepository.findByFeedName(feedname);

        DeviceDataModel newData = new DeviceDataModel();
        newData.setTime(time);
        newData.setValue(value);
        newData.setDevice(device);
        return deviceDataRepository.save(newData);
    }

    @Override
    public List<DeviceDataModel> getAllDeviceData(String feedName){
        return deviceDataRepository.findByDevice_feedName(feedName);
    }

    @Override
    public List<DeviceDataModel> getAllZoneData(Long zoneId){
        List<DeviceModel> devices = deviceRepository.findByZoneId(zoneId);

        List<DeviceDataModel> deviceDataModels = new ArrayList<>();

        for (DeviceModel device : devices) {
            List<DeviceDataModel> dataForThisDevice = deviceDataRepository.findByDevice_feedName(device.getFeedName());
            deviceDataModels.addAll(dataForThisDevice);
        }

        return deviceDataModels;
    }

    @Override
    public List<DeviceDataModel> getDataOneDay(String feedName) {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Xác định start và end
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();      // 2025-04-09T00:00:00
        LocalDateTime endOfDay   = now.toLocalDate().atTime(23, 59, 59); // 2025-04-09T23:59:59

        // Chuyển sang Timestamp
        Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp endTimestamp   = Timestamp.valueOf(endOfDay);

        // Gọi repository
        return deviceDataRepository.findByDeviceIdAndTimeRangeNative(
                feedName,
                startTimestamp,
                endTimestamp
        );
    }

    @Override
    public List<DeviceDataModel> getDataOneWeek(String feedName) {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // startOfWeek: chuyển về Monday 00:00
        // Giả sử Thứ Hai là 1, Thứ Ba là 2, ...
        LocalDateTime startOfWeek = now
                .with(ChronoField.DAY_OF_WEEK, 1)  // chuyển về thứ hai
                .toLocalDate()
                .atStartOfDay();

        // endOfWeek: Chủ nhật 23:59:59
        // Tùy logic của bạn, có thể +7 ngày rồi trừ 1 giây...
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        Timestamp startTimestamp = Timestamp.valueOf(startOfWeek);
        Timestamp endTimestamp   = Timestamp.valueOf(endOfWeek);

        return deviceDataRepository.findByDeviceIdAndTimeRangeNative(
                feedName,
                startTimestamp,
                endTimestamp
        );
    }

    @Override
    public List<DeviceDataModel> getDataOneMonth(String feedName) {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // startOfMonth
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();

        // endOfMonth: cộng 1 tháng, lùi đi 1 ngày và set 23:59:59
        // Hoặc có thể dùng YearMonth
        YearMonth yearMonth = YearMonth.from(now);
        LocalDateTime endDayOfMonth = yearMonth.atEndOfMonth().atStartOfDay(); // 2025-04-30
        LocalDateTime endOfMonth = endDayOfMonth.toLocalDate().atTime(23, 59, 59);

        Timestamp startTimestamp = Timestamp.valueOf(startOfMonth);
        Timestamp endTimestamp   = Timestamp.valueOf(endOfMonth);

        return deviceDataRepository.findByDeviceIdAndTimeRangeNative(
                feedName,
                startTimestamp,
                endTimestamp
        );
    }
}
