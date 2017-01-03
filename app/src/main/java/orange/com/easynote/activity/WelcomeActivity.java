package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import orange.com.easynote.R;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent intent = new Intent();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };

        if (SharedPreferenceUtil.getFirstStartMark(WelcomeActivity.this)){
            intent.setClass(WelcomeActivity.this, FirstStartActivity.class);
        }else {
            intent.setClass(WelcomeActivity.this, MainActivity.class);
        }

        handler.sendEmptyMessageDelayed(AppConstant.MODE_0, 2000);

    }
}
