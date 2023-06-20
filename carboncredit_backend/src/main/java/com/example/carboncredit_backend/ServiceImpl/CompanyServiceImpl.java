package com.example.carboncredit_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.carboncredit_backend.Dao.CompanyDao;
import com.example.carboncredit_backend.Dao.CompanyTypeDao;
import com.example.carboncredit_backend.Dao.TravelRecordDao;
import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Entity.CompanyType;
import com.example.carboncredit_backend.Entity.TravelRecord;
import com.example.carboncredit_backend.Repository.CompanyRepository;
import com.example.carboncredit_backend.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Autowired
    private TravelRecordDao travelRecordDao;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public String check(String company_name, String password) {
        Company c = companyDao.check(company_name);
        if (c == null)
            return "-1";    //找不到公司
        String pwd = c.getPassword();
        if (!Objects.equals(pwd, password))
            return "-1";    //密码错误

        ArrayList<String> con = new ArrayList<>();
        con.add(String.valueOf(c.getCompany_id()));    //id
        con.add(c.getCompany_name());    //名称
        con.add(String.valueOf(c.getType_id()));    //出行工具类别
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    @Override
    public int create(String company_name, String password, String contact_phone, String contact_email, String type_name) {
        int type_id = companyTypeDao.getIdByName(type_name);
        if (type_id == 0) {
            type_id = companyTypeDao.create(type_name);
        }
        return companyDao.create(company_name, password, contact_phone, contact_email, type_id);
    }

    @Override
    public String getInfo(int company_id) {
        Company c = companyDao.getById(company_id);
        int type_id = c.getType_id();
        CompanyType t = companyTypeDao.getCompanyType(type_id);
        String name = t.getType_name();
        String photo = t.getType_photo();
        ArrayList<String> con = new ArrayList<>();
        con.add(String.valueOf(company_id));    //id
        con.add(c.getCompany_name());    //名称
        con.add(c.getContact_phone());    //电话
        con.add(c.getContact_email());    //邮箱
        con.add(name);    //出行工具类别
        con.add(String.valueOf(c.getTotal_emission()));    //碳排放总量
        con.add(photo);    //出行工具照片
        con.add(String.valueOf(t.getDistance_rate()));
        con.add(String.valueOf(t.getCredit_rate()));
        con.add(String.valueOf(c.getCarbon_cost()));    //购买单价
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String getAuthenticationEmission() {
        Company one = companyDao.getAuthentication();
        ArrayList<String> con = new ArrayList<>();
        con.add(one.getCompany_name());
        con.add(String.valueOf(one.getTotal_emission()));
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String buyEmission(int company_id, int emission) {
        Company one = companyDao.getAuthentication();
        int total = one.getTotal_emission();
        if (emission > total) {
            return "-1";  //库存不足
        } else {
            companyDao.cutEmission(one.getCompany_id(), emission);  //认证机构减去碳排放量
            companyDao.addEmission(company_id, emission);  //出行公司加上碳排放量
            Company travel = companyDao.getById(company_id);
            double cost = travel.getCarbon_cost();
            double money = emission * cost;
            return String.valueOf(money);
        }
    }

    @Override
    public String getTravelCompanies() {
        List<Company> list = companyDao.getTravelCompanies();
        ArrayList<JSONArray> recordJson = new ArrayList<>();
        for (Company one : list) {
            CompanyType t = companyTypeDao.getCompanyType(one.getType_id());
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getCompany_id()));  //id
            arrayList.add(one.getCompany_name());  //名称
            arrayList.add(one.getContact_phone());  //电话
            arrayList.add(one.getContact_email());  //邮箱
            arrayList.add(t.getType_name());  //类型
            arrayList.add(String.valueOf(one.getTotal_emission()));  //持有碳排放量
            arrayList.add(String.valueOf(one.getCarbon_cost()));  //碳排放量购买单价
            recordJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(recordJson, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String updatePrices() {
        double duration_rate = 0.6;
        double distance_rate = 0.8;

        List<Company> companies = companyDao.getTravelCompanies();
        List<TravelRecord> records = travelRecordDao.getAll();
        double total_duration = 0.0;
        double total_distance = 0.0;

        // 初始出行记录（公司、时长、距离）数据
        int[][] keyValuePairs = new int[records.size()][3];
        int dataSize = records.size();
        int company_count = companies.size();
        for (int i = 0; i < dataSize; i++) {
            TravelRecord record = records.get(i);
            keyValuePairs[i][0] = record.getCompany_id();
            keyValuePairs[i][1] = record.getDuration();
            total_duration += keyValuePairs[i][1];
            keyValuePairs[i][2] = record.getTravel_distance();
            total_distance += keyValuePairs[i][2];
        }
        double arg_duration = total_duration / records.size();
        double arg_distance = total_distance / records.size();
        double total_all = duration_rate * total_duration + distance_rate * total_distance;
        total_all /= company_count;
        System.out.println("total；" + total_duration + " " + total_distance);
        System.out.println("arg；" + arg_duration + " " + arg_distance);

        // 初始化每家公司的价格
        Map<Integer, Double> weights = new HashMap<>();
        for (Company company : companies) {
            weights.put(company.getCompany_id(), (double) company.getCarbon_cost());
        }
        System.out.println("weight：" + weights);

        int iterations = 100;  // 迭代次数
        double learningRate = 0.01; // 学习率

        for (int i = 0; i < iterations; i++) {
            // 计算损失函数关于权重（价格）的梯度
            Map<Integer, Double> gradients = new HashMap<>();

            for (int key : weights.keySet()) {
                int count = 0;
                double arg_sum = 0.0;
                double total_sum = 0.0;
                double total_du = 0.0;
                double total_di = 0.0;
                for (int j = 0; j < dataSize; j++) {
                    int dataKey = keyValuePairs[j][0];
                    if (dataKey == key) {
                        total_du += keyValuePairs[j][1];
                        total_di += keyValuePairs[j][2];
                        arg_sum += duration_rate * (arg_duration - keyValuePairs[j][1]) + distance_rate * (arg_distance - keyValuePairs[j][2]);
                    }
                    total_sum = duration_rate * total_du + distance_rate * total_di;
                }
                double gradient = (-0.5 / (2 * dataSize - count) * arg_sum) + (-0.2 / dataSize * (total_all - total_sum));
                gradients.put(key, gradient);
            }

            // 更新权重
            for (int key : weights.keySet()) {
                double updatedWeight = weights.get(key) - learningRate * gradients.get(key);
                weights.put(key, updatedWeight);
            }
        }

        // 打印最终的权重
        for (int key : weights.keySet()) {
            System.out.println("Key: " + key + ", Weight: " + weights.get(key));
        }

        //同步至数据库，并告知前端
        ArrayList<JSONArray> recordJson = new ArrayList<>();
        for (Company one : companies) {
            int id = one.getCompany_id();
            String name = one.getCompany_name();
            float old_cost = one.getCarbon_cost();
            float new_cost = weights.get(id).floatValue();
            one.setCarbon_cost(new_cost);
            companyRepository.save(one);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(name);  //名称
            arrayList.add(String.valueOf(old_cost));  //旧值
            arrayList.add(String.valueOf(new_cost));  //新值
            recordJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(recordJson, SerializerFeature.BrowserCompatible);
    }
}
