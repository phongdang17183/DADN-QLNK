package com.example.IotProject.controller;

import com.example.IotProject.model.ZoneModel;
import com.example.IotProject.service.ZoneService.IZoneService;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/zone")
public class ZoneController {
    IZoneService zoneService;

    @Autowired
    public ZoneController(IZoneService zoneService){
        this.zoneService = zoneService;
    }

    @GetMapping("")
    public ResponseEntity<List<ZoneModel>> getZones(){
        return ResponseEntity.ok(zoneService.getZones());
    }

    @GetMapping("/id")
    public ResponseEntity<?> getById(@Param Long id){
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<ZoneModel> getByName(@Param String name){
        return ResponseEntity.ok(zoneService.getZoneByName(name));
    }

    @PostMapping("")
    public ResponseEntity<ZoneModel> addZone(@RequestBody ZoneModel zone){
        return ResponseEntity.status(HttpStatus.CREATED).body(zoneService.addZone(zone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteZone(@PathVariable Long id){
        zoneService.deleteZone(id);
        return ResponseEntity.ok("Zone deleted successfully!");
    }
}
