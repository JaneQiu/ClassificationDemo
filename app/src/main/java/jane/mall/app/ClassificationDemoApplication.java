package jane.mall.app;

import android.app.Application;

import jane.mall.util.DeviceHelper;
import jane.mall.util.MD5Util;
import jane.mall.util.SpCache;
import jane.mall.util.TimeFormatUtil;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/6 18:16
 *         description
 *         vsersion
 */
public class ClassificationDemoApplication extends Application {

    private static ClassificationDemoApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        DataDictionary.setDevuceId(DeviceHelper.getInstance(getApplicationContext()).getImei());
        DataDictionary.setTimestamp(TimeFormatUtil.getTime());
        DataDictionary.setSignData(MD5Util.getMD5Key());

//        BlockCanary.install(this, new AppBlockCanaryContext());
//        LeakCanary.install(this);//内存泄漏监控
        SpCache.init(this);//初始化SpCache

    }


}
