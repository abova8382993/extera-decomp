package androidx.camera.camera2.pipe.compat;

import androidx.camera.camera2.pipe.CameraColorSpace;
import androidx.camera.camera2.pipe.compat.CameraCaptureSessionWrapper;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes3.dex */
public final class SessionConfigData {
    private final Executor executor;
    private final List inputConfiguration;
    private final List outputConfigurations;
    private final String sessionColorSpace;
    private final Map sessionParameters;
    private final int sessionTemplateId;
    private final int sessionType;
    private final CameraCaptureSessionWrapper.StateCallback stateCallback;

    public /* synthetic */ SessionConfigData(int i, List list, List list2, Executor executor, CameraCaptureSessionWrapper.StateCallback stateCallback, int i2, Map map, String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, list, list2, executor, stateCallback, i2, map, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L4
            return r0
        L4:
            boolean r1 = r5 instanceof androidx.camera.camera2.pipe.compat.SessionConfigData
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            androidx.camera.camera2.pipe.compat.SessionConfigData r5 = (androidx.camera.camera2.pipe.compat.SessionConfigData) r5
            int r1 = r4.sessionType
            int r3 = r5.sessionType
            if (r1 == r3) goto L13
            return r2
        L13:
            java.util.List r1 = r4.inputConfiguration
            java.util.List r3 = r5.inputConfiguration
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 != 0) goto L1e
            return r2
        L1e:
            java.util.List r1 = r4.outputConfigurations
            java.util.List r3 = r5.outputConfigurations
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 != 0) goto L29
            return r2
        L29:
            java.util.concurrent.Executor r1 = r4.executor
            java.util.concurrent.Executor r3 = r5.executor
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 != 0) goto L34
            return r2
        L34:
            androidx.camera.camera2.pipe.compat.CameraCaptureSessionWrapper$StateCallback r1 = r4.stateCallback
            androidx.camera.camera2.pipe.compat.CameraCaptureSessionWrapper$StateCallback r3 = r5.stateCallback
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 != 0) goto L3f
            return r2
        L3f:
            int r1 = r4.sessionTemplateId
            int r3 = r5.sessionTemplateId
            if (r1 == r3) goto L46
            return r2
        L46:
            java.util.Map r1 = r4.sessionParameters
            java.util.Map r3 = r5.sessionParameters
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r1 != 0) goto L51
            return r2
        L51:
            java.lang.String r1 = r4.sessionColorSpace
            java.lang.String r5 = r5.sessionColorSpace
            if (r1 != 0) goto L5d
            if (r5 != 0) goto L5b
            r5 = r0
            goto L64
        L5b:
            r5 = r2
            goto L64
        L5d:
            if (r5 != 0) goto L60
            goto L5b
        L60:
            boolean r5 = androidx.camera.camera2.pipe.CameraColorSpace.m1535equalsimpl0(r1, r5)
        L64:
            if (r5 != 0) goto L67
            return r2
        L67:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.pipe.compat.SessionConfigData.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = this.sessionType * 31;
        List list = this.inputConfiguration;
        int iHashCode = (((((((((((i + (list == null ? 0 : list.hashCode())) * 31) + this.outputConfigurations.hashCode()) * 31) + this.executor.hashCode()) * 31) + this.stateCallback.hashCode()) * 31) + this.sessionTemplateId) * 31) + this.sessionParameters.hashCode()) * 31;
        String str = this.sessionColorSpace;
        return iHashCode + (str != null ? CameraColorSpace.m1536hashCodeimpl(str) : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SessionConfigData(sessionType=");
        sb.append(this.sessionType);
        sb.append(", inputConfiguration=");
        sb.append(this.inputConfiguration);
        sb.append(", outputConfigurations=");
        sb.append(this.outputConfigurations);
        sb.append(", executor=");
        sb.append(this.executor);
        sb.append(", stateCallback=");
        sb.append(this.stateCallback);
        sb.append(", sessionTemplateId=");
        sb.append(this.sessionTemplateId);
        sb.append(", sessionParameters=");
        sb.append(this.sessionParameters);
        sb.append(", sessionColorSpace=");
        String str = this.sessionColorSpace;
        sb.append((Object) (str == null ? "null" : CameraColorSpace.m1538toStringimpl(str)));
        sb.append(')');
        return sb.toString();
    }

    private SessionConfigData(int i, List list, List outputConfigurations, Executor executor, CameraCaptureSessionWrapper.StateCallback stateCallback, int i2, Map sessionParameters, String str) {
        Intrinsics.checkNotNullParameter(outputConfigurations, "outputConfigurations");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(stateCallback, "stateCallback");
        Intrinsics.checkNotNullParameter(sessionParameters, "sessionParameters");
        this.sessionType = i;
        this.inputConfiguration = list;
        this.outputConfigurations = outputConfigurations;
        this.executor = executor;
        this.stateCallback = stateCallback;
        this.sessionTemplateId = i2;
        this.sessionParameters = sessionParameters;
        this.sessionColorSpace = str;
    }

    public final int getSessionType() {
        return this.sessionType;
    }

    public final List getInputConfiguration() {
        return this.inputConfiguration;
    }

    public final List getOutputConfigurations() {
        return this.outputConfigurations;
    }

    public final Executor getExecutor() {
        return this.executor;
    }

    public final CameraCaptureSessionWrapper.StateCallback getStateCallback() {
        return this.stateCallback;
    }

    public final int getSessionTemplateId() {
        return this.sessionTemplateId;
    }

    public final Map getSessionParameters() {
        return this.sessionParameters;
    }

    /* JADX INFO: renamed from: getSessionColorSpace-dxVZaPA */
    public final String m1866getSessionColorSpacedxVZaPA() {
        return this.sessionColorSpace;
    }
}
