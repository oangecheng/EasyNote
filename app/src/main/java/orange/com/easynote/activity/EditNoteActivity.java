package orange.com.easynote.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.NoteInfo;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class EditNoteActivity extends AddNoteActivity {

    private long noteID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteID = getIntent().getLongExtra(AppConstant.NOTE_ID, -1);
        showInfo(noteID);

        this.tvSave.setOnClickListener(new EditNote());


    }

    private void showInfo(long id) {
        NoteInfo info = null;
        if (id != -1) {
            info = DatabaseFactory.getNoteTable(EditNoteActivity.this).getNoteList(AppConstant.MODE_3, String.valueOf(id)).get(0);
            if (info != null) {
                this.etTitle.setText(info.getTitle());
                this.etContent.setText(info.getContent());
                if (!info.getImage().isEmpty()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(info.getImage());
                    this.ivNote.setImageBitmap(bitmap);
                }

                if (!info.getVoice().isEmpty()) {
                    //有录音
                } else {
                    //没录音
                }

                this.tvTitle.setText("修改日记");
            }
        }

        tvAddImage.setText("修改图片");
        tvAddCategory.setText("修改分类");
    }

    private class EditNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String title = etTitle.getText().toString();

            if (title.equals("")) {
                Toast.makeText(EditNoteActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            } else {
                String content = etContent.getText().toString();
                String category = SharedPreferenceUtil.getCategory(EditNoteActivity.this);
                String voice = "";

                boolean success = DatabaseFactory.getNoteTable(EditNoteActivity.this).
                        updateNote(noteID, title, content, imagePath, voice, category);

                if (success) {
                    Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditNoteActivity.this, "修改日记失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
