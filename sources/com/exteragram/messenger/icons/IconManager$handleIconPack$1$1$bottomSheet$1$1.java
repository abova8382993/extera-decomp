package com.exteragram.messenger.icons;

import com.exteragram.messenger.icons.IconPackStorageResult;
import java.io.File;
import kotlin.LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.internal.url._UrlKt;
import okio.Segment$$ExternalSyntheticBUOutline1;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.LocaleController;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.Components.BulletinFactory;

/* JADX INFO: loaded from: classes4.dex */
@Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
@DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1", m896f = "IconManager.kt", m897i = {1}, m898l = {676, 677}, m899m = "invokeSuspend", m900n = {"installResult"}, m902s = {"L$0"}, m903v = 1)
public final class IconManager$handleIconPack$1$1$bottomSheet$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ BaseFragment $baseFragment;
    final /* synthetic */ boolean $enable;
    final /* synthetic */ File $file;
    final /* synthetic */ IconPack $pack;
    final /* synthetic */ boolean $update;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconManager$handleIconPack$1$1$bottomSheet$1$1(File file, BaseFragment baseFragment, boolean z, IconPack iconPack, boolean z2, Continuation<? super IconManager$handleIconPack$1$1$bottomSheet$1$1> continuation) {
        super(2, continuation);
        this.$file = file;
        this.$baseFragment = baseFragment;
        this.$update = z;
        this.$pack = iconPack;
        this.$enable = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new IconManager$handleIconPack$1$1$bottomSheet$1$1(this.$file, this.$baseFragment, this.$update, this.$pack, this.$enable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((IconManager$handleIconPack$1$1$bottomSheet$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0052, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r3, r10) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L21
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L16
            java.lang.Object r10 = r10.L$0
            com.exteragram.messenger.icons.IconPackStorageResult r10 = (com.exteragram.messenger.icons.IconPackStorageResult) r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L55
        L16:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            okio.Segment$$ExternalSyntheticBUOutline1.m992m(r10)
            r10 = 0
            return r10
        L1d:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L31
        L21:
            kotlin.ResultKt.throwOnFailure(r11)
            com.exteragram.messenger.icons.IconPackStorage r11 = com.exteragram.messenger.icons.IconPackStorage.INSTANCE
            java.io.File r1 = r10.$file
            r10.label = r3
            java.lang.Object r11 = r11.installPack(r1, r10)
            if (r11 != r0) goto L31
            goto L54
        L31:
            r4 = r11
            com.exteragram.messenger.icons.IconPackStorageResult r4 = (com.exteragram.messenger.icons.IconPackStorageResult) r4
            kotlinx.coroutines.MainCoroutineDispatcher r11 = kotlinx.coroutines.Dispatchers.getMain()
            com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1 r3 = new com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1
            org.telegram.ui.ActionBar.BaseFragment r5 = r10.$baseFragment
            boolean r6 = r10.$update
            com.exteragram.messenger.icons.IconPack r7 = r10.$pack
            boolean r8 = r10.$enable
            r9 = 0
            r3.<init>(r4, r5, r6, r7, r8, r9)
            java.lang.Object r1 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r4)
            r10.L$0 = r1
            r10.label = r2
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r11, r3, r10)
            if (r10 != r0) goto L55
        L54:
            return r0
        L55:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: renamed from: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1 */
    @Metadata(m876d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, m877d2 = {"<anonymous>", _UrlKt.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/CoroutineScope;"}, m878k = 3, m879mv = {2, 2, 0}, m881xi = 48)
    @DebugMetadata(m895c = "com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1", m896f = "IconManager.kt", m897i = {}, m898l = {}, m899m = "invokeSuspend", m900n = {}, m902s = {}, m903v = 1)
    public static final class C11391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ BaseFragment $baseFragment;
        final /* synthetic */ boolean $enable;
        final /* synthetic */ IconPackStorageResult<Unit> $installResult;
        final /* synthetic */ IconPack $pack;
        final /* synthetic */ boolean $update;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11391(IconPackStorageResult<Unit> iconPackStorageResult, BaseFragment baseFragment, boolean z, IconPack iconPack, boolean z2, Continuation<? super C11391> continuation) {
            super(2, continuation);
            this.$installResult = iconPackStorageResult;
            this.$baseFragment = baseFragment;
            this.$update = z;
            this.$pack = iconPack;
            this.$enable = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C11391(this.$installResult, this.$baseFragment, this.$update, this.$pack, this.$enable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C11391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                Segment$$ExternalSyntheticBUOutline1.m992m("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
            ResultKt.throwOnFailure(obj);
            IconPackStorageResult<Unit> iconPackStorageResult = this.$installResult;
            if (iconPackStorageResult instanceof IconPackStorageResult.Success) {
                BulletinFactory.m1143of(this.$baseFragment).createSimpleBulletin(C2797R.raw.contact_check, LocaleController.formatString(this.$update ? C2797R.string.PluginUpdated : C2797R.string.PluginInstalled, this.$pack.getName())).show();
                if (this.$enable) {
                    IconManager.INSTANCE.setActiveCustomPack(this.$pack.getId());
                } else {
                    IconManager iconManager = IconManager.INSTANCE;
                    iconManager.syncInstalledCustomPacks(CollectionsKt.listOf(this.$pack));
                    iconManager.initialize(true);
                }
            } else if (iconPackStorageResult instanceof IconPackStorageResult.Failure) {
                IconManager.INSTANCE.showIconPackError(this.$baseFragment, ((IconPackStorageResult.Failure) iconPackStorageResult).getError());
            } else {
                LazyKt__LazyJVMKt$$ExternalSyntheticBUOutline0.m874m();
                return null;
            }
            return Unit.INSTANCE;
        }
    }
}
