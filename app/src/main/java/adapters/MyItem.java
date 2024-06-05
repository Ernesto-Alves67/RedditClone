package adapters;

public class MyItem {
    private String title;
    private String description;

    public MyItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
