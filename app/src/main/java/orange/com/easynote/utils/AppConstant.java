package orange.com.easynote.utils;

/**
 * Created by Orange on 2016/12/24.
 */

//app当中使用的一些常量

public class AppConstant {

    /**
     * string
     */
    public static final String CATEGORY = "category_name";
    public static final String SELECTED_IMAGE = "selected_image";
    //intent中note的id
    public static final String NOTE_ID = "note_id";


    /**
     * number
     */

    // 0查询全部， 1根据分类查询，2根据收藏查询，3根据ID查询
    public static final int MODE_0 = 0;
    public static final int MODE_1 = 1;
    public static final int MODE_2 = 2;
    public static final int MODE_3 = 3;

    //note被收藏：0表示未收藏， 1表示收藏
    public static final int NOTE_UNCOLLECT = 0;
    public static final int NOTE_COLLECT = 1;




}
