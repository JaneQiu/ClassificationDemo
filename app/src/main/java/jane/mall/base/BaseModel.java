package jane.mall.base;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 15:57
 *         description
    *         vsersion
    */
    public class BaseModel {

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


    @Override
    public String toString() {
        return "BaseEntity{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
