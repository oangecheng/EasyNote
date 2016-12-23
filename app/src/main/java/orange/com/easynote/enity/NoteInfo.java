package orange.com.easynote.enity;

/**
 * Created by Orange on 2016/12/23.
 */

public class NoteInfo {
    private Long id;
    private String title;
    private String content;
    private String time;
    private String image;
    private String voice;
    private String category;
    private int collect;

    public NoteInfo(Long id, String title, String content, String time, String image, String voice, String category, int collect) {
        this.id = id;
        this.category = category;
        this.collect = collect;
        this.content = content;
        this.image = image;
        this.title = title;
        this.voice = voice;
        this.time = time;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
}
