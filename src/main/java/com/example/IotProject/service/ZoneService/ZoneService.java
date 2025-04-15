package com.example.IotProject.service.ZoneService;

import com.example.IotProject.exception.DataNotFoundException;
import com.example.IotProject.exception.ExistUsernameException;
import com.example.IotProject.model.ZoneModel;
import com.example.IotProject.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ZoneService implements IZoneService {
    ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    @Override
    public List<ZoneModel> getZones(){
        return zoneRepository.findAll();
    }

    @Override
    public ZoneModel getZoneById(Long id) {
        return zoneRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Zone not found with id: " + id));
    }

    @Override
    public ZoneModel getZoneByName(String name) {
        return zoneRepository.findByName(name);
    }

    @Override
    public ZoneModel addZone(ZoneModel zone) {
        if(zoneRepository.existsById(zone.getId())){
            throw new ExistUsernameException("Zone already exists!");
        }
        return zoneRepository.save(zone);
    }

    @Override
    public ZoneModel updateZone(ZoneModel zone) {
        if(!zoneRepository.existsById(zone.getId())){
            throw new DataNotFoundException("Cannot update. zone not found with name: " + zone.getName());
        }
        return zoneRepository.save(zone);
    }

    @Override
    public void deleteZone(Long id) {
        if(!zoneRepository.existsById(id)){
            throw new DataNotFoundException("Cannot delete. zone not found with id: " + id);
        }
        zoneRepository.deleteById(id);
    }


}
