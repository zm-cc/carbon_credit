package com.example.carboncredit_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.carboncredit_backend.Dao.CompanyDao;
import com.example.carboncredit_backend.Dao.CompanyTypeDao;
import com.example.carboncredit_backend.Dao.TravelRecordDao;
import com.example.carboncredit_backend.Dao.UserDao;
import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Entity.CompanyType;
import com.example.carboncredit_backend.Entity.TravelRecord;
import com.example.carboncredit_backend.Entity.User;
import com.example.carboncredit_backend.Repository.TravelRecordRepository;
import com.example.carboncredit_backend.Service.TravelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelRecordServiceImpl implements TravelRecordService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Autowired
    private TravelRecordDao travelRecordDao;

    @Autowired
    private TravelRecordRepository travelRecordRepository;

    @Override
    public String getCompanyRecord(int company_id) {
        List<TravelRecord> list = travelRecordDao.getById(company_id);
        ArrayList<JSONArray> recordJson = new ArrayList<>();
        for (TravelRecord one : list) {
            User u = userDao.getById(one.getUser_id());
            Company c = companyDao.getById(one.getCompany_id());
            CompanyType t = companyTypeDao.getCompanyType(c.getType_id());
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getRecord_id()));
            arrayList.add(u.getUsername());
            arrayList.add(c.getCompany_name());
            arrayList.add(t.getType_name());
            arrayList.add(String.valueOf(one.getDuration()));
            arrayList.add(String.valueOf(one.getTravel_distance()));
            arrayList.add(String.valueOf(one.getCredits()));
            recordJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(recordJson, SerializerFeature.BrowserCompatible);
    }

    @Override
    public int addTravelRecord(int user_id,  int type_id,  int distance, int duration){
        TravelRecord travelRecord = new TravelRecord();
        travelRecord.setUser_id(user_id);
        travelRecord.setCompany_id(companyDao.getByType_id(type_id).getCompany_id());
        travelRecord.setDuration(duration);
        travelRecord.setTravel_distance(distance);
        CompanyType companyType = companyTypeDao.getCompanyType(type_id);
        float distance_rate = companyType.getDistance_rate();
        float credit_rate = companyType.getCredit_rate();
        int credit = (int) (distance * distance_rate * credit_rate);
        travelRecord.setCredits(credit);
        travelRecordRepository.save(travelRecord);
        return travelRecord.getRecord_id();
    }
}
