package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import androidx.camera.core.CameraXTracer;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.imagecapture.Bitmap2JpegBytes;
import androidx.camera.core.imagecapture.Image2JpegBytes;
import androidx.camera.core.imagecapture.JpegBytes2Disk;
import androidx.camera.core.imagecapture.ProcessingNode;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.compat.quirk.DeviceQuirks;
import androidx.camera.core.internal.compat.quirk.IncorrectJpegMetadataQuirk;
import androidx.camera.core.internal.compat.quirk.LowMemoryQuirk;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.core.processing.Edge;
import androidx.camera.core.processing.InternalImageProcessor;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes4.dex */
public class ProcessingNode {
    private Operation<Bitmap2JpegBytes.AbstractC0257In, Packet<byte[]>> mBitmap2JpegBytes;
    private Operation<Packet<Bitmap>, Packet<Bitmap>> mBitmapEffect;
    final Executor mBlockingExecutor;
    private final CameraCharacteristics mCameraCharacteristics;
    private final boolean mHasIncorrectJpegMetadataQuirk;
    private Operation<Packet<ImageProxy>, Bitmap> mImage2Bitmap;
    private Operation<Image2JpegBytes.AbstractC0261In, Packet<byte[]>> mImage2JpegBytes;
    final InternalImageProcessor mImageProcessor;
    private Operation<InputPacket, Packet<ImageProxy>> mInput2Packet;
    private AbstractC0263In mInputEdge;
    private Operation<Packet<byte[]>, Packet<Bitmap>> mJpegBytes2CroppedBitmap;
    private Operation<JpegBytes2Disk.AbstractC0262In, ImageCapture.OutputFileResults> mJpegBytes2Disk;
    private Operation<Packet<byte[]>, Packet<ImageProxy>> mJpegBytes2Image;
    private Operation<Packet<ImageProxy>, ImageProxy> mJpegImage2Result;
    private final Quirks mQuirks;

    public void release() {
    }

    public ProcessingNode(Executor executor, CameraCharacteristics cameraCharacteristics, InternalImageProcessor internalImageProcessor) {
        this(executor, cameraCharacteristics, internalImageProcessor, DeviceQuirks.getAll());
    }

    public ProcessingNode(Executor executor, CameraCharacteristics cameraCharacteristics, InternalImageProcessor internalImageProcessor, Quirks quirks) {
        if (DeviceQuirks.get(LowMemoryQuirk.class) != null) {
            this.mBlockingExecutor = CameraXExecutors.newSequentialExecutor(executor);
        } else {
            this.mBlockingExecutor = executor;
        }
        this.mImageProcessor = internalImageProcessor;
        this.mCameraCharacteristics = cameraCharacteristics;
        this.mQuirks = quirks;
        this.mHasIncorrectJpegMetadataQuirk = quirks.contains(IncorrectJpegMetadataQuirk.class);
    }

