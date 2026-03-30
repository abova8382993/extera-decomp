package j$.util.stream;

import j$.util.Spliterator;
import java.util.Set;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class a implements Supplier {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ a(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return ((b) obj).E0(0);
            case 1:
                return (Spliterator) obj;
            default:
                Set set = Collectors.a;
                return new j$.util.q1((CharSequence) obj);
        }
    }
}
