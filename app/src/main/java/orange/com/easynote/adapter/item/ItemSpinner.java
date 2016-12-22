package orange.com.easynote.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import orange.com.easynote.R;
import orange.com.easynote.enity.CategoryInfo;

/**
 * Created by Orange on 2016/12/22.
 */

public class ItemSpinner extends RelativeLayout {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl)
    RelativeLayout rl;

    public ItemSpinner(Context context) {
        super(context);
        init();
    }

    public ItemSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_spinner, this);
        ButterKnife.bind(this);
    }

    public void bind(CategoryInfo info, int i) {
        tvTitle.setText(info.getCategoryTitle());
        if (i==0){
            tvCount.setVisibility(View.GONE);
            rl.setBackgroundResource(R.color.BLUE_COMMON);
        }else {
            tvCount.setText(info.getNoteNum()+"");
        }

    }
}
