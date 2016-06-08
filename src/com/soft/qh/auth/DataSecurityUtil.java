package com.soft.qh.auth;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.soft.util.CommonUtil;
import com.soft.util.JSONUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

 
public class DataSecurityUtil {
	public static final String KEY_STORE = "JKS";
	public static final String X509 = "X.509";
	public static PublicKey publicKey;
	public static PrivateKey privateKey;
	
	/**
	 * 获取公钥
	 * 
	 * @author lujf
	 * @param certificatePath
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	private static PublicKey getPublicKey(String certificatePath)
			throws Exception {
		if (publicKey != null) {
			return publicKey;
		}
		InputStream is = null;
		try {
			// is = new FileInputStream("D:\\KeyScript\\test\\bistest_2.cer");
			is = new FileInputStream(certificatePath);
			CertificateFactory cf = CertificateFactory.getInstance(X509);
			X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
			return cert.getPublicKey();
		} catch (FileNotFoundException e) {
			throw new Exception("E000029");
		} catch (CertificateException e) {
			throw new Exception("E000030");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取秘钥
	 * 
	 * @author lujf
	 * @param keyStorePath
	 * @param alias	别名
	 * @param password 用于恢复密钥的密码
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	private static PrivateKey getPrivateKey(String keyStorePath, String alias,
			String password) throws Exception {
		char[] storePwdArr;
		int i;
		BufferedInputStream bis = null;
		try {
			KeyStore ks = KeyStore.getInstance(KEY_STORE);
			FileInputStream fis = new FileInputStream(keyStorePath);
			bis = new BufferedInputStream(fis);
			storePwdArr = new char[password.length()];// store password
			for (i = 0; i < password.length(); i++) {
				storePwdArr[i] = password.charAt(i);
			}
			ks.load(bis, storePwdArr);
			PrivateKey priv = (PrivateKey) ks.getKey(alias, storePwdArr);
			return priv;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			throw new Exception("E000033");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("E000031", e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("E000032", e);
		} catch (CertificateException e) {
			e.printStackTrace();
			throw new Exception("E000030", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("E000033", e);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			throw new Exception("E000033", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * sha1加密
	 * 
	 * @author lujf
	 * @param oriByte
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	public static String digest(byte[] oriByte) throws Exception {
		MessageDigest md = null;
		String strDes = null;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(oriByte);
			strDes = CommonUtil.bytes2Hex(md.digest());
		} catch (Exception e) {
			throw new Exception("E000016", e);
		}
		return strDes;
	}

	/**
	 * 数据签名加密
	 * 
	 * @author lujf
	 * @param data
	 * @param keyStorePath
	 * @param alias
	 * @param password
	 * @param algorithm
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	public static String signData(String data, String keyStorePath,
			String alias, String password,String algorithm) throws Exception {
		try {
 			PrivateKey key = getPrivateKey(keyStorePath, alias, password);
			Signature sig = Signature.getInstance(algorithm);
			sig.initSign(key);
			sig.update(data.getBytes("UTF-8"));
			byte[] sigBytes = sig.sign();
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encodeBuffer(sigBytes);
		} catch (Exception e) {
			throw new Exception("E000012", e);
		}
	}
		
	  /**
	   * 验证签名
	   * @author 001362
	   * @param certificatePath
	   * @param data
	   * @param signValue
	   * @param algorithm
	   * @throws Exception
	   * @date 2016年5月27日
	   */
	  public static void verifyData(String certificatePath, String data, String signValue,String algorithm) throws Exception
	    {
	        try
	        {
	            PublicKey key = getPublicKey(certificatePath);
	            Signature sig = Signature.getInstance(algorithm);
	            sig.initVerify(key);
	            sig.update(data.getBytes("utf-8"));
	            BASE64Decoder decoder = new BASE64Decoder();
	            byte[] signValueByte = decoder.decodeBuffer(signValue);
	            if (!sig.verify(signValueByte))
	            {
	                throw new Exception("E000013");
	            }
	        } catch (Exception e)
	        {
	            throw new Exception("E000014", e);
	        }
	    }
	  
