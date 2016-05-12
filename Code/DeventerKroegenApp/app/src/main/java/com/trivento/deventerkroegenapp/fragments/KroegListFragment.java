package com.trivento.deventerkroegenapp.fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.model.KroegData;
import com.trivento.deventerkroegenapp.model.KroegListArrayAdapter;

public class KroegListFragment extends ListFragment {

    public static KroegListArrayAdapter adapter;
    private Callbacks activity;

    public KroegListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new KroegListArrayAdapter(getContext(), R.layout.list_item_kroeg, KroegData.kroegen);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kroeg_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public interface Callbacks{
        //TODO add item select method parameters
        void onItemSelected();
    }

}
