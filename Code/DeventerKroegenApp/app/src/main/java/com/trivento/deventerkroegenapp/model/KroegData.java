package com.trivento.deventerkroegenapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class KroegData {

    private static KroegData data = null;
    public static List<Kroeg> kroegen = new ArrayList<>();

    private KroegData(){
        for(int i = 0; i < 10; i++){
            Kroeg kroeg = new Kroeg("Kroeg " + i);
            float rating = (i)/2F;
            Log.d("KROEG", "rating: " + rating);
            kroeg.setRating(rating);
            kroegen.add(kroeg);
        }
    }

    public static KroegData getInstance() {
        if(data == null){
            data = new KroegData();
        }
        return data;
    }

}
