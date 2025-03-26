package com.example.IotProject.service;

import com.example.IotProject.model.ZoneModel;

import java.util.List;
import java.util.Optional;

public interface IZoneService {
    public List<ZoneModel> getZones();
    public ZoneModel getZoneById(Long id);
    public ZoneModel getZoneByName(String name);
    public ZoneModel addZone(ZoneModel zone);
    public ZoneModel updateZone(ZoneModel zone);
    public void deleteZone(Long id);
}
