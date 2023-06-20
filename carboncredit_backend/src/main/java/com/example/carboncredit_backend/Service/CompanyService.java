package com.example.carboncredit_backend.Service;

public interface CompanyService {
    String check(String company_name, String password);
    int create(String company_name, String password, String contact_phone, String contact_email, String type_name);
    String getInfo(int company_id);
    String getAuthenticationEmission();
    String buyEmission(int company_id, int emission);
    String getTravelCompanies();
    String updatePrices();
}
