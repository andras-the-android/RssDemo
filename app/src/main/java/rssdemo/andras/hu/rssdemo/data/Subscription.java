package rssdemo.andras.hu.rssdemo.data;


public class Subscription {

    private String name = "";
    private String url = "";

    public Subscription(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Subscription(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
