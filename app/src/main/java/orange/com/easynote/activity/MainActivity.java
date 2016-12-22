package orange.com.easynote.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.adapter.SpinnerAdapter;
import orange.com.easynote.enity.CategoryInfo;

public class MainActivity extends Activity {

    private Spinner spinner;
    private List<CategoryInfo> list = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        CategoryInfo info = new CategoryInfo("1233", 1, 1);
        list.add(info);
        CategoryInfo info1 = new CategoryInfo("4566", 1, 1);
        list.add(info1);
        spinnerAdapter = new SpinnerAdapter(MainActivity.this, list);

        spinner.setAdapter(spinnerAdapter);


    }

    private void initView(){
        spinner = (Spinner)findViewById(R.id.sp_selectNote);
    }

}
