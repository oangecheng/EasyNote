package orange.com.easynote.enity;

/**
 * Created by Orange on 2016/12/22.
 */

public class CategoryInfo {

    private String categoryTitle;
    private int noteNum;
    private long categoryID;

    public CategoryInfo(String categoryTitle, int noteNum, long categoryID) {
        this.categoryTitle = categoryTitle;
        this.noteNum = noteNum;
        this.categoryID = categoryID;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getNoteNum() {
        return noteNum;
    }

    public void setNoteNum(int noteNum) {
        this.noteNum = noteNum;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
