package com.apple.csndra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CsndraConn {

    public CqlSession session() {
        return CqlSession.builder()
                         .build();
    }


    public void checkConn() {
        try (CqlSession session = session()) {
            ResultSet rs = session.execute("select release_version from system.local");              // (2)
            Row row = rs.one();
            System.out.println("Release Version -> " + row.getString("release_version"));             // (3)
        }
    }
}
