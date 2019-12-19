package com.apple.util;

public class Utils {

    public static void sleep(int seconds) {
        try {
            System.out.println(" Sleeping for " + seconds + " seconds");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
