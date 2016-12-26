package orange.com.easynote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import orange.com.easynote.adapter.item.ItemAlbum;
import orange.com.easynote.enity.AlbumPhoto;

/**
 * Created by Orange on 2016/12/26.
 */

public class AlbumAdapter extends BaseAdapter {

    private List<AlbumPhoto> list;
    private Context context;

    public AlbumAdapter(Context context, List<AlbumPhoto> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlbumPhoto photo = list.get(i);
        ItemAlbum itemAlbum;
        if (view==null){
            itemAlbum = new ItemAlbum(context);
        }else {
            itemAlbum = (ItemAlbum)view;
        }

        itemAlbum.bind(photo);
        itemAlbum.setTag(photo);
        return itemAlbum;
    }
}
