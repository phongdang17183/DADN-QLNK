# DADN-QLNK: Botanica Mobile App Backend

Botanica is a greenhouse environmental monitoring and control system.  
This repository contains the **backend** services: data ingestion, processing, storage, and MQTT/REST interfaces for the mobile app.

---

## Features

- **Real-time data streaming** from sensors via MQTT  
- **Persistent storage** of sensor readings & actuator logs in MySQL  
- **RESTful API** for mobile frontend (data query & command endpoints)  
- **Caffeine cache** for low-latency access to recent data  
- **Secure actuator control** through role-based access  

---

## Technology Stack

- **Language:** Java 21 
- **Build:** Maven  
- **Database:** MySQL  
- **Caching:** Caffeine  
- **Messaging:** MQTT  
- **VCS:** Git  

---

## Architecture
Sensor Devices --> MQTT Broker Adafruit --> Backend (Spring Boot) --> Sensor Actuators

Mobile App <--> REST API <--> Backend 

## Frontend
The React Native mobile app for Botanica lives here:
👉 Botanica Mobile App Frontend
https://github.com/Darius-UT/DADN-242
It uses this backend’s REST endpoints and MQTT topics to display sensor data and send control commands.
