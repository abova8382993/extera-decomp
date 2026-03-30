package androidx.camera.camera2.pipe.internal;

import androidx.camera.camera2.adapter.EvCompValue$$ExternalSyntheticBackport0;
import androidx.camera.camera2.pipe.CameraTimestamp;
import androidx.camera.camera2.pipe.CameraTimestamp$$ExternalSyntheticBackport0;
import androidx.camera.camera2.pipe.FrameNumber;
import androidx.camera.camera2.pipe.OutputStatus;
import androidx.camera.camera2.pipe.internal.OutputResult;
import androidx.camera.camera2.pipe.media.Finalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* JADX INFO: loaded from: classes3.dex */
public final class OutputDistributor implements AutoCloseable {
    private final Map availableOutputs;
    private long cameraOutputSequenceNumbers;
    private boolean closed;
    private long lastFailedCameraOutputNumber;
    private long lastFailedFrameNumber;
    private final Object lock;
    private final int maximumCachedOutputs;
    private long newestCameraOutputNumber;
    private long newestFrameNumber;
    private final Finalizer outputFinalizer;
    private final OutputMatcher outputMatcher;
    private final List startedOutputs;

    public interface OutputListener {
        /* JADX INFO: renamed from: onOutputComplete-3ejhThk */
        void mo1943onOutputComplete3ejhThk(long j, long j2, long j3, long j4, Object obj);
    }

    public OutputDistributor(int i, Finalizer outputFinalizer, OutputMatcher outputMatcher) {
        Intrinsics.checkNotNullParameter(outputFinalizer, "outputFinalizer");
        Intrinsics.checkNotNullParameter(outputMatcher, "outputMatcher");
        this.maximumCachedOutputs = i;
        this.outputFinalizer = outputFinalizer;
        this.outputMatcher = outputMatcher;
        this.lock = new Object();
        this.cameraOutputSequenceNumbers = 1L;
        this.newestCameraOutputNumber = Long.MIN_VALUE;
        this.newestFrameNumber = FrameNumber.m1643constructorimpl(Long.MIN_VALUE);
        this.lastFailedFrameNumber = Long.MIN_VALUE;
        this.lastFailedCameraOutputNumber = Long.MIN_VALUE;
        this.startedOutputs = new ArrayList();
        this.availableOutputs = new LinkedHashMap();
    }

