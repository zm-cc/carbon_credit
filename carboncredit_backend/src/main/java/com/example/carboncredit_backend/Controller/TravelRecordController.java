package com.example.carboncredit_backend.Controller;

import com.example.carboncredit_backend.Service.TravelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Component
public class TravelRecordController {
    @Autowired
    private TravelRecordService travelRecordService;

    @RequestMapping("/GetCompanyRecord")
    @ResponseBody
    public String getRecord(@RequestBody Map map) {     //对于某一公司，获取所有用户通过他的工具出行的信息
        String tmp = String.valueOf(map.get("company_id"));
        int cid = Integer.parseInt(tmp);
        return travelRecordService.getCompanyRecord(cid);
    }
}
