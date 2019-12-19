package com.apple.poc;

import com.apple.model.User;

public interface MovieDao {

    Movie findByDirector(String name);

    User findUser(String id);
}