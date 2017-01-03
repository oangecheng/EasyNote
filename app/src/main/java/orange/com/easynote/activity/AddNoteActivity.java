package orange.com.easynote.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.CommonFunction;
import orange.com.easynote.utils.DialogUtil;
import orange.com.easynote.utils.FileUtil;
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
    protected RelativeLayout rlRecordVoice;
    protected RelativeLayout rlPlayVoice;
    protected TextView tvRecordVoice;

    protected ImageView ivPlayRecord;
    protected ImageView ivDeleteRecord;
    //全局变量
    protected String imagePath = "";
    protected String voicePath = "";
    private TextView tvRecordTime;
    //语音操作对象，MediaRecorder为录音，MediaPlayer为播放
    protected MediaRecorder mediaRecorder = null;
    protected MediaPlayer mediaPlayer = null;
    //判断是否为长按的标记
    private int isLongClick = 0;
    //记录开始的时间
    private long startTime = 0;
    //录音的时间长度
    private int voiceTime = 0;
    protected boolean isPlaying = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();


        //添加图片
        tvAddImage.setOnClickListener(new AddImage());
        //保存
        tvSave.setOnClickListener(new SaveNote());
        //修改分类
        tvAddCategory.setOnClickListener(new AddCategory());
        //tvAddVoice.setOnClickListener(new AddVoice());
        //返回
        tvBack.setOnClickListener(new Back());
        //添加录音
        tvAddVoice.setOnClickListener(new AddVoice());

        //长按开始录音
        tvRecordVoice.setOnLongClickListener(new StartRecord());
        tvRecordVoice.setOnClickListener(new StopRecord());

        //播放录音
        ivPlayRecord.setOnClickListener(new PlayRecord());
        //删除录音
        ivDeleteRecord.setOnClickListener(new DeleteRecord());

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
        rlRecordVoice = (RelativeLayout) findViewById(R.id.rl_voice);
        rlPlayVoice = (RelativeLayout) findViewById(R.id.rl_play_voice);
        tvRecordVoice = (TextView) findViewById(R.id.tv_start_record);
        ivPlayRecord = (ImageView) findViewById(R.id.iv_play_pause_record);
        ivDeleteRecord = (ImageView) findViewById(R.id.iv_delete_record);
        tvRecordTime = (TextView) findViewById(R.id.tv_record_time);

        tvTitle.setText("写日记");
        rlRecordVoice.setVisibility(View.GONE);
        rlPlayVoice.setVisibility(View.GONE);
        ivNote.setImageResource(R.mipmap.default_img);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    //返回结果
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

    //返回退出页面
    protected void showBackDialog() {

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
                if (!voicePath.isEmpty()){
                    deleteVoice(voicePath);
                }
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

        LayoutInflater inflater = LayoutInflater.from(AddNoteActivity.this);
        View view = inflater.inflate(R.layout.dialog_choice, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_cancel);

        final DialogUtil dialog = new DialogUtil(AddNoteActivity.this, view);

        tvTitle.setText("确认删除录音？");
        tvSure.setText("确定");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVoice(voicePath);
                voicePath = "";
                rlPlayVoice.setVisibility(View.GONE);
                tvAddVoice.setClickable(true);
                tvAddVoice.setTextColor(getResources().getColor(R.color.WHITE_COMMON));
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

    //获取转存之后的图片路径
    private String getImagePath(String imagePath) {
        if (imagePath.isEmpty()) {
            return "";
        }

        File oldFile = new File(imagePath);
        String imageName = oldFile.getName();

        File newFile = FileUtil.writeImageFile(AddNoteActivity.this, imageName);
        FileUtil.copyFile(oldFile, newFile);

        return newFile.getAbsolutePath();
    }


    /**
     * 判断录音时间够不够2s
     *
     * @param startTime
     * @return
     */
    private boolean checkRecordTime(long startTime) {

        if (System.currentTimeMillis() - startTime > 2000) {

            //获取录音的时间长度
            voiceTime = Math.round((System.currentTimeMillis() - startTime) / 1000);
            return true;
        }
        return false;
    }

    /**
     * 不够2s取消录音并且删除
     *
     * @param path
     */
    protected void deleteVoice(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
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
            } else {
                rlRecordVoice.setVisibility(View.GONE);
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
                String voice = voicePath;
                String image = getImagePath(imagePath);

                boolean success = DatabaseFactory.getNoteTable(AddNoteActivity.this).
                        insertNote(title, content, time, image, voice, category, AppConstant.NOTE_UNCOLLECT);

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
    protected class Back implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (etTitle.getText().toString().equals("") && etContent.getText().toString().equals("")
                    && imagePath.equals("") && voicePath.equals("")) {
                finish();
            } else {
                showBackDialog();
            }

        }
    }


    //录音按钮响应事件
    private class StartRecord implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {

            File file = FileUtil.writeVoiceFile(AddNoteActivity.this, System.currentTimeMillis() + FileUtil.VOICE_FORMAT);
            voicePath = file.getAbsolutePath();

            startTime = System.currentTimeMillis();
            isLongClick = 1;
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(voicePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();
            tvRecordVoice.setText("正  在  录  音");
            return false;
        }
    }

    //录音完成之后执行
    private class StopRecord implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (isLongClick == 1) {

                if (checkRecordTime(startTime)) {
                    rlRecordVoice.setVisibility(View.GONE);
                    rlPlayVoice.setVisibility(View.VISIBLE);
                    tvAddVoice.setClickable(false);
                    tvAddVoice.setTextColor(getResources().getColor(R.color.DEFINED_GREY));
                    tvRecordTime.setText(CommonFunction.showTime(voiceTime));
                    ivPlayRecord.setImageResource(R.mipmap.start);
                } else {
                    Toast.makeText(AddNoteActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                    deleteVoice(voicePath);
                    voicePath = "";
                    tvRecordVoice.setText("按  住  录  音");
                }

                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                isLongClick = 0;

            }
        }
    }


    //播放录音
    private class PlayRecord implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (isPlaying){
                mediaPlayer = new MediaPlayer();
                ivPlayRecord.setImageResource(R.mipmap.pose);
                try {
                    mediaPlayer.setDataSource(voicePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    //播放器增加完成监听事件
                    mediaPlayer.setOnCompletionListener(new RecordComplete());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                ivPlayRecord.setImageResource(R.mipmap.start);
                mediaPlayer.pause();
                mediaPlayer = null;
            }
            isPlaying = !isPlaying;

        }
    }

    //删除录音
    private class DeleteRecord implements View.OnClickListener {
        @Override
        public void onClick(View view) {
          showDeleteVoiceDialog();
        }
    }

    //录音播放完成之后
    private class RecordComplete implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            ivPlayRecord.setImageResource(R.mipmap.start);
        }
    }


}
