package com.example.carboncredit_backend.Dao;

import com.example.carboncredit_backend.Entity.Company;

import java.util.List;

public interface CompanyDao {
    Company check(String company_name);
    int create(String company_name, String password, String contact_phone, String contact_email, int type_id);
    Company getById(int company_id);
    Company getByType_id(int type_id);
    Company getAuthentication();
    void cutEmission(int company_id, int emission);
    void addEmission(int company_id, int emission);
    List<Company> getTravelCompanies();
}
