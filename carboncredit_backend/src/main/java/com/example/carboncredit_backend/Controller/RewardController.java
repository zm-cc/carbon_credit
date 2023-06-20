package com.example.carboncredit_backend.Controller;

import com.example.carboncredit_backend.Service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @RequestMapping("/AllRewards")
    @ResponseBody
    public String getAllRewards() {     //获取所有实物奖励
        return rewardService.getAllRewards();
    }
}
