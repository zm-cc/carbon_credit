package com.example.carboncredit_backend.DaoImpl;

import com.example.carboncredit_backend.Dao.CompanyDao;
import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company check(String company_name) {
        return companyRepository.findByCompany_name(company_name);
    }

    @Override
    public int create(String company_name, String password, String contact_phone, String contact_email, int type_id) {
        Company one = companyRepository.findByCompany_name(company_name);
        if (one != null) {
            System.out.println("Meet a same company.");
            return 0;
        }
        Company c = new Company();
        c.setCompany_name(company_name);
        c.setPassword(password);
        c.setContact_phone(contact_phone);
        c.setContact_email(contact_email);
        c.setType_id(type_id);
        c.setTotal_emission(0);
        c.setCarbon_cost(50);
        companyRepository.save(c);
        return c.getCompany_id();
    }

    @Override
    public Company getById(int company_id) {
        return companyRepository.findByCompany_id(company_id);
    }

    @Override
    public Company getByType_id(int type_id){
        return companyRepository.findByType_id(type_id);
    }

    @Override
    public Company getAuthentication() {
        return companyRepository.findAuthentication();
    }

    @Override
    public void cutEmission(int company_id, int emission) {
        Company c = companyRepository.findByCompany_id(company_id);
        int tmp = c.getTotal_emission();
        tmp -= emission;
        c.setTotal_emission(tmp);
        companyRepository.save(c);
    }

    @Override
    public void addEmission(int company_id, int emission) {
        Company c = companyRepository.findByCompany_id(company_id);
        int tmp = c.getTotal_emission();
        tmp += emission;
        c.setTotal_emission(tmp);
        companyRepository.save(c);
    }

    @Override
    public List<Company> getTravelCompanies() {
        return companyRepository.findTravelCompanies();
    }
}
