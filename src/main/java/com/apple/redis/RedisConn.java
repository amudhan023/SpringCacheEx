package com.apple.redis;


import com.apple.util.Utils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.domain.Timeout;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component("redisConn")
public class RedisConn {

    RedisClient redisClient;
    CountDownLatch latch=new CountDownLatch(1);
    public static void main(String[] args) {
        RedisConn obj = new RedisConn();
        obj.readWriteExample();

    }

    public StatefulRedisConnection<String, String> getRedisConn() {
        // Syntax: redis://[password@]host[:port][/databaseNumber]
//        RedisClient redisClient = RedisClient.create(RedisURI.create("redis://password@localhost:6379/0"));

        redisClient = RedisClient.create(RedisURI.create("redis://localhost:6379/0"));
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        return connection;

    }

    public void readWriteExample() {

        StatefulRedisConnection<String, String> conn = getRedisConn();
        RedisCommands<String, String> syncComm = conn.sync();
        RedisAsyncCommands asyncComm = conn.async();
        syncComm.set("foo", "bar");
        asyncComm.set("key1", "samplekey1");
        asyncComm.set("key1", "samplekey2");
        asyncComm.set("key1", "samplekey3");

        // Getting the no of keys present in redis
        RedisFuture<Long> future = asyncComm.dbsize();
        try {
            long val = future.get(3, TimeUnit.SECONDS).longValue();
            System.out.println(" Future result No of keys -> " + val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // asyncComm.expire("key1",3);
        String value = syncComm.get("foo");
        String value2 = syncComm.get("key1");
        System.out.println(value);

        System.out.println("  key1's Value -> " + value2 + " time " + System.currentTimeMillis());

        // changing key1's value
        asyncComm.set("key1", "samplekey2");
        value2 = syncComm.get("key1");
        System.out.println("  key1's Value -> " + value2 + " time " + System.currentTimeMillis());

        // adding two more keys
        asyncComm.set("key2", "samplekey2");
        syncComm.set("key3", "samplekey3");

        // Getting the no of keys present in redis
        future = asyncComm.dbsize();
        try {
            long val = future.get(3, TimeUnit.SECONDS).longValue();
            System.out.println(" Future result No of keys ->  " + val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        RedisStringReactiveCommands<String, String> commands = conn.reactive();
        commands
                .get("key1")
                .subscribe(v -> System.out.println(" Subscribed to key1 -> "+v));

        asyncComm.set("key1", "samplekey4");
        asyncComm.set("key1", "samplekey5");
        asyncComm.set("key1", "samplekey6");
        asyncComm.set("key1", "samplekey7");


        Utils.sleep(5);
        conn.close();
        close();
    }

    public void close() {
        redisClient.shutdown();
    }
}