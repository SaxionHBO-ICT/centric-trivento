package com.trivento.deventerkroegenapp.model;

import com.trivento.deventerkroegenapp.connections.ConnectionTask;

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
        ConnectionTask connectionTask = new ConnectionTask(kroegen);
        connectionTask.execute(category);
    }

    public void searchData(){
        ConnectionTask connectionTask = new ConnectionTask(kroegen);
        connectionTask.execute();
    }

    public static KroegData getInstance() {
        if(data == null){
            data = new KroegData();
        }
        return data;
    }

}
