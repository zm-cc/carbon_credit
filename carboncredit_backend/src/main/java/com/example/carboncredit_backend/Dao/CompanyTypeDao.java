package com.example.carboncredit_backend.Dao;

import com.example.carboncredit_backend.Entity.CompanyType;

import java.util.List;

public interface CompanyTypeDao {
    CompanyType getCompanyType(int type_id);
    int getIdByName(String type_name);
    int create(String type_name);
    List<CompanyType> getAllTypes();
}
