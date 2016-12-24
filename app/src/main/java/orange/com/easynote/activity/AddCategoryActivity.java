package orange.com.easynote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.adapter.AddCategoryAdapter;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.CategoryInfo;
import orange.com.easynote.utils.AppPublicString;
import orange.com.easynote.utils.DialogUtil;

public class AddCategoryActivity extends Activity {

    //添加category的按钮
    private RelativeLayout tvAddCategory;
    //确定按钮
    private TextView tvSure;
    //显示category的listview
    private ListView lvCategory;

    private List<CategoryInfo> categoryInfoList;

    private AddCategoryAdapter adapter;


    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initView();

        tvAddCategory.setOnClickListener(new AddCategory());
        tvSure.setOnClickListener(new SureListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    //初始化界面控件
    private void initView() {
        tvAddCategory = (RelativeLayout)findViewById(R.id.rl_1);
        lvCategory = (ListView) findViewById(R.id.lv_add_category);
        tvSure = (TextView) findViewById(R.id.tv_sure);

        initList();
    }

    //初始化list
    private void initList() {
        categoryInfoList = DatabaseFactory.getCategoryTable(AddCategoryActivity.this).getCategoryList();
        setAdapter();
    }

    //ListView设置Adapter
    private void setAdapter() {
        adapter = new AddCategoryAdapter(categoryInfoList, AddCategoryActivity.this);
        lvCategory.setAdapter(adapter);
        lvCategory.setOnItemClickListener(new ItemCLick());
    }


    //显示添加category的dialog
    private void showAddCategoryDialog() {

        LayoutInflater inflater = LayoutInflater.from(AddCategoryActivity.this);
        View layout = inflater.inflate(R.layout.dialog_edit, null);

        final EditText editText = (EditText) layout.findViewById(R.id.et_category_title);
        Button btnSure = (Button) layout.findViewById(R.id.btn_sure);
        Button btnCancel = (Button) layout.findViewById(R.id.btn_cancel);

        final DialogUtil dialog = new DialogUtil(AddCategoryActivity.this, layout, true);

        //确定
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success;
                String title = editText.getText().toString();
                if (title.equals("")) {
                    Toast.makeText(AddCategoryActivity.this, R.string.no_content, Toast.LENGTH_SHORT).show();
                } else if (title.contains(" ")) {
                    Toast.makeText(AddCategoryActivity.this, R.string.contains_backspace, Toast.LENGTH_SHORT).show();
                } else if (DatabaseFactory.getCategoryTable(AddCategoryActivity.this).categoryExist(title)) {
                    Toast.makeText(AddCategoryActivity.this, R.string.category_exist, Toast.LENGTH_SHORT).show();
                } else {
                    //插入数据库
                    success = DatabaseFactory.getCategoryTable(AddCategoryActivity.this).insertCategory(title);
                    if (success) {
                        Toast.makeText(AddCategoryActivity.this, R.string.add_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCategoryActivity.this, R.string.add_failed, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            }
        });

        //取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //添加分类的按钮响应事件
    private class AddCategory implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showAddCategoryDialog();
        }
    }

    //确定按钮的响应事件
    private class SureListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddCategoryActivity.this, AddNoteActivity.class);
            if (selectedCategory.equals("")) {
                selectedCategory = "未分类";
            }
            intent.putExtra(AppPublicString.CATEGORY, selectedCategory);
            startActivity(intent);
        }
    }

    //ListView设置Item的单击响应事件
    private class ItemCLick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCategory = categoryInfoList.get(i).getCategoryTitle();
        }
    }

}
