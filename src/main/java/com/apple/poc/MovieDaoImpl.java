package com.apple.poc;

import com.apple.csndra.CsndraConnOld;
import com.apple.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("movieDao")
public class MovieDaoImpl implements MovieDao {

    @Cacheable(value = "movieFindCache", key = "#name")
    public Movie findByDirector(String name) {
        slowQuery(2000L);
        System.out.println("findByDirector is running...");
        return new Movie(1, "Forrest Gump", "Robert Zemeckis");
    }

    @Cacheable(value = "userCache", key = "#id")
    public User findUser(String id) {

        System.out.println("FindUser from Cassandra");
        CsndraConnOld cass = new CsndraConnOld();
        return cass.getUser(id);
    }

    private void slowQuery(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}