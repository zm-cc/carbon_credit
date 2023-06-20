package com.example.carboncredit_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.carboncredit_backend.Dao.CompanyTypeDao;
import com.example.carboncredit_backend.Entity.CompanyType;
import com.example.carboncredit_backend.Service.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyTypeServiceImpl implements CompanyTypeService {
    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Override
    public String getTypes() {
        List<CompanyType> list = companyTypeDao.getAllTypes();
        ArrayList<String> con = new ArrayList<>();
        for (CompanyType one : list) {
            con.add(one.getType_name());
        }
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }
}
