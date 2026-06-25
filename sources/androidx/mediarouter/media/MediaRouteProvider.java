package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import p005c.g$$ExternalSyntheticBUOutline1;
import p005c.g$$ExternalSyntheticBUOutline2;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaRouteProvider {
    private Callback mCallback;
    private final Context mContext;
    private MediaRouteProviderDescriptor mDescriptor;
    private MediaRouteDiscoveryRequest mDiscoveryRequest;
    private final ProviderHandler mHandler;
    private final ProviderMetadata mMetadata;
    private boolean mPendingDescriptorChange;
    private boolean mPendingDiscoveryRequestChange;

    public static abstract class Callback {
        public abstract void onDescriptorChanged(MediaRouteProvider mediaRouteProvider, MediaRouteProviderDescriptor mediaRouteProviderDescriptor);
    }

    public abstract void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest);

    public MediaRouteProvider(Context context) {
        this(context, null);
    }

    public MediaRouteProvider(Context context, ProviderMetadata providerMetadata) {
        this.mHandler = new ProviderHandler();
        if (context == null) {
            g$$ExternalSyntheticBUOutline1.m207m("context must not be null");
            throw null;
        }
        this.mContext = context;
        if (providerMetadata == null) {
            this.mMetadata = new ProviderMetadata(new ComponentName(context, getClass()));
        } else {
            this.mMetadata = providerMetadata;
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ProviderMetadata getMetadata() {
        return this.mMetadata;
    }

    public final void setCallback(Callback callback) {
        MediaRouter.checkCallingThread();
        this.mCallback = callback;
    }

    public final MediaRouteDiscoveryRequest getDiscoveryRequest() {
        return this.mDiscoveryRequest;
    }

    public final void setDiscoveryRequest(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        MediaRouter.checkCallingThread();
        if (ObjectsCompat.equals(this.mDiscoveryRequest, mediaRouteDiscoveryRequest)) {
            return;
        }
        setDiscoveryRequestInternal(mediaRouteDiscoveryRequest);
    }

    public final void setDiscoveryRequestInternal(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        this.mDiscoveryRequest = mediaRouteDiscoveryRequest;
        if (this.mPendingDiscoveryRequestChange) {
            return;
        }
        this.mPendingDiscoveryRequestChange = true;
        this.mHandler.sendEmptyMessage(2);
    }

    public final void deliverDiscoveryRequestChanged() {
        this.mPendingDiscoveryRequestChange = false;
        onDiscoveryRequestChanged(this.mDiscoveryRequest);
    }

    public final MediaRouteProviderDescriptor getDescriptor() {
        return this.mDescriptor;
    }

    public final void setDescriptor(MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        MediaRouter.checkCallingThread();
        if (this.mDescriptor != mediaRouteProviderDescriptor) {
            this.mDescriptor = mediaRouteProviderDescriptor;
            if (this.mPendingDescriptorChange) {
                return;
            }
            this.mPendingDescriptorChange = true;
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public final void deliverDescriptorChanged() {
        this.mPendingDescriptorChange = false;
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onDescriptorChanged(this, this.mDescriptor);
        }
    }

    public RouteController onCreateRouteController(String str, RouteControllerOptions routeControllerOptions) {
        return onCreateRouteController(str);
    }

    public RouteController onCreateRouteController(String str) {
        if (str != null) {
            return null;
        }
        g$$ExternalSyntheticBUOutline1.m207m("routeId cannot be null");
        return null;
    }

    public RouteController onCreateRouteController(String str, String str2) {
        if (str == null) {
            g$$ExternalSyntheticBUOutline1.m207m("routeId cannot be null");
            return null;
        }
        if (str2 == null) {
            g$$ExternalSyntheticBUOutline1.m207m("routeGroupId cannot be null");
            return null;
        }
        return onCreateRouteController(str, RouteControllerOptions.EMPTY);
    }

    public DynamicGroupRouteController onCreateDynamicGroupRouteController(String str, RouteControllerOptions routeControllerOptions) {
        return onCreateDynamicGroupRouteController(str);
    }

    public DynamicGroupRouteController onCreateDynamicGroupRouteController(String str) {
        if (str != null) {
            return null;
        }
        g$$ExternalSyntheticBUOutline1.m207m("initialMemberRouteId cannot be null.");
        return null;
    }

    public static final class ProviderMetadata {
        private final ComponentName mComponentName;

        public ProviderMetadata(ComponentName componentName) {
            if (componentName == null) {
                g$$ExternalSyntheticBUOutline1.m207m("componentName must not be null");
                throw null;
            }
            this.mComponentName = componentName;
        }

        public String getPackageName() {
            return this.mComponentName.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mComponentName;
        }

        public String toString() {
            return "ProviderMetadata{ componentName=" + this.mComponentName.flattenToShortString() + " }";
        }
    }

    public static abstract class RouteController {
        public void onRelease() {
        }

        public void onSelect() {
        }

        public abstract void onSetVolume(int i);

        @Deprecated
        public void onUnselect() {
        }

        public abstract void onUpdateVolume(int i);

        public void onUnselect(int i) {
            onUnselect();
        }
    }

    /* JADX INFO: loaded from: classes4.dex */
    public static abstract class DynamicGroupRouteController extends RouteController {
        Executor mExecutor;
        OnDynamicRoutesChangedListener mListener;
        private final Object mLock = new Object();
        MediaRouteDescriptor mPendingGroupRoute;
        Collection<DynamicRouteDescriptor> mPendingRoutes;

        public interface OnDynamicRoutesChangedListener {
            void onRoutesChanged(DynamicGroupRouteController dynamicGroupRouteController, MediaRouteDescriptor mediaRouteDescriptor, Collection<DynamicRouteDescriptor> collection);
        }

        public String getGroupableSelectionTitle() {
            return null;
        }

        public String getTransferableSectionTitle() {
            return null;
        }

        public abstract void onAddMemberRoute(String str);

        public abstract void onRemoveMemberRoute(String str);

        public abstract void onUpdateMemberRoutes(List<String> list);

        public void setOnDynamicRoutesChangedListener(Executor executor, final OnDynamicRoutesChangedListener onDynamicRoutesChangedListener) {
            synchronized (this.mLock) {
                try {
                    if (executor == null) {
                        throw new NullPointerException("Executor shouldn't be null");
                    }
                    if (onDynamicRoutesChangedListener == null) {
                        throw new NullPointerException("Listener shouldn't be null");
                    }
                    this.mExecutor = executor;
                    this.mListener = onDynamicRoutesChangedListener;
                    Collection<DynamicRouteDescriptor> collection = this.mPendingRoutes;
                    if (collection != null && !collection.isEmpty()) {
                        final MediaRouteDescriptor mediaRouteDescriptor = this.mPendingGroupRoute;
                        final Collection<DynamicRouteDescriptor> collection2 = this.mPendingRoutes;
                        this.mPendingGroupRoute = null;
                        this.mPendingRoutes = null;
                        this.mExecutor.execute(new Runnable() { // from class: androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaRouteProvider.DynamicGroupRouteController.$r8$lambda$GkvzHmFi7J0hPJ2hkNMUQ5T3hOI(this.f$0, onDynamicRoutesChangedListener, mediaRouteDescriptor, collection2);
                            }
                        });
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static /* synthetic */ void $r8$lambda$GkvzHmFi7J0hPJ2hkNMUQ5T3hOI(DynamicGroupRouteController dynamicGroupRouteController, OnDynamicRoutesChangedListener onDynamicRoutesChangedListener, MediaRouteDescriptor mediaRouteDescriptor, Collection collection) {
            dynamicGroupRouteController.getClass();
            onDynamicRoutesChangedListener.onRoutesChanged(dynamicGroupRouteController, mediaRouteDescriptor, collection);
        }

        public final void notifyDynamicRoutesChanged(final MediaRouteDescriptor mediaRouteDescriptor, final Collection<DynamicRouteDescriptor> collection) {
            if (mediaRouteDescriptor == null) {
                g$$ExternalSyntheticBUOutline2.m208m("groupRoute must not be null");
                return;
            }
            if (collection == null) {
                g$$ExternalSyntheticBUOutline2.m208m("dynamicRoutes must not be null");
                return;
            }
            synchronized (this.mLock) {
                try {
                    Executor executor = this.mExecutor;
                    if (executor != null) {
                        final OnDynamicRoutesChangedListener onDynamicRoutesChangedListener = this.mListener;
                        executor.execute(new Runnable() { // from class: androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaRouteProvider.DynamicGroupRouteController.$r8$lambda$jW55csm3zybsNPVmkVatnJA7rDI(this.f$0, onDynamicRoutesChangedListener, mediaRouteDescriptor, collection);
                            }
                        });
                    } else {
                        this.mPendingGroupRoute = mediaRouteDescriptor;
                        this.mPendingRoutes = new ArrayList(collection);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static /* synthetic */ void $r8$lambda$jW55csm3zybsNPVmkVatnJA7rDI(DynamicGroupRouteController dynamicGroupRouteController, OnDynamicRoutesChangedListener onDynamicRoutesChangedListener, MediaRouteDescriptor mediaRouteDescriptor, Collection collection) {
            dynamicGroupRouteController.getClass();
            onDynamicRoutesChangedListener.onRoutesChanged(dynamicGroupRouteController, mediaRouteDescriptor, collection);
        }

        public static final class DynamicRouteDescriptor {
            final boolean mIsGroupable;
            final boolean mIsTransferable;
            final boolean mIsUnselectable;
            final MediaRouteDescriptor mMediaRouteDescriptor;
            final int mSelectionState;

            public DynamicRouteDescriptor(MediaRouteDescriptor mediaRouteDescriptor, int i, boolean z, boolean z2, boolean z3) {
                this.mMediaRouteDescriptor = mediaRouteDescriptor;
                this.mSelectionState = i;
                this.mIsUnselectable = z;
                this.mIsGroupable = z2;
                this.mIsTransferable = z3;
            }

            public MediaRouteDescriptor getRouteDescriptor() {
                return this.mMediaRouteDescriptor;
            }

            public int getSelectionState() {
                return this.mSelectionState;
            }

            public boolean isUnselectable() {
                return this.mIsUnselectable;
            }

            public boolean isGroupable() {
                return this.mIsGroupable;
            }

            public boolean isTransferable() {
                return this.mIsTransferable;
            }

            public static DynamicRouteDescriptor fromBundle(Bundle bundle) {
                if (bundle == null) {
                    return null;
                }
                return new DynamicRouteDescriptor(MediaRouteDescriptor.fromBundle(bundle.getBundle("mrDescriptor")), bundle.getInt("selectionState", 1), bundle.getBoolean("isUnselectable", false), bundle.getBoolean("isGroupable", false), bundle.getBoolean("isTransferable", false));
            }

            public static final class Builder {
                private final MediaRouteDescriptor mRouteDescriptor;
                private int mSelectionState = 1;
                private boolean mIsUnselectable = false;
                private boolean mIsGroupable = false;
                private boolean mIsTransferable = false;

                public Builder(MediaRouteDescriptor mediaRouteDescriptor) {
                    if (mediaRouteDescriptor == null) {
                        g$$ExternalSyntheticBUOutline2.m208m("descriptor must not be null");
                        throw null;
                    }
                    this.mRouteDescriptor = mediaRouteDescriptor;
                }

                public Builder setSelectionState(int i) {
                    this.mSelectionState = i;
                    return this;
                }

                public Builder setIsUnselectable(boolean z) {
                    this.mIsUnselectable = z;
                    return this;
                }

                public Builder setIsGroupable(boolean z) {
                    this.mIsGroupable = z;
                    return this;
                }

                public Builder setIsTransferable(boolean z) {
                    this.mIsTransferable = z;
                    return this;
                }

                public DynamicRouteDescriptor build() {
                    return new DynamicRouteDescriptor(this.mRouteDescriptor, this.mSelectionState, this.mIsUnselectable, this.mIsGroupable, this.mIsTransferable);
                }
            }
        }
    }

    /* JADX INFO: loaded from: classes4.dex */
    public static final class RouteControllerOptions {
        static final RouteControllerOptions EMPTY = new Builder().build();
        private final Bundle mBundle;

        public RouteControllerOptions(Bundle bundle) {
            this.mBundle = new Bundle(bundle);
        }

        public Bundle asBundle() {
            return this.mBundle;
        }

        public static final class Builder {
            private final Bundle mBundle = new Bundle();

            public Builder setClientPackageName(String str) {
                this.mBundle.putString("clientPackageName", str);
                return this;
            }

            public RouteControllerOptions build() {
                return new RouteControllerOptions(this.mBundle);
            }
        }
    }

    public final class ProviderHandler extends Handler {
        public ProviderHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                MediaRouteProvider.this.deliverDescriptorChanged();
            } else {
                if (i != 2) {
                    return;
                }
                MediaRouteProvider.this.deliverDiscoveryRequestChanged();
            }
        }
    }
}
