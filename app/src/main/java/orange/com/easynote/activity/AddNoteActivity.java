package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import orange.com.easynote.R;

public class AddNoteActivity extends BaseActivity {
    private TextView tvAddCategory;
    private TextView tvAddImage;
    private TextView tvAddVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();


        tvAddImage.setOnClickListener(new AddImage());

    }


    private void initView(){
        tvAddImage = (TextView)findViewById(R.id.tv_add_image);
    }


    private class AddImage implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Image"),1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
