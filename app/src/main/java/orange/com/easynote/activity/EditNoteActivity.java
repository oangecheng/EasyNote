package orange.com.easynote.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.NoteInfo;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.DialogUtil;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class EditNoteActivity extends AddNoteActivity {

    private long noteID = -1;
    private boolean isDelete = false;
    private NoteInfo info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteID = getIntent().getLongExtra(AppConstant.NOTE_ID, -1);
        showInfo(noteID);

        this.tvSave.setOnClickListener(new EditNote());
        this.ivPlayRecord.setOnClickListener(new PlayRecord());
        this.ivDeleteRecord.setOnClickListener(new DeleteVoiceListener());
        this.tvBack.setOnClickListener(new Back());

    }

    private void showInfo(long id) {
        if (id != -1) {
            info = DatabaseFactory.getNoteTable(EditNoteActivity.this).getNoteList(AppConstant.MODE_3, String.valueOf(id)).get(0);
            if (info != null) {
                this.etTitle.setText(info.getTitle());
                this.etContent.setText(info.getContent());
                if (!info.getImage().isEmpty()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(info.getImage());
                    this.ivNote.setImageBitmap(bitmap);
                    imagePath = info.getImage();
                }

                if (!info.getVoice().isEmpty()) {
                    //有录音
                    voicePath = info.getVoice();
                    rlPlayVoice.setVisibility(View.VISIBLE);
                    ivPlayRecord.setImageResource(R.mipmap.start);
                    rlRecordVoice.setVisibility(View.GONE);
                } else {
                    rlPlayVoice.setVisibility(View.GONE);
                    rlRecordVoice.setVisibility(View.GONE);
                }

                this.tvTitle.setText("修改日记");
            }
        }

        tvAddImage.setText("修改图片");
        tvAddCategory.setText("修改分类");
        tvAddVoice.setVisibility(View.GONE);
    }

    //返回退出页面
    protected void showBackDialog() {

        LayoutInflater inflater = LayoutInflater.from(EditNoteActivity.this);
        View view = inflater.inflate(R.layout.dialog_choice, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_cancel);

        final DialogUtil dialog = new DialogUtil(EditNoteActivity.this, view);

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

    //删除录音dialog
    protected void showDeleteVoiceDialog() {

        LayoutInflater inflater = LayoutInflater.from(EditNoteActivity.this);
        View view = inflater.inflate(R.layout.dialog_choice, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_cancel);

        final DialogUtil dialog = new DialogUtil(EditNoteActivity.this, view);

        tvTitle.setText("确认删除录音？");
        tvSure.setText("确定");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDelete = true;
                rlPlayVoice.setVisibility(View.GONE);
                dialog.dismiss();
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

    private class EditNote implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String title = etTitle.getText().toString();

            if (title.equals("")) {
                Toast.makeText(EditNoteActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            } else {
                String content = etContent.getText().toString();
                String category = SharedPreferenceUtil.getCategory(EditNoteActivity.this);
                if (isDelete) {
                    deleteVoice(voicePath);
                    voicePath = "";
                }

                boolean success = DatabaseFactory.getNoteTable(EditNoteActivity.this).
                        updateNote(noteID, title, content, imagePath, voicePath, category);

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

    //播放录音
    private class PlayRecord implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (isPlaying) {
                mediaPlayer = new MediaPlayer();
                ivPlayRecord.setImageResource(R.mipmap.pose);
                try {
                    mediaPlayer.setDataSource(voicePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    //播放器增加完成监听事件
                    mediaPlayer.setOnCompletionListener(new PlayComplete());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ivPlayRecord.setImageResource(R.mipmap.start);
                mediaPlayer.pause();
                mediaPlayer = null;
            }
            isPlaying = !isPlaying;

        }
    }

    private class PlayComplete implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            ivPlayRecord.setImageResource(R.mipmap.start);
        }
    }

    private class DeleteVoiceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showDeleteVoiceDialog();
        }
    }

    protected class Back implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (etTitle.getText().toString().equals(info.getTitle()) && etContent.getText().toString().equals(info.getContent())
                    && imagePath.equals(info.getImage()) && !isDelete && info.getCategory().equals(info.getCategory())) {
                finish();
            } else {
                showBackDialog();
            }

        }
    }

}
