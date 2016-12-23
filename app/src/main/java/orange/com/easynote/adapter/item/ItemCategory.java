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
 * Created by Orange on 2016/12/22.
 */

public class ItemCategory extends RelativeLayout {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl)
    RelativeLayout rl;

    public ItemCategory(Context context) {
        super(context);
        init();
    }

    public ItemCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_category, this);
        ButterKnife.bind(this);
    }

    public void bind(final CategoryInfo info, int i) {
        tvTitle.setText(info.getCategoryTitle());
        tvCount.setText(info.getNoteNum() + "");
    }
}
