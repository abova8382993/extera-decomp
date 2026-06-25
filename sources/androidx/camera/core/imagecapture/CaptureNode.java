package androidx.camera.core.imagecapture;

import android.util.Size;
import android.view.Surface;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.ImageReaderProxys;
import androidx.camera.core.Logger;
import androidx.camera.core.MetadataImageReader;
import androidx.camera.core.SafeCloseImageReaderProxy;
import androidx.camera.core.imagecapture.CaptureNode;
import androidx.camera.core.imagecapture.ProcessingNode;
import androidx.camera.core.imagecapture.TakePictureManager;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureCallbacks;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.processing.Edge;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
class CaptureNode {
    private AbstractC0260In mInputEdge;
    private ProcessingNode.AbstractC0263In mOutputEdge;
    SafeCloseImageReaderProxy mSafeCloseImageReaderForPostview;
    SafeCloseImageReaderProxy mSafeCloseImageReaderProxy;
    SafeCloseImageReaderProxy mSecondarySafeCloseImageReaderProxy;
    ProcessingRequest mCurrentRequest = null;
    private NoMetadataImageReader mNoMetadataImageReader = null;

    public ProcessingNode.AbstractC0263In transform(AbstractC0260In abstractC0260In) {
        Consumer<ProcessingRequest> consumer;
        ImageReaderProxy imageReaderProxy;
        MetadataImageReader metadataImageReader;
        ImageReaderProxy imageReaderProxy2;
        Preconditions.checkState(this.mInputEdge == null && this.mSafeCloseImageReaderProxy == null, "CaptureNode does not support recreation yet.");
        this.mInputEdge = abstractC0260In;
        Size size = abstractC0260In.getSize();
        int inputFormat = abstractC0260In.getInputFormat();
        boolean zIsVirtualCamera = abstractC0260In.isVirtualCamera();
        CameraCaptureCallback c02581 = new C02581();
        boolean z = abstractC0260In.getOutputFormats().size() > 1;
        CameraCaptureCallback cameraCaptureCallbackCreateComboCallback = null;
        if (!zIsVirtualCamera) {
            abstractC0260In.getImageReaderProxyProvider();
            if (z) {
                MetadataImageReader metadataImageReader2 = new MetadataImageReader(size.getWidth(), size.getHeight(), 256, 4);
                CameraCaptureCallback cameraCaptureCallbackCreateComboCallback2 = CameraCaptureCallbacks.createComboCallback(c02581, metadataImageReader2.getCameraCaptureCallback());
                metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), 32, 4);
                CameraCaptureCallback[] cameraCaptureCallbackArr = {c02581, metadataImageReader.getCameraCaptureCallback()};
                c02581 = cameraCaptureCallbackCreateComboCallback2;
                cameraCaptureCallbackCreateComboCallback = CameraCaptureCallbacks.createComboCallback(cameraCaptureCallbackArr);
                imageReaderProxy2 = metadataImageReader2;
            } else {
                MetadataImageReader metadataImageReader3 = new MetadataImageReader(size.getWidth(), size.getHeight(), inputFormat, 4);
                c02581 = CameraCaptureCallbacks.createComboCallback(c02581, metadataImageReader3.getCameraCaptureCallback());
                imageReaderProxy2 = metadataImageReader3;
                metadataImageReader = null;
            }
            consumer = new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.onRequestAvailable((ProcessingRequest) obj);
                }
            };
            imageReaderProxy = imageReaderProxy2;
        } else {
            abstractC0260In.getImageReaderProxyProvider();
            NoMetadataImageReader noMetadataImageReader = new NoMetadataImageReader(createImageReaderProxy(null, size.getWidth(), size.getHeight(), inputFormat));
            this.mNoMetadataImageReader = noMetadataImageReader;
            consumer = new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    CaptureNode.m1856$r8$lambda$7aA5yFpboCKw0n21h0OvTjZyL0(this.f$0, (ProcessingRequest) obj);
                }
            };
            imageReaderProxy = noMetadataImageReader;
            metadataImageReader = null;
        }
        abstractC0260In.setCameraCaptureCallback(c02581);
        if (z && cameraCaptureCallbackCreateComboCallback != null) {
            abstractC0260In.setSecondaryCameraCaptureCallback(cameraCaptureCallbackCreateComboCallback);
        }
        Surface surface = imageReaderProxy.getSurface();
        Objects.requireNonNull(surface);
        abstractC0260In.setSurface(surface);
        this.mSafeCloseImageReaderProxy = new SafeCloseImageReaderProxy(imageReaderProxy);
        setOnImageAvailableListener(imageReaderProxy);
        abstractC0260In.getPostviewSettings();
        if (z && metadataImageReader != null) {
            abstractC0260In.setSecondarySurface(metadataImageReader.getSurface());
            this.mSecondarySafeCloseImageReaderProxy = new SafeCloseImageReaderProxy(metadataImageReader);
            setOnImageAvailableListener(metadataImageReader);
        }
        abstractC0260In.getRequestEdge().setListener(consumer);
        abstractC0260In.getErrorEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.sendCaptureError((TakePictureManager.CaptureError) obj);
            }
        });
        ProcessingNode.AbstractC0263In abstractC0263InM89of = ProcessingNode.AbstractC0263In.m89of(abstractC0260In.getInputFormat(), abstractC0260In.getOutputFormats());
        this.mOutputEdge = abstractC0263InM89of;
        return abstractC0263InM89of;
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.CaptureNode$1 */
    public class C02581 extends CameraCaptureCallback {
        public C02581() {
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureStarted(int i) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CaptureNode.this.mCurrentRequest;
                }
            });
        }

        @Override // androidx.camera.core.impl.CameraCaptureCallback
        public void onCaptureProcessProgressed(int i, final int i2) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CaptureNode.C02581 c02581 = this.f$0;
                    int i3 = i2;
                    CaptureNode.this.mCurrentRequest;
                }
            });
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$7aA-5yFpboCKw0n21h0OvTjZyL0, reason: not valid java name */
    public static /* synthetic */ void m1856$r8$lambda$7aA5yFpboCKw0n21h0OvTjZyL0(CaptureNode captureNode, ProcessingRequest processingRequest) {
        captureNode.onRequestAvailable(processingRequest);
        captureNode.mNoMetadataImageReader.acceptProcessingRequest(processingRequest);
    }

    public static /* synthetic */ void $r8$lambda$pMuiQYF7TOgEFV3jPwxF5CXMq8Y(CaptureNode captureNode, ImageReaderProxy imageReaderProxy) {
        captureNode.getClass();
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                captureNode.propagatePostviewImage(imageProxyAcquireLatestImage);
            }
        } catch (IllegalStateException e) {
            Logger.m77e("CaptureNode", "Failed to acquire latest image of postview", e);
        }
    }

    private static ImageReaderProxy createImageReaderProxy(ImageReaderProxyProvider imageReaderProxyProvider, int i, int i2, int i3) {
        if (imageReaderProxyProvider != null) {
            return imageReaderProxyProvider.newInstance(i, i2, i3, 4, 0L);
        }
        return ImageReaderProxys.createIsolatedReader(i, i2, i3, 4);
    }

    private void setOnImageAvailableListener(ImageReaderProxy imageReaderProxy) {
        imageReaderProxy.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda7
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy2) {
                CaptureNode.$r8$lambda$7C7m1vQE0Vvq_cf9LPRtus1dPGk(this.f$0, imageReaderProxy2);
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    public static /* synthetic */ void $r8$lambda$7C7m1vQE0Vvq_cf9LPRtus1dPGk(CaptureNode captureNode, ImageReaderProxy imageReaderProxy) {
        captureNode.getClass();
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            StringBuilder sb = new StringBuilder("OnImageAvailableListener: mCurrentRequest ID = ");
            ProcessingRequest processingRequest = captureNode.mCurrentRequest;
            sb.append((Object) null);
            sb.append(", image.isNull = ");
            sb.append(imageProxyAcquireLatestImage == null);
            Logger.m74d("CaptureNode", sb.toString());
            if (imageProxyAcquireLatestImage != null) {
                captureNode.onImageProxyAvailable(imageProxyAcquireLatestImage);
            } else {
                ProcessingRequest processingRequest2 = captureNode.mCurrentRequest;
            }
        } catch (IllegalStateException unused) {
            ProcessingRequest processingRequest3 = captureNode.mCurrentRequest;
        }
    }

    public void onImageProxyAvailable(ImageProxy imageProxy) {
        Threads.checkMainThread();
        Logger.m79w("CaptureNode", "Discarding ImageProxy which was inadvertently acquired: " + imageProxy);
        imageProxy.close();
    }

    private void matchAndPropagateImage(ImageProxy imageProxy) {
        Threads.checkMainThread();
        ProcessingNode.AbstractC0263In abstractC0263In = this.mOutputEdge;
        Objects.requireNonNull(abstractC0263In);
        abstractC0263In.getEdge().accept(ProcessingNode.InputPacket.m90of(this.mCurrentRequest, imageProxy));
        ProcessingRequest processingRequest = this.mCurrentRequest;
        AbstractC0260In abstractC0260In = this.mInputEdge;
        if (abstractC0260In != null) {
            abstractC0260In.getOutputFormats().size();
        }
        processingRequest.onImageCaptured();
    }

    private void propagatePostviewImage(ImageProxy imageProxy) {
        Logger.m79w("CaptureNode", "Postview image is closed due to request completed or aborted");
        imageProxy.close();
    }

    public void onRequestAvailable(final ProcessingRequest processingRequest) {
        Threads.checkMainThread();
        Preconditions.checkState(processingRequest.getStageIds().size() == 1, "only one capture stage is supported.");
        Preconditions.checkState(getCapacity() > 0, "Too many acquire images. Close image to be able to process next.");
        this.mCurrentRequest = processingRequest;
        Futures.addCallback(processingRequest.getCaptureFuture(), new FutureCallback<Void>() { // from class: androidx.camera.core.imagecapture.CaptureNode.2
            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onSuccess(Void r1) {
            }

            @Override // androidx.camera.core.impl.utils.futures.FutureCallback
            public void onFailure(Throwable th) {
                Threads.checkMainThread();
                if (processingRequest == CaptureNode.this.mCurrentRequest) {
                    Logger.m79w("CaptureNode", "request aborted, id=" + CaptureNode.this.mCurrentRequest.getRequestId());
                    if (CaptureNode.this.mNoMetadataImageReader != null) {
                        CaptureNode.this.mNoMetadataImageReader.clearProcessingRequest();
                    }
                    CaptureNode.this.mCurrentRequest = null;
                }
            }
        }, CameraXExecutors.directExecutor());
    }

    public void sendCaptureError(TakePictureManager.CaptureError captureError) {
        Threads.checkMainThread();
    }

    public void release() {
        Threads.checkMainThread();
        AbstractC0260In abstractC0260In = this.mInputEdge;
        Objects.requireNonNull(abstractC0260In);
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mSafeCloseImageReaderProxy;
        Objects.requireNonNull(safeCloseImageReaderProxy);
        releaseInputResources(abstractC0260In, safeCloseImageReaderProxy, this.mSecondarySafeCloseImageReaderProxy, this.mSafeCloseImageReaderForPostview);
    }

    private void releaseInputResources(AbstractC0260In abstractC0260In, final SafeCloseImageReaderProxy safeCloseImageReaderProxy, final SafeCloseImageReaderProxy safeCloseImageReaderProxy2, final SafeCloseImageReaderProxy safeCloseImageReaderProxy3) {
        abstractC0260In.getSurface().close();
        abstractC0260In.getSurface().getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                safeCloseImageReaderProxy.safeClose();
            }
        }, CameraXExecutors.mainThreadExecutor());
        if (abstractC0260In.getPostviewSurface() != null) {
            abstractC0260In.getPostviewSurface().close();
            abstractC0260In.getPostviewSurface().getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    CaptureNode.$r8$lambda$rWWtbZD2YG5XEuVxruKY108_pIc(safeCloseImageReaderProxy3);
                }
            }, CameraXExecutors.mainThreadExecutor());
        }
        if (abstractC0260In.getOutputFormats().size() <= 1 || abstractC0260In.getSecondarySurface() == null) {
            return;
        }
        abstractC0260In.getSecondarySurface().close();
        abstractC0260In.getSecondarySurface().getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.imagecapture.CaptureNode$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                CaptureNode.$r8$lambda$kl76V5Ze8dYKadnZQeFK_044WOE(safeCloseImageReaderProxy2);
            }
        }, CameraXExecutors.mainThreadExecutor());
    }

    public static /* synthetic */ void $r8$lambda$rWWtbZD2YG5XEuVxruKY108_pIc(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        if (safeCloseImageReaderProxy != null) {
            safeCloseImageReaderProxy.safeClose();
        }
    }

    public static /* synthetic */ void $r8$lambda$kl76V5Ze8dYKadnZQeFK_044WOE(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        if (safeCloseImageReaderProxy != null) {
            safeCloseImageReaderProxy.safeClose();
        }
    }

    public int getCapacity() {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        return this.mSafeCloseImageReaderProxy.getCapacity();
    }

    public void setOnImageCloseListener(ForwardingImageProxy.OnImageCloseListener onImageCloseListener) {
        Threads.checkMainThread();
        Preconditions.checkState(this.mSafeCloseImageReaderProxy != null, "The ImageReader is not initialized.");
        this.mSafeCloseImageReaderProxy.setOnImageCloseListener(onImageCloseListener);
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.CaptureNode$In */
    public static abstract class AbstractC0260In {
        private CameraCaptureCallback mCameraCaptureCallback = new CameraCaptureCallback() { // from class: androidx.camera.core.imagecapture.CaptureNode.In.1
        };
        private DeferrableSurface mPostviewSurface = null;
        private CameraCaptureCallback mSecondaryCameraCaptureCallback;
        private DeferrableSurface mSecondarySurface;
        private DeferrableSurface mSurface;

        public abstract Edge<TakePictureManager.CaptureError> getErrorEdge();

        public abstract ImageReaderProxyProvider getImageReaderProxyProvider();

        public abstract int getInputFormat();

        public abstract List<Integer> getOutputFormats();

        public abstract PostviewSettings getPostviewSettings();

        public abstract Edge<ProcessingRequest> getRequestEdge();

        public abstract Size getSize();

        public abstract boolean isVirtualCamera();

        public DeferrableSurface getSurface() {
            DeferrableSurface deferrableSurface = this.mSurface;
            Objects.requireNonNull(deferrableSurface);
            return deferrableSurface;
        }

        public DeferrableSurface getPostviewSurface() {
            return this.mPostviewSurface;
        }

        public DeferrableSurface getSecondarySurface() {
            return this.mSecondarySurface;
        }

        public void setSurface(Surface surface) {
            Preconditions.checkState(this.mSurface == null, "The surface is already set.");
            this.mSurface = new ImmediateSurface(surface, getSize(), getInputFormat());
        }

        public void setPostviewSurface(Surface surface, Size size, int i) {
            this.mPostviewSurface = new ImmediateSurface(surface, size, i);
        }

        public void setSecondarySurface(Surface surface) {
            Preconditions.checkState(this.mSecondarySurface == null, "The secondary surface is already set.");
            this.mSecondarySurface = new ImmediateSurface(surface, getSize(), getInputFormat());
        }

        public void setCameraCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
            this.mCameraCaptureCallback = cameraCaptureCallback;
        }

        public void setSecondaryCameraCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
            this.mSecondaryCameraCaptureCallback = cameraCaptureCallback;
        }

        /* JADX INFO: renamed from: of */
        public static AbstractC0260In m87of(Size size, int i, List<Integer> list, boolean z, ImageReaderProxyProvider imageReaderProxyProvider, PostviewSettings postviewSettings) {
            return new AutoValue_CaptureNode_In(size, i, list, z, imageReaderProxyProvider, postviewSettings, new Edge(), new Edge());
        }
    }
}
