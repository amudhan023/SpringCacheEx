package com.apple.csndra;

import com.apple.model.CsndraModel;

import java.util.List;

public interface CsndraDao {

    CsndraModel get(String key, String source);

    List<CsndraModel> getAllEntries(String source);

    void add(String key, String value, String source);

    void delete(String key, String source);

//    void update(String key, String value, String source);

    void delAll(String table);
}
