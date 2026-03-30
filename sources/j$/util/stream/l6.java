package j$.util.stream;

import j$.util.Collection;
import j$.util.List;
import j$.util.Objects;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public final class l6 extends d6 {
    public ArrayList d;

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void m(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.d = j >= 0 ? new ArrayList((int) j) : new ArrayList();
    }

    @Override // j$.util.stream.k5, j$.util.stream.o5
    public final void end() {
        List.EL.sort(this.d, this.b);
        long size = this.d.size();
        o5 o5Var = this.a;
        o5Var.m(size);
        if (!this.c) {
            ArrayList arrayList = this.d;
            Objects.requireNonNull(o5Var);
            Collection.EL.a(arrayList, new j$.time.s(8, o5Var));
        } else {
            ArrayList arrayList2 = this.d;
            int size2 = arrayList2.size();
            int i = 0;
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                if (o5Var.p()) {
                    break;
                } else {
                    o5Var.v(obj);
                }
            }
        }
        o5Var.end();
        this.d = null;
    }

    @Override // java.util.function.Consumer
    /* JADX INFO: renamed from: accept */
    public final void v(Object obj) {
        this.d.add(obj);
    }
}
