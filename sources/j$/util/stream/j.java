package j$.util.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class j implements Supplier {
    public final /* synthetic */ int a;

    public /* synthetic */ j(int i) {
        this.a = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.a) {
            case 0:
                return new j$.util.w();
            case 1:
                return new ArrayList();
            case 2:
                return new j$.util.x();
            case 3:
                return new HashSet();
            case 4:
                return new j$.util.z();
            case 5:
                return new StringBuilder();
            case 6:
                return new LinkedHashSet();
            case 7:
                return new double[4];
            case 8:
                return new double[3];
            case 9:
                return new g0();
            case 10:
                return new h0();
            case 11:
                return new i0();
            case 12:
                return new j0();
            case 13:
                return new long[2];
            default:
                return new long[2];
        }
    }
}