    public /* synthetic */ OutputDistributor(int i, Finalizer finalizer, OutputMatcher outputMatcher, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 3 : i, finalizer, outputMatcher);
    }

    /* JADX WARN: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x018e  */
    /* JADX INFO: renamed from: onOutputStarted-qGubWw0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m1949onOutputStartedqGubWw0(long r22, long r24, long r26, androidx.camera.camera2.pipe.internal.OutputDistributor.OutputListener r28) {
        /*
            Method dump skipped, instruction units count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.internal.OutputDistributor.m1949onOutputStartedqGubWw0(long, long, long, androidx.camera.camera2.pipe.internal.OutputDistributor$OutputListener):void");
    }

    /* JADX INFO: renamed from: onOutputResult-DvZWqE8, reason: not valid java name */
    public final void m1948onOutputResultDvZWqE8(long j, Object obj) {
        Object objM1954boximpl;
        List listRemoveOutputsOlderThan;
        Object next;
        synchronized (this.lock) {
            try {
                if (this.closed || this.outputMatcher.fuzzyEqual(this.lastFailedCameraOutputNumber, j)) {
                    objM1954boximpl = OutputResult.m1954boximpl(obj);
                } else {
                    Iterator it = this.startedOutputs.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (this.outputMatcher.fuzzyEqual(((StartedOutput) next).getCameraOutputNumber(), j)) {
                                break;
                            }
                        }
                    }
                    StartedOutput startedOutput = (StartedOutput) next;
                    if (startedOutput != null) {
                        listRemoveOutputsOlderThan = removeOutputsOlderThan(startedOutput);
                        startedOutput.m1950completeWithDvZWqE8(j, obj);
                        this.startedOutputs.remove(startedOutput);
                        objM1954boximpl = null;
                    } else {
                        this.availableOutputs.put(Long.valueOf(j), OutputResult.m1954boximpl(obj));
                        if (this.availableOutputs.size() > this.maximumCachedOutputs) {
                            objM1954boximpl = this.availableOutputs.remove(Long.valueOf(((Number) CollectionsKt.first(this.availableOutputs.keySet())).longValue()));
                        } else {
                            objM1954boximpl = null;
                            listRemoveOutputsOlderThan = null;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                listRemoveOutputsOlderThan = null;
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        OutputResult outputResult = (OutputResult) objM1954boximpl;
        if (outputResult != null) {
            Object objM1961unboximpl = outputResult.m1961unboximpl();
            Object obj2 = OutputResult.m1957getAvailableimpl(objM1961unboximpl) ? objM1961unboximpl : null;
            if (obj2 != null) {
                this.outputFinalizer.finalize(obj2);
            }
        }
        if (listRemoveOutputsOlderThan != null) {
            Iterator it2 = listRemoveOutputsOlderThan.iterator();
            while (it2.hasNext()) {
                ((StartedOutput) it2.next()).m1951completeWithFailuretXNfJfc(OutputStatus.Companion.m1681getERROR_OUTPUT_MISSINGU7r42EA());
            }
        }
    }

    /* JADX INFO: renamed from: onOutputFailure-Vw7M1qk, reason: not valid java name */
    public final void m1947onOutputFailureVw7M1qk(long j) {
        synchronized (this.lock) {
            try {
                if (this.closed) {
                    return;
                }
                this.lastFailedFrameNumber = j;
                Iterator it = this.startedOutputs.iterator();
                StartedOutput startedOutput = null;
                boolean z = false;
                Object obj = null;
                while (true) {
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (FrameNumber.m1645equalsimpl0(((StartedOutput) next).m1952getCameraFrameNumberUgla2oM(), j)) {
                            if (z) {
                                break;
                            }
                            z = true;
                            obj = next;
                        }
                    } else if (!z) {
                    }
                }
                obj = null;
                StartedOutput startedOutput2 = (StartedOutput) obj;
                if (startedOutput2 != null) {
                    this.lastFailedCameraOutputNumber = startedOutput2.getCameraOutputNumber();
                    this.startedOutputs.remove(startedOutput2);
                    Unit unit = Unit.INSTANCE;
                    startedOutput = startedOutput2;
                }
                if (startedOutput != null) {
                    startedOutput.m1951completeWithFailuretXNfJfc(OutputStatus.Companion.m1680getERROR_OUTPUT_FAILEDU7r42EA());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final List removeOutputsOlderThan(StartedOutput startedOutput) {
        return removeOutputsOlderThan(startedOutput.isOutOfOrder(), startedOutput.getCameraOutputSequence(), startedOutput.getCameraOutputNumber());
    }

    private final List removeOutputsOlderThan(boolean z, long j, long j2) {
        List list = this.startedOutputs;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            StartedOutput startedOutput = (StartedOutput) obj;
            if (startedOutput.isOutOfOrder() == z && startedOutput.getCameraOutputSequence() < j && startedOutput.getCameraOutputNumber() < j2) {
                arrayList.add(obj);
            }
        }
        this.startedOutputs.removeAll(arrayList);
        return arrayList;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            List mutableList = CollectionsKt.toMutableList(this.availableOutputs.values());
            this.availableOutputs.clear();
            List mutableList2 = CollectionsKt.toMutableList((Collection) this.startedOutputs);
            this.startedOutputs.clear();
            Unit unit = Unit.INSTANCE;
            Iterator it = mutableList.iterator();
            while (it.hasNext()) {
                Object objM1961unboximpl = ((OutputResult) it.next()).m1961unboximpl();
                Finalizer finalizer = this.outputFinalizer;
                if (!OutputResult.m1957getAvailableimpl(objM1961unboximpl)) {
                    objM1961unboximpl = null;
                }
                finalizer.finalize(objM1961unboximpl);
            }
            Iterator it2 = mutableList2.iterator();
            while (it2.hasNext()) {
                ((StartedOutput) it2.next()).m1951completeWithFailuretXNfJfc(OutputStatus.Companion.m1679getERROR_OUTPUT_ABORTEDU7r42EA());
            }
        }
    }

    private static final class StartedOutput {
        private final long cameraFrameNumber;
        private final long cameraOutputNumber;
        private final long cameraOutputSequence;
        private final long cameraTimestamp;
        private final AtomicBoolean complete;
        private final boolean isOutOfOrder;
        private final OutputListener outputListener;

        public /* synthetic */ StartedOutput(boolean z, long j, long j2, long j3, long j4, OutputListener outputListener, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, j, j2, j3, j4, outputListener);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StartedOutput)) {
                return false;
            }
            StartedOutput startedOutput = (StartedOutput) obj;
            return this.isOutOfOrder == startedOutput.isOutOfOrder && FrameNumber.m1645equalsimpl0(this.cameraFrameNumber, startedOutput.cameraFrameNumber) && CameraTimestamp.m1618equalsimpl0(this.cameraTimestamp, startedOutput.cameraTimestamp) && this.cameraOutputSequence == startedOutput.cameraOutputSequence && this.cameraOutputNumber == startedOutput.cameraOutputNumber && Intrinsics.areEqual(this.outputListener, startedOutput.outputListener);
        }

        public int hashCode() {
            return (((((((((EvCompValue$$ExternalSyntheticBackport0.m10m(this.isOutOfOrder) * 31) + FrameNumber.m1646hashCodeimpl(this.cameraFrameNumber)) * 31) + CameraTimestamp.m1619hashCodeimpl(this.cameraTimestamp)) * 31) + CameraTimestamp$$ExternalSyntheticBackport0.m47m(this.cameraOutputSequence)) * 31) + CameraTimestamp$$ExternalSyntheticBackport0.m47m(this.cameraOutputNumber)) * 31) + this.outputListener.hashCode();
        }

        public String toString() {
            return "StartedOutput(isOutOfOrder=" + this.isOutOfOrder + ", cameraFrameNumber=" + ((Object) FrameNumber.m1647toStringimpl(this.cameraFrameNumber)) + ", cameraTimestamp=" + ((Object) CameraTimestamp.m1620toStringimpl(this.cameraTimestamp)) + ", cameraOutputSequence=" + this.cameraOutputSequence + ", cameraOutputNumber=" + this.cameraOutputNumber + ", outputListener=" + this.outputListener + ')';
        }

        private StartedOutput(boolean z, long j, long j2, long j3, long j4, OutputListener outputListener) {
            Intrinsics.checkNotNullParameter(outputListener, "outputListener");
            this.isOutOfOrder = z;
            this.cameraFrameNumber = j;
            this.cameraTimestamp = j2;
            this.cameraOutputSequence = j3;
            this.cameraOutputNumber = j4;
            this.outputListener = outputListener;
            this.complete = AtomicFU.atomic(false);
        }

        public final boolean isOutOfOrder() {
            return this.isOutOfOrder;
        }

        /* JADX INFO: renamed from: getCameraFrameNumber-Ugla2oM, reason: not valid java name */
        public final long m1952getCameraFrameNumberUgla2oM() {
            return this.cameraFrameNumber;
        }

        public final long getCameraOutputSequence() {
            return this.cameraOutputSequence;
        }

        public final long getCameraOutputNumber() {
            return this.cameraOutputNumber;
        }

        /* JADX INFO: renamed from: completeWithFailure-tXNfJfc, reason: not valid java name */
        public final void m1951completeWithFailuretXNfJfc(int i) {
            OutputResult.Companion companion = OutputResult.Companion;
            m1950completeWithDvZWqE8(-1L, OutputResult.m1955constructorimpl(OutputStatus.m1672boximpl(i)));
        }

        /* JADX INFO: renamed from: completeWith-DvZWqE8, reason: not valid java name */
        public final void m1950completeWithDvZWqE8(long j, Object obj) {
            if (!this.complete.compareAndSet(false, true)) {
                throw new IllegalStateException(("Output " + this.cameraOutputSequence + " at " + ((Object) FrameNumber.m1647toStringimpl(this.cameraFrameNumber)) + " for " + j + " was completed multiple times!").toString());
            }
            this.outputListener.mo1943onOutputComplete3ejhThk(this.cameraFrameNumber, this.cameraTimestamp, this.cameraOutputSequence, j, obj);
        }
    }
}
