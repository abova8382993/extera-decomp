package p026j$.time.format;

import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import okhttp3.internal.url._UrlKt;
import p026j$.time.AbstractC2283b;
import p026j$.time.chrono.C2311r;
import p026j$.time.chrono.InterfaceC2304k;
import p026j$.time.temporal.EnumC2365a;
import p026j$.time.temporal.InterfaceC2380p;

/* JADX INFO: renamed from: j$.time.format.w */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public class C2347w {

    /* JADX INFO: renamed from: a */
    public static final ConcurrentMap f896a = new ConcurrentHashMap(16, 0.75f, 2);

    /* JADX INFO: renamed from: b */
    public static final C2345u f897b = new C2345u();

    /* JADX INFO: renamed from: c */
    public static final C2347w f898c = new C2347w();

    /* JADX INFO: renamed from: a */
    public static Object m787a(InterfaceC2380p interfaceC2380p, Locale locale) {
        Object c2346v;
        AbstractMap.SimpleImmutableEntry simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(interfaceC2380p, locale);
        Object obj = ((ConcurrentHashMap) f896a).get(simpleImmutableEntry);
        if (obj != null) {
            return obj;
        }
        HashMap map = new HashMap();
        if (interfaceC2380p == EnumC2365a.ERA) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            HashMap map2 = new HashMap();
            HashMap map3 = new HashMap();
            String[] eras = dateFormatSymbols.getEras();
            for (int i = 0; i < eras.length; i++) {
                if (!eras[i].isEmpty()) {
                    long j = i;
                    map2.put(Long.valueOf(j), eras[i]);
                    Long lValueOf = Long.valueOf(j);
                    String str = eras[i];
                    map3.put(lValueOf, str.substring(0, Character.charCount(str.codePointAt(0))));
                }
            }
            if (!map2.isEmpty()) {
                map.put(TextStyle.FULL, map2);
                map.put(TextStyle.SHORT, map2);
                map.put(TextStyle.NARROW, map3);
            }
            c2346v = new C2346v(map);
        } else if (interfaceC2380p == EnumC2365a.MONTH_OF_YEAR) {
            int length = DateFormatSymbols.getInstance(locale).getMonths().length;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
            for (long j2 = 1; j2 <= length; j2++) {
                String strM673b = AbstractC2283b.m673b(j2, "LLLL", locale);
                linkedHashMap.put(Long.valueOf(j2), strM673b);
                linkedHashMap2.put(Long.valueOf(j2), strM673b.substring(0, Character.charCount(strM673b.codePointAt(0))));
                linkedHashMap3.put(Long.valueOf(j2), AbstractC2283b.m673b(j2, "LLL", locale));
            }
            if (length > 0) {
                map.put(TextStyle.FULL_STANDALONE, linkedHashMap);
                map.put(TextStyle.NARROW_STANDALONE, linkedHashMap2);
                map.put(TextStyle.SHORT_STANDALONE, linkedHashMap3);
                map.put(TextStyle.FULL, linkedHashMap);
                map.put(TextStyle.NARROW, linkedHashMap2);
                map.put(TextStyle.SHORT, linkedHashMap3);
            }
            c2346v = new C2346v(map);
        } else if (interfaceC2380p == EnumC2365a.DAY_OF_WEEK) {
            int length2 = DateFormatSymbols.getInstance(locale).getWeekdays().length;
            LinkedHashMap linkedHashMap4 = new LinkedHashMap();
            LinkedHashMap linkedHashMap5 = new LinkedHashMap();
            LinkedHashMap linkedHashMap6 = new LinkedHashMap();
            boolean z = locale == Locale.SIMPLIFIED_CHINESE || locale == Locale.TRADITIONAL_CHINESE;
            for (long j3 = 1; j3 <= length2; j3++) {
                String strM672a = AbstractC2283b.m672a(j3, "cccc", locale);
                linkedHashMap4.put(Long.valueOf(j3), strM672a);
                linkedHashMap5.put(Long.valueOf(j3), z ? new StringBuilder().appendCodePoint(strM672a.codePointBefore(strM672a.length())).toString() : strM672a.substring(0, Character.charCount(strM672a.codePointAt(0))));
                linkedHashMap6.put(Long.valueOf(j3), AbstractC2283b.m672a(j3, "ccc", locale));
            }
            if (length2 > 0) {
                map.put(TextStyle.FULL_STANDALONE, linkedHashMap4);
                map.put(TextStyle.NARROW_STANDALONE, linkedHashMap5);
                map.put(TextStyle.SHORT_STANDALONE, linkedHashMap6);
                map.put(TextStyle.FULL, linkedHashMap4);
                map.put(TextStyle.NARROW, linkedHashMap5);
                map.put(TextStyle.SHORT, linkedHashMap6);
            }
            c2346v = new C2346v(map);
        } else if (interfaceC2380p == EnumC2365a.AMPM_OF_DAY) {
            DateFormatSymbols dateFormatSymbols2 = DateFormatSymbols.getInstance(locale);
            HashMap map4 = new HashMap();
            HashMap map5 = new HashMap();
            String[] amPmStrings = dateFormatSymbols2.getAmPmStrings();
            for (int i2 = 0; i2 < amPmStrings.length; i2++) {
                if (!amPmStrings[i2].isEmpty()) {
                    long j4 = i2;
                    map4.put(Long.valueOf(j4), amPmStrings[i2]);
                    Long lValueOf2 = Long.valueOf(j4);
                    String str2 = amPmStrings[i2];
                    map5.put(lValueOf2, str2.substring(0, Character.charCount(str2.codePointAt(0))));
                }
            }
            if (!map4.isEmpty()) {
                map.put(TextStyle.FULL, map4);
                map.put(TextStyle.SHORT, map4);
                map.put(TextStyle.NARROW, map5);
            }
            c2346v = new C2346v(map);
        } else {
            c2346v = _UrlKt.FRAGMENT_ENCODE_SET;
        }
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) f896a;
        concurrentHashMap.putIfAbsent(simpleImmutableEntry, c2346v);
        return concurrentHashMap.get(simpleImmutableEntry);
    }

    /* JADX INFO: renamed from: b */
    public String mo744b(InterfaceC2304k interfaceC2304k, InterfaceC2380p interfaceC2380p, long j, TextStyle textStyle, Locale locale) {
        if (interfaceC2304k == C2311r.f803c || !(interfaceC2380p instanceof EnumC2365a)) {
            return mo745c(interfaceC2380p, j, textStyle, locale);
        }
        return null;
    }

    /* JADX INFO: renamed from: c */
    public String mo745c(InterfaceC2380p interfaceC2380p, long j, TextStyle textStyle, Locale locale) {
        Object objM787a = m787a(interfaceC2380p, locale);
        if (objM787a instanceof C2346v) {
            return ((C2346v) objM787a).m786a(j, textStyle);
        }
        return null;
    }

    /* JADX INFO: renamed from: d */
    public Iterator mo746d(InterfaceC2304k interfaceC2304k, InterfaceC2380p interfaceC2380p, TextStyle textStyle, Locale locale) {
        if (interfaceC2304k == C2311r.f803c || !(interfaceC2380p instanceof EnumC2365a)) {
            return mo747e(interfaceC2380p, textStyle, locale);
        }
        return null;
    }

    /* JADX INFO: renamed from: e */
    public Iterator mo747e(InterfaceC2380p interfaceC2380p, TextStyle textStyle, Locale locale) {
        List list;
        Object objM787a = m787a(interfaceC2380p, locale);
        if (!(objM787a instanceof C2346v) || (list = (List) ((HashMap) ((C2346v) objM787a).f895b).get(textStyle)) == null) {
            return null;
        }
        return list.iterator();
    }
}
