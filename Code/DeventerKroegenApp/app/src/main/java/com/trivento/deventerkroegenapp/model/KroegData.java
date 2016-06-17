package com.trivento.deventerkroegenapp.model;

import com.trivento.deventerkroegenapp.tasks.KroegenTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class KroegData {

    private static KroegData data = null;
    public static List<Kroeg> kroegen = new ArrayList<>();

    private KroegData(){
        searchData();
    }

    public static void searchData(String category){
        try {
            category = URLEncoder.encode(category, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KroegenTask kroegenTask = new KroegenTask(kroegen);
        kroegenTask.execute(category);
    }

    public static void searchData(){
        KroegenTask kroegenTask = new KroegenTask(kroegen);
        kroegenTask.execute();
    }

    public static KroegData getInstance() {
        if(data == null){
            data = new KroegData();
        }
        return data;
    }

}
