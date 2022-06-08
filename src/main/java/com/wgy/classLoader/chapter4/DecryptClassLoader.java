package com.wgy.classLoader.chapter4;

import java.io.*;

public class DecryptClassLoader extends ClassLoader {
    private static final String DEFAULT_DIR = "C:\\classLoader\\cl3";
    private String dir = DEFAULT_DIR;

    public DecryptClassLoader() {
        super();
    }

    public DecryptClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String classPath = name.replace(".", "/");
        File classFile = new File(this.dir, classPath + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("The class " + name + " not found under directory [" + dir + "]");
        }
        byte[] classBytes = loadClassBytes(classFile);
        if (null == classBytes || classBytes.length == 0) {
            throw new ClassNotFoundException("Load class " + name + " failed");
        }
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(File classFile) {
        try (FileInputStream fis = new FileInputStream(classFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] ^= (byte) 0xff;
                }
                baos.write(bytes, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}
