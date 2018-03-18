package projectzero.model;

public class AppResponse {

    private Object response;
    private Exception error;

    public AppResponse(Object response, Exception error) {
        this.response = response;
        this.error = error;
    }

    public AppResponse(Object response) {
        this.response = response;
    }

    public AppResponse(Exception error) {
        this.error = error;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }
}
