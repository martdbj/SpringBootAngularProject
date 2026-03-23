package com.martdbj.backend.repository;

import com.martdbj.backend.models.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}
