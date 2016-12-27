package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.adapter.AlbumAdapter;
import orange.com.easynote.enity.AlbumPhoto;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.CommonFunction;

public class AlbumPhotoActivity extends BaseActivity {

    //通知handler扫描完成的消息
    private static final int SCAN_OK = 1;


    private GridView gridView;
    private TextView ivBack;

    private List<AlbumPhoto> photoList;
    private AlbumAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    setAdapter();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photo);
        initView();

        ivBack.setOnClickListener(new Back());

    }


    private void initView() {
        gridView = (GridView) findViewById(R.id.gv_album);
        ivBack = (TextView) findViewById(R.id.tv_back_album);
        initList();
    }

    private void initList() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                photoList = CommonFunction.initAlbum(AlbumPhotoActivity.this);
                handler.sendEmptyMessage(SCAN_OK);
            }
        }).start();

    }

    private void setAdapter() {
        adapter = new AlbumAdapter(AlbumPhotoActivity.this, photoList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new GridViewItemClick());
    }

    private class GridViewItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            String path = photoList.get(i).getImagePath();
            intent.putExtra(AppConstant.SELECTED_IMAGE, path);
            AlbumPhotoActivity.this.setResult(RESULT_OK, intent);
            finish();
        }
    }

    //返回
    private class Back implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

}
