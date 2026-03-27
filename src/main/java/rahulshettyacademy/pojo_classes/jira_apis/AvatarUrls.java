package rahulshettyacademy.pojo_classes.jira_apis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvatarUrls {

    @JsonProperty("48x48")
    private String x48;

    @JsonProperty("24x24")
    private String x24;

    @JsonProperty("16x16")
    private String x16;

    @JsonProperty("32x32")
    private String x32;


    public String getX48() {
        return x48;
    }

    public String getX24() {
        return x24;
    }

    public String getX16() {
        return x16;
    }

    public String getX32() {
        return x32;
    }


}
