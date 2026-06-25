package com.google.android.gms.cast;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Cast {
    public static final Api<CastOptions> API;
    public static final CastApi CastApi;
    static final Api.AbstractClientBuilder zza;

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    @Deprecated
    public interface CastApi {
    }

    public static final class CastOptions implements Api.ApiOptions {
        final CastDevice zza;
        final Listener zzb;
        final Bundle zzc;
        final int zzd;
        final String zze = UUID.randomUUID().toString();

        public static final class Builder {
            final CastDevice zza;
            final Listener zzb;
            private int zzc;
            private Bundle zzd;

            public Builder(CastDevice castDevice, Listener listener) {
                Preconditions.checkNotNull(castDevice, "CastDevice parameter cannot be null");
                Preconditions.checkNotNull(listener, "CastListener parameter cannot be null");
                this.zza = castDevice;
                this.zzb = listener;
                this.zzc = 0;
            }

            public CastOptions build() {
                return new CastOptions(this, null);
            }

            public final Builder zza(Bundle bundle) {
                this.zzd = bundle;
                return this;
            }

            public final /* synthetic */ int zzb() {
                return this.zzc;
            }

            public final /* synthetic */ Bundle zzc() {
                return this.zzd;
            }
        }

        public /* synthetic */ CastOptions(Builder builder, byte[] bArr) {
            this.zza = builder.zza;
            this.zzb = builder.zzb;
            this.zzd = builder.zzb();
            this.zzc = builder.zzc();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CastOptions)) {
                return false;
            }
            CastOptions castOptions = (CastOptions) obj;
            return Objects.equal(this.zza, castOptions.zza) && Objects.checkBundlesEquality(this.zzc, castOptions.zzc) && this.zzd == castOptions.zzd && Objects.equal(this.zze, castOptions.zze);
        }

        public int hashCode() {
            return Objects.hashCode(this.zza, this.zzc, Integer.valueOf(this.zzd), this.zze);
        }
    }

    public static class Listener {
        public abstract void onActiveInputStateChanged(int i);

        public abstract void onApplicationDisconnected(int i);

        public abstract void onApplicationMetadataChanged(ApplicationMetadata applicationMetadata);

        public abstract void onApplicationStatusChanged();

        public void onDeviceNameChanged() {
        }

        public abstract void onStandbyStateChanged(int i);

        public abstract void onVolumeChanged();
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    static {
        zze zzeVar = new zze();
        zza = zzeVar;
        API = new Api<>("Cast.API", zzeVar, com.google.android.gms.cast.internal.zzal.zza);
        CastApi = new zzm();
    }

    public static zzq zza(Context context, CastOptions castOptions) {
        return new zzbm(context, castOptions);
    }
}
