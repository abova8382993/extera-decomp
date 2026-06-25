package p026j$.time.zone;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: renamed from: j$.time.zone.h */
/* JADX INFO: compiled from: r8-map-id-bb8912b0da79b368bf495f96f4c609789c2f8ac0d4329ff658606d7ee861475b */
/* JADX INFO: loaded from: classes2.dex */
public final class C2397h {

    /* JADX INFO: renamed from: b */
    public static final CopyOnWriteArrayList f1008b;

    /* JADX INFO: renamed from: c */
    public static final ConcurrentMap f1009c;

    /* JADX INFO: renamed from: d */
    public static volatile Set f1010d;

    /* JADX INFO: renamed from: a */
    public final Set f1011a;

    static {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        f1008b = copyOnWriteArrayList;
        f1009c = new ConcurrentHashMap(512, 0.75f, 2);
        ArrayList arrayList = new ArrayList();
        AccessController.doPrivileged(new C2396g(arrayList));
        copyOnWriteArrayList.addAll(arrayList);
    }

    public C2397h() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : TimeZone.getAvailableIDs()) {
            linkedHashSet.add(str);
        }
        this.f1011a = Collections.unmodifiableSet(linkedHashSet);
    }

    /* JADX INFO: renamed from: a */
    public static ZoneRules m871a(String str) {
        Objects.requireNonNull(str, "zoneId");
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) f1009c;
        C2397h c2397h = (C2397h) concurrentHashMap.get(str);
        if (c2397h == null) {
            if (concurrentHashMap.isEmpty()) {
                throw new C2395f("No time-zone data files registered");
            }
            throw new C2395f("Unknown time-zone ID: ".concat(str));
        }
        if (c2397h.f1011a.contains(str)) {
            return new ZoneRules(TimeZone.getTimeZone(str));
        }
        throw new C2395f("Not a built-in time zone: ".concat(str));
    }

    /* JADX INFO: renamed from: b */
    public static void m872b(C2397h c2397h) {
        Objects.requireNonNull(c2397h, "provider");
        synchronized (C2397h.class) {
            try {
                for (String str : c2397h.f1011a) {
                    Objects.requireNonNull(str, "zoneId");
                    if (((C2397h) ((ConcurrentHashMap) f1009c).putIfAbsent(str, c2397h)) != null) {
                        throw new C2395f("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + c2397h);
                    }
                }
                f1010d = Collections.unmodifiableSet(new HashSet(((ConcurrentHashMap) f1009c).keySet()));
            } catch (Throwable th) {
                throw th;
            }
        }
        f1008b.add(c2397h);
    }
}
