package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.adapter.CategoryAdapter;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.CategoryInfo;
import orange.com.easynote.enity.NoteInfo;

public class MainActivity extends BaseActivity {

    //显示分类部分
    private LinearLayout linearLayout;
    private ListView lvCategory;
    private TextView tvCategory;
    private FrameLayout frameLayout;

    //添加备忘录的按钮
    private TextView tvAddNote;

    //查询按钮
    private TextView tvSelect;

    private List<CategoryInfo> list = new ArrayList<>();
    private CategoryAdapter cateAdapter;

    //变量，控制FrameLayout是否显示
    private boolean showFrameLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setAdapter();


        tvSelect = (TextView)findViewById(R.id.tv_more);

        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<NoteInfo> info = new ArrayList<NoteInfo>();
                info = DatabaseFactory.getNoteTable(MainActivity.this).getNoteList("444");
                String i = info.get(0).getTitle();
            }
        });

        tvCategory.setOnClickListener(new CategoryClickListener());
        tvAddNote.setOnClickListener(new AddNoteClickListener());


    }

    private void initView() {
        lvCategory = (ListView) findViewById(R.id.lv_category);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        frameLayout = (FrameLayout) findViewById(R.id.fl_category);
        tvAddNote = (TextView)findViewById(R.id.tv_add_btn);
        frameLayout.setVisibility(View.GONE);
        initList();
    }

    private void initList() {
        CategoryInfo info = new CategoryInfo("1233", 1, 1);
        list.add(info);
        CategoryInfo info1 = new CategoryInfo("4566", 1, 1);
        list.add(info1);
    }

    private void setAdapter() {
        cateAdapter = new CategoryAdapter(MainActivity.this, list);
        lvCategory.setAdapter(cateAdapter);
        lvCategory.setOnItemClickListener(new CategoryItemClick());
    }

    //查看备忘录分类按钮响应事件
    private class CategoryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showFrameLayout = !showFrameLayout;

            if (showFrameLayout) {
                frameLayout.setVisibility(View.VISIBLE);
            } else {
                frameLayout.setVisibility(View.GONE);
            }
        }
    }

    //分类list的item响应事件
    private class CategoryItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this, i + "", Toast.LENGTH_SHORT).show();
            tvCategory.setText(list.get(i).getCategoryTitle());
        }
    }

    //添加备忘录按钮响应
    private class AddNoteClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        }
    }



}
