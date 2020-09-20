package com.ohh.classLoader.chapter4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class EncryptUtils {

    public static final byte ENCRYPT_FACTOR = (byte) 0xff;

    private EncryptUtils() {
        //empty
    }

    public static void doEncrypt(String source, String target) {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data ^ ENCRYPT_FACTOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        doEncrypt("C:\\classLoader\\cl3\\com\\ohh\\classLoader\\chapter3\\plain.class",
                "C:\\classLoader\\cl3\\com\\ohh\\classLoader\\chapter3\\MyObject.class");
    }
}
