package com.apple.poc;

import com.apple.model.CsndraModel;

import java.util.List;

public interface EmpService {

    CsndraModel get(String key, String source);

    List<CsndraModel> getAllEmp(String source);

    void addEmp(String key, String value, String source);

    void updEmp(String key, String value, String source);

    void delEmp(String key, String source);

    void delAllEmp(String key, String source);

}
