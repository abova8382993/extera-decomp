package org.telegram.p035ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Components.ColorPicker;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.Premium.GLIcon.GLIconRenderer;
import org.telegram.p035ui.Components.Premium.GLIcon.Icon3D;
import org.telegram.p035ui.Components.SeekBarView;
import org.telegram.tgnet.TLObject;

/* JADX INFO: loaded from: classes6.dex */
public class GLIconSettingsView extends LinearLayout {
    public static float smallStarsSize = 1.0f;

    public GLIconSettingsView(Context context, GLIconRenderer gLIconRenderer) {
        super(context);
        setOrientation(1);
        TextView textView = new TextView(context);
        textView.setText("Spectral top ");
        int i = Theme.key_dialogTextBlue2;
        textView.setTextColor(Theme.getColor(i));
        textView.setTextSize(1, 16.0f);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(textView, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 21.0f, 13.0f, 21.0f, 0.0f));
        SeekBarView seekBarView = new SeekBarView(context);
        seekBarView.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: org.telegram.ui.GLIconSettingsView.1
            final /* synthetic */ GLIconRenderer val$mRenderer;

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarPressed(boolean z) {
            }

            public C56411(GLIconRenderer gLIconRenderer2) {
                gLIconRenderer = gLIconRenderer2;
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarDrag(boolean z, float f) {
                Icon3D icon3D = gLIconRenderer.model;
                if (icon3D != null) {
                    icon3D.spec1 = f * 2.0f;
                }
            }
        });
        Icon3D icon3D = gLIconRenderer2.model;
        seekBarView.setProgress(icon3D == null ? 0.0f : icon3D.spec1 / 2.0f);
        seekBarView.setReportChanges(true);
        addView(seekBarView, LayoutHelper.createFrame(-1, 38.0f, 0, 5.0f, 4.0f, 5.0f, 0.0f));
        TextView textView2 = new TextView(context);
        textView2.setText("Spectral bottom ");
        textView2.setTextColor(Theme.getColor(i));
        textView2.setTextSize(1, 16.0f);
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(textView2, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 21.0f, 13.0f, 21.0f, 0.0f));
        SeekBarView seekBarView2 = new SeekBarView(context);
        seekBarView2.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: org.telegram.ui.GLIconSettingsView.2
            final /* synthetic */ GLIconRenderer val$mRenderer;

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarPressed(boolean z) {
            }

            public C56422(GLIconRenderer gLIconRenderer2) {
                gLIconRenderer = gLIconRenderer2;
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarDrag(boolean z, float f) {
                Icon3D icon3D2 = gLIconRenderer.model;
                if (icon3D2 != null) {
                    icon3D2.spec2 = f * 2.0f;
                }
            }
        });
        Icon3D icon3D2 = gLIconRenderer2.model;
        seekBarView2.setProgress(icon3D2 == null ? 0.0f : icon3D2.spec2 / 2.0f);
        seekBarView2.setReportChanges(true);
        addView(seekBarView2, LayoutHelper.createFrame(-1, 38.0f, 0, 5.0f, 4.0f, 5.0f, 0.0f));
        TextView textView3 = new TextView(context);
        textView3.setText("Setup spec color");
        textView3.setTextSize(1, 16.0f);
        textView3.setLines(1);
        textView3.setGravity(17);
        textView3.setMaxLines(1);
        textView3.setSingleLine(true);
        int i2 = Theme.key_featuredStickers_buttonText;
        textView3.setTextColor(Theme.getColor(i2));
        int i3 = Theme.key_featuredStickers_addButton;
        textView3.setBackground(Theme.AdaptiveRipple.filledRect(Theme.getColor(i3), 4.0f));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GLIconSettingsView.3
            final /* synthetic */ Context val$context;
            final /* synthetic */ GLIconRenderer val$mRenderer;

            public ViewOnClickListenerC56433(Context context2, GLIconRenderer gLIconRenderer2) {
                context = context2;
                gLIconRenderer = gLIconRenderer2;
            }

            /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$3$1 */
            public class AnonymousClass1 implements ColorPicker.ColorPickerDelegate {
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                public void setColor(int i, int i2, boolean z) {
                    Icon3D icon3D = gLIconRenderer.model;
                    if (icon3D != null) {
                        icon3D.specColor = i;
                    }
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AnonymousClass2 anonymousClass2 = new ColorPicker(context, false, new ColorPicker.ColorPickerDelegate() { // from class: org.telegram.ui.GLIconSettingsView.3.1
                    public AnonymousClass1() {
                    }

                    @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                    public void setColor(int i4, int i22, boolean z) {
                        Icon3D icon3D3 = gLIconRenderer.model;
                        if (icon3D3 != null) {
                            icon3D3.specColor = i4;
                        }
                    }
                }) { // from class: org.telegram.ui.GLIconSettingsView.3.2
                    public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                        super(context2, z, colorPickerDelegate);
                    }

                    @Override // android.widget.FrameLayout, android.view.View
                    public void onMeasure(int i4, int i5) {
                        super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                    }
                };
                Icon3D icon3D3 = gLIconRenderer.model;
                anonymousClass2.setColor(icon3D3 != null ? icon3D3.specColor : 0, 0);
                anonymousClass2.setType(-1, true, 1, 1, false, 0, false);
                BottomSheet bottomSheet = new BottomSheet(context, false);
                bottomSheet.setCustomView(anonymousClass2);
                bottomSheet.setDimBehind(false);
                bottomSheet.show();
            }

            /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$3$2 */
            public class AnonymousClass2 extends ColorPicker {
                public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                    super(context2, z, colorPickerDelegate);
                }

                @Override // android.widget.FrameLayout, android.view.View
                public void onMeasure(int i4, int i5) {
                    super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                }
            }
        });
        addView(textView3, LayoutHelper.createFrame(-1, 48.0f, 16, 16.0f, 0.0f, 16.0f, 0.0f));
        TextView textView4 = new TextView(context2);
        textView4.setText("Diffuse ");
        textView4.setTextColor(Theme.getColor(i));
        textView4.setTextSize(1, 16.0f);
        textView4.setLines(1);
        textView4.setMaxLines(1);
        textView4.setSingleLine(true);
        textView4.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(textView4, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 21.0f, 13.0f, 21.0f, 0.0f));
        SeekBarView seekBarView3 = new SeekBarView(context2);
        seekBarView3.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: org.telegram.ui.GLIconSettingsView.4
            final /* synthetic */ GLIconRenderer val$mRenderer;

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarPressed(boolean z) {
            }

            public C56444(GLIconRenderer gLIconRenderer2) {
                gLIconRenderer = gLIconRenderer2;
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarDrag(boolean z, float f) {
                Icon3D icon3D3 = gLIconRenderer.model;
                if (icon3D3 != null) {
                    icon3D3.diffuse = f;
                }
            }
        });
        Icon3D icon3D3 = gLIconRenderer2.model;
        seekBarView3.setProgress(icon3D3 == null ? 0.0f : icon3D3.diffuse);
        seekBarView3.setReportChanges(true);
        addView(seekBarView3, LayoutHelper.createFrame(-1, 38.0f, 0, 5.0f, 4.0f, 5.0f, 0.0f));
        TextView textView5 = new TextView(context2);
        textView5.setText("Normal map spectral");
        textView5.setTextColor(Theme.getColor(i));
        textView5.setTextSize(1, 16.0f);
        textView5.setLines(1);
        textView5.setMaxLines(1);
        textView5.setSingleLine(true);
        textView5.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(textView5, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 21.0f, 13.0f, 21.0f, 0.0f));
        SeekBarView seekBarView4 = new SeekBarView(context2);
        seekBarView4.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: org.telegram.ui.GLIconSettingsView.5
            final /* synthetic */ GLIconRenderer val$mRenderer;

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarPressed(boolean z) {
            }

            public C56455(GLIconRenderer gLIconRenderer2) {
                gLIconRenderer = gLIconRenderer2;
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarDrag(boolean z, float f) {
                Icon3D icon3D4 = gLIconRenderer.model;
                if (icon3D4 != null) {
                    icon3D4.normalSpec = f * 2.0f;
                }
            }
        });
        Icon3D icon3D4 = gLIconRenderer2.model;
        seekBarView4.setProgress(icon3D4 != null ? icon3D4.normalSpec / 2.0f : 0.0f);
        seekBarView4.setReportChanges(true);
        addView(seekBarView4, LayoutHelper.createFrame(-1, 38.0f, 0, 5.0f, 4.0f, 5.0f, 0.0f));
        TextView textView6 = new TextView(context2);
        textView6.setText("Setup normal spec color");
        textView6.setTextSize(1, 16.0f);
        textView6.setLines(1);
        textView6.setGravity(17);
        textView6.setMaxLines(1);
        textView6.setSingleLine(true);
        textView6.setTextColor(Theme.getColor(i2));
        textView6.setBackground(Theme.AdaptiveRipple.filledRect(Theme.getColor(i3), 4.0f));
        textView6.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GLIconSettingsView.6
            final /* synthetic */ Context val$context;
            final /* synthetic */ GLIconRenderer val$mRenderer;

            public ViewOnClickListenerC56466(Context context2, GLIconRenderer gLIconRenderer2) {
                context = context2;
                gLIconRenderer = gLIconRenderer2;
            }

            /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$6$1 */
            public class AnonymousClass1 implements ColorPicker.ColorPickerDelegate {
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                public void setColor(int i, int i2, boolean z) {
                    Icon3D icon3D;
                    if (i2 != 0 || (icon3D = gLIconRenderer.model) == null) {
                        return;
                    }
                    icon3D.normalSpecColor = i;
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AnonymousClass2 anonymousClass2 = new ColorPicker(context, false, new ColorPicker.ColorPickerDelegate() { // from class: org.telegram.ui.GLIconSettingsView.6.1
                    public AnonymousClass1() {
                    }

                    @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                    public void setColor(int i4, int i22, boolean z) {
                        Icon3D icon3D5;
                        if (i22 != 0 || (icon3D5 = gLIconRenderer.model) == null) {
                            return;
                        }
                        icon3D5.normalSpecColor = i4;
                    }
                }) { // from class: org.telegram.ui.GLIconSettingsView.6.2
                    public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                        super(context2, z, colorPickerDelegate);
                    }

                    @Override // android.widget.FrameLayout, android.view.View
                    public void onMeasure(int i4, int i5) {
                        super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                    }
                };
                Icon3D icon3D5 = gLIconRenderer.model;
                anonymousClass2.setColor(icon3D5 == null ? 0 : icon3D5.normalSpecColor, 0);
                anonymousClass2.setType(-1, true, 1, 1, false, 0, false);
                BottomSheet bottomSheet = new BottomSheet(context, false);
                bottomSheet.setCustomView(anonymousClass2);
                bottomSheet.setDimBehind(false);
                bottomSheet.show();
            }

            /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$6$2 */
            public class AnonymousClass2 extends ColorPicker {
                public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                    super(context2, z, colorPickerDelegate);
                }

                @Override // android.widget.FrameLayout, android.view.View
                public void onMeasure(int i4, int i5) {
                    super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                }
            }
        });
        addView(textView6, LayoutHelper.createFrame(-1, 48.0f, 16, 16.0f, 0.0f, 16.0f, 0.0f));
        TextView textView7 = new TextView(context2);
        textView7.setText("Small starts size");
        textView7.setTextColor(Theme.getColor(i));
        textView7.setTextSize(1, 16.0f);
        textView7.setLines(1);
        textView7.setMaxLines(1);
        textView7.setSingleLine(true);
        textView7.setGravity((LocaleController.isRTL ? 3 : 5) | 48);
        addView(textView7, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 21.0f, 13.0f, 21.0f, 0.0f));
        SeekBarView seekBarView5 = new SeekBarView(context2);
        seekBarView5.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: org.telegram.ui.GLIconSettingsView.7
            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarPressed(boolean z) {
            }

            public C56477() {
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public void onSeekBarDrag(boolean z, float f) {
                GLIconSettingsView.smallStarsSize = f * 2.0f;
            }
        });
        seekBarView5.setProgress(smallStarsSize / 2.0f);
        seekBarView5.setReportChanges(true);
        addView(seekBarView5, LayoutHelper.createFrame(-1, 38.0f, 0, 5.0f, 4.0f, 5.0f, 0.0f));
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$1 */
    public class C56411 implements SeekBarView.SeekBarViewDelegate {
        final /* synthetic */ GLIconRenderer val$mRenderer;

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarPressed(boolean z) {
        }

        public C56411(GLIconRenderer gLIconRenderer2) {
            gLIconRenderer = gLIconRenderer2;
        }

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarDrag(boolean z, float f) {
            Icon3D icon3D = gLIconRenderer.model;
            if (icon3D != null) {
                icon3D.spec1 = f * 2.0f;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$2 */
    public class C56422 implements SeekBarView.SeekBarViewDelegate {
        final /* synthetic */ GLIconRenderer val$mRenderer;

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarPressed(boolean z) {
        }

        public C56422(GLIconRenderer gLIconRenderer2) {
            gLIconRenderer = gLIconRenderer2;
        }

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarDrag(boolean z, float f) {
            Icon3D icon3D2 = gLIconRenderer.model;
            if (icon3D2 != null) {
                icon3D2.spec2 = f * 2.0f;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$3 */
    public class ViewOnClickListenerC56433 implements View.OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ GLIconRenderer val$mRenderer;

        public ViewOnClickListenerC56433(Context context2, GLIconRenderer gLIconRenderer2) {
            context = context2;
            gLIconRenderer = gLIconRenderer2;
        }

        /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$3$1 */
        public class AnonymousClass1 implements ColorPicker.ColorPickerDelegate {
            public AnonymousClass1() {
            }

            @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
            public void setColor(int i4, int i22, boolean z) {
                Icon3D icon3D3 = gLIconRenderer.model;
                if (icon3D3 != null) {
                    icon3D3.specColor = i4;
                }
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            AnonymousClass2 anonymousClass2 = new ColorPicker(context, false, new ColorPicker.ColorPickerDelegate() { // from class: org.telegram.ui.GLIconSettingsView.3.1
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                public void setColor(int i4, int i22, boolean z) {
                    Icon3D icon3D3 = gLIconRenderer.model;
                    if (icon3D3 != null) {
                        icon3D3.specColor = i4;
                    }
                }
            }) { // from class: org.telegram.ui.GLIconSettingsView.3.2
                public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                    super(context2, z, colorPickerDelegate);
                }

                @Override // android.widget.FrameLayout, android.view.View
                public void onMeasure(int i4, int i5) {
                    super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                }
            };
            Icon3D icon3D3 = gLIconRenderer.model;
            anonymousClass2.setColor(icon3D3 != null ? icon3D3.specColor : 0, 0);
            anonymousClass2.setType(-1, true, 1, 1, false, 0, false);
            BottomSheet bottomSheet = new BottomSheet(context, false);
            bottomSheet.setCustomView(anonymousClass2);
            bottomSheet.setDimBehind(false);
            bottomSheet.show();
        }

        /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$3$2 */
        public class AnonymousClass2 extends ColorPicker {
            public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                super(context2, z, colorPickerDelegate);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i4, int i5) {
                super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$4 */
    public class C56444 implements SeekBarView.SeekBarViewDelegate {
        final /* synthetic */ GLIconRenderer val$mRenderer;

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarPressed(boolean z) {
        }

        public C56444(GLIconRenderer gLIconRenderer2) {
            gLIconRenderer = gLIconRenderer2;
        }

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarDrag(boolean z, float f) {
            Icon3D icon3D3 = gLIconRenderer.model;
            if (icon3D3 != null) {
                icon3D3.diffuse = f;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$5 */
    public class C56455 implements SeekBarView.SeekBarViewDelegate {
        final /* synthetic */ GLIconRenderer val$mRenderer;

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarPressed(boolean z) {
        }

        public C56455(GLIconRenderer gLIconRenderer2) {
            gLIconRenderer = gLIconRenderer2;
        }

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarDrag(boolean z, float f) {
            Icon3D icon3D4 = gLIconRenderer.model;
            if (icon3D4 != null) {
                icon3D4.normalSpec = f * 2.0f;
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$6 */
    public class ViewOnClickListenerC56466 implements View.OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ GLIconRenderer val$mRenderer;

        public ViewOnClickListenerC56466(Context context2, GLIconRenderer gLIconRenderer2) {
            context = context2;
            gLIconRenderer = gLIconRenderer2;
        }

        /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$6$1 */
        public class AnonymousClass1 implements ColorPicker.ColorPickerDelegate {
            public AnonymousClass1() {
            }

            @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
            public void setColor(int i4, int i22, boolean z) {
                Icon3D icon3D5;
                if (i22 != 0 || (icon3D5 = gLIconRenderer.model) == null) {
                    return;
                }
                icon3D5.normalSpecColor = i4;
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            AnonymousClass2 anonymousClass2 = new ColorPicker(context, false, new ColorPicker.ColorPickerDelegate() { // from class: org.telegram.ui.GLIconSettingsView.6.1
                public AnonymousClass1() {
                }

                @Override // org.telegram.ui.Components.ColorPicker.ColorPickerDelegate
                public void setColor(int i4, int i22, boolean z) {
                    Icon3D icon3D5;
                    if (i22 != 0 || (icon3D5 = gLIconRenderer.model) == null) {
                        return;
                    }
                    icon3D5.normalSpecColor = i4;
                }
            }) { // from class: org.telegram.ui.GLIconSettingsView.6.2
                public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                    super(context2, z, colorPickerDelegate);
                }

                @Override // android.widget.FrameLayout, android.view.View
                public void onMeasure(int i4, int i5) {
                    super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
                }
            };
            Icon3D icon3D5 = gLIconRenderer.model;
            anonymousClass2.setColor(icon3D5 == null ? 0 : icon3D5.normalSpecColor, 0);
            anonymousClass2.setType(-1, true, 1, 1, false, 0, false);
            BottomSheet bottomSheet = new BottomSheet(context, false);
            bottomSheet.setCustomView(anonymousClass2);
            bottomSheet.setDimBehind(false);
            bottomSheet.show();
        }

        /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$6$2 */
        public class AnonymousClass2 extends ColorPicker {
            public AnonymousClass2(Context context2, boolean z, ColorPicker.ColorPickerDelegate colorPickerDelegate) {
                super(context2, z, colorPickerDelegate);
            }

            @Override // android.widget.FrameLayout, android.view.View
            public void onMeasure(int i4, int i5) {
                super.onMeasure(i4, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1036dp(300.0f), TLObject.FLAG_30));
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.GLIconSettingsView$7 */
    public class C56477 implements SeekBarView.SeekBarViewDelegate {
        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarPressed(boolean z) {
        }

        public C56477() {
        }

        @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
        public void onSeekBarDrag(boolean z, float f) {
            GLIconSettingsView.smallStarsSize = f * 2.0f;
        }
    }
}
