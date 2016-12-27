package orange.com.easynote.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import orange.com.easynote.utils.DialogUtil;
import orange.com.easynote.utils.SharedPreferenceUtil;

public class AddCategoryActivity extends BaseActivity {

    //添加category的按钮
    private RelativeLayout tvAddCategory;
    //确定按钮
    private TextView tvSure;
    //显示category的listview
    private ListView lvCategory;
    //返回
    private TextView tvBack;

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
        tvBack.setOnClickListener(new BackClick());
    }


    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    //初始化界面控件
    private void initView() {
        tvAddCategory = (RelativeLayout) findViewById(R.id.rl_1);
        lvCategory = (ListView) findViewById(R.id.lv_add_category);
        tvSure = (TextView) findViewById(R.id.tv_sure);
        tvBack = (TextView) findViewById(R.id.tv_add_category_back);

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
        TextView btnSure = (TextView) layout.findViewById(R.id.tv_dialog_sure);
        TextView btnCancel = (TextView) layout.findViewById(R.id.tv_dialog_cancel);

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
                        SharedPreferenceUtil.setCategory(AddCategoryActivity.this, title);
                        //刷新数据
                        initList();
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
            if (SharedPreferenceUtil.setCategory(AddCategoryActivity.this, selectedCategory)) {
                finish();
            }
        }
    }

    //返回按钮响应事件
    private class BackClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //ListView设置Item的单击响应事件
    private class ItemCLick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CategoryInfo info = categoryInfoList.get(i);
            selectedCategory = info.getCategoryTitle();

            if (!info.isSelected()) {
                for (CategoryInfo in : categoryInfoList) {
                    in.setSelected(false);
                }
                info.setSelected(true);
            }

            adapter.notifyDataSetChanged();

        }
    }

}
