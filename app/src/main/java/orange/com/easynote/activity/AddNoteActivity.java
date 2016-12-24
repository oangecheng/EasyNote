package orange.com.easynote.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.utils.AppPublicString;
import orange.com.easynote.utils.TimeFormatUtil;

public class AddNoteActivity extends BaseActivity {
    private TextView tvSave;
    private TextView tvAddCategory;
    private TextView tvAddImage;
    private TextView tvAddVoice;
    private ImageView ivNote;
    private EditText etTitle;
    private EditText etContent;

    private final int IMAGE = 1;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();


        tvAddImage.setOnClickListener(new AddImage());
        tvSave.setOnClickListener(new SaveNote());
        tvAddCategory.setOnClickListener(new AddCategory());

    }


    private void initView() {
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        ivNote = (ImageView) findViewById(R.id.iv_note);
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvAddCategory = (TextView)findViewById(R.id.tv_add_category);
        tvSave =(TextView)findViewById(R.id.tv_save);

    }


    private class AddImage implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }

    private class AddCategory implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddNoteActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        }
    }

    private class SaveNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String time = TimeFormatUtil.formatTimeStampString(AddNoteActivity.this, System.currentTimeMillis());
            String image = imagePath;
            String voice = "";
            String category = getIntent().getStringExtra(AppPublicString.CATEGORY);
            int collect = 0;

            boolean success = DatabaseFactory.getNoteTable(AddNoteActivity.this).
                    insertNote(title, content, time, image, voice, category, collect);

            if (success){
                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(AddNoteActivity.this, "新建日记失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String filePath[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePath, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePath[0]);
            imagePath = cursor.getString(columnIndex);
            showImage(imagePath);
            cursor.close();
        }
    }

    private void showImage(String filepath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        ivNote.setImageBitmap(bitmap);
    }

}
