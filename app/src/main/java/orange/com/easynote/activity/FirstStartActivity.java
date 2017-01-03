package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;

import orange.com.easynote.R;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class FirstStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        DatabaseFactory.getCategoryTable(FirstStartActivity.this).insertCategory("其他分类");

        SharedPreferenceUtil.setFirstStartMark(FirstStartActivity.this, false);

        startActivity(new Intent(FirstStartActivity.this, MainActivity.class));
        finish();
    }
}
