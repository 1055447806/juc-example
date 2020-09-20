package com.ohh.classLoader.chapter6;

import com.ohh.classLoader.chapter3.MyClassLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadContextClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        Thread.currentThread().setContextClassLoader(new MyClassLoader());
        classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);

        Class.forName("com.mysql.jdbc.Driver");
        // getConnection ->
        // callerCL = Thread.currentThread().getContextClassLoader(); ->
        // isDriverAllowed ->
        // Class.forName(driver.getClass().getName(), true, classLoader);
        Connection connection = DriverManager.getConnection("url");
    }
}
