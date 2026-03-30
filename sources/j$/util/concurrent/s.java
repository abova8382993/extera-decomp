package j$.util.concurrent;

import j$.util.Spliterator;
import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.function.Consumer$CC;
import j$.util.stream.c7;
import j$.util.stream.d7;
import j$.util.stream.l7;
import j$.util.stream.l8;
import j$.util.stream.t1;
import j$.util.stream.u1;
import j$.util.stream.v1;
import j$.util.stream.x3;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class s implements BiConsumer, BiFunction, Consumer, l8 {
    public final /* synthetic */ int a;
    public final Object b;
    public final Object c;

    public /* synthetic */ s(int i, Object obj, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    public /* synthetic */ s(BiFunction biFunction, Function function) {
        this.a = 2;
        this.c = biFunction;
        this.b = function;
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.a) {
        }
        return BiConsumer$CC.$default$andThen(this, biConsumer);
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction$CC.$default$andThen(this, function);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // java.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((Function) this.b).apply(((BiFunction) this.c).apply(obj, obj2));
    }

    @Override // java.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                ConcurrentMap concurrentMap = (ConcurrentMap) this.b;
                BiFunction biFunction = (BiFunction) this.c;
                while (!concurrentMap.replace(obj, obj2, biFunction.apply(obj, obj2)) && (obj2 = concurrentMap.get(obj)) != null) {
                }
                break;
            default:
                BiConsumer biConsumer = (BiConsumer) this.b;
                BiConsumer biConsumer2 = (BiConsumer) this.c;
                biConsumer.accept(obj, obj2);
                biConsumer2.accept(obj, obj2);
                break;
        }
    }

    public s(d7 d7Var, u1 u1Var, Supplier supplier) {
        this.a = 5;
        this.b = u1Var;
        this.c = supplier;
    }

    @Override // j$.util.stream.l8
    public int u() {
        return c7.u | c7.r;
    }

    @Override // j$.util.stream.l8
    public Object f(j$.util.stream.b bVar, Spliterator spliterator) {
        t1 t1Var = (t1) ((Supplier) this.c).get();
        bVar.s0(spliterator, t1Var);
        return Boolean.valueOf(t1Var.b);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // j$.util.stream.l8
    public Object i(x3 x3Var, Spliterator spliterator) {
        return (Boolean) new v1(this, (j$.util.stream.b) x3Var, spliterator).invoke();
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public void v(Object obj) {
        switch (this.a) {
            case 3:
                Consumer consumer = (Consumer) this.b;
                Consumer consumer2 = (Consumer) this.c;
                consumer.v(obj);
                consumer2.v(obj);
                break;
            case 4:
                AtomicBoolean atomicBoolean = (AtomicBoolean) this.b;
                ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.c;
                if (obj != null) {
                    concurrentHashMap.putIfAbsent(obj, Boolean.TRUE);
                } else {
                    atomicBoolean.set(true);
                }
                break;
            case 5:
            default:
                l7 l7Var = (l7) this.b;
                Consumer consumer3 = (Consumer) this.c;
                if (l7Var.b.putIfAbsent(obj != null ? obj : l7.d, Boolean.TRUE) == null) {
                    consumer3.v(obj);
                }
                break;
            case 6:
                ((BiConsumer) this.b).accept(this.c, obj);
                break;
        }
    }
}
