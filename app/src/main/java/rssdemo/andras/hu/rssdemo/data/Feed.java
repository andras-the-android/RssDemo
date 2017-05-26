package rssdemo.andras.hu.rssdemo.data;

import java.util.List;

public class Feed {

    private String description;
    private List<FeedItem> items;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FeedItem> getItems() {
        return items;
    }

    public void setItems(List<FeedItem> items) {
        this.items = items;
    }
}
