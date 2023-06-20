package com.example.carboncredit_backend.Controller;

import com.example.carboncredit_backend.Service.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class CompanyTypeController {
    @Autowired
    CompanyTypeService companyTypeService;

    @RequestMapping("/GetAllTypes")
    @ResponseBody
    public String getAll() {     //获取所有出行工具类型
        return companyTypeService.getTypes();
    }
}
