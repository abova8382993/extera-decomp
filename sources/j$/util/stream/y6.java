package j$.util.stream;

import j$.util.Spliterator;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public abstract class y6 extends d implements Iterable, j$.lang.a {
    public Object e;
    public Object[] f;

    public abstract void i(Object obj, int i, int i2, Object obj2);

    public abstract int j(Object obj);

    public abstract Object newArray(int i);

    public abstract Object[] o();

    public abstract Spliterator spliterator();

    @Override // java.lang.Iterable
    public final /* synthetic */ java.util.Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    public y6(int i) {
        super(i);
        this.e = newArray(1 << this.a);
    }

    public y6() {
        this.e = newArray(16);
    }

    public final void l(long j) {
        long j2;
        int i = this.c;
        if (i == 0) {
            j2 = j(this.e);
        } else {
            j2 = ((long) j(this.f[i])) + this.d[i];
        }
        if (j > j2) {
            if (this.f == null) {
                Object[] objArrO = o();
                this.f = objArrO;
                this.d = new long[8];
                objArrO[0] = this.e;
            }
            int i2 = this.c + 1;
            while (j > j2) {
                Object[] objArr = this.f;
                if (i2 >= objArr.length) {
                    int length = objArr.length * 2;
                    this.f = Arrays.copyOf(objArr, length);
                    this.d = Arrays.copyOf(this.d, length);
                }
                int iMin = this.a;
                if (i2 != 0 && i2 != 1) {
                    iMin = Math.min((iMin + i2) - 1, 30);
                }
                int i3 = 1 << iMin;
                this.f[i2] = newArray(i3);
                long[] jArr = this.d;
                int i4 = i2 - 1;
                jArr[i2] = jArr[i4] + ((long) j(this.f[i4]));
                j2 += (long) i3;
                i2++;
            }
        }
    }

    public final int k(long j) {
        if (this.c == 0) {
            if (j < this.b) {
                return 0;
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j >= count()) {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        for (int i = 0; i <= this.c; i++) {
            if (j < this.d[i] + ((long) j(this.f[i]))) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(Long.toString(j));
    }

    public void c(int i, Object obj) {
        long j = i;
        long jCount = count() + j;
        if (jCount > j(obj) || jCount < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.c == 0) {
            System.arraycopy(this.e, 0, obj, i, this.b);
            return;
        }
        for (int i2 = 0; i2 < this.c; i2++) {
            Object obj2 = this.f[i2];
            System.arraycopy(obj2, 0, obj, i, j(obj2));
            i += j(this.f[i2]);
        }
        int i3 = this.b;
        if (i3 > 0) {
            System.arraycopy(this.e, 0, obj, i, i3);
        }
    }

    public Object b() {
        long jCount = count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object objNewArray = newArray((int) jCount);
        c(0, objNewArray);
        return objNewArray;
    }

    public final void q() {
        long j;
        if (this.b == j(this.e)) {
            if (this.f == null) {
                Object[] objArrO = o();
                this.f = objArrO;
                this.d = new long[8];
                objArrO[0] = this.e;
            }
            int i = this.c;
            int i2 = i + 1;
            Object[] objArr = this.f;
            if (i2 >= objArr.length || objArr[i2] == null) {
                if (i == 0) {
                    j = j(this.e);
                } else {
                    j = ((long) j(objArr[i])) + this.d[i];
                }
                l(j + 1);
            }
            this.b = 0;
            int i3 = this.c + 1;
            this.c = i3;
            this.e = this.f[i3];
        }
    }

    @Override // j$.util.stream.d
    public final void clear() {
        Object[] objArr = this.f;
        if (objArr != null) {
            this.e = objArr[0];
            this.f = null;
            this.d = null;
        }
        this.b = 0;
        this.c = 0;
    }

    public void d(Object obj) {
        for (int i = 0; i < this.c; i++) {
            Object obj2 = this.f[i];
            i(obj2, 0, j(obj2), obj);
        }
        i(this.e, 0, this.b, obj);
    }
}
