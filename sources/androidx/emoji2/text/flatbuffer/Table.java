package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class Table {

    /* JADX INFO: renamed from: bb */
    protected ByteBuffer f56bb;
    protected int bb_pos;
    Utf8 utf8 = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    public int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f56bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public int __indirect(int i) {
        return i + this.f56bb.getInt(i);
    }

    public int __vector_len(int i) {
        int i2 = i + this.bb_pos;
        return this.f56bb.getInt(i2 + this.f56bb.getInt(i2));
    }

    public int __vector(int i) {
        int i2 = i + this.bb_pos;
        return i2 + this.f56bb.getInt(i2) + 4;
    }

    public void __reset(int i, ByteBuffer byteBuffer) {
        this.f56bb = byteBuffer;
        if (byteBuffer != null) {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.f56bb.getShort(i2);
            return;
        }
        this.bb_pos = 0;
        this.vtable_start = 0;
        this.vtable_size = 0;
    }
}
