package jane.mall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jane.mall.app.DataDictionary;
import jane.mall.net.ApiConstant;
import jane.mall.util.log.KLog;

public class MD5Util {

    private static final String TAG = "MD5Util";

    /**
     * 32位md5
     */
    public final static String md5(String str) {
        KLog.d(TAG, "str start= " + str);
        return encrypt("MD5", str);
    }


    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 32 位 MD5加密
     *
     * @param str 待加密的字符串
     * @return
     */
    public static String encrypt(String str) {
        String result = null;
        try {
            result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String encrypt(String algorithm, String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(str.getBytes());
            StringBuffer sb = new StringBuffer();
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            KLog.d(TAG, "str end= " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getMD5Key() {
        String key = ApiConstant.SECRET_KEY + DataDictionary.getDeviceId() + DataDictionary.getTimestamp();
        KLog.d(TAG, "deviceId" + DataDictionary.getDeviceId() + "时间" + DataDictionary.getTimestamp());
        return md5(key);
    }

    /**
     * MD5 加密后小写
     * @param secret
     * @return
     */
    public static String getMd5Value(String secret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(secret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
