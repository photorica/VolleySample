package jp.espla.sample.volley.fragment;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import jp.espla.sample.volley.R;

/**
 * Created by yt_kaga on 2013/11/20.
 */
public class MainListFragment  extends ListFragment {

    public static final String[] ITEMS = {"1", "2", "3", "4", "5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ITEMS);
        setListAdapter(adapter);
    }
}