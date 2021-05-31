package com.psy;

import com.psy.util.ConnectionManager;
import org.springframework.aop.scope.ScopedProxyUtils;

import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        String sql = "SELECT * FROM indications" ;


        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            var executeResult = statement.executeQuery(sql);
            while(executeResult.next()){
                System.out.println(executeResult.getLong("id"));
                System.out.println(executeResult.getLong("subscriber_id"));
                System.out.println(executeResult.getLong("counter_id"));
                System.out.println(executeResult.getBigDecimal("u"));
                System.out.println(executeResult.getBigDecimal("i"));
                System.out.println(executeResult.getBigDecimal("p"));
                System.out.println(executeResult.getBigDecimal("q"));
                System.out.println(executeResult.getBigDecimal("power_factor"));
                System.out.println(executeResult.getBigDecimal("w"));
                System.out.println(executeResult.getTimestamp("date"));
                System.out.println("-------");
            }
        } finally {
            ConnectionManager.closePool();
        }

    }
}
