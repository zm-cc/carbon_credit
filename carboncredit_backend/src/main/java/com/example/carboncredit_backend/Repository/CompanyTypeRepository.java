package com.example.carboncredit_backend.Repository;

import com.example.carboncredit_backend.Entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Integer> {
    @Query(nativeQuery = true, value = "select * from carbon_credits.company_types where company_types.type_id = :type_id")
    CompanyType findByType_id(@Param("type_id") int type_id);

    @Query(nativeQuery = true, value = "select * from carbon_credits.company_types where company_types.type_name = :type_name")
    CompanyType findByType_name(@Param("type_name") String type_name);

    @Query(nativeQuery = true, value = "select * from carbon_credits.company_types where company_types.type_name != 'authentication'")
    List<CompanyType> findAllTypes();
}
