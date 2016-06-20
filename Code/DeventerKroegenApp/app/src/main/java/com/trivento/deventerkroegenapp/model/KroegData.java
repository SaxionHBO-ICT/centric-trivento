package com.trivento.deventerkroegenapp.model;

import com.trivento.deventerkroegenapp.tasks.KroegenTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Class waarin de data van de kroegen wordt bewaard
 */
public class KroegData {

    private static KroegData data = null;
    public static List<Kroeg> kroegen = new ArrayList<>();

    private KroegData(){
        searchData();
    }

    //Zoek alle kroegen uit 1 categorie
    public static void searchData(String category){
        try {
            category = URLEncoder.encode(category, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KroegenTask kroegenTask = new KroegenTask(kroegen);
        kroegenTask.execute(category);
    }

    //Zoek alle kroegen
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
