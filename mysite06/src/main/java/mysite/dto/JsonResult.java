package mysite.dto;

public class JsonResult {
    private String result;
    private Object data;
    private String message;

    private JsonResult() {
    }

    public static JsonResult success(Object data, String message) {
        JsonResult result = new JsonResult();
        result.setResult("success");
        result.setData(data);
        result.setMessage(message);

        return result;
    }

    public static JsonResult fail(String message) {
        JsonResult result = new JsonResult();
        result.setResult("fail");
        result.setMessage(message);

        return result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
