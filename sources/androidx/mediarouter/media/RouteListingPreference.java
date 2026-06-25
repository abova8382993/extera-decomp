package androidx.mediarouter.media;

import android.content.ComponentName;
import android.media.RouteListingPreference;
import android.text.TextUtils;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public final class RouteListingPreference {
    private final boolean mIsSystemOrderingEnabled;
    private final List<Item> mItems;
    private final ComponentName mLinkedItemComponentName;

    public RouteListingPreference(Builder builder) {
        this.mItems = builder.mItems;
        this.mIsSystemOrderingEnabled = builder.mIsSystemOrderingEnabled;
        this.mLinkedItemComponentName = builder.mLinkedItemComponentName;
    }

    public List<Item> getItems() {
        return this.mItems;
    }

    public boolean isSystemOrderingEnabled() {
        return this.mIsSystemOrderingEnabled;
    }

    public ComponentName getLinkedItemComponentName() {
        return this.mLinkedItemComponentName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RouteListingPreference)) {
            return false;
        }
        RouteListingPreference routeListingPreference = (RouteListingPreference) obj;
        return this.mItems.equals(routeListingPreference.mItems) && this.mIsSystemOrderingEnabled == routeListingPreference.mIsSystemOrderingEnabled && Objects.equals(this.mLinkedItemComponentName, routeListingPreference.mLinkedItemComponentName);
    }

    public int hashCode() {
        return Objects.hash(this.mItems, Boolean.valueOf(this.mIsSystemOrderingEnabled), this.mLinkedItemComponentName);
    }

    public android.media.RouteListingPreference toPlatformRouteListingPreference() {
        return Api34Impl.toPlatformRouteListingPreference(this);
    }

    public static final class Builder {
        ComponentName mLinkedItemComponentName;
        List<Item> mItems = Collections.EMPTY_LIST;
        boolean mIsSystemOrderingEnabled = true;

        public Builder setItems(List<Item> list) {
            Objects.requireNonNull(list);
            this.mItems = Collections.unmodifiableList(new ArrayList(list));
            return this;
        }

        public RouteListingPreference build() {
            return new RouteListingPreference(this);
        }
    }

    public static final class Item {
        private final CharSequence mCustomSubtextMessage;
        private final int mFlags;
        private final String mRouteId;
        private final int mSelectionBehavior;
        private final int mSubText;

        public Item(Builder builder) {
            this.mRouteId = builder.mRouteId;
            this.mSelectionBehavior = builder.mSelectionBehavior;
            this.mFlags = builder.mFlags;
            this.mSubText = builder.mSubText;
            this.mCustomSubtextMessage = builder.mCustomSubtextMessage;
            validateCustomMessageSubtext();
        }

        public String getRouteId() {
            return this.mRouteId;
        }

        public int getSelectionBehavior() {
            return this.mSelectionBehavior;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getSubText() {
            return this.mSubText;
        }

        public CharSequence getCustomSubtextMessage() {
            return this.mCustomSubtextMessage;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Item)) {
                return false;
            }
            Item item = (Item) obj;
            return this.mRouteId.equals(item.mRouteId) && this.mSelectionBehavior == item.mSelectionBehavior && this.mFlags == item.mFlags && this.mSubText == item.mSubText && TextUtils.equals(this.mCustomSubtextMessage, item.mCustomSubtextMessage);
        }

        public int hashCode() {
            return Objects.hash(this.mRouteId, Integer.valueOf(this.mSelectionBehavior), Integer.valueOf(this.mFlags), Integer.valueOf(this.mSubText), this.mCustomSubtextMessage);
        }

        private void validateCustomMessageSubtext() {
            Preconditions.checkArgument((this.mSubText == 10000 && this.mCustomSubtextMessage == null) ? false : true, "The custom subtext message cannot be null if subtext is SUBTEXT_CUSTOM.");
        }

        public static final class Builder {
            CharSequence mCustomSubtextMessage;
            int mFlags;
            final String mRouteId;
            int mSelectionBehavior;
            int mSubText;

            public Builder(String str) {
                Preconditions.checkArgument(!TextUtils.isEmpty(str));
                this.mRouteId = str;
                this.mSelectionBehavior = 1;
                this.mSubText = 0;
            }

            public Builder setFlags(int i) {
                this.mFlags = i;
                return this;
            }

            public Item build() {
                return new Item(this);
            }
        }
    }

    public static class Api34Impl {
        public static android.media.RouteListingPreference toPlatformRouteListingPreference(RouteListingPreference routeListingPreference) {
            ArrayList arrayList = new ArrayList();
            Iterator<Item> it = routeListingPreference.getItems().iterator();
            while (it.hasNext()) {
                arrayList.add(toPlatformItem(it.next()));
            }
            return new RouteListingPreference.Builder().setItems(arrayList).setLinkedItemComponentName(routeListingPreference.getLinkedItemComponentName()).setUseSystemOrdering(routeListingPreference.isSystemOrderingEnabled()).build();
        }

        public static RouteListingPreference.Item toPlatformItem(Item item) {
            return new RouteListingPreference.Item.Builder(item.getRouteId()).setFlags(item.getFlags()).setSubText(item.getSubText()).setCustomSubtextMessage(item.getCustomSubtextMessage()).setSelectionBehavior(item.getSelectionBehavior()).build();
        }
    }
}
