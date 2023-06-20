package com.example.carboncredit_backend.Dao;

import com.example.carboncredit_backend.Entity.TravelRecord;

import java.util.List;

public interface TravelRecordDao {
    List<TravelRecord> getById(int company_id);
    List<TravelRecord> getAll();
}
