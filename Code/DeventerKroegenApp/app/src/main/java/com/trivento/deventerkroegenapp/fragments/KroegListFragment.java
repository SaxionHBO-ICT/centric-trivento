package com.trivento.deventerkroegenapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.activities.KroegDetailActivity;
import com.trivento.deventerkroegenapp.model.KroegData;
import com.trivento.deventerkroegenapp.model.KroegListArrayAdapter;

public class KroegListFragment extends ListFragment {

    public static KroegListArrayAdapter adapter;

    public KroegListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new KroegListArrayAdapter(getContext(), R.layout.list_item_kroeg, KroegData.kroegen);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getContext(), KroegDetailActivity.class);
        intent.putExtra("KROEG", KroegData.kroegen.get(position));
        startActivity(intent);
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

    public static void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

}
