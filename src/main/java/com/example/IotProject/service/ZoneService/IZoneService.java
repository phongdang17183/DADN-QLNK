package com.example.IotProject.service.ZoneService;

import com.example.IotProject.model.ZoneModel;

import java.util.List;

public interface IZoneService {
    public List<ZoneModel> getZones();
    public ZoneModel getZoneById(Long id);
    public ZoneModel getZoneByName(String name);
    public ZoneModel addZone(ZoneModel zone);
    public ZoneModel updateZone(ZoneModel zone);
    public void deleteZone(Long id);
}
