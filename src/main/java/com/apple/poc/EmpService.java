package com.apple.poc;

import com.apple.model.CsndraModel;

import java.util.List;

public interface EmpService {

    public CsndraModel get(String key, String source);

    public List<CsndraModel> getAllEmp(String source);

    public void addEmp(String key, String value, String source);

    public void updEmp(String key, String value, String source);

    public void delEmp(String key, String source);

    public void delAllEmp(String key, String source);

}
