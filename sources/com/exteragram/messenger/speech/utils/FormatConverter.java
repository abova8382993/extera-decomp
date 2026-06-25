package com.exteragram.messenger.speech.utils;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.UByte;
import org.vosk.Model$$ExternalSyntheticBUOutline0;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FormatConverter {
    /* JADX WARN: Code restructure failed: missing block: B:11:0x002c, code lost:
    
        r7 = r4.getInteger("sample-rate");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getSampleRate(java.lang.String r7) {
        /*
            java.lang.String r0 = "sample-rate"
            android.media.MediaExtractor r1 = new android.media.MediaExtractor
            r1.<init>()
            r2 = -1
            r1.setDataSource(r7)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            int r7 = r1.getTrackCount()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r3 = 0
        L10:
            if (r3 >= r7) goto L38
            android.media.MediaFormat r4 = r1.getTrackFormat(r3)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            java.lang.String r5 = "mime"
            java.lang.String r5 = r4.getString(r5)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            if (r5 == 0) goto L35
            java.lang.String r6 = "audio/"
            boolean r5 = r5.startsWith(r6)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            if (r5 == 0) goto L35
            boolean r5 = r4.containsKey(r0)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            if (r5 == 0) goto L35
            int r7 = r4.getInteger(r0)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            goto L39
        L31:
            r7 = move-exception
            goto L4f
        L33:
            r7 = move-exception
            goto L3d
        L35:
            int r3 = r3 + 1
            goto L10
        L38:
            r7 = r2
        L39:
            r1.release()
            goto L48
        L3d:
            java.lang.String r0 = "exteraGram"
            java.lang.String r3 = "Error detecting sample rate"
            com.google.android.exoplayer2.util.Log.m323e(r0, r3, r7)     // Catch: java.lang.Throwable -> L31
            r1.release()
            r7 = r2
        L48:
            if (r7 == r2) goto L4b
            goto L4e
        L4b:
            r7 = 48000(0xbb80, float:6.7262E-41)
        L4e:
            return r7
        L4f:
            r1.release()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.speech.utils.FormatConverter.getSampleRate(java.lang.String):int");
    }

    public static InputStream extractAndConvertToPcm(String str, boolean z) {
        return new LazyPcmInputStream(str, z);
    }

    public static class LazyPcmInputStream extends InputStream {
        private final MediaCodec codec;
        private ByteBuffer currentOutputBuffer;
        private final MediaExtractor extractor;
        private final ByteBuffer[] inputBuffers;
        private boolean isEOS;
        private final boolean limitToOneMinute;
        private ByteBuffer[] outputBuffers;
        private long totalDecodedDurationUs;
        private final long oneMinuteUs = 60000000;
        private final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

        public LazyPcmInputStream(String str, boolean z) throws IOException {
            this.limitToOneMinute = z;
            MediaExtractor mediaExtractor = new MediaExtractor();
            this.extractor = mediaExtractor;
            mediaExtractor.setDataSource(str);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(0);
            String string = trackFormat.getString("mime");
            if (!string.startsWith("audio/")) {
                Model$$ExternalSyntheticBUOutline0.m1247m("Not an audio file");
                throw null;
            }
            MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(string);
            this.codec = mediaCodecCreateDecoderByType;
            mediaCodecCreateDecoderByType.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
            mediaCodecCreateDecoderByType.start();
            this.inputBuffers = mediaCodecCreateDecoderByType.getInputBuffers();
            this.outputBuffers = mediaCodecCreateDecoderByType.getOutputBuffers();
            mediaExtractor.selectTrack(0);
        }

        @Override // java.io.InputStream
        public int read() {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) == -1) {
                return -1;
            }
            return bArr[0] & UByte.MAX_VALUE;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            if (this.isEOS) {
                return -1;
            }
            int i3 = 0;
            while (i3 < i2 && !this.isEOS) {
                ByteBuffer byteBuffer = this.currentOutputBuffer;
                if (byteBuffer == null || !byteBuffer.hasRemaining()) {
                    ByteBuffer nextOutputBuffer = getNextOutputBuffer();
                    this.currentOutputBuffer = nextOutputBuffer;
                    if (nextOutputBuffer == null) {
                        break;
                    }
                }
                int iMin = Math.min(i2 - i3, this.currentOutputBuffer.remaining());
                this.currentOutputBuffer.get(bArr, i + i3, iMin);
                i3 += iMin;
            }
            if (i3 > 0) {
                return i3;
            }
            return -1;
        }

        private ByteBuffer getNextOutputBuffer() {
            while (!this.isEOS) {
                int iDequeueInputBuffer = this.codec.dequeueInputBuffer(10000L);
                if (iDequeueInputBuffer >= 0) {
                    int sampleData = this.extractor.readSampleData(this.inputBuffers[iDequeueInputBuffer], 0);
                    if (sampleData < 0) {
                        this.codec.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                        this.isEOS = true;
                    } else {
                        this.codec.queueInputBuffer(iDequeueInputBuffer, 0, sampleData, this.extractor.getSampleTime(), 0);
                        this.extractor.advance();
                    }
                }
                int iDequeueOutputBuffer = this.codec.dequeueOutputBuffer(this.bufferInfo, 10000L);
                if (iDequeueOutputBuffer == -3) {
                    this.outputBuffers = this.codec.getOutputBuffers();
                } else if (iDequeueOutputBuffer != -2 && iDequeueOutputBuffer != -1) {
                    ByteBuffer byteBuffer = this.outputBuffers[iDequeueOutputBuffer];
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.bufferInfo.size);
                    byteBufferAllocate.put(byteBuffer);
                    byteBufferAllocate.flip();
                    this.codec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                    long j = this.totalDecodedDurationUs;
                    long j2 = j + (this.bufferInfo.presentationTimeUs - j);
                    this.totalDecodedDurationUs = j2;
                    if (this.limitToOneMinute && j2 >= 60000000) {
                        this.isEOS = true;
                    }
                    return byteBufferAllocate;
                }
            }
            return null;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.codec.stop();
            this.codec.release();
            this.extractor.release();
            super.close();
        }
    }
}
