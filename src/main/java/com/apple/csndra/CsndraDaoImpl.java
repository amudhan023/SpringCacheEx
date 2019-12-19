package com.apple.csndra;

import com.apple.model.CsndraModel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository("CsndraDao")
public class CsndraDaoImpl extends CsndraConn implements CsndraDao {


    @Override
    public CsndraModel get(String key, String source) {

        System.out.println(" Cassandra dao layer called .... ");
        CsndraModel model = null;
        try (CqlSession session = session()) {

            PreparedStatement prepared = session.prepare(
                    "SELECT value FROM mykeyspace.EMPLOYEE where key = ? and source = ?");

            BoundStatement bound = prepared.bind(key, source);
            ResultSet rs = session.execute(bound);

//            ResultSet rs = session.execute("SELECT value FROM mykeyspace.EMPLOYEE  where key = '" + key + "'");
            model = new CsndraModel();
            model.setKey(key);
            model.setSource(source);
            model.setValue(rs.one()
                             .getString("value"));
//            System.out.println(" Value " + model.getValue());
//            rs.forEach(e -> System.out.println("key -> " + key + "  Value -> " + e.getString("value")));

        }
        return model;
    }

    @Override
    public List<CsndraModel> getAllEntries(String source) {

        List<CsndraModel> ls = null;
        Function<Row, CsndraModel> mapper = e -> new CsndraModel(e.getString("key"), e.getString("value"),
                                                                 e.getString("source"));

        try (CqlSession session = session()) {
            ResultSet rs = session.execute("SELECT key,value,source FROM mykeyspace.EMPLOYEE");
            ls = rs.all()
                   .stream()
                   .map(mapper)
                   .collect(Collectors.toList());

//            rs.forEach(e -> System.out.println("key -> " + e.getString("key") + "  Value -> " + e.getString
//            ("value")));
        }
        return ls;
    }

    @Override
    public void add(String key, String value, String source) {

        try (CqlSession session = session()) {
            PreparedStatement prepared = session.prepare(
                    "INSERT INTO mykeyspace.EMPLOYEE (key, value, source) VALUES (?,?,?)");

            BoundStatement bound = prepared.bind(key, value, source);
            session.execute(bound);
            System.out.println(" Added key " + key + " successfully");
        }
    }

    @Override
    public void delete(String key, String source) {
        try (CqlSession session = session()) {
            PreparedStatement prepared = session.prepare(
                    "DELETE FROM mykeyspace.EMPLOYEE  where key = ? and source = ?");

            BoundStatement bound = prepared.bind(key, source);
            session.execute(bound);
            System.out.println(" Deleted key " + key + " successfully");
        }

    }
//
//    @Override
//    public void update(String key, String value, String source) {
//        try (CqlSession session = session()) {
//            session.execute("UPDATE mykeyspace.EMPLOYEE  SET value = '" + value + "' where key = '" + key+"'");
//        }
//        System.out.println("Record updated successfully ");
//    }

    @Override
    public void delAll(String table) {
        try (CqlSession session = session()) {
            session.execute("TRUNCATE mykeyspace." + table);
        }
        System.out.println(" Truncated table EMPLOYEE  successfully");
    }


    public static void main(String[] args) {
        CsndraDaoImpl obj = new CsndraDaoImpl();
//          obj.getConn();
//        obj.addUser();
//        obj.getUser("1");
//        obj.addMoreUsers();


    }

}
