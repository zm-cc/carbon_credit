package com.example.carboncredit_backend.DaoImpl;

import com.example.carboncredit_backend.Dao.RewardDao;
import com.example.carboncredit_backend.Entity.Reward;
import com.example.carboncredit_backend.Repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RewardDaoImpl implements RewardDao {
    @Autowired
    private RewardRepository rewardRepository;

    @Override
    public List<Reward> getAllRewards() {
        return rewardRepository.findALLRewards();
    }
}
