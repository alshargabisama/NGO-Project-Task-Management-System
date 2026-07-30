/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.util;

/**
 *
 * @author ENJAZ
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final Properties properties;
    private final String fileName = "config.properties";

    public Config() {
        properties = new Properties();
        load();
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            properties.store(out, "Application Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try (FileInputStream in = new FileInputStream(fileName)) {
            properties.load(in);
        } catch (IOException e) {
            // أول تشغيل للبرنامج، لا يوجد ملف بعد.
        }
    }
}