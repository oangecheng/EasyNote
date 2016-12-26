package orange.com.easynote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.adapter.CategoryAdapter;
import orange.com.easynote.adapter.NoteAdapter;
import orange.com.easynote.database.DatabaseFactory;
import orange.com.easynote.enity.CategoryInfo;
import orange.com.easynote.enity.NoteInfo;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.DialogUtil;

public class MainActivity extends BaseActivity {

    //显示分类部分
    private LinearLayout linearLayout;
    private ListView lvCategory;
    private TextView tvCategory;
    private FrameLayout frameLayout;
    private ImageView ivMore;
    private RelativeLayout rlAllNote;
    private RelativeLayout rlMyNote;
    private TextView tvAllNoteCount;
    private TextView tvMyNoteCount;

    //添加备忘录的按钮
    private TextView tvAddNote;

    //查询按钮
    private TextView tvSave;

    //note的listview
    private ListView lvNote;

    //类别的list
    private List<CategoryInfo> categoryInfoList = new ArrayList<>();
    //日记的list
    private List<NoteInfo> noteInfoList = new ArrayList<>();

    //category的adapter
    private CategoryAdapter cateAdapter;
    //note的adapter
    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        tvCategory.setOnClickListener(new CategoryClickListener());
        tvAddNote.setOnClickListener(new AddNoteClickListener());

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    private void initView() {
        lvCategory = (ListView) findViewById(R.id.lv_category);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        frameLayout = (FrameLayout) findViewById(R.id.fl_category);
        tvAddNote = (TextView) findViewById(R.id.tv_add_btn);
        lvNote = (ListView) findViewById(R.id.lv_note);
        ivMore = (ImageView) findViewById(R.id.iv_category);
        rlAllNote = (RelativeLayout) findViewById(R.id.rl_all_note);
        rlMyNote = (RelativeLayout) findViewById(R.id.rl_my_note);
        tvAllNoteCount = (TextView) findViewById(R.id.tv_all_note_count);
        tvMyNoteCount = (TextView) findViewById(R.id.tv_my_note_count);

        frameLayout.setVisibility(View.GONE);
        initList();
    }


    private void initList() {

        //类别的list
        categoryInfoList = DatabaseFactory.getCategoryTable(MainActivity.this).getCategoryList();

        //日记的list
        noteInfoList = DatabaseFactory.getNoteTable(MainActivity.this).getNoteList(AppConstant.MODE_0, "");

        setAdapter();
    }

    private void setAdapter() {

        //category
        cateAdapter = new CategoryAdapter(MainActivity.this, categoryInfoList);
        lvCategory.setAdapter(cateAdapter);
        lvCategory.setOnItemClickListener(new CategoryItemClick());
        tvAllNoteCount.setText(DatabaseFactory.getNoteTable(MainActivity.this).getCountByCategory(AppConstant.MODE_0, "") + "");
        tvMyNoteCount.setText(DatabaseFactory.getNoteTable(MainActivity.this).getCountByCategory(AppConstant.MODE_2, "") + "");


        //note
        noteAdapter = new NoteAdapter(MainActivity.this, noteInfoList);
        lvNote.setAdapter(noteAdapter);
        lvNote.setOnItemLongClickListener(new DeleteNote());


    }


    private void showDialog(final long id) {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.dialog_choice, null);

        TextView tvSure = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_dialog_deleteNote_cancel);

        final DialogUtil dialog = new DialogUtil(MainActivity.this, view);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseFactory.getNoteTable(MainActivity.this).deleteNote(id);
                initList();
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

    /**
     * 按钮响应事件
     */

    //查看备忘录分类按钮响应事件
    private class CategoryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //变量，控制FrameLayout是否显示
            if (frameLayout.getVisibility() == View.GONE) {
                frameLayout.setVisibility(View.VISIBLE);
                ivMore.setBackgroundResource(R.mipmap.less);
            } else {
                frameLayout.setVisibility(View.GONE);
                ivMore.setBackgroundResource(R.mipmap.more);
            }
        }
    }

    //全部日记按钮
    private class AllNoteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            initList();
            tvCategory.setText("全部日记");
        }
    }

    //我的收藏
    private class MyNoteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }


    //分类list的item响应事件
    private class CategoryItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this, i + "", Toast.LENGTH_SHORT).show();

            String title = categoryInfoList.get(i).getCategoryTitle();
            tvCategory.setText(title);

            if (title.equals("全部备忘录")) {
                initList();
            } else if (title.equals("我的收藏")) {

            } else {
                noteInfoList = DatabaseFactory.getNoteTable(MainActivity.this).getNoteList(1, title);
                setAdapter();
            }
        }
    }

    //添加备忘录按钮响应
    private class AddNoteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        }
    }


    //长按删除note
    private class DeleteNote implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            showDialog(noteInfoList.get(i).getId());

            return false;
        }
    }

}
