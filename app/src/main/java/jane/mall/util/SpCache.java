package jane.mall.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ZHY
 *         address  https://github.com/hongyangAndroid
 *         date 2015-12-18 19:17
 *         description SP封装 （下一步改善，取出SpCaceh不行相关的功能，比如存储用户信息，注意职责单一）
 *         vsersion 1.0
 */
public class SpCache {
    public static final String TOKEN = "token";
    public static final String USERID = "uid";
    public static final String CURRENTFRAG = "currentFrag";
    public static final String ISMOBILEVALIDATE = "isMobileValidate";
    public static final String ENABLE_PAY_PWD_TIP = "enablePayPWDTip";
    public static final String USERMOBILE = "userMobile";
    public static final String LOGINTYPE = "loginType";
    public static final String THIRDLOGINTYPE = "thirdLoginType";
    public static final String SEARCH_STYLE = "search_style";
    public static final String APP_IS_NEWCOME = "isNewcome";
    public static final String IS_USER_LOGGED = "isUserLogged";

    public static final String LOGGEDUSERNAME = "loggedUsername";

    public static final String PASSWORD = "password";
    public static final String QQ_OPEN_ID = "qq_openId";
    public static final String SINA_OPEN_ID = "sina_openId";
    public static final String WEIXIN_OPEN_ID = "weixin_openId";
    public static final String TabHomePagFragmentjson = "tabhomepagfragmentjson";

    private static final String TAG = SpCache.class.getSimpleName();
    private static SpCache INSTANCE;
    private ConcurrentMap<String, SoftReference<Object>> mCache;
    private String mPrefFileName = "km_spcache";
    private Context mContext;

    private SpCache(Context context, String prefFileName) {
        mContext = context.getApplicationContext();
        mCache = new ConcurrentHashMap<String, SoftReference<Object>>();
        initDatas(prefFileName);

    }

    private void initDatas(String prefFileName) {
        if (null != prefFileName && prefFileName.trim().length() > 0) {
            mPrefFileName = prefFileName;
        } else {
            Log.d(TAG, "prefFileName is invalid , we will use default value ");
        }

    }

    public static SpCache init(Context context, String prefFileName) {
        if (INSTANCE == null) {
            synchronized (SpCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpCache(context, prefFileName);
                }
            }
        }
        return INSTANCE;
    }

    public static SpCache init(Context context) {
        return init(context, null);
    }

    private static SpCache getInstance() {
        if (INSTANCE == null)
            throw new NullPointerException("you show invoke SpCache.init() before you use it ");

        return INSTANCE;
    }


    //put
    public static SpCache putInt(String key, int val) {
        return getInstance().put(key, val);
    }

    public static SpCache putLong(String key, long val) {
        return getInstance().put(key, val);
    }

    public static SpCache putString(String key, String val) {
        return getInstance().put(key, val);
    }

    public static SpCache putBoolean(String key, boolean val) {
        return getInstance().put(key, val);
    }

    public static SpCache putFloat(String key, float val) {
        return getInstance().put(key, val);
    }


    //get
    public static int getInt(String key, int defaultVal) {
        return (int) (getInstance().get(key, defaultVal));
    }

    public static long getLong(String key, long defaultVal) {
        return (long) (getInstance().get(key, defaultVal));
    }

    public static String getString(String key, String defaultVal) {
        return (String) (getInstance().get(key, defaultVal));
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        return (boolean) (getInstance().get(key, defaultVal));
    }

    public static float getFloat(String key, float defaultVal) {
        return (float) (getInstance().get(key, defaultVal));
    }

    //contains
    public boolean contains(String key) {
        return mCache.get(key).get() != null ? true : getSharedPreferences().contains(key);
    }

    //remove
    public static SpCache remove(String key) {
        return INSTANCE._remove(key);
    }

    private SpCache _remove(String key) {
        mCache.remove(key);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    //clear
    public static SpCache clear() {
        return INSTANCE._clear();
    }

    private SpCache _clear() {
        mCache.clear();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private <T> SpCache put(String key, T t) {
        mCache.put(key, new SoftReference<Object>(t));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (t instanceof String) {
            editor.putString(key, (String) t);
        } else if (t instanceof Integer) {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Boolean) {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float) {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Long) {
            editor.putLong(key, (Long) t);
        } else {
            Log.d(TAG, "you may be put a invalid object :" + t);
            editor.putString(key, t.toString());
        }

        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }


    private Object readDisk(String key, Object defaultObject) {
        Log.e("TAG", "readDisk");
        SharedPreferences sp = getSharedPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        Log.e(TAG, "you can not read object , which class is " + defaultObject.getClass().getSimpleName());
        return null;

    }

    private Object get(String key, Object defaultVal) {
        SoftReference reference = mCache.get(key);
        Object val = null;
        if (null == reference || null == reference.get()) {
            val = readDisk(key, defaultVal);
            mCache.put(key, new SoftReference<Object>(val));
        }
        val = mCache.get(key).get();
        return val;
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(final SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }


    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
    }


    public static String Object2Base64String(Object SceneList)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    public static Object Stringbase642string(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object SceneList = objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }


    public static String getToken() {
        return getString(TOKEN, "");
    }

    public static String getUserId() {
        return getString(USERID, "");
    }

    public static boolean getSearchStyle() {
        return getBoolean(SEARCH_STYLE, true);
    }

    public static boolean getAppIsNewCome() {
        return getBoolean(APP_IS_NEWCOME, true);
    }
}
