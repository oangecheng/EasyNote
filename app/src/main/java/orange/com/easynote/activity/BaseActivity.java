package orange.com.easynote.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}
