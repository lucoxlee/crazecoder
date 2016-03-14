package top.latfat.crazecoder.entity;

import java.security.MessageDigest;
import java.util.Arrays;

public class WechatMsg {
	
	private String token;
	
	private String signature;
	
	private String timestamp;
	
	private String nonce;
	
	private String echostr;
	
	public WechatMsg(){}
	
	public WechatMsg(String token) {
		this.token = token;
	}
	
	public WechatMsg setAll(WechatMsg msg) {
		this.signature = msg.getSignature();
		this.timestamp = msg.getTimestamp();
		this.nonce = msg.getNonce();
		this.echostr = msg.getEchostr();
		return this;
	}
	
	public boolean accessing() {
		if (this.token == null || this.signature == null
		 || this.timestamp == null || this.nonce == null
		 || this.echostr == null) {
			return false;
		}
		String[] arr = new String[]{this.token, this.timestamp, this.nonce};
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (String string : arr) {
			sb.append(string);
		}
		String tmp;
		try {
			tmp = shaEncode(sb.toString());
		} catch (Exception e) {
			return false;
		}
		return this.signature.equalsIgnoreCase(tmp);
	}
	
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) { 
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "token=" + token + "\nsignature=" + signature + "\ntimestamp="
				+ timestamp + "\nnonce=" + nonce + "\nechostr=" + echostr
				+ "\n";
	}

}
