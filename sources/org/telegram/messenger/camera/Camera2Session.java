package org.telegram.messenger.camera;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.view.Surface;
import com.exteragram.messenger.ExteraConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;

/* JADX INFO: loaded from: classes5.dex */
public class Camera2Session {
    private CameraCharacteristics cameraCharacteristics;
    private CameraDevice cameraDevice;
    public final String cameraId;
    private final CameraManager cameraManager;
    private final CameraDevice.StateCallback cameraStateCallback;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession captureSession;
    private final CameraCaptureSession.StateCallback captureStateCallback;
    private Runnable doneCallback;
    private boolean flashing;
    private Handler handler;
    private ImageReader imageReader;
    private boolean isClosed;
    private boolean isError;
    private final boolean isFront;
    private boolean isSuccess;
    private long lastTime;
    private float maxZoom;
    private boolean nightMode;
    private final android.util.Size previewSize;
    private boolean recordingVideo;
    private boolean scanningBarcode;
    private Rect sensorSize;
    private Surface surface;
    private SurfaceTexture surfaceTexture;
    private HandlerThread thread;
    private float currentZoom = 1.0f;
    private boolean opened = false;
    private final Rect cropRegion = new Rect();

    public float getMinZoom() {
        return 1.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00bf A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.telegram.messenger.camera.Camera2Session create(boolean r22, int r23, int r24) {
        /*
            r1 = r22
            r0 = r23
            r2 = r24
            android.content.Context r3 = org.telegram.messenger.ApplicationLoader.applicationContext
            java.lang.String r4 = "camera"
            java.lang.Object r4 = r3.getSystemService(r4)
            android.hardware.camera2.CameraManager r4 = (android.hardware.camera2.CameraManager) r4
            java.lang.String[] r6 = r4.getCameraIdList()     // Catch: java.lang.Exception -> Lb4
            int r7 = r6.length     // Catch: java.lang.Exception -> Lb4
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
        L19:
            if (r10 >= r7) goto Lb1
            r14 = r6[r10]     // Catch: java.lang.Exception -> Lad
            android.hardware.camera2.CameraCharacteristics r15 = r4.getCameraCharacteristics(r14)     // Catch: java.lang.Exception -> Lad
            r16 = 0
            android.hardware.camera2.CameraCharacteristics$Key r5 = android.hardware.camera2.CameraCharacteristics.LENS_FACING     // Catch: java.lang.Exception -> L93
            java.lang.Object r5 = r15.get(r5)     // Catch: java.lang.Exception -> L93
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.lang.Exception -> L93
            int r5 = r5.intValue()     // Catch: java.lang.Exception -> L93
            r17 = 0
            r9 = r1 ^ 1
            if (r5 == r9) goto L3a
            r19 = r4
        L37:
            r5 = 0
            goto La7
        L3a:
            android.hardware.camera2.CameraCharacteristics$Key r5 = android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP     // Catch: java.lang.Exception -> L93
            java.lang.Object r5 = r15.get(r5)     // Catch: java.lang.Exception -> L93
            android.hardware.camera2.params.StreamConfigurationMap r5 = (android.hardware.camera2.params.StreamConfigurationMap) r5     // Catch: java.lang.Exception -> L93
            android.hardware.camera2.CameraCharacteristics$Key r9 = android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE     // Catch: java.lang.Exception -> L93
            java.lang.Object r9 = r15.get(r9)     // Catch: java.lang.Exception -> L93
            android.util.Size r9 = (android.util.Size) r9     // Catch: java.lang.Exception -> L93
            if (r9 != 0) goto L4f
            r15 = r17
            goto L5a
        L4f:
            int r15 = r9.getWidth()     // Catch: java.lang.Exception -> L93
            float r15 = (float) r15     // Catch: java.lang.Exception -> L93
            int r9 = r9.getHeight()     // Catch: java.lang.Exception -> L93
            float r9 = (float) r9     // Catch: java.lang.Exception -> L93
            float r15 = r15 / r9
        L5a:
            float r9 = (float) r0     // Catch: java.lang.Exception -> L93
            float r8 = (float) r2     // Catch: java.lang.Exception -> L93
            float r9 = r9 / r8
            r8 = 1065353216(0x3f800000, float:1.0)
            int r18 = (r9 > r8 ? 1 : (r9 == r8 ? 0 : -1))
            r19 = 1
            if (r18 < 0) goto L6a
            r18 = r8
            r8 = r19
            goto L6d
        L6a:
            r18 = r8
            r8 = 0
        L6d:
            int r20 = (r15 > r18 ? 1 : (r15 == r18 ? 0 : -1))
            if (r20 < 0) goto L78
            r21 = r19
            r19 = r4
            r4 = r21
            goto L7b
        L78:
            r19 = r4
            r4 = 0
        L7b:
            if (r8 == r4) goto L7f
            float r15 = r18 / r15
        L7f:
            int r4 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r4 <= 0) goto L95
            float r4 = r9 - r13
            float r4 = java.lang.Math.abs(r4)     // Catch: java.lang.Exception -> L93
            float r9 = r9 - r15
            float r8 = java.lang.Math.abs(r9)     // Catch: java.lang.Exception -> L93
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L37
            goto L95
        L93:
            r0 = move-exception
            goto Lba
        L95:
            if (r5 == 0) goto L37
            java.lang.Class<android.graphics.SurfaceTexture> r4 = android.graphics.SurfaceTexture.class
            android.util.Size[] r4 = r5.getOutputSizes(r4)     // Catch: java.lang.Exception -> L93
            r5 = 0
            android.util.Size r4 = chooseOptimalSize(r4, r0, r2, r5)     // Catch: java.lang.Exception -> L93
            if (r4 == 0) goto La7
            r11 = r4
            r12 = r14
            r13 = r15
        La7:
            int r10 = r10 + 1
            r4 = r19
            goto L19
        Lad:
            r0 = move-exception
            r16 = 0
            goto Lba
        Lb1:
            r16 = 0
            goto Lbd
        Lb4:
            r0 = move-exception
            r16 = 0
            r11 = r16
            r12 = r11
        Lba:
            org.telegram.messenger.FileLog.m1136e(r0)
        Lbd:
            if (r12 != 0) goto Lc0
            return r16
        Lc0:
            org.telegram.messenger.camera.Camera2Session r0 = new org.telegram.messenger.camera.Camera2Session
            r0.<init>(r3, r1, r12, r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.Camera2Session.create(boolean, int, int):org.telegram.messenger.camera.Camera2Session");
    }

    private Camera2Session(Context context, boolean z, String str, android.util.Size size) {
        float fFloatValue = 1.0f;
        this.maxZoom = 1.0f;
        HandlerThread handlerThread = new HandlerThread("tg_camera2");
        this.thread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.thread.getLooper());
        C29071 c29071 = new C29071(str);
        this.cameraStateCallback = c29071;
        this.captureStateCallback = new C29082(str);
        this.isFront = z;
        this.cameraId = str;
        this.previewSize = size;
        this.lastTime = System.currentTimeMillis();
        this.imageReader = ImageReader.newInstance(size.getWidth(), size.getHeight(), 256, 1);
        CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
        this.cameraManager = cameraManager;
        try {
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
            this.cameraCharacteristics = cameraCharacteristics;
            this.sensorSize = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            Float f = (Float) this.cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
            if (f != null && f.floatValue() >= 1.0f) {
                fFloatValue = f.floatValue();
            }
            this.maxZoom = fFloatValue;
            cameraManager.openCamera(str, c29071, this.handler);
        } catch (Exception e) {
            FileLog.m1136e(e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            });
        }
    }

    /* JADX INFO: renamed from: org.telegram.messenger.camera.Camera2Session$1 */
    class C29071 extends CameraDevice.StateCallback {
        final /* synthetic */ String val$cameraId;

        C29071(String str) {
            this.val$cameraId = str;
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(CameraDevice cameraDevice) {
            Camera2Session.this.cameraDevice = cameraDevice;
            Camera2Session.this.lastTime = System.currentTimeMillis();
            FileLog.m1133d("Camera2Session camera #" + this.val$cameraId + " opened");
            Camera2Session.this.checkOpen();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(CameraDevice cameraDevice) {
            Camera2Session.this.cameraDevice = cameraDevice;
            FileLog.m1133d("Camera2Session camera #" + this.val$cameraId + " disconnected");
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(CameraDevice cameraDevice, int i) {
            Camera2Session.this.cameraDevice = cameraDevice;
            FileLog.m1134e("Camera2Session camera #" + this.val$cameraId + " received " + i + " error");
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onError$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0() {
            Camera2Session.this.isError = true;
        }
    }

    /* JADX INFO: renamed from: org.telegram.messenger.camera.Camera2Session$2 */
    class C29082 extends CameraCaptureSession.StateCallback {
        final /* synthetic */ String val$cameraId;

        C29082(String str) {
            this.val$cameraId = str;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            Camera2Session.this.captureSession = cameraCaptureSession;
            FileLog.m1134e("Camera2Session camera #" + this.val$cameraId + " capture session configured");
            Camera2Session.this.lastTime = System.currentTimeMillis();
            try {
                Camera2Session.this.updateCaptureRequest();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onConfigured$0();
                    }
                });
            } catch (Exception e) {
                FileLog.m1136e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigured$0() {
            Camera2Session.this.isSuccess = true;
            if (Camera2Session.this.doneCallback != null) {
                Camera2Session.this.doneCallback.run();
                Camera2Session.this.doneCallback = null;
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            Camera2Session.this.captureSession = cameraCaptureSession;
            FileLog.m1134e("Camera2Session camera #" + this.val$cameraId + " capture session failed to configure");
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onConfigureFailed$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigureFailed$1() {
            Camera2Session.this.isError = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.isError = true;
    }

    public void whenDone(Runnable runnable) {
        if (isInitiated()) {
            runnable.run();
            this.doneCallback = null;
        } else {
            this.doneCallback = runnable;
        }
    }

    public void open(final SurfaceTexture surfaceTexture) {
        this.handler.post(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$open$1(surfaceTexture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$open$1(SurfaceTexture surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.setDefaultBufferSize(getPreviewWidth(), getPreviewHeight());
        }
        checkOpen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkOpen() {
        if (this.opened || this.surfaceTexture == null || this.cameraDevice == null) {
            return;
        }
        this.opened = true;
        this.surface = new Surface(this.surfaceTexture);
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.surface);
            arrayList.add(this.imageReader.getSurface());
            this.cameraDevice.createCaptureSession(arrayList, this.captureStateCallback, null);
        } catch (Exception e) {
            FileLog.m1136e(e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkOpen$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkOpen$2() {
        this.isError = true;
    }

    public boolean isInitiated() {
        return (this.isError || !this.isSuccess || this.isClosed) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getDisplayOrientation() {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Exception -> L45
            if (r1 != 0) goto L6
            return r0
        L6:
            java.lang.String r2 = "window"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch: java.lang.Exception -> L45
            android.view.WindowManager r1 = (android.view.WindowManager) r1     // Catch: java.lang.Exception -> L45
            android.view.Display r1 = r1.getDefaultDisplay()     // Catch: java.lang.Exception -> L45
            int r1 = r1.getRotation()     // Catch: java.lang.Exception -> L45
            if (r1 == 0) goto L21
            r2 = 1
            if (r1 == r2) goto L29
            r2 = 2
            if (r1 == r2) goto L26
            r2 = 3
            if (r1 == r2) goto L23
        L21:
            r1 = r0
            goto L2b
        L23:
            r1 = 270(0x10e, float:3.78E-43)
            goto L2b
        L26:
            r1 = 180(0xb4, float:2.52E-43)
            goto L2b
        L29:
            r1 = 90
        L2b:
            android.hardware.camera2.CameraCharacteristics r2 = r4.cameraCharacteristics     // Catch: java.lang.Exception -> L45
            android.hardware.camera2.CameraCharacteristics$Key r3 = android.hardware.camera2.CameraCharacteristics.SENSOR_ORIENTATION     // Catch: java.lang.Exception -> L45
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Exception -> L45
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Exception -> L45
            boolean r3 = r4.isFront     // Catch: java.lang.Exception -> L45
            if (r3 == 0) goto L47
            int r2 = r2.intValue()     // Catch: java.lang.Exception -> L45
            int r2 = r2 + r1
            int r2 = r2 % 360
            int r1 = 360 - r2
            int r1 = r1 % 360
            return r1
        L45:
            r1 = move-exception
            goto L51
        L47:
            int r2 = r2.intValue()     // Catch: java.lang.Exception -> L45
            int r2 = r2 - r1
            int r2 = r2 + 360
            int r2 = r2 % 360
            return r2
        L51:
            org.telegram.messenger.FileLog.m1136e(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.Camera2Session.getDisplayOrientation():int");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getJpegOrientation() {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Exception -> L45
            if (r1 != 0) goto L6
            return r0
        L6:
            java.lang.String r2 = "window"
            java.lang.Object r1 = r1.getSystemService(r2)     // Catch: java.lang.Exception -> L45
            android.view.WindowManager r1 = (android.view.WindowManager) r1     // Catch: java.lang.Exception -> L45
            android.view.Display r1 = r1.getDefaultDisplay()     // Catch: java.lang.Exception -> L45
            int r1 = r1.getRotation()     // Catch: java.lang.Exception -> L45
            if (r1 == 0) goto L21
            r2 = 1
            if (r1 == r2) goto L29
            r2 = 2
            if (r1 == r2) goto L26
            r2 = 3
            if (r1 == r2) goto L23
        L21:
            r1 = r0
            goto L2b
        L23:
            r1 = 270(0x10e, float:3.78E-43)
            goto L2b
        L26:
            r1 = 180(0xb4, float:2.52E-43)
            goto L2b
        L29:
            r1 = 90
        L2b:
            android.hardware.camera2.CameraCharacteristics r2 = r4.cameraCharacteristics     // Catch: java.lang.Exception -> L45
            android.hardware.camera2.CameraCharacteristics$Key r3 = android.hardware.camera2.CameraCharacteristics.SENSOR_ORIENTATION     // Catch: java.lang.Exception -> L45
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Exception -> L45
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Exception -> L45
            boolean r3 = r4.isFront     // Catch: java.lang.Exception -> L45
            if (r3 == 0) goto L47
            int r2 = r2.intValue()     // Catch: java.lang.Exception -> L45
            int r2 = r2 + r1
            int r2 = r2 % 360
            int r1 = 360 - r2
            int r1 = r1 % 360
            return r1
        L45:
            r1 = move-exception
            goto L51
        L47:
            int r2 = r2.intValue()     // Catch: java.lang.Exception -> L45
            int r2 = r2 - r1
            int r2 = r2 + 360
            int r2 = r2 % 360
            return r2
        L51:
            org.telegram.messenger.FileLog.m1136e(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.Camera2Session.getJpegOrientation():int");
    }

    public int getWorldAngle() {
        int jpegOrientation = getJpegOrientation() - getDisplayOrientation();
        return jpegOrientation < 0 ? jpegOrientation + 360 : jpegOrientation;
    }

    public int getCurrentOrientation() {
        return getJpegOrientation();
    }

    public void setZoom(float f) {
        if (!isInitiated() || this.captureRequestBuilder == null || this.cameraDevice == null || this.sensorSize == null) {
            return;
        }
        this.currentZoom = Utilities.clamp(f, this.maxZoom, 1.0f);
        updateCaptureRequest();
        try {
            this.captureSession.setRepeatingRequest(this.captureRequestBuilder.build(), null, this.handler);
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    public void setFlash(boolean z) {
        if (this.flashing != z) {
            this.flashing = z;
            updateCaptureRequest();
        }
    }

    public boolean getFlash() {
        return this.flashing;
    }

    public float getZoom() {
        return this.currentZoom;
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public int getPreviewWidth() {
        return this.previewSize.getWidth();
    }

    public int getPreviewHeight() {
        return this.previewSize.getHeight();
    }

    public void destroy(boolean z) {
        destroy(z, null);
    }

    public void destroy(boolean z, final Runnable runnable) {
        this.isClosed = true;
        if (z) {
            this.handler.post(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$destroy$4(runnable);
                }
            });
            return;
        }
        CameraCaptureSession cameraCaptureSession = this.captureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.captureSession = null;
        }
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.cameraDevice = null;
        }
        ImageReader imageReader = this.imageReader;
        if (imageReader != null) {
            imageReader.close();
            this.imageReader = null;
        }
        this.thread.quitSafely();
        try {
            this.thread.join();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (runnable != null) {
            AndroidUtilities.runOnUIThread(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$destroy$4(final Runnable runnable) {
        CameraCaptureSession cameraCaptureSession = this.captureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.captureSession = null;
        }
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.cameraDevice = null;
        }
        ImageReader imageReader = this.imageReader;
        if (imageReader != null) {
            imageReader.close();
            this.imageReader = null;
        }
        this.thread.quitSafely();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.camera.Camera2Session$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$destroy$3(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$destroy$3(Runnable runnable) {
        try {
            this.thread.join();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setRecordingVideo(boolean z) {
        if (this.recordingVideo != z) {
            this.recordingVideo = z;
            updateCaptureRequest();
        }
    }

    public void setScanningBarcode(boolean z) {
        if (this.scanningBarcode != z) {
            this.scanningBarcode = z;
            updateCaptureRequest();
        }
    }

    public void setNightMode(boolean z) {
        if (this.nightMode != z) {
            this.nightMode = z;
            updateCaptureRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCaptureRequest() {
        int i;
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice == null || this.surface == null || this.captureSession == null) {
            return;
        }
        try {
            if (this.recordingVideo) {
                i = 3;
            } else {
                i = this.scanningBarcode ? 2 : 1;
            }
            CaptureRequest.Builder builderCreateCaptureRequest = cameraDevice.createCaptureRequest(i);
            this.captureRequestBuilder = builderCreateCaptureRequest;
            if (this.scanningBarcode) {
                builderCreateCaptureRequest.set(CaptureRequest.CONTROL_SCENE_MODE, 16);
            } else if (this.nightMode) {
                builderCreateCaptureRequest.set(CaptureRequest.CONTROL_SCENE_MODE, Integer.valueOf(this.isFront ? 6 : 5));
            }
            this.captureRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(this.flashing ? this.recordingVideo ? 2 : 1 : 0));
            if (this.recordingVideo) {
                this.captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                if (ExteraConfig.extendedFramesPerSecond) {
                    this.captureRequestBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, new Range(30, 60));
                    this.captureRequestBuilder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
                }
                if (ExteraConfig.cameraStabilization) {
                    chooseStabilizationMode(this.captureRequestBuilder);
                }
                chooseFocusMode(this.captureRequestBuilder);
            }
            if (this.sensorSize != null && Math.abs(this.currentZoom - 1.0f) >= 0.01f) {
                int iWidth = this.sensorSize.width() / 2;
                int iHeight = this.sensorSize.height() / 2;
                int iWidth2 = (int) ((this.sensorSize.width() * 0.5f) / this.currentZoom);
                int iHeight2 = (int) ((this.sensorSize.height() * 0.5f) / this.currentZoom);
                this.cropRegion.set(iWidth - iWidth2, iHeight - iHeight2, iWidth + iWidth2, iHeight + iHeight2);
                this.captureRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, this.cropRegion);
            }
            this.captureRequestBuilder.addTarget(this.surface);
            this.captureSession.setRepeatingRequest(this.captureRequestBuilder.build(), null, this.handler);
        } catch (Exception e) {
            FileLog.m1135e("Camera2Sessions setRepeatingRequest error in updateCaptureRequest", e);
        }
    }

    public boolean takePicture(File file, Utilities.Callback<Integer> callback) {
        CameraDevice cameraDevice = this.cameraDevice;
        if (cameraDevice != null && this.captureSession != null) {
            try {
                CaptureRequest.Builder builderCreateCaptureRequest = cameraDevice.createCaptureRequest(2);
                int jpegOrientation = getJpegOrientation();
                builderCreateCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(jpegOrientation));
                this.imageReader.setOnImageAvailableListener(new C29093(file, callback, jpegOrientation), null);
                if (this.scanningBarcode) {
                    builderCreateCaptureRequest.set(CaptureRequest.CONTROL_SCENE_MODE, 16);
                }
                builderCreateCaptureRequest.addTarget(this.imageReader.getSurface());
                this.captureSession.capture(builderCreateCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() { // from class: org.telegram.messenger.camera.Camera2Session.4
                }, null);
                return true;
            } catch (Exception e) {
                FileLog.m1135e("Camera2Sessions takePicture error", e);
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: org.telegram.messenger.camera.Camera2Session$3 */
    class C29093 implements ImageReader.OnImageAvailableListener {
        final /* synthetic */ File val$file;
        final /* synthetic */ int val$orientation;
        final /* synthetic */ Utilities.Callback val$whenDone;

        C29093(File file, Utilities.Callback callback, int i) {
            this.val$file = file;
            this.val$whenDone = callback;
            this.val$orientation = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.media.ImageReader.OnImageAvailableListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onImageAvailable(android.media.ImageReader r5) throws java.lang.Throwable {
            /*
                r4 = this;
                android.media.Image r5 = r5.acquireLatestImage()
                android.media.Image$Plane[] r0 = r5.getPlanes()
                r1 = 0
                r0 = r0[r1]
                java.nio.ByteBuffer r0 = r0.getBuffer()
                int r1 = r0.remaining()
                byte[] r1 = new byte[r1]
                r0.get(r1)
                r0 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L37
                java.io.File r3 = r4.val$file     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L37
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L37
                r2.write(r1)     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L31
                r5.close()
            L26:
                r2.close()     // Catch: java.io.IOException -> L2a
                goto L43
            L2a:
                r5 = move-exception
                r5.printStackTrace()
                goto L43
            L2f:
                r0 = move-exception
                goto L50
            L31:
                r0 = move-exception
                goto L3a
            L33:
                r1 = move-exception
                r2 = r0
                r0 = r1
                goto L50
            L37:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L3a:
                r0.printStackTrace()     // Catch: java.lang.Throwable -> L2f
                r5.close()
                if (r2 == 0) goto L43
                goto L26
            L43:
                org.telegram.messenger.Utilities$Callback r5 = r4.val$whenDone
                int r0 = r4.val$orientation
                org.telegram.messenger.camera.Camera2Session$3$$ExternalSyntheticLambda0 r1 = new org.telegram.messenger.camera.Camera2Session$3$$ExternalSyntheticLambda0
                r1.<init>()
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r1)
                return
            L50:
                r5.close()
                if (r2 == 0) goto L5d
                r2.close()     // Catch: java.io.IOException -> L59
                goto L5d
            L59:
                r5 = move-exception
                r5.printStackTrace()
            L5d:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.Camera2Session.C29093.onImageAvailable(android.media.ImageReader):void");
        }

        public static /* synthetic */ void $r8$lambda$C8GruD4YcNmICIAfGZxauIzKYfU(Utilities.Callback callback, int i) {
            if (callback != null) {
                callback.run(Integer.valueOf(i));
            }
        }
    }

    private void chooseStabilizationMode(CaptureRequest.Builder builder) {
        int[] iArr = (int[]) this.cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION);
        if (iArr != null) {
            for (int i : iArr) {
                if (i == 1) {
                    builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, 1);
                    builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 0);
                    FileLog.m1133d("Using optical stabilization.");
                    return;
                }
            }
        }
        for (int i2 : (int[]) this.cameraCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)) {
            if (i2 == 1) {
                builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 1);
                builder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, 0);
                FileLog.m1133d("Using video stabilization.");
                return;
            }
        }
        FileLog.m1133d("Stabilization not available.");
    }

    private void chooseFocusMode(CaptureRequest.Builder builder) {
        for (int i : (int[]) this.cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)) {
            if (i == 3) {
                builder.set(CaptureRequest.CONTROL_AF_MODE, 3);
                FileLog.m1133d("Using continuous video auto-focus.");
                return;
            }
        }
        FileLog.m1133d("Auto-focus is not available.");
    }

    public static android.util.Size chooseOptimalSize(android.util.Size[] sizeArr, int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList(sizeArr.length);
        ArrayList arrayList2 = new ArrayList(sizeArr.length);
        for (android.util.Size size : sizeArr) {
            if (!z || (size.getHeight() <= i2 && size.getWidth() <= i)) {
                if (size.getHeight() == (size.getWidth() * i2) / i && size.getWidth() >= i && size.getHeight() >= i2) {
                    arrayList.add(size);
                } else if (size.getHeight() * size.getWidth() <= i * i2 * 4 && size.getWidth() >= i && size.getHeight() >= i2) {
                    arrayList2.add(size);
                }
            }
        }
        if (arrayList.size() > 0) {
            return (android.util.Size) Collections.min(arrayList, new CompareSizesByArea());
        }
        if (arrayList2.size() > 0) {
            return (android.util.Size) Collections.min(arrayList2, new CompareSizesByArea());
        }
        return (android.util.Size) Collections.max(Arrays.asList(sizeArr), new CompareSizesByArea());
    }

    static class CompareSizesByArea implements Comparator<android.util.Size> {
        CompareSizesByArea() {
        }

        @Override // java.util.Comparator
        public int compare(android.util.Size size, android.util.Size size2) {
            return Long.signum((((long) size.getWidth()) * ((long) size.getHeight())) - (((long) size2.getWidth()) * ((long) size2.getHeight())));
        }
    }

    public boolean isTorchAvailable(boolean z) {
        String strFindCameraId = findCameraId(z);
        if (strFindCameraId == null) {
            return false;
        }
        try {
            return Boolean.TRUE.equals(this.cameraManager.getCameraCharacteristics(strFindCameraId).get(CameraCharacteristics.FLASH_INFO_AVAILABLE));
        } catch (CameraAccessException e) {
            FileLog.m1136e(e);
            return false;
        }
    }

    private String findCameraId(boolean z) {
        int i;
        try {
            String[] cameraIdList = this.cameraManager.getCameraIdList();
            int length = cameraIdList.length;
            while (i < length) {
                String str = cameraIdList[i];
                Integer num = (Integer) this.cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING);
                i = (num == null || (!(z && num.intValue() == 0) && (z || num.intValue() != 1))) ? i + 1 : 0;
                return str;
            }
            return null;
        } catch (CameraAccessException e) {
            FileLog.m1136e(e);
            return null;
        }
    }
}
