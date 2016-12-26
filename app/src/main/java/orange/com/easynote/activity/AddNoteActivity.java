package orange.com.easynote.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class AddNoteActivity extends BaseActivity {
    //final型常量
    private final int IMAGE = 1;

    //控件
    private TextView tvSave;
    private TextView tvAddCategory;
    private TextView tvAddImage;
    private TextView tvAddVoice;
    private ImageView ivNote;
    private EditText etTitle;
    private EditText etContent;
    private LinearLayout linerVoice;
    private TextView tvBack;

    //全局变量
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();


        tvAddImage.setOnClickListener(new AddImage());
        tvSave.setOnClickListener(new SaveNote());
        tvAddCategory.setOnClickListener(new AddCategory());
        tvAddVoice.setOnClickListener(new AddVoice());
        tvBack.setOnClickListener(new Back());

    }


    private void initView() {
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        ivNote = (ImageView) findViewById(R.id.iv_note);
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvAddCategory = (TextView) findViewById(R.id.tv_add_category);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvAddVoice = (TextView) findViewById(R.id.tv_add_voice);
        linerVoice = (LinearLayout)findViewById(R.id.ll_record);
        tvBack = (TextView)findViewById(R.id.tv_add_note_back);

        linerVoice.setVisibility(View.GONE);

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

    private void showImage(String filepath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        ivNote.setImageBitmap(bitmap);
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
           if (linerVoice.getVisibility()==View.GONE){
               linerVoice.setVisibility(View.VISIBLE);
               tvAddVoice.setText("点击保存");
           }else {
               linerVoice.setVisibility(View.GONE);
               tvAddVoice.setText(getResources().getString(R.string.record_voice));
           }
        }
    }


    //保存note
    private class SaveNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String time = System.currentTimeMillis() + "";
            String image = imagePath;
            String category = SharedPreferenceUtil.getCategory(AddNoteActivity.this);
            String voice = "";
            int collect = 0;

            boolean success = DatabaseFactory.getNoteTable(AddNoteActivity.this).
                    insertNote(title, content, time, image, voice, category, collect);

            if (success) {
                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddNoteActivity.this, "新建日记失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //返回
    private class Back implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
