package com.mobeta.android.demodslv;

import java.util.List;
import java.util.ArrayList;

import com.mobeta.android.dslv.DragSortListView;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

public class ArbItemSizeDSLV extends ListActivity {

    private JazzAdapter adapter;
    private ArrayList<JazzArtist> mArtists;
    private String[] mArtistNames;
    private String[] mArtistAlbums;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hetero_main);

        DragSortListView lv = (DragSortListView) getListView();//ListActivity的方法
        lv.setDropListener(onDrop);
        lv.setRemoveListener(onRemove);

        //初始化数据
        mArtistNames = getResources().getStringArray(R.array.jazz_artist_names);
        mArtistAlbums = getResources().getStringArray(R.array.jazz_artist_albums);
        mArtists = new ArrayList<JazzArtist>();
        JazzArtist ja;
        for (int i = 0; i < mArtistNames.length; ++i) {
            ja = new JazzArtist();
            ja.name = mArtistNames[i];
            if (i < mArtistAlbums.length) {
                ja.albums = mArtistAlbums[i];
            } else {
                ja.albums = "No albums listed";
            }
            mArtists.add(ja);
        }
        adapter = new JazzAdapter(mArtists);
        setListAdapter(adapter);//ListActivity的方法
    }

    //bean
    private class JazzArtist {
        public String name;
        public String albums;

        @Override
        public String toString() {
            return name;
        }
    }

    //拖动的监听
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    JazzArtist item = adapter.getItem(from);
                    adapter.remove(item);
                    adapter.insert(item, to);
                }
            };
    //删除的监听
    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    adapter.remove(adapter.getItem(which));
                }
            };

    //adapter
    private class JazzAdapter extends ArrayAdapter<JazzArtist> {

        public JazzAdapter(List<JazzArtist> artists) {
            super(ArbItemSizeDSLV.this, R.layout.jazz_artist_list_item,
                    R.id.artist_name_textview, artists);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            if (v != convertView && v != null) {
                ViewHolder holder = new ViewHolder();
                TextView tv = (TextView) v.findViewById(R.id.artist_albums_textview);
                holder.albumsView = tv;
                v.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) v.getTag();
            String albums = getItem(position).albums;
            holder.albumsView.setText(albums);
            return v;
        }

        private class ViewHolder {
            public TextView albumsView;
        }
    }

}
