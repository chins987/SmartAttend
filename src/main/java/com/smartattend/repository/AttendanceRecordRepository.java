package com.smartattend.repository;

import com.smartattend.model.AttendanceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AttendanceRecordRepository extends MongoRepository<AttendanceRecord, Long> {

}