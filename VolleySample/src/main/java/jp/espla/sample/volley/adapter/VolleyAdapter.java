package jp.espla.sample.volley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import jp.espla.sample.volley.R;

/**
 * Created by yt_kaga on 2013/11/20.
 */
public class VolleyAdapter extends ArrayAdapter<String> {

    private static final String TAG = "VolleyAdapter";

    private LayoutInflater mLayoutInflator;
    private ViewHolder mHolder;
    private ImageLoader mImageLoader;

    private class ViewHolder {
        public NetworkImageView iconView;
    }

    public VolleyAdapter(Context context, int resource,
                         ImageLoader imageLoader, List<String> urlLists) {
        super(context, resource, urlLists);
        mImageLoader = imageLoader;
        mLayoutInflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflator.inflate(R.layout.adapter_volley, null);
            mHolder = new ViewHolder();
            mHolder.iconView = (NetworkImageView) convertView.findViewById(R.id.ic_thumbnail);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        String imgUrl = getItem(position);
        mHolder.iconView.setImageUrl(imgUrl, mImageLoader);

        return convertView;
    }
}
