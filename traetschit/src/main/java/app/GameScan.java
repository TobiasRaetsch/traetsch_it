package app;

public class GameScan {
    private long id;
    private String name;
    private String platform;
    private String href;
    private String searchRequest;

    public GameScan(String pName, String pPlatform, String pHref, String pSearchRequest, long pId) {
        this.name = pName;
        this.platform = pPlatform;
        this.href = pHref;
        this.searchRequest = pSearchRequest;
        this.id = pId;
    }

    public GameScan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(String searchRequest) {
        this.searchRequest = searchRequest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrefId() {
        return this.getIdPrefix() + id;
    }

    public void setPrefId(String pPrefId) {

    }

    public void setIdPrefix(String pPrefId) {

    }

    public String getIdPrefix(){
        return "GS";
    }
}