    public Void transform(AbstractC0263In abstractC0263In) {
        this.mInputEdge = abstractC0263In;
        abstractC0263In.getEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ProcessingNode.m1859$r8$lambda$6U13Zlx7P74SDaHwciYmFh6g8(this.f$0, (ProcessingNode.InputPacket) obj);
            }
        });
        abstractC0263In.getPostviewEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ProcessingNode.m1861$r8$lambda$EI5MKd_NwbtkM8O4wtsjxa9o4(this.f$0, (ProcessingNode.InputPacket) obj);
            }
        });
        this.mInput2Packet = new ProcessingInput2Packet();
        this.mImage2JpegBytes = new Image2JpegBytes(this.mQuirks);
        this.mJpegBytes2CroppedBitmap = new JpegBytes2CroppedBitmap();
        this.mBitmap2JpegBytes = new Bitmap2JpegBytes();
        this.mJpegBytes2Disk = new JpegBytes2Disk();
        this.mJpegImage2Result = new JpegImage2Result();
        this.mImage2Bitmap = new Image2Bitmap();
        if (abstractC0263In.getInputFormat() != 35 && !this.mHasIncorrectJpegMetadataQuirk) {
            return null;
        }
        this.mJpegBytes2Image = new JpegBytes2Image();
        return null;
    }

    /* JADX INFO: renamed from: $r8$lambda$6U13Zlx7P74SDa-Hwc-iYmFh6g8 */
    public static /* synthetic */ void m1859$r8$lambda$6U13Zlx7P74SDaHwciYmFh6g8(final ProcessingNode processingNode, final InputPacket inputPacket) {
        processingNode.getClass();
        if (inputPacket.getProcessingRequest().isAborted()) {
            inputPacket.getImageProxy().close();
        } else {
            processingNode.mBlockingExecutor.execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.processInputPacket(inputPacket);
                }
            });
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$EI5MKd_Nwbt-kM8O4-wtsjxa9o4 */
    public static /* synthetic */ void m1861$r8$lambda$EI5MKd_NwbtkM8O4wtsjxa9o4(final ProcessingNode processingNode, final InputPacket inputPacket) {
        processingNode.getClass();
        if (inputPacket.getProcessingRequest().isAborted()) {
            Logger.m79w("ProcessingNode", "The postview image is closed due to request aborted");
            inputPacket.getImageProxy().close();
        } else {
            processingNode.mBlockingExecutor.execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.processPostviewInputPacket(inputPacket);
                }
            });
        }
    }

    public void processInputPacket(final InputPacket inputPacket) {
        CameraXTracer.trace("processInputPacket", new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ProcessingNode.m1862$r8$lambda$gHbFgZCj0Kb9M357jPQNmeH6s(this.f$0, inputPacket);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$gHbFgZCj0Kb9M357jP-QNmeH6-s */
    public static /* synthetic */ void m1862$r8$lambda$gHbFgZCj0Kb9M357jPQNmeH6s(ProcessingNode processingNode, InputPacket inputPacket) {
        processingNode.getClass();
        final ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        try {
            try {
                boolean z = true;
                if (processingNode.mInputEdge.getOutputFormats().size() <= 1) {
                    z = false;
                }
                if (inputPacket.getProcessingRequest().isInMemoryCapture()) {
                    final ImageProxy imageProxyProcessInMemoryCapture = processingNode.processInMemoryCapture(inputPacket);
                    CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            processingRequest.onFinalResult(imageProxyProcessInMemoryCapture);
                        }
                    });
                    return;
                }
                final ImageCapture.OutputFileResults outputFileResultsProcessOnDiskCapture = processingNode.processOnDiskCapture(inputPacket);
                if (z) {
                    processingRequest.getTakePictureRequest();
                    throw null;
                }
                CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        processingRequest.onFinalResult(outputFileResultsProcessOnDiskCapture);
                    }
                });
            } catch (ImageCaptureException e) {
                processingNode.sendError(processingRequest, e);
            } catch (OutOfMemoryError e2) {
                processingNode.sendError(processingRequest, new ImageCaptureException(0, "Processing failed due to low memory.", e2));
            }
        } catch (RuntimeException e3) {
            processingNode.sendError(processingRequest, new ImageCaptureException(0, "Processing failed.", e3));
        }
    }

    public void processPostviewInputPacket(InputPacket inputPacket) {
        final ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        try {
            Packet<ImageProxy> packetApply = this.mInput2Packet.apply(inputPacket);
            int format = packetApply.getFormat();
            Preconditions.checkArgument(format == 35 || format == 256 || format == 4101, String.format("Postview only supports to convert YUV, JPEG and JPEG_R format image to the postview output bitmap. Image format: %s", Integer.valueOf(format)));
            final Bitmap bitmapApply = this.mImage2Bitmap.apply(packetApply);
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    processingRequest.onPostviewBitmapAvailable(bitmapApply);
                }
            });
        } catch (Exception e) {
            inputPacket.getImageProxy().close();
            Logger.m77e("ProcessingNode", "process postview input packet failed.", e);
        }
    }

    public ImageCapture.OutputFileResults processOnDiskCapture(InputPacket inputPacket) {
        Logger.m74d("ProcessingNode", "processOnDiskCapture: request ID = " + inputPacket.getProcessingRequest().getRequestId());
        List<Integer> outputFormats = this.mInputEdge.getOutputFormats();
        Preconditions.checkArgument(outputFormats.isEmpty() ^ true);
        Integer num = outputFormats.get(0);
        int iIntValue = num.intValue();
        Preconditions.checkArgument(ImageUtil.isJpegFormats(iIntValue) || ImageUtil.isRawFormats(iIntValue), String.format("On-disk capture only support JPEG and JPEG/R and RAW output formats. Output format: %s", num));
        ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        processingRequest.getOutputFileOptions();
        Preconditions.checkArgument(false, "OutputFileOptions cannot be empty");
        Packet<ImageProxy> packetApply = this.mInput2Packet.apply(inputPacket);
        if (outputFormats.size() <= 1) {
            if (iIntValue == 32) {
                processingRequest.getOutputFileOptions();
                throw null;
            }
            processingRequest.getOutputFileOptions();
            throw null;
        }
        processingRequest.getOutputFileOptions();
        Preconditions.checkArgument(false, "The number of OutputFileOptions for simultaneous capture should be at least two");
        if (packetApply.getFormat() == 32) {
            processingRequest.getOutputFileOptions();
            throw null;
        }
        processingRequest.getSecondaryOutputFileOptions();
        throw null;
    }

    public ImageProxy processInMemoryCapture(InputPacket inputPacket) {
        Logger.m74d("ProcessingNode", "processInMemoryCapture: request ID = " + inputPacket.getProcessingRequest().getRequestId());
        ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        Packet<ImageProxy> packetApply = this.mInput2Packet.apply(inputPacket);
        List<Integer> outputFormats = this.mInputEdge.getOutputFormats();
        Preconditions.checkArgument(!outputFormats.isEmpty());
        int iIntValue = outputFormats.get(0).intValue();
        if ((packetApply.getFormat() == 35 || this.mBitmapEffect != null || this.mHasIncorrectJpegMetadataQuirk) && iIntValue == 256) {
            Packet<byte[]> packetApply2 = this.mImage2JpegBytes.apply(Image2JpegBytes.AbstractC0261In.m88of(packetApply, processingRequest.getJpegQuality()));
            if (this.mBitmapEffect != null) {
                packetApply2 = cropAndMaybeApplyEffect(packetApply2, processingRequest.getJpegQuality());
            }
            packetApply = this.mJpegBytes2Image.apply(packetApply2);
        }
        ImageProxy imageProxyApply = this.mJpegImage2Result.apply(packetApply);
        if (outputFormats.size() <= 1) {
            return imageProxyApply;
        }
        processingRequest.getTakePictureRequest();
        imageProxyApply.getFormat();
        throw null;
    }

    private Packet<byte[]> cropAndMaybeApplyEffect(Packet<byte[]> packet, int i) {
        Preconditions.checkState(ImageUtil.isJpegFormats(packet.getFormat()));
        Packet<Bitmap> packetApply = this.mJpegBytes2CroppedBitmap.apply(packet);
        Operation<Packet<Bitmap>, Packet<Bitmap>> operation = this.mBitmapEffect;
        if (operation != null) {
            packetApply = operation.apply(packetApply);
        }
        return this.mBitmap2JpegBytes.apply(Bitmap2JpegBytes.AbstractC0257In.m86of(packetApply, i));
    }

    private void sendError(final ProcessingRequest processingRequest, final ImageCaptureException imageCaptureException) {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.ProcessingNode$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                processingRequest.onProcessFailure(imageCaptureException);
            }
        });
    }

    public static abstract class InputPacket {
        public abstract ImageProxy getImageProxy();

        public abstract ProcessingRequest getProcessingRequest();

        /* JADX INFO: renamed from: of */
        public static InputPacket m90of(ProcessingRequest processingRequest, ImageProxy imageProxy) {
            return new AutoValue_ProcessingNode_InputPacket(processingRequest, imageProxy);
        }
    }

    /* JADX INFO: renamed from: androidx.camera.core.imagecapture.ProcessingNode$In */
    public static abstract class AbstractC0263In {
        public abstract Edge<InputPacket> getEdge();

        public abstract int getInputFormat();

        public abstract List<Integer> getOutputFormats();

        public abstract Edge<InputPacket> getPostviewEdge();

        /* JADX INFO: renamed from: of */
        public static AbstractC0263In m89of(int i, List<Integer> list) {
            return new AutoValue_ProcessingNode_In(new Edge(), new Edge(), i, list);
        }
    }
}
