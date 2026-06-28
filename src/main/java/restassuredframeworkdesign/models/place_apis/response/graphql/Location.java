package restassuredframeworkdesign.models.place_apis.response.graphql;

public class Location {

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    private String dimension;
    private String type;

}
