package orange.com.easynote.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.NoteInfo;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.CommonFunction;
import orange.com.easynote.utils.TimeFormatUtil;

public class DetailNoteActivity extends BaseActivity {

    private TextView tvTitle;
    private TextView tvEdit;
    private TextView tvContent;
    private TextView tvPlay;
    private TextView tvBack;
    private TextView tvTime;
    private RelativeLayout rlRecord;
    private ImageView ivImage;
    private ImageView ivCollect;

    private long noteID = -1;
    private boolean isCollect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        //获取到intent传过来的note_id
        noteID = getIntent().getLongExtra(AppConstant.NOTE_ID, -1);
        initView();

        ivCollect.setOnClickListener(new CollectNote());
        tvEdit.setOnClickListener(new EditNote());
        tvBack.setOnClickListener(new Back());

    }

    private void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvEdit = (TextView) findViewById(R.id.tv_edit_note);
        tvContent = (TextView) findViewById(R.id.tv_detail_content);
        tvPlay = (TextView) findViewById(R.id.tv_detail_play);
        tvBack = (TextView) findViewById(R.id.tv_back_detail);
        tvTime = (TextView) findViewById(R.id.tv_detail_time);
        rlRecord = (RelativeLayout) findViewById(R.id.rl_7);
        ivImage = (ImageView) findViewById(R.id.iv_detail_image);
        ivCollect = (ImageView) findViewById(R.id.iv_detail_collect);
        showInfo(noteID);

    }

    //将查到的信息显示到控件当中
    private void showInfo(long id) {

        NoteInfo info = null;

        if (id != -1) {
            info = DatabaseFactory.getNoteTable(DetailNoteActivity.this).getNoteList(AppConstant.MODE_3, id + "").get(0);
        }

        if (info != null) {

            tvTitle.setText(info.getTitle());
            tvTime.setText(TimeFormatUtil.formatTimeStampString(DetailNoteActivity.this, Long.parseLong(info.getTime())));

            if (!info.getContent().equals("")) {
                tvContent.setText(info.getContent());
                tvContent.setTextColor(getResources().getColor(R.color.BLUE_COMMON));
            }

            if (info.getImage().equals("")) {
                ivImage.setVisibility(View.GONE);
            } else {

                ivImage.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(info.getImage());
                ivImage.setImageBitmap(bitmap);
            }


            if (info.getVoice().equals("")) {
                rlRecord.setVisibility(View.GONE);
            } else {
                rlRecord.setVisibility(View.VISIBLE);
            }

            if (info.getCollect() == AppConstant.NOTE_COLLECT) {
                ivCollect.setBackgroundResource(R.mipmap.collect);
                isCollect = true;
            } else {
                ivCollect.setBackgroundResource(R.mipmap.uncollect);
                isCollect = false;
            }
        }
    }

    //收藏的按钮响应事件
    private class CollectNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int c;

            if (!isCollect) {
                ivCollect.setBackgroundResource(R.mipmap.collect);
                c = AppConstant.NOTE_COLLECT;
                Toast.makeText(DetailNoteActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                ivCollect.setBackgroundResource(R.mipmap.uncollect);
                c = AppConstant.NOTE_UNCOLLECT;
                Toast.makeText(DetailNoteActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
            }
            isCollect = !isCollect;

            try {
                DatabaseFactory.getNoteTable(DetailNoteActivity.this).updateNoteCollect(noteID, c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //返回响应事件
    private class Back implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //单击编辑按钮进入编辑页面
    private class EditNote implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(DetailNoteActivity.this, EditNoteActivity.class);
            intent.putExtra(AppConstant.NOTE_ID, noteID);
            startActivity(intent);
        }
    }
}
