package jp.espla.sample.volley.fragment;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.espla.sample.volley.R;
import jp.espla.sample.volley.adapter.VolleyAdapter;

/**
 * Created by yt_kaga on 2013/11/20.
 */
public class MainListFragment  extends Fragment {

    private static final String TAG = "MainListFragment";
    private static final String INSTAGRAM_CLIENT_ID = "b8a416a08c5b420f9e4c4e433c1642b1";
    private static final String REQUEST_URL = "https://api.instagram.com/v1/media/popular?client_id=" + INSTAGRAM_CLIENT_ID;

    private RequestQueue mQueue;
    private GridView mGridView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        View emptyView = getView().findViewById(android.R.id.empty);
        mGridView = (GridView) getView().findViewById(R.id.grid_icon);
        mGridView.setEmptyView(emptyView);
        refreshData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQueue.cancelAll(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void refreshData() {
        JsonObjectRequest jsonRequet = new JsonObjectRequest(Method.GET, REQUEST_URL,
                null, new Listener<JSONObject>() {
            public void onResponse(JSONObject result) {
                try {
                    int code = parseJson(result);
                    if (code != 200) {
                        toast(R.string.server_error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast(R.string.json_error);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                toast(R.string.connection_error);
            }
        });
        jsonRequet.setTag(TAG);
        mQueue.add(jsonRequet);
    }

    private int parseJson(JSONObject root) throws JSONException {
        int code = root.getJSONObject("meta").getInt("code");
        if (code == 200) {
            ArrayList<String> urlLists = new ArrayList<String>();
            JSONArray arr = root.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json = arr.getJSONObject(i);
                String imgUrl = json.getJSONObject("images")
                        .getJSONObject("low_resolution").getString("url");
                urlLists.add(imgUrl);
            }

            ImageLoader imageLoader = new ImageLoader(mQueue, new ImageCache() {
                private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url, bitmap);
                }
                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }
            });
            VolleyAdapter adapter = new VolleyAdapter(
                    getActivity().getApplicationContext(), 0, imageLoader, urlLists);
            mGridView.setAdapter(adapter);
        }
        return code;
    }

    private void toast(int id) {
        String text = getResources().getString(id);
        Toast.makeText(getActivity().getApplicationContext(),
                text, Toast.LENGTH_LONG).show();
    }
}