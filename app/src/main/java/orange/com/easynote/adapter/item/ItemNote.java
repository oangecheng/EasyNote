package orange.com.easynote.adapter.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import orange.com.easynote.R;
import orange.com.easynote.enity.CategoryInfo;
import orange.com.easynote.enity.NoteInfo;

/**
 * Created by Orange on 2016/12/24.
 */

public class ItemNote extends LinearLayout {

    @BindView(R.id.tv_note_title)
    TextView tvNoteTitle;
    @BindView(R.id.tv_note_content)
    TextView tvNoteContent;
    @BindView(R.id.tv_note_time)
    TextView tvNoteTime;
    @BindView(R.id.iv_note_image)
    ImageView ivNoteImage;
    @BindView(R.id.iv_note_voice)
    ImageView ivNoteVoice;




    public ItemNote(Context context) {
        super(context);
        init();
    }

    public ItemNote(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemNote(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_note, this);
        ButterKnife.bind(this);
    }

    public void bind(final NoteInfo info) {

        if (tvNoteTitle.equals("")){
            tvNoteTitle.setText("无标题");
            tvNoteTitle.setTextColor(getResources().getColor(R.color.BLACK_HINT));
        }else {
            tvNoteTitle.setText(info.getTitle());
            tvNoteTitle.setTextColor(getResources().getColor(R.color.BLACK_COMMON));
        }

        tvNoteContent.setText(info.getContent());
        tvNoteTime.setText(info.getTime());
        if (!info.getImage().equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(info.getImage());
            ivNoteImage.setImageBitmap(bitmap);
        }
        if (info.getVoice().equals("")){
            ivNoteVoice.setVisibility(View.GONE);
        }else {
            ivNoteVoice.setVisibility(View.VISIBLE);
        }
    }
}
