package jane.mall.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import jane.mall.util.log.KLog;

public class DeviceHelper {

    private Context mContext = null;
    private static DeviceHelper mInstance = null;
    private TelephonyManager tm;

    public synchronized static DeviceHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DeviceHelper(context);
        }
        return mInstance;
    }

    private DeviceHelper(Context context) {
        this.mContext = context;
        tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getImei() {
        String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            return getLocalMacAddressFromIp(mContext);
        } else {
            return deviceId;
        }
    }

    public String getDeviceModel() {
        return android.os.Build.MANUFACTURER + "-" + android.os.Build.MODEL;
    }

    public int getAppVersion() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private String getLocalMacAddressFromIp(Context context) {
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            return byte2hex(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            KLog.e("WifiPreference IpAddress", ex.toString());
        }
        return "";
    }


}
