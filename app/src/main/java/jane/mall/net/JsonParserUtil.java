package jane.mall.net;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import jane.mall.app.DataDictionary;
import jane.mall.util.SpCache;
import jane.mall.util.log.KLog;

public class JsonParserUtil {
    private static final String TAG = JsonParserUtil.class.getSimpleName();
    private Context context;

    private JsonParserUtil(Context context) {
        this.context = context;
    }

    private static JsonParserUtil jsonParses = null;

    public static JsonParserUtil getInstance(Context context) {
        if (jsonParses == null) {
            jsonParses = new JsonParserUtil(context);
        }
        return jsonParses;
    }

    public DataDictionary filterCode(JSONObject jsonObject) {
        if (TextUtils.isEmpty(jsonObject.toString())) {
            return null;
        }
        DataDictionary dataDictionary = null;
        try {
            dataDictionary = new DataDictionary();
            if (jsonObject.has("code") || jsonObject.has("message")) {
                dataDictionary.setCode(jsonObject.getInt("code"));
                dataDictionary.setMessage(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataDictionary;
    }

    public DataDictionary filterStringCode(String response) {
        if (TextUtils.isEmpty(response)) {
            return null;
        }
        DataDictionary dataDictionary = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            dataDictionary = new DataDictionary();
            if (jsonObject.has("code")) {
                dataDictionary.setCode(jsonObject.getInt("code"));
                if (jsonObject.has("message")) {
                    dataDictionary.setMessage(jsonObject.getString("message"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataDictionary;
    }

    public static JSONObject getJsonObject(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            KLog.w(TAG, "checkResponse wrong ", e);
        }
        return jsonObject;
    }

    public static int filterRegisterCode(String response) {
        if (TextUtils.isEmpty(response)) {
            return -1;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("code")) {
                return jsonObject.getInt("code");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String filterMessage(String response) {
        if (TextUtils.isEmpty(response)) {
            return "返回message信息为空";
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("message")) {
                return jsonObject.getString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String filterMoney(String response) {
        if (TextUtils.isEmpty(response)) {
            return "返回message信息为空";
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("returnObject")) {
                JSONObject moneyObj = jsonObject.getJSONObject("returnObject");
                return moneyObj.getString("money");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int filterCode(String response) {
        if (TextUtils.isEmpty(response)) {
            return 0;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("returnObject")) {
                JSONObject moneyObj = jsonObject.getJSONObject("returnObject");
                return moneyObj.getInt("status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void getToken(String response) {
        if (TextUtils.isEmpty(response)) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("returnObject")) {
                SpCache.putString(SpCache.TOKEN, jsonObject.getString("returnObject"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
