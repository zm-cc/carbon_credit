package com.example.carboncredit_backend.Repository;

import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Integer> {

    @Query(nativeQuery = true, value = "select * from carbon_credits.rewards")
    List<Reward> findALLRewards();
}
