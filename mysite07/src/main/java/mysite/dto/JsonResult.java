package mysite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