	  	/**
	  	 * 获取DESEDE加密秘钥
	  	 * @author lujf
	  	 * @param key
	  	 * @return
	  	 * @throws Exception
	  	 * @date 2016年5月27日
	  	 */
	    private static SecretKey getKey(String key) throws Exception
	    {
	        try
	        {
	            // 实例化DESede密钥
	            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("utf-8"));
	            // 实例化密钥工厂
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	            // 生成密钥
	            SecretKey secretKey = keyFactory.generateSecret(dks);
	            return secretKey;
	        } catch (Exception e)
	        {
	            throw new Exception("E000036", e);
	        }
	    }
	    
	    /**
	     * 解密
	     * @author lujf
	     * @param sealTxt 密文
	     * @param keyStr 秘钥
	     * @param transformation("DES/CBC/PKCS5Padding","DES")
	     * @return
	     * @throws Exception
	     * @date 2016年5月27日
	     */
	    public static String decrypt(String sealTxt, String keyStr,String transformation) throws Exception
	    {
	        try
	        {
	            Cipher cipher = null;
	            byte[] byteFina = null;
	            SecretKey key = getKey(keyStr);
	            try
	            {
	                cipher = Cipher.getInstance(transformation);
	                cipher.init(Cipher.DECRYPT_MODE, key);
	                BASE64Decoder decoder = new BASE64Decoder();
	                byte[] sealByte = decoder.decodeBuffer(sealTxt);
	                byteFina = cipher.doFinal(sealByte);
	                return new String(byteFina, "UTF-8");
	            } catch (Exception e)
	            {
	                throw new Exception("E000034", e);
	            }
	            finally
	            {
	                cipher = null;
	            }
	        } catch (Exception ee)
	        {
	            throw new Exception(ee);
	        }
	    }
	    
	    /**
	     * 加密
	     * @author lujf
	     * @param oriByte 
	     * @param keyStr 
	     * @param transformation("DES/CBC/PKCS5Padding","DES") 
	     * @return
	     * @throws Exception
	     * @date 2016年5月27日
	     */
	    public static String encrypt(byte[] oriByte, String keyStr,String transformation) throws Exception
	    {
	        try
	        {
	            byte[] sealTxt = null;
	            SecretKey key = getKey(keyStr);
	            Cipher cipher = null;
	            try
	            {
	                cipher = Cipher.getInstance(transformation);
	                cipher.init(Cipher.ENCRYPT_MODE, key);
	                sealTxt = cipher.doFinal(oriByte);
	                BASE64Encoder encoder = new BASE64Encoder();
	                String ret = encoder.encode(sealTxt);
	                return ret;
	            } catch (Exception e)
	            {
	                throw new Exception("E000035", e);
	            }
	            finally
	            {
	                cipher = null;
	            }
	        } catch (Exception ee)
	        {
	            throw new Exception(ee);
	        }
	    }
	    
	    
	    public static void main(String[] arg) throws Exception{
	    	String str = "{\"securityInfo\":{\"signatureValue\":\"asXpwxhpdjM6A/6Ux+5dcgTXkAyBvA1bbt1oZaD3gpz8QOT/KFTwup2AG3kP+xVal17KodRCEEEd\n/ZKS9NhHK+BsBCYNM7FU9OQjQvDwy6gsKNkygwxo+gn035PbnJ7jzP3OPYawjeoW/U65P28d1JG7\nGj3WIDf7poQhhv26qnA=\n\"},\"oriMessage\":\"\",\"busiData\":\"OHQMWgf3em8ngz2z3KIG+7jdAUksdWvBDHRfmifPF66qWqnRugeI/eyYkkl+UdVF5hCpp4bJrBbL\nWjKLDUfodggSxEYM0hNh5vpHMnWX/d/5CDHGpJ27EnUyUtMx1reErJ6NETCo9SwVuhSYaXvlsSWD\nho5laozy2+6I5Nqv/hqfkA84EsOvuYMKISMZK3SRE7j+sMUVPIvV38cs+RVQNuY8sUmN5I+pTYkj\nCSL8E580BJKMKSKUVuFAv2RZzLJTxTj2wj6ys8QUwr0Ebb+xw947ZqMHiLCFAM/TzltTNgWN9ePj\nyw5s0+cfB2xfnSwDZlixBGLwxU/G+FnpGf5PumH9JqQe1ZCw88GowH1uaK5emoVr+8i4wsdHcu/U\n3Smx7V/UFk6Ffg7E2KfsqhbPAlhonifnrag2vKoqLI8W6w1m+sfCBRAqQBMFz+LT4BA+1G3C46MC\nNNS2H1BIO6A0iwXf03Rp7zSAjW0PrYrHBfc=\",\"header\":{\"transDate\":\"2015-02-02 14:12:14\",\"authCode\":\"CRT001A2\",\"rtMsg\":\"交易成功\",\"orgCode\":\"10000000\",\"chnlId\":\"qhcs-dcs\",\"transNo\":\"11000112212\",\"rtCode\":\"E000000\",\"authDate\":\"2015-12-02 14:12:14\"}}";
	    	DataSecurityUtil.verifyData("D:\\workspace\\接口实现\\前海接口\\stg证书\\credoo_stg.cer", JSONUtils.getStringByKey(str,"busiData"), JSONUtils.getStringByKey(str,"securityInfo"), "SHA1WithRSA");
	    }
}
