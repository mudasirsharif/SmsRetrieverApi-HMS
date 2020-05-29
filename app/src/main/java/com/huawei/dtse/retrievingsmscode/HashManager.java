package com.huawei.dtse.retrievingsmscode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashManager {

    private final String TAG = HashManager.class.getCanonicalName();

    public String getHashValue(Context appContext) {

        String packageName = appContext.getPackageName();
        MessageDigest messageDigest = getMessageDigest();
        String signature = getSignature(appContext, packageName);
        String hashCode = getHashCode(packageName, messageDigest, signature);
        Log.d(TAG, hashCode);

        return hashCode;
    }

    private MessageDigest getMessageDigest() {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "No Such Algorithm.", e);
        }
        return messageDigest;
    }

    private String getSignature(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Signature[] signatureArrs;
        try {
            signatureArrs = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package name inexistent.");
            return "";
        }
        if (null == signatureArrs || 0 == signatureArrs.length) {
            Log.e(TAG, "signature is null.");
            return "";
        }
        return signatureArrs[0].toCharsString();
    }

    private String getHashCode(String packageName, MessageDigest messageDigest, String signature) {
        String appInfo = packageName + " " + signature;
        messageDigest.update(appInfo.getBytes(StandardCharsets.UTF_8));
        byte[] hashSignature = messageDigest.digest();
        hashSignature = Arrays.copyOfRange(hashSignature, 0, 9);
        String base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING | Base64.NO_WRAP);
        base64Hash = base64Hash.substring(0, 11);
        return base64Hash;
    }
}
