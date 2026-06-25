package com.google.common.hash;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import okio.Buffer$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes5.dex */
final class Murmur3_128HashFunction extends AbstractHashFunction implements Serializable {
    private final int seed;
    static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
    static final HashFunction GOOD_FAST_HASH_128 = new Murmur3_128HashFunction(Hashing.GOOD_FAST_HASH_SEED);

    public Murmur3_128HashFunction(int i) {
        this.seed = i;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_128(" + this.seed + ")";
    }

    public boolean equals(Object obj) {
        return (obj instanceof Murmur3_128HashFunction) && this.seed == ((Murmur3_128HashFunction) obj).seed;
    }

    public int hashCode() {
        return this.seed ^ Murmur3_128HashFunction.class.hashCode();
    }

    public static final class Murmur3_128Hasher extends AbstractStreamingHasher {

        /* JADX INFO: renamed from: h1 */
        private long f630h1;

        /* JADX INFO: renamed from: h2 */
        private long f631h2;
        private int length;

        private static long fmix64(long j) {
            long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
            long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
            return j3 ^ (j3 >>> 33);
        }

        public Murmur3_128Hasher(int i) {
            super(16);
            long j = i;
            this.f630h1 = j;
            this.f631h2 = j;
            this.length = 0;
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public void process(ByteBuffer byteBuffer) {
            bmix64(byteBuffer.getLong(), byteBuffer.getLong());
            this.length += 16;
        }

        private void bmix64(long j, long j2) {
            long jMixK1 = mixK1(j) ^ this.f630h1;
            this.f630h1 = jMixK1;
            long jRotateLeft = Long.rotateLeft(jMixK1, 27);
            long j3 = this.f631h2;
            this.f630h1 = ((jRotateLeft + j3) * 5) + 1390208809;
            long jMixK2 = mixK2(j2) ^ j3;
            this.f631h2 = jMixK2;
            this.f631h2 = ((Long.rotateLeft(jMixK2, 31) + this.f630h1) * 5) + 944331445;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.google.common.hash.AbstractStreamingHasher
        public void processRemaining(ByteBuffer byteBuffer) {
            long jM526m;
            long jM526m2;
            long jM526m3;
            long jM526m4;
            long jM526m5;
            long jM526m6;
            long jM526m7;
            this.length += byteBuffer.remaining();
            long jM526m8 = 0;
            switch (byteBuffer.remaining()) {
                case 1:
                    jM526m = 0;
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 2:
                    jM526m2 = 0;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 3:
                    jM526m3 = 0;
                    jM526m2 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(2))) << 16) ^ jM526m3;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 4:
                    jM526m4 = 0;
                    jM526m3 = jM526m4 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(3))) << 24);
                    jM526m2 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(2))) << 16) ^ jM526m3;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 5:
                    jM526m5 = 0;
                    jM526m4 = jM526m5 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(4))) << 32);
                    jM526m3 = jM526m4 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(3))) << 24);
                    jM526m2 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(2))) << 16) ^ jM526m3;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 6:
                    jM526m6 = 0;
                    jM526m5 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(5))) << 40) ^ jM526m6;
                    jM526m4 = jM526m5 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(4))) << 32);
                    jM526m3 = jM526m4 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(3))) << 24);
                    jM526m2 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(2))) << 16) ^ jM526m3;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 7:
                    jM526m6 = ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(6))) << 48;
                    jM526m5 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(5))) << 40) ^ jM526m6;
                    jM526m4 = jM526m5 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(4))) << 32);
                    jM526m3 = jM526m4 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(3))) << 24);
                    jM526m2 = (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(2))) << 16) ^ jM526m3;
                    jM526m = jM526m2 ^ (((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(1))) << 8);
                    jM526m7 = jM526m ^ ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(0)));
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 8:
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 9:
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 10:
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 11:
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(10))) << 16;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 12:
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(11))) << 24;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(10))) << 16;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 13:
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(12))) << 32;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(11))) << 24;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(10))) << 16;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 14:
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(13))) << 40;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(12))) << 32;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(11))) << 24;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(10))) << 16;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                case 15:
                    jM526m8 = ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(14))) << 48;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(13))) << 40;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(12))) << 32;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(11))) << 24;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(10))) << 16;
                    jM526m8 ^= ((long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(9))) << 8;
                    jM526m8 ^= (long) AbstractC1845xc13a9e41.m526m(byteBuffer.get(8));
                    jM526m7 = byteBuffer.getLong();
                    this.f630h1 = mixK1(jM526m7) ^ this.f630h1;
                    this.f631h2 ^= mixK2(jM526m8);
                    break;
                default:
                    Buffer$$ExternalSyntheticBUOutline2.m976m("Should never get here.");
                    break;
            }
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public HashCode makeHash() {
            long j = this.f630h1;
            int i = this.length;
            long j2 = j ^ ((long) i);
            long j3 = this.f631h2 ^ ((long) i);
            long j4 = j2 + j3;
            this.f630h1 = j4;
            this.f631h2 = j3 + j4;
            this.f630h1 = fmix64(j4);
            long jFmix64 = fmix64(this.f631h2);
            long j5 = this.f630h1 + jFmix64;
            this.f630h1 = j5;
            this.f631h2 = jFmix64 + j5;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.f630h1).putLong(this.f631h2).array());
        }

        private static long mixK1(long j) {
            return Long.rotateLeft(j * (-8663945395140668459L), 31) * 5545529020109919103L;
        }

        private static long mixK2(long j) {
            return Long.rotateLeft(j * 5545529020109919103L, 33) * (-8663945395140668459L);
        }
    }
}
