package Model;

public class Project {
    private int serialNum;
    private int amountPledged;
    private String blurb;
    private String writtenBy;
    private String country;
    private String currency;
    private String endTime;
    private String location;
    private int percentage;
    private int numOfBackers;
    private String state;
    private String title;
    private String type;
    private String url;

    public Project() {
    }

    public Project(int serialNum, int amountPledged, String blurb, String writtenBy, String country, String currency, String endTime, String location, int percentage, int numOfBackers, String state, String title, String type, String url) {
        this.serialNum = serialNum;
        this.amountPledged = amountPledged;
        this.blurb = blurb;
        this.writtenBy = writtenBy;
        this.country = country;
        this.currency = currency;
        this.endTime = endTime;
        this.location = location;
        this.percentage = percentage;
        this.numOfBackers = numOfBackers;
        this.state = state;
        this.title = title;
        this.type = type;
        this.url = url;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getAmountPledged() {
        return amountPledged;
    }

    public void setAmountPledged(int amountPledged) {
        this.amountPledged = amountPledged;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getNumOfBackers() {
        return numOfBackers;
    }

    public void setNumOfBackers(int numOfBackers) {
        this.numOfBackers = numOfBackers;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
