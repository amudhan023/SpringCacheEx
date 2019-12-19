package com.apple.poc;

import com.apple.csndra.CsndraDao;
import com.apple.csndra.CsndraDaoImpl;
import com.apple.model.CsndraModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("empServ")
public class EmpServiceImpl implements EmpService {

    @Autowired
    CsndraDao cass;

    @Override
    public void addEmp(String key, String value, String source) {
        cass.add(key, value, source);
    }

    @Override
    @Cacheable(value = "emplCache", key = "#key")
    public CsndraModel get(String key, String source) {


        return cass.get(key, source);
    }

    @Override
    public List<CsndraModel> getAllEmp(String source) {

        return cass.getAllEntries(source);
    }


    @Override
    public void updEmp(String key, String value, String source) {
        cass.update(key, value, source);
    }

    @Override
    public void delEmp(String key, String source) {
        cass.delete(key, source);
    }

    @Override
    public void delAllEmp(String key, String source) {
        cass.delAll(key, source);
    }


}
