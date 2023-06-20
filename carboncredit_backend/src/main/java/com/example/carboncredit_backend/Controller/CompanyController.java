package com.example.carboncredit_backend.Controller;

import com.example.carboncredit_backend.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Component
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @RequestMapping("/CheckCompany")
    @ResponseBody
    public String check(@RequestBody Map map) {     //登录时检测用户名与密码
        String company_name = String.valueOf(map.get("company_name"));
        String password = String.valueOf(map.get("password"));
        return companyService.check(company_name, password);
    }

    @RequestMapping("/CreateCompany")
    @ResponseBody
    public String create(@RequestBody Map map) {    //注册新用户
        String company_name = String.valueOf(map.get("company_name"));
        String password = String.valueOf(map.get("password"));
        String contact_phone = String.valueOf(map.get("contact_phone"));
        String contact_email = String.valueOf(map.get("contact_email"));
        String type_name = String.valueOf(map.get("type_name"));
        int cid = companyService.create(company_name, password, contact_phone, contact_email, type_name);
        return String.valueOf(cid);
    }

    @RequestMapping("/GetCompanyInfo")
    @ResponseBody
    public String getInfo(@RequestBody Map map) {     //获取公司（出行工具公司、认证机构）信息
        String tmp = String.valueOf(map.get("id"));
        int id = Integer.parseInt(tmp);
        return companyService.getInfo(id);
    }

    @RequestMapping("/GetAuthenticationEmission")
    @ResponseBody
    public String getEmission() {     //获取认证机构目前持有的碳排放总量
        return companyService.getAuthenticationEmission();
    }

    @RequestMapping("/BuyEmission")
    @ResponseBody
    public String buyEmission(@RequestBody Map map) {     //出行工具公司向认证机构购买碳排放量
        String tmp1 = String.valueOf(map.get("company_id"));
        int id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("emission"));
        int emission = Integer.parseInt(tmp2);
        return companyService.buyEmission(id, emission);
    }

    @RequestMapping("/AllTravelCompany")
    @ResponseBody
    public String getTravelCompanies() {     //获取所有出行工具公司
        return companyService.getTravelCompanies();
    }

    @RequestMapping("/UpdatePrices")
    @ResponseBody
    public String updatePrices() {     //智能化更新每家公司购买碳排放量的价格
        return companyService.updatePrices();
    }
}
