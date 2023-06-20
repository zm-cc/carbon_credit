package com.example.carboncredit_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.carboncredit_backend.Dao.RewardDao;
import com.example.carboncredit_backend.Entity.Reward;
import com.example.carboncredit_backend.Service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    private RewardDao rewardDao;

    @Override
    public String getAllRewards() {
        List<Reward> list = rewardDao.getAllRewards();
        ArrayList<JSONArray> recordJson = new ArrayList<>();
        for (Reward one : list) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getReward_id()));  //id
            arrayList.add(one.getImage());  //图片
            arrayList.add(one.getName());  //名称
            arrayList.add(String.valueOf(one.getRequire_credits()));  //所需积分
            arrayList.add(String.valueOf(one.getRequire_level()));  //所需等级
            arrayList.add(String.valueOf(one.getInventory()));  //库存
            recordJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(recordJson, SerializerFeature.BrowserCompatible);
    }
}
