package orange.com.easynote.enity;

/**
 * Created by Orange on 2016/12/26.
 */

public class AlbumPhoto {

    /**
     * 原图的存储路径
     */
    private String imagePath;

    /**
     * 图片的旋转角度
     */
    private int orientation;

    /**
     * 图片被选中
     */
    private boolean check;

    public AlbumPhoto(String imagePath, int orientation) {
        this.imagePath = imagePath;
        this.orientation = orientation;
        this.check = false;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
