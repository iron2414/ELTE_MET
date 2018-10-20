package util.Response;

public class DataResponse extends AbstractResponse {
    private Object data;


    public DataResponse(Object data) {
        this.data = data;
        this.setSuccess(true);
    }

    public Object getData() {
        return data;
    }
}