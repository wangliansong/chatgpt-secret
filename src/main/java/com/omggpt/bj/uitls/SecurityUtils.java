package com.omggpt.bj.uitls;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class SecurityUtils {
    public final static String letters = "abcdefghijklmnopqrstuvwxyz0123456789";

    public final static String key = "ed26d4cd99aa11e5b8a4c89cdc776729";

    private static String Algorithm = "AES";

    private static String AlgorithmProvider = "AES/ECB/PKCS5Padding";

    private final static String encoding = "UTF-8";

    public static String encrypt(String src) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), Algorithm);
        //IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
        return Base64Utils.encodeToString(cipherBytes);
    }

    public static String decrypt(String src) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), Algorithm);

        //IvParameterSpec ivParameterSpec = getIv();
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] hexBytes = Base64Utils.decodeFromString(src);
        byte[] plainBytes = cipher.doFinal(hexBytes);
        return new String(plainBytes, "utf-8");
    }

    public static String md5Encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptKey(String key) throws Exception {
        String encryptedKey = "";

        String[] array = key.split("");

        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            encryptedKey += array[i];
            for (int j = 0; j < i % 2 + 1; j++) {
                int index = random.nextInt(letters.length());
                encryptedKey += letters.substring(index, index + 1);
            }
        }
        return Base64Utils.encodeToString(new StringBuilder(encryptedKey).reverse().toString().getBytes(encoding)).replaceAll("\n", "");
    }

    public static String decryptKey(String encryptedKey) {
        encryptedKey = new String(Base64Utils.decodeFromString(encryptedKey));
        String key = "";

        char[] c = new StringBuilder(encryptedKey).reverse().toString().toCharArray();

        for (int i = 0, j = 0; i < encryptedKey.length(); i++) {
            key += c[i];
            i += (j++ % 2 + 1);
        }

        return key;
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("kw/h+VVmDFcJjJGn8QuqO2zvz9B4Ska7cwRNjebLRyEjqXsLoFUn/BKVQVV+aRZ9ldFthSjQ7nfWYvVTGyRxl0BJmPdWYMUJlLDlvREsmebtW9YISIQ4mJqKsLoltz+eKKnh1rYCsF7rcYvTYXMHz1cgg0r5E3S9yIqnJrtWrwo=");
        String decrypt = decrypt(sb.toString());
//        String decrypt = encrypt("{\"code\":\"312312312\",\"name\":\"31231231312\",\"abbr\":\"\",\"customerSourceCode\":\"01\",\"custChanTypeId\":\"0Pf2RM2q459TDfz6X1oE\",\"customerCategoryId\":\"1001A110000000000MLO\",\"customerTypeCode\":\"01\",\"organizationId\":\"\",\"isChannelCustomer\":\"0\",\"isServiceProvider\":\"\",\"entNatureId\":\"a0ea1cb5-6f7f-449a-9937-66a934067619\",\"industryId\":\"afccb207-278e-4bab-853d-8e6f66a0e0be\",\"employeeNum\":1,\"turnover\":\"0.00\",\"superiorCustomerId\":null,\"payerCustomerId\":\"\",\"custStatusId\":\"\",\"countryId\":\"\",\"provinceId\":\"\",\"cityId\":\"\",\"countyId\":\"\",\"townId\":\"\",\"detailAddr\":\"\",\"legalPerson\":\"\",\"regCapital\":\"\",\"establishTime\":\"\",\"socioCreditCode\":\"\",\"orgStrCode\":\"\",\"bizRegNo\":\"\",\"taxpayerNo\":\"\",\"isFrozen\":\"0\",\"isEnable\":\"1\",\"custChanTypeName\":\"01\",\"customerBaseInfos\":[{\"id\":null,\"persistStatus\":\"new\",\"organizationId\":\"0001A110000000002M9Y\",\"organizationCode\":\"14C0\",\"organizationName\":\"中化化肥有限公司河北分公司\",\"isEnable\":\"1\"}],\"customerAreas\":[{\"id\":null,\"persistStatus\":\"new\",\"organizationId\":\"0001A110000000002M9Y\",\"organizationCode\":\"14C0\",\"organizationName\":\"中化化肥有限公司河北分公司\",\"cityMarketAreaId\":null,\"cityMarketAreaCode\":null,\"cityMarketAreaName\":null,\"departmentId\":null,\"departmentCode\":null,\"departmentName\":null,\"staffUserId\":\"1001ZZ100000000031M8\",\"staffUserCode\":\"020\",\"staffUserName\":\"020\"}],\"custChanTypeId__code\":\"01\",\"custChanTypeId__name\":\"01\",\"custChanTypeId__id\":\"0Pf2RM2q459TDfz6X1oE\",\"customerCategoryCode\":\"C\",\"customerCategoryName\":\"内部客户\",\"entNatureCode\":\"PRIVATELY_OPERATED\",\"entNatureName\":\"民营\",\"industryCode\":\"20190627000001\",\"industryName\":\"行业\",\"custChanTypeCode\":\"\",\"organizationCode\":\"\",\"organizationName\":\"\",\"superiorCustomerCode\":null,\"superiorCustomerName\":null,\"payerCustomerCode\":\"\",\"payerCustomerName\":\"\",\"custStatusCode\":\"\",\"custStatusName\":\"\",\"countryCode\":\"\",\"countryName\":\"\",\"provinceCode\":\"\",\"provinceName\":\"\",\"cityCode\":\"\",\"cityName\":\"\",\"countyCode\":\"\",\"countyName\":\"\",\"townCode\":\"\",\"townName\":\"\",\"customerAddresses\":[],\"customerAccounts\":[],\"customerInvoices\":[],\"isVerify\":\"0\",\"persistStatus\":\"new\",\"isPotential\":0}");
        System.out.println(decrypt);
    }
}