package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public final class g8 extends e7 {
    @Override // j$.util.stream.e7
    public final e7 e(Spliterator spliterator) {
        return new g8(this.b, spliterator, this.a);
    }

    @Override // j$.util.stream.e7
    public final void d() {
        z6 z6Var = new z6();
        this.h = z6Var;
        Objects.requireNonNull(z6Var);
        this.e = this.b.t0(new f8(z6Var, 0));
        this.f = new j$.time.s(14, this);
    }

    @Override // j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        Object obj;
        Objects.requireNonNull(consumer);
        boolean zA = a();
        if (!zA) {
            return zA;
        }
        z6 z6Var = (z6) this.h;
        long j = this.g;
        if (z6Var.c != 0) {
            if (j >= z6Var.count()) {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            for (int i = 0; i <= z6Var.c; i++) {
                long j2 = z6Var.d[i];
                Object[] objArr = z6Var.f[i];
                if (j < ((long) objArr.length) + j2) {
                    obj = objArr[(int) (j - j2)];
                }
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j < z6Var.b) {
            obj = z6Var.e[(int) j];
        } else {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        consumer.v(obj);
        return zA;
    }

    @Override // j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        if (this.h == null && !this.i) {
            Objects.requireNonNull(consumer);
            c();
            Objects.requireNonNull(consumer);
            f8 f8Var = new f8(consumer, 1);
            this.b.s0(this.d, f8Var);
            this.i = true;
            return;
        }
        while (tryAdvance(consumer)) {
        }
    }
}
