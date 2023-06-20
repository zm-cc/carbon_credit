package com.example.carboncredit_backend.DaoImpl;

import com.example.carboncredit_backend.Dao.TravelRecordDao;
import com.example.carboncredit_backend.Entity.TravelRecord;
import com.example.carboncredit_backend.Repository.TravelRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TravelRecordDaoImpl implements TravelRecordDao {
    @Autowired
    private TravelRecordRepository travelRecordRepository;

    @Override
    public List<TravelRecord> getById(int company_id) {
        return travelRecordRepository.findByCompany_id(company_id);
    }

    @Override
    public List<TravelRecord> getAll() {
        return travelRecordRepository.findAll();
    }
}
