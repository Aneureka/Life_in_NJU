package leftovers.model;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Hiki on 2017/6/11.
 */

public class SimpleAlgorithm {

    private String algoId;

    private String algoName;

    private String username;

    private String time;


    public SimpleAlgorithm() {
    }

    public SimpleAlgorithm(String algoId, String algoName, String username, String time) {
        this.algoId = algoId;
        this.algoName = algoName;
        this.username = username;
        this.time = time;
    }

    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
