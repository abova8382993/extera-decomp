package com.google.android.play.core.integrity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.play.core.integrity.model.C1766a;
import java.util.Locale;
import p005c.g$$ExternalSyntheticBUOutline1;

/* JADX INFO: loaded from: classes5.dex */
public class IntegrityServiceException extends ApiException {

    /* JADX INFO: renamed from: a */
    private final Throwable f496a;

    public IntegrityServiceException(int i, Throwable th) {
        super(new Status(i, String.format(Locale.ROOT, "Integrity API error (%d): %s.", Integer.valueOf(i), C1766a.m435a(i))));
        if (i != 0) {
            this.f496a = th;
        } else {
            g$$ExternalSyntheticBUOutline1.m207m("ErrorCode should not be 0.");
            throw null;
        }
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable getCause() {
        return this.f496a;
    }

    public int getErrorCode() {
        return super.getStatusCode();
    }
}
