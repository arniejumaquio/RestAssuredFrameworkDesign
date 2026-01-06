package rahulshettyacademy.pojo_classes.place_apis.response.graphql;

import rahulshettyacademy.pojo_classes.place_apis.response.Location;

import java.util.List;

public class Character {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    private String status;
    private Location location;
    private List<Episodes> episodes;


}
