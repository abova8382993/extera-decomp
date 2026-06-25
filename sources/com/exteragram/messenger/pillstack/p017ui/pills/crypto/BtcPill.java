package com.exteragram.messenger.pillstack.p017ui.pills.crypto;

import android.annotation.SuppressLint;
import android.content.Context;
import com.exteragram.messenger.pillstack.core.PillStackConfig;
import com.exteragram.messenger.pillstack.core.PillType;
import com.exteragram.messenger.pillstack.p017ui.pills.crypto.RatePill;
import com.exteragram.messenger.pillstack.p017ui.pills.crypto.utils.ColoredBackground;
import org.telegram.messenger.C2797R;
import org.telegram.p035ui.ActionBar.Theme;

/* JADX INFO: loaded from: classes4.dex */
@SuppressLint({"ViewConstructor"})
public class BtcPill extends RatePill {
    private static final RatePill.RateCache CACHE = new RatePill.RateCache();

    public BtcPill(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider, CACHE, "BTC", 2, C2797R.drawable.pillstack_btc, new ColoredBackground(-1071598, -1608430));
    }

    @Override // com.exteragram.messenger.pillstack.p017ui.pills.BasePill
    public int getPillId() {
        return PillType.BTC.getId();
    }

    @Override // com.exteragram.messenger.pillstack.p017ui.pills.crypto.RatePill
    public String getTargetSelection() {
        return PillStackConfig.getBtcTargetCurrency();
    }

    @Override // com.exteragram.messenger.pillstack.p017ui.pills.crypto.RatePill
    public void setTargetSelection(String str) {
        PillStackConfig.setBtcTargetCurrency(str);
    }
}
