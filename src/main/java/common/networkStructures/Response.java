package common.networkStructures;

import java.io.Serializable;

public class Response implements Serializable {
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Response(String output) {
        this.output = output;
    }
}
