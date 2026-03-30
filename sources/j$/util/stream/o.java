package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes2.dex */
public final class o extends e5 {
    public static l2 H0(x3 x3Var, Spliterator spliterator) {
        j jVar = new j(6);
        j$.time.format.a aVar = new j$.time.format.a(23);
        j$.time.format.a aVar2 = new j$.time.format.a(24);
        Objects.requireNonNull(jVar);
        Objects.requireNonNull(aVar);
        Objects.requireNonNull(aVar2);
        return new l2((Collection) new c4(d7.REFERENCE, aVar2, aVar, jVar, 3).i(x3Var, spliterator));
    }

    @Override // j$.util.stream.b
    public final h2 A0(x3 x3Var, Spliterator spliterator, IntFunction intFunction) {
        b bVar = (b) x3Var;
        if (c7.DISTINCT.k(bVar.m)) {
            return x3Var.d0(spliterator, false, intFunction);
        }
        if (c7.ORDERED.k(bVar.m)) {
            return H0(x3Var, spliterator);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        j$.util.concurrent.s sVar = new j$.util.concurrent.s(4, atomicBoolean, concurrentHashMap);
        Objects.requireNonNull(sVar);
        new q0(sVar, false).a(x3Var, spliterator);
        Collection collectionKeySet = concurrentHashMap.keySet();
        if (atomicBoolean.get()) {
            HashSet hashSet = new HashSet(collectionKeySet);
            hashSet.add(null);
            collectionKeySet = hashSet;
        }
        return new l2(collectionKeySet);
    }

    @Override // j$.util.stream.b
    public final Spliterator B0(b bVar, Spliterator spliterator) {
        if (c7.DISTINCT.k(bVar.m)) {
            return bVar.u0(spliterator);
        }
        if (c7.ORDERED.k(bVar.m)) {
            return H0(bVar, spliterator).spliterator();
        }
        return new l7(bVar.u0(spliterator), new ConcurrentHashMap());
    }

    @Override // j$.util.stream.b
    public final o5 D0(int i, o5 o5Var) {
        Objects.requireNonNull(o5Var);
        if (c7.DISTINCT.k(i)) {
            return o5Var;
        }
        if (c7.SORTED.k(i)) {
            return new m(o5Var);
        }
        return new n(o5Var);
    }
}
