package com.apple.poc;

import com.apple.redis.RedisConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.Instant;

public class App {

    @Autowired
    RedisConn redisConn;

    EmpService empServ;
    ConfigurableApplicationContext context;
    final String SOURCE = "EMPLOYEE";
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App obj = new App();
        obj.config();
        obj.add();
        obj.getEmp();
        obj.getAllEmp();
        obj.getAllEmp();
        obj.delEmp();
        obj.delAllEmp();
        obj.getAllEmp();
        obj.close();

    }

    public void config() {

        context = new AnnotationConfigApplicationContext(AppConfig.class);
        empServ = (EmpService) context.getBean("empServ");

    }

    public void add() {
        // Add key value
        empServ.addEmp("emp001", "Sample_Emp_001", SOURCE);
        empServ.addEmp("emp002", "Sample_Emp_002", SOURCE);
        empServ.addEmp("emp003", "Sample_Emp_003", SOURCE);

    }

    public void getEmp() {

        System.out.println(" Get Employee ");
        Instant first = Instant.now();
        System.out.println(" Get Employee is Called 1st time " + first);
        System.out.println(" 1st time Result : {}" + empServ.get("emp001", SOURCE));
        Instant first1 = Instant.now();
        System.out.println(" Get Employee is returned " + first1);
        System.out.println(" Time taken to get Employee record is " + Duration.between(first, first1)
                                                                              .getSeconds() + " seconds ");

        System.out.println();
        System.out.println();

        //  slowQuery(2);
        Instant second = Instant.now();
        System.out.println(" Get Employee is Called 2nd time " + second);
        System.out.println(" 2nd time Result : {}" + empServ.get("emp001", SOURCE));
        Instant second1 = Instant.now();
        System.out.println(" Get Employee is returned " + second1);
        System.out.println(" Time taken to get Employee record is " + Duration.between(second, second1)
                                                                              .getSeconds() + " seconds ");

        System.out.println();
        System.out.println();

        Instant third = Instant.now();
        System.out.println(" Get Employee is Called 3rd time " + third);
        System.out.println(" 3rd time Result : {}" + empServ.get("emp001", SOURCE));
        Instant third1 = Instant.now();
        System.out.println(" Get Employee is returned " + third1);
        System.out.println(" Time taken to get Employee record is " + Duration.between(third, third1)
                                                                              .getSeconds() + " seconds ");
    }

    public void getAllEmp() {
        System.out.println(" Printing all the employees from cassandra ... ");
        System.out.println();
        System.out.println(empServ.getAllEmp(SOURCE));
        System.out.println();
    }

//    public void updEmp() {
//        empServ.updEmp("emp001", "Sample_Updated_Emp_001", SOURCE);
//    }

    public void delEmp() {
        empServ.delEmp("emp001", SOURCE);
    }

    public void delAllEmp() {
        empServ.delAllEmp("emp001", SOURCE);
    }


    public void redis() {
//        // Getting redis connection
        redisConn = (RedisConn) context.getBean("redisConn");
        redisConn.getRedisConn();

        redisConn.readWriteExample();


    }

    public void close() {
        //shut down the Spring context so that Ehcache got chance to shut down as well
        context.close();
    }

    public void config2() {

        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MovieDao obj = (MovieDao) context.getBean("movieDao");
//        System.out.println("findByDirector is Called " + Instant.now());
//        System.out.println("Result : {}" + obj.findByDirector("dummy"));
//        System.out.println("findByDirector is returned " + Instant.now());
//        System.out.println("Result : {}" + obj.findByDirector("dummy"));
//        System.out.println("Result : {}" + obj.findByDirector("dummy"));

        System.out.println(" Get Employee ");
        Instant first = Instant.now();
        System.out.println(" Get Employee is Called 1st time " + first);
        System.out.println(" 1st time Result : {}" + obj.findUser("1"));
        Instant first1 = Instant.now();
        System.out.println(" Get Employee is returned " + first1);
        System.out.println(" Time taken to get user record is " + Duration.between(first, first1)
                                                                          .getSeconds() + " seconds ");

        //  slowQuery(2);
        Instant second = Instant.now();
        System.out.println(" Get Employee is Called 2nd time " + second);
        System.out.println(" 2nd time Result : {}" + obj.findUser("1"));
        Instant second1 = Instant.now();
        System.out.println(" Get Employee is returned " + second1);
        System.out.println(" Time taken to get user record is " + Duration.between(second, second1)
                                                                          .getSeconds() + " seconds ");

        Instant third = Instant.now();
        System.out.println(" Get Employee is Called 3rd time " + third);
        System.out.println(" 3rd time Result : {}" + obj.findUser("1"));
        Instant third1 = Instant.now();
        System.out.println(" Get Employee is returned " + third1);
        System.out.println(" Time taken to get user record is " + Duration.between(third, third1)
                                                                          .getSeconds() + " seconds ");

//        // Getting redis connection
//        redisConn = (RedisConn) context.getBean("redisConn");
//        redisConn.getRedisConn();
//
//        redisConn.readWriteExample();

        //shut down the Spring context so that Ehcache got chance to shut down as well
        context.close();

    }

    private void slowQuery(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}