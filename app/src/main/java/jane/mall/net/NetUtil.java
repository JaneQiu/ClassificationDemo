package jane.mall.net;

import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import jane.mall.app.DataDictionary;
import jane.mall.util.MD5Util;
import jane.mall.util.SpCache;
import jane.mall.util.TimeFormatUtil;
import jane.mall.util.TreatmentBase64;
import jane.mall.util.log.KLog;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/5 11:55
 *         description
 *         vsersion
 */
public class NetUtil {

    private static final String TAG = NetUtil.class.getName();

    public static NetUtil getInstance() {
        return new NetUtil();
    }

    public void post(String url, LinkedHashMap paramsMap, StringCallback callback) {
        KLog.d(url);
        OkHttpUtils
                .post()
                .url(url)
                .headers(assembleHeaders(getParamsJson(paramsMap)))
                .params(paramsMap)
                .build()
                .execute(callback);
        KLog.d(url + "--------" + assembleHeaders(getParamsJson(paramsMap)));
    }

    public String getParamsJson(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        JSONObject builder = null;
        try {
            builder = new JSONObject();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (iterator.hasNext()) {
                    builder.put(entry.getKey(), entry.getValue());
                } else {
                    builder.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        KLog.d(TAG, "拼接参数：" + builder.toString());
        return TreatmentBase64.encryptBASE64(builder.toString());
    }

    public Map<String, String> assembleHeaders(String params) {
        String time = TimeFormatUtil.getTime();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        if (!TextUtils.isEmpty(SpCache.getToken())) {
            headers.put("token", SpCache.getToken());
            KLog.d(TAG, "Headers 参数：" + "token = " + SpCache.getToken());

            String signData = MD5Util.md5(ApiConstant.SECRET_KEY + SpCache.getToken() + params + time);
            KLog.d(TAG, "Headers 参数：" + "token = " + SpCache.getToken() + ",params= " + params
                    + ",time = " + time);
            headers.put("signData", signData);
            KLog.d(TAG, "Headers 参数：" + "signData = " + signData);

            headers.put("timeStamp", time);
            KLog.d(TAG, "Headers 参数：" + "timeStamp = " + time);
        } else {
            headers.put("signData", DataDictionary.getSignData());
            KLog.d(TAG, "Headers 参数：" + "signData = " + DataDictionary.getSignData());

            headers.put("timeStamp", DataDictionary.getTimestamp());
            KLog.d(TAG, "Headers 参数：" + "timeStamp = " + DataDictionary.getTimestamp());
        }

        headers.put("deviceInfo", getParameter());
//        Log.i(TAG, "assembleHeaders: " + headers.get);
        return headers;
    }

    public String getParameter() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceId", DataDictionary.getDeviceId());
            jsonObject.put("deviceType", "ANDROID");
            KLog.json(TAG, jsonObject.toString());
            return TreatmentBase64.encryptBASE64(jsonObject.toString()).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
