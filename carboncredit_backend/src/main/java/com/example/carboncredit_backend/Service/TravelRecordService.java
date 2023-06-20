package com.example.carboncredit_backend.Service;

public interface TravelRecordService {
    String getCompanyRecord(int company_id);
    int addTravelRecord(int user_id,  int type_id,  int distance, int duration);
}
