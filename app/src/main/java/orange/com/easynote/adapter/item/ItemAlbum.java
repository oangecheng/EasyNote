package orange.com.easynote.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import orange.com.easynote.R;
import orange.com.easynote.enity.AlbumPhoto;
import orange.com.easynote.utils.CommonFunction;

/**
 * Created by Orange on 2016/12/26.
 */

public class ItemAlbum extends RelativeLayout {

    @BindView(R.id.iv_album_item)
    ImageView iv;

    public ItemAlbum(Context context) {
        super(context);
        init();
    }

    public ItemAlbum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemAlbum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.item_album, this);
        ButterKnife.bind(this);
    }

    public void bind(AlbumPhoto photo){

        CommonFunction.showImage(getContext(), iv, photo.getImagePath());

    }
}
