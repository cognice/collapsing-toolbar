package us.cognice.android.collapsing.toolbar;

/**
 * Created by Kirill Simonov on 11.10.2017.
 */
public class Item {

    private String id;
    private String name;
    private String message;

    public Item(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public Item(String id) {
        this(id, "", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
