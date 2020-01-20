package com.example.traveladvisor.dal;


import com.example.traveladvisor.bll.Location;
import com.example.traveladvisor.services.ServiceGetLocationList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DatabaseManager {
    private static DatabaseManager db = null;
    private static String ipHost = "http://192.168.193.88:8080/";

    private DatabaseManager() {
    }

    public static DatabaseManager newInstance() {
        if (db == null) {
            db = new DatabaseManager();
        }
        return db;
    }

    public static DatabaseManager newInstance(String ip) {
        if (db == null) {
            db = new DatabaseManager();
        }
        ipHost = ip;
        return db;
    }

    public ArrayList<Location> getAllLocations() throws Exception {
        Gson gson = new Gson();
        ArrayList<Location> retLocations;

        ServiceGetLocationList controller = new ServiceGetLocationList();
        ServiceGetLocationList.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = controller.get();
        try {
            Type colltype = new TypeToken<ArrayList<Location>>() { }.getType();
            retLocations = gson.fromJson(strFromWebService, colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }

        return retLocations;
    }
}
