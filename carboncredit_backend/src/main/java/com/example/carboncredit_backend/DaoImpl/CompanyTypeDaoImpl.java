package com.example.carboncredit_backend.DaoImpl;

import com.example.carboncredit_backend.Dao.CompanyTypeDao;
import com.example.carboncredit_backend.Entity.CompanyType;
import com.example.carboncredit_backend.Repository.CompanyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyTypeDaoImpl implements CompanyTypeDao {
    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyType getCompanyType(int type_id) {
        return companyTypeRepository.findByType_id(type_id);
    }

    @Override
    public int getIdByName(String type_name) {
        CompanyType companyType = companyTypeRepository.findByType_name(type_name);
        if (companyType == null) {
            return 0;
        } else {
            return companyType.getType_id();
        }
    }

    @Override
    public int create(String type_name) {
        CompanyType t = new CompanyType();
        t.setType_name(type_name);
        t.setDistance_rate(0.5F);
        t.setCredit_rate(0.5F);
        companyTypeRepository.save(t);
        return t.getType_id();
    }

    @Override
    public List<CompanyType> getAllTypes() {
        return companyTypeRepository.findAllTypes();
    }
}
