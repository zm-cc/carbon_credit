package com.example.carboncredit_backend.Repository;

import com.example.carboncredit_backend.Entity.TravelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRecordRepository extends JpaRepository<TravelRecord, Integer> {
    @Query(nativeQuery = true, value = "select * from carbon_credits.travel_records where travel_records.company_id = :company_id")
    List<TravelRecord> findByCompany_id(@Param("company_id") int company_id);

    @Query(nativeQuery = true, value = "select * from carbon_credits.travel_records")
    List<TravelRecord> findAllRecords();
}
