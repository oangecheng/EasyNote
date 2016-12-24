package orange.com.easynote.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import orange.com.easynote.R;
import orange.com.easynote.enity.CategoryInfo;

/**
 * Created by Orange on 2016/12/24.
 */

public class ItemAddCategory extends RelativeLayout {

    @BindView(R.id.tv_category_title)
    TextView tvTitle;

    public ItemAddCategory(Context context) {
        super(context);
        init();
    }

    public ItemAddCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemAddCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_add_category, this);
        ButterKnife.bind(this);
    }

    public void bind(final CategoryInfo info) {
        tvTitle.setText(info.getCategoryTitle());
    }

}
