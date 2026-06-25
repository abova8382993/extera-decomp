package com.google.firebase.crashlytics.internal.metadata;

import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.common.CommonUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.coroutines.Continuation;
import okhttp3.internal.url._UrlKt;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes.dex */
class KeysMap {
    private final Map<String, String> keys = new HashMap();
    private final int maxEntries;
    private final int maxEntryLength;

    public KeysMap(int i, int i2) {
        this.maxEntries = i;
        this.maxEntryLength = i2;
    }

    public synchronized Map<String, String> getKeys() {
        return Collections.unmodifiableMap(new HashMap(this.keys));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, java.lang.String, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r2v3, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public synchronized boolean setKey(String str, String str2) {
        ?? SanitizeKey = sanitizeKey(str);
        if (this.keys.size() >= this.maxEntries && this.keys.probeCoroutineSuspended((Continuation<?>) SanitizeKey) == 0) {
            Logger.getLogger().m537w("Ignored entry \"" + str + "\" when adding custom keys. Maximum allowable: " + this.maxEntries);
            return false;
        }
        String strSanitizeString = sanitizeString(str2, this.maxEntryLength);
        if (CommonUtils.nullSafeEquals(this.keys.get(SanitizeKey), strSanitizeString)) {
            return false;
        }
        Map<String, String> map = this.keys;
        if (str2 == null) {
            strSanitizeString = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        map.put((String) SanitizeKey, strSanitizeString);
        return true;
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object, java.lang.String, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r3v4, types: [void] */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public synchronized void setKeys(Map<String, String> map) {
        try {
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                ?? SanitizeKey = sanitizeKey(entry.getKey());
                if (this.keys.size() < this.maxEntries || this.keys.probeCoroutineSuspended((Continuation<?>) SanitizeKey) != 0) {
                    String value = entry.getValue();
                    this.keys.put((String) SanitizeKey, value == null ? _UrlKt.FRAGMENT_ENCODE_SET : sanitizeString(value, this.maxEntryLength));
                } else {
                    i++;
                }
            }
            if (i > 0) {
                Logger.getLogger().m537w("Ignored " + i + " entries when adding custom keys. Maximum allowable: " + this.maxEntries);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private String sanitizeKey(String str) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("Custom attribute key must not be null.");
            return null;
        }
        return sanitizeString(str, this.maxEntryLength);
    }

    public static String sanitizeString(String str, int i) {
        if (str == null) {
            return str;
        }
        String strTrim = str.trim();
        return strTrim.length() > i ? strTrim.substring(0, i) : strTrim;
    }
}
