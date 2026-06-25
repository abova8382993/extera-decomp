package io.noties.markwon.core;

import io.noties.markwon.Prop;

/* JADX INFO: loaded from: classes5.dex */
public abstract class CoreProps {
    public static final Prop<ListItemType> LIST_ITEM_TYPE = Prop.m562of("list-item-type");
    public static final Prop<Integer> BULLET_LIST_ITEM_LEVEL = Prop.m562of("bullet-list-item-level");
    public static final Prop<Integer> ORDERED_LIST_ITEM_NUMBER = Prop.m562of("ordered-list-item-number");
    public static final Prop<Integer> HEADING_LEVEL = Prop.m562of("heading-level");
    public static final Prop<String> LINK_DESTINATION = Prop.m562of("link-destination");
    public static final Prop<Boolean> PARAGRAPH_IS_IN_TIGHT_LIST = Prop.m562of("paragraph-is-in-tight-list");
    public static final Prop<String> CODE_BLOCK_INFO = Prop.m562of("code-block-info");

    public enum ListItemType {
        BULLET,
        ORDERED
    }
}
