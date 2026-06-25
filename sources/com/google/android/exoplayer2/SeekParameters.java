package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.Assertions;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes4.dex */
public final class SeekParameters {
    public static final SeekParameters CLOSEST_SYNC;
    public static final SeekParameters DEFAULT;
    public static final SeekParameters EXACT;
    public static final SeekParameters NEXT_SYNC;
    public static final SeekParameters PREVIOUS_SYNC;
    public final long toleranceAfterUs;
    public final long toleranceBeforeUs;

    static {
        SeekParameters seekParameters = new SeekParameters(0L, 0L);
        EXACT = seekParameters;
        CLOSEST_SYNC = new SeekParameters(LongCompanionObject.MAX_VALUE, LongCompanionObject.MAX_VALUE);
        PREVIOUS_SYNC = new SeekParameters(LongCompanionObject.MAX_VALUE, 0L);
        NEXT_SYNC = new SeekParameters(0L, LongCompanionObject.MAX_VALUE);
        DEFAULT = seekParameters;
    }

    public SeekParameters(long j, long j2) {
        Assertions.checkArgument(j >= 0);
        Assertions.checkArgument(j2 >= 0);
        this.toleranceBeforeUs = j;
        this.toleranceAfterUs = j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0051 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long resolveSeekPositionUs(long r8, long r10, long r12) {
        /*
            r7 = this;
            long r2 = r7.toleranceBeforeUs
            r0 = 0
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 != 0) goto Lf
            long r4 = r7.toleranceAfterUs
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 != 0) goto Lf
            return r8
        Lf:
            r4 = -9223372036854775808
            r0 = r8
            long r8 = com.google.android.exoplayer2.util.Util.subtractWithOverflowDefault(r0, r2, r4)
            long r2 = r7.toleranceAfterUs
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            long r2 = com.google.android.exoplayer2.util.Util.addWithOverflowDefault(r0, r2, r4)
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            r4 = 0
            r5 = 1
            if (r7 > 0) goto L2d
            int r7 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r7 > 0) goto L2d
            r7 = r5
            goto L2e
        L2d:
            r7 = r4
        L2e:
            int r6 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r6 > 0) goto L37
            int r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r2 > 0) goto L37
            r4 = r5
        L37:
            if (r7 == 0) goto L4c
            if (r4 == 0) goto L4c
            long r7 = r10 - r0
            long r7 = java.lang.Math.abs(r7)
            long r0 = r12 - r0
            long r0 = java.lang.Math.abs(r0)
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 > 0) goto L51
            goto L4e
        L4c:
            if (r7 == 0) goto L4f
        L4e:
            return r10
        L4f:
            if (r4 == 0) goto L52
        L51:
            return r12
        L52:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.SeekParameters.resolveSeekPositionUs(long, long, long):long");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && SeekParameters.class == obj.getClass()) {
            SeekParameters seekParameters = (SeekParameters) obj;
            if (this.toleranceBeforeUs == seekParameters.toleranceBeforeUs && this.toleranceAfterUs == seekParameters.toleranceAfterUs) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((int) this.toleranceBeforeUs) * 31) + ((int) this.toleranceAfterUs);
    }
}
