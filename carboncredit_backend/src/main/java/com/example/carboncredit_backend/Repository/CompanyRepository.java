package com.example.carboncredit_backend.Repository;

import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Entity.TravelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query(nativeQuery = true, value = "select * from carbon_credits.companies where companies.company_name = :company_name")
    Company findByCompany_name(@Param("company_name") String company_name);

    @Query(nativeQuery = true, value = "select * from carbon_credits.companies where companies.type_id = :type_id")
    Company findByType_id(@Param("type_id") int type_id);

    @Query(nativeQuery = true, value = "select * from carbon_credits.companies where companies.company_id = :company_id")
    Company findByCompany_id(@Param("company_id") int company_id);

    @Query(nativeQuery = true, value = "select * from carbon_credits.companies where companies.type_id = 1")
    Company findAuthentication();

    @Query(nativeQuery = true, value = "select * from carbon_credits.companies where companies.type_id != 1")
    List<Company> findTravelCompanies();
}
