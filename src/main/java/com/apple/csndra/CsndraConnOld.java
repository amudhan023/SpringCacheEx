package com.apple.csndra;

import com.apple.model.User;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.List;
import java.util.stream.Collectors;

public class CsndraConnOld {

    String user1 = "INSERT INTO User (id, fullname, age) VALUES (1, 'Tom', 30)";
    String user2 = "INSERT INTO User (id, fullname, age) VALUES (2, 'Jerry', 10)";

    public static void main(String[] args) {
        CsndraConnOld obj = new CsndraConnOld();
//          obj.getConn();
//        obj.addUser();
        obj.getUser("1");
//        obj.addMoreUsers();


    }

    public void getConn() {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {                                  // (1)
            ResultSet rs = session.execute("select release_version from system.local");              // (2)
            Row row = rs.one();
            System.out.println("Release Version -> " + row.getString("release_version"));             // (3)
        }
    }

    public User getUser(String id) {
        User u = new User();
        System.out.println(" Getting user from cassandra for id ->  " + id);
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            ResultSet rs = session.execute("SELECT * FROM mykeyspace.User where id= '" + id + "'");
//            rs.forEach(e -> System.out.println("Id -> " + e.getInt("id") + "  Name -> " + e.getString("fullname") +
//                                                       "  Age" +
//                                                       " ->  " + e.getInt("age")));
            Row r = rs.one();

            u.setId(r.getString("id"));
            u.setName(r.getString("fullname"));
            u.setAge(r.getInt("age"));
            //  System.out.println(u);
        }
        return u;
    }

    public void addUser() {

        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            //  session.execute(user1);
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO mykeyspace.User (id, fullname, age) VALUES (?,?,?)");

            BoundStatement bound = prepared.bind(3, "Cat", 20);
            session.execute(bound);
            System.out.println(" Added user successfully");
        }
    }

    public void addMoreUsers() {

        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            //  session.execute(user1);
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO mykeyspace.User (id, fullname, age) VALUES (?,?,?)");
            BoundStatement bound = null;
            for (int i = 4; i < 10; i++)
                bound = prepared.bind(i, "Cat-No-" + i, i * 2);

            session.execute(bound);
            System.out.println(" Added user successfully");
        }
    }

    public void deleteUser() {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            session.execute("DELETE FROM User WHERE id = 1");
        }

    }

    public void updateUser() {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            session.execute("UPDATE User SET fullname = 'Tommy_new_value' where id = 1");
        }

    }


    public String get(String key, String source) {

        String value = null;
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            ResultSet rs = session.execute("SELECT value FROM mykeyspace." + source);
            value = rs.one()
                      .getString("value");
            System.out.println(" Value " + value);
            rs.forEach(e -> System.out.println("key -> " + e.getString("key") + "  Value -> " + e.getString("value")));

        }
        return value;
    }

    public List<String> getAllKeys(String source) {
        List<String> ls = null;
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            ResultSet rs = session.execute("SELECT key FROM mykeyspace." + source);
            ls = rs.all()
                   .stream()
                   .map(e -> e.getString("key"))
                   .collect(Collectors.toList());

            rs.forEach(e -> System.out.println("key -> " + e.getString("key") + "  Value -> " + e.getString("value")));

        }

        return ls;
    }

    public void add(String key, String value, String source) {

        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            //  session.execute(user1);
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO mykeyspace." + source + "(key, value) VALUES (?,?)");

            BoundStatement bound = prepared.bind(key, value);
            session.execute(bound);
            System.out.println(" Added key " + key + " successfully");
        }
    }

//    public void addMoreUsers() {
//
//        try (CqlSession session = CqlSession.builder().build()) {
//            //  session.execute(user1);
//            PreparedStatement prepared = session.prepare(
//                    "INSERT INTO mykeyspace.User (id, fullname, age) VALUES (?,?,?)");
//            BoundStatement bound = null;
//            for (int i = 4; i < 10; i++)
//                bound = prepared.bind(i, "Cat-No-"+i, i*2);
//
//            session.execute(bound);
//            System.out.println(" Added user successfully");
//        }
//    }

    public void delete(String key, String source) {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            session.execute("DELETE FROM mykeyspace." + source + " WHERE key =" + key);
            System.out.println(" Deleted key " + key + " successfully");
        }
        System.out.println("Record deleted successfully ");
    }

    public void update(String key, String value, String source) {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            session.execute("UPDATE mykeyspace." + source + " SET value = " + value + " where key =" + key);
        }
        System.out.println("Record updated successfully ");
    }

    public void update() {
        try (CqlSession session = CqlSession.builder()
                                            .build()) {
            session.execute("UPDATE User SET fullname = 'Tommy_new_value' where id = 1");
        }
        System.out.println("Record updated successfully ");
    }

    public CqlSession session() {
        return CqlSession.builder()
                         .build();
    }

}

