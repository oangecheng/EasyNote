package orange.com.easynote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import orange.com.easynote.adapter.item.ItemAddCategory;
import orange.com.easynote.adapter.item.ItemCategory;
import orange.com.easynote.enity.CategoryInfo;

/**
 * Created by Orange on 2016/12/24.
 */

public class AddCategoryAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryInfo> list;

    public AddCategoryAdapter(List<CategoryInfo> list, Context context) {
        this.list = list;
        this.context = context;
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

        CategoryInfo info = list.get(i);
        ItemAddCategory itemAddCategory;
        if (view == null) {
            itemAddCategory = new ItemAddCategory(context);
        } else {
            itemAddCategory = (ItemAddCategory) view;
        }
        itemAddCategory.bind(info);
        itemAddCategory.setTag(info);
        return itemAddCategory;
    }
}
