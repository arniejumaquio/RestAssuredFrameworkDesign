package rahulshettyacademy.pojo_classes.place_apis.response.graphql;

import java.util.List;

public class Episodes {

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    private List<Result> result;
}
