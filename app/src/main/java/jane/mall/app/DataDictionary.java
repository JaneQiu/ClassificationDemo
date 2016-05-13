package jane.mall.app;

public class DataDictionary {
	private static String signData;
	private static String timestamp;
	private static String deviceId;
	private int code;
	private boolean serviceCode;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(boolean serviceCode) {
		this.serviceCode = serviceCode;
	}

	public static String getSignData() {
		return signData;
	}

	public static void setSignData(String signdatas) {
		signData = signdatas;
	}

	public static String getTimestamp() {
		return timestamp;
	}
	
	public static void setTimestamp(String timestamps) {
		timestamp = timestamps;
	}

	public static String getDeviceId() {
		return deviceId;
	}

	public static void setDevuceId(String deviceIds) {
		deviceId = deviceIds;
	}
}
