package orange.com.easynote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.enity.NoteInfo;

/**
 * Created by orang on 2016/12/23.
 */
public class NoteAdapter extends BaseAdapter {

    private List<NoteInfo> list = new ArrayList<>();
    private Context context;

    public NoteAdapter(Context context, List<NoteInfo> list) {
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
