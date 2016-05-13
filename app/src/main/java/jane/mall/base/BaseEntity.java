package jane.mall.base;

import java.io.Serializable;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/13 11:56
 *         description
 *         vsersion
 */
public class BaseEntity implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
