package com.trivento.deventerkroegenapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.activities.KroegDetailActivity;
import com.trivento.deventerkroegenapp.model.KroegData;
import com.trivento.deventerkroegenapp.model.KroegListArrayAdapter;
import com.trivento.deventerkroegenapp.tasks.CategoryTask;

public class KroegListFragment extends ListFragment {

    public static KroegListArrayAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kroeg_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                KroegData.searchData();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                CategoryTask categoryTask = new CategoryTask(navigationView.getMenu(), getContext());
                categoryTask.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public static void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

}
