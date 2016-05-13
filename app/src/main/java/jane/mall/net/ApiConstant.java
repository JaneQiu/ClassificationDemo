package jane.mall.net;


public class ApiConstant {


    public static final int UNLAWFUL_CODE = -100;
    public static final int TOKEN_INVALID_CODE = -101;
    public static final int LONGIN_TOKEN_INVALID_CODE = -102;
    public static final int TOKEN_OK = 200;
    public static final int GET_VERIFIED_MSG_OR_EMAIL_FREQUENT = 204;
    public static final int OK = 200;
    public static final String PAYCALLBack_HOST = "http://testpay5.km1818.com/";//预发布 支付
    //    public static final String SEARCH = "http://10.1.6.115:8081/app/";
    public static final String SEARCH = "http://appsearch.km1818.com/app/";
    public static String HOST = "http://10.1.6.111:8080/app/";   //测试环境
    //  public static final String SEARCH = "http://appsearch.km1818.com/app/";
    public final static String SECRET_KEY = "2E2E4786E4D4767DD7CD1804551AB980";

    public final static String INDEX_INFO = HOST + "getIndexInfo.action";


    /**
     * 获取分类信息列表
     */

    public static final String CATEGORY_LIST = HOST + "getCategoryList.action";

    public static final String GET_TOKEN = HOST + "genToken.action";
}
