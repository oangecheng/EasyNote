package orange.com.easynote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import orange.com.easynote.adapter.item.ItemSpinner;
import orange.com.easynote.enity.CategoryInfo;

/**
 * Created by Orange on 2016/12/22.
 */

public class SpinnerAdapter extends BaseAdapter {

    private List<CategoryInfo> categoryInfoList;
    private Context context;

    public SpinnerAdapter(Context context, List<CategoryInfo> categoryInfoList) {
        this.categoryInfoList = categoryInfoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categoryInfoList.get(i).getCategoryID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CategoryInfo info = categoryInfoList.get(i);

        ItemSpinner itemSpinner;

        if (view == null) {
            itemSpinner = new ItemSpinner(context);
        } else {
            itemSpinner = (ItemSpinner) view;
        }
        itemSpinner.bind(info, i);
        itemSpinner.setTag(info);
        return itemSpinner;

    }
}
