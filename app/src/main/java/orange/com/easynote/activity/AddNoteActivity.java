package orange.com.easynote.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.DialogUtil;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class AddNoteActivity extends BaseActivity {
    //final型常量
    protected final int IMAGE = 1;

    //控件
    protected TextView tvSave;
    protected TextView tvAddCategory;
    protected TextView tvAddImage;
    protected TextView tvAddVoice;
    protected ImageView ivNote;
    protected EditText etTitle;
    protected EditText etContent;
    protected TextView tvBack;
    protected TextView tvTitle;
    private RelativeLayout rlRecordVoice;
    private ImageView ivStartPose;

    //全局变量
    protected String imagePath = "";
    private boolean isRecording = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();


        tvAddImage.setOnClickListener(new AddImage());
        tvSave.setOnClickListener(new SaveNote());
        tvAddCategory.setOnClickListener(new AddCategory());
        //tvAddVoice.setOnClickListener(new AddVoice());
        tvBack.setOnClickListener(new Back());
        tvAddVoice.setOnClickListener(new AddVoice());

        ivStartPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording){
                    ivStartPose.setImageResource(R.mipmap.pose);
                }else {
                    ivStartPose.setImageResource(R.mipmap.start);
                }
               isRecording = !isRecording;
            }
        });

    }


    //初始化应用界面
    private void initView() {
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        ivNote = (ImageView) findViewById(R.id.iv_note);
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvAddCategory = (TextView) findViewById(R.id.tv_add_category);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvAddVoice = (TextView) findViewById(R.id.tv_add_voice);
        tvBack = (TextView) findViewById(R.id.tv_add_note_back);
        tvTitle = (TextView) findViewById(R.id.tv_activity_title);
        rlRecordVoice = (RelativeLayout)findViewById(R.id.rl_voice);
        ivStartPose = (ImageView)findViewById(R.id.iv_start_pose);

        tvTitle.setText("写日记");
        rlRecordVoice.setVisibility(View.GONE);
        ivStartPose.setImageResource(R.mipmap.start);
        ivNote.setImageResource(R.mipmap.default_img);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK) {

            imagePath = data.getStringExtra(AppConstant.SELECTED_IMAGE);
            showImage(imagePath);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (etTitle.getText().toString().equals("") && etContent.getText().toString().equals("")) {
                finish();
            } else {
                showBackDialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showImage(String filepath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        ivNote.setImageBitmap(bitmap);
    }

    private void showBackDialog() {

        LayoutInflater inflater = LayoutInflater.from(AddNoteActivity.this);
        View view = inflater.inflate(R.layout.dialog_choice, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_cancel);

        final DialogUtil dialog = new DialogUtil(AddNoteActivity.this, view);

        tvTitle.setText("日记未保存，确认退出？");
        tvSure.setText("确定");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * 按钮响应事件
     */

    //添加图片
    private class AddImage implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddNoteActivity.this, AlbumPhotoActivity.class);
            startActivityForResult(intent, IMAGE);
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, IMAGE);
        }
    }

    //选择分类
    private class AddCategory implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddNoteActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        }
    }

    //录音按钮
    private class AddVoice implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (rlRecordVoice.getVisibility() == View.GONE) {
                rlRecordVoice.setVisibility(View.VISIBLE);
                tvAddVoice.setText("点击取消");
            } else {
                rlRecordVoice.setVisibility(View.GONE);
                tvAddVoice.setText(getResources().getString(R.string.record_voice));
            }
        }
    }


    //保存note
    private class SaveNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String title = etTitle.getText().toString();

            if (title.equals("")) {
                Toast.makeText(AddNoteActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            } else {
                String content = etContent.getText().toString();
                String time = System.currentTimeMillis() + "";
                String category = SharedPreferenceUtil.getCategory(AddNoteActivity.this);
                String voice = "";

                boolean success = DatabaseFactory.getNoteTable(AddNoteActivity.this).
                        insertNote(title, content, time, imagePath, voice, category, AppConstant.NOTE_UNCOLLECT);

                if (success) {
                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "新建日记失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //返回
    private class Back implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (etTitle.getText().toString().equals("") && etContent.getText().toString().equals("")) {
                finish();
            } else {
                showBackDialog();
            }

        }
    }


}
