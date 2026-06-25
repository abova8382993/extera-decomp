package org.telegram.p035ui.Components;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.export.p014ui.ExportMapper$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.AlertDialog;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.BottomSheet;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.Cells.CollapseTextCell;
import org.telegram.p035ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p035ui.Components.DeleteMessagesBottomSheet;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* JADX INFO: loaded from: classes7.dex */
public class DeleteMessagesBottomSheet extends BottomSheetWithRecyclerListView {
    private ButtonWithCounterView actionButton;
    private UniversalAdapter adapter;
    private boolean banChecked;
    private boolean[] banFilter;
    private Action banOrRestrict;
    private TLRPC.TL_chatBannedRights bannedRights;
    private boolean canRestrict;
    private TLRPC.TL_chatBannedRights defaultBannedRights;
    private Action deleteAll;
    private Action deleteAllReactions;
    private TLRPC.Chat inChat;
    private boolean isForum;
    private final boolean isReactionOnlyMode;
    private final boolean isSingleUsersMode;
    private long mergeDialogId;
    private ArrayList<MessageObject> messages;
    private int mode;
    private boolean monoforum;
    private Runnable onDelete;
    private int[] participantMessageCounts;
    private boolean participantMessageCountsLoaded;
    private boolean participantMessageCountsLoading;
    private ArrayList<TLRPC.TL_chatBannedRights> participantsBannedRights;
    private Action report;
    private boolean restrict;
    private boolean[] restrictFilter;
    private boolean restrictUserCollapsed;
    private boolean restrictUserDeleteAllMessages;
    private boolean restrictUserDeleteAllReactions;
    private boolean sendMediaCollapsed;
    private float shiftDp;
    private int topicId;

    public class Action {
        boolean[] checks;
        boolean collapsed;
        boolean[] filter;
        int filteredCount;
        ArrayList<TLObject> options;
        int selectedCount;
        String title;
        int totalCount;
        int type;

        public Action(int i, ArrayList<TLObject> arrayList) {
            this.type = i;
            int size = arrayList.size();
            this.totalCount = size;
            this.selectedCount = 0;
            if (size > 0) {
                this.options = arrayList;
                this.checks = new boolean[size];
                this.collapsed = true;
                updateTitle();
            }
        }

        public int getCount() {
            if (this.filter != null) {
                return this.filteredCount;
            }
            return this.totalCount;
        }

        public boolean isPresent() {
            return getCount() > 0;
        }

        public boolean isExpandable() {
            return getCount() > 1;
        }

        public void setFilter(boolean[] zArr) {
            if (this.totalCount == 0) {
                return;
            }
            this.filter = zArr;
            updateCounters();
            updateTitle();
        }

        public void updateCounters() {
            this.selectedCount = 0;
            this.filteredCount = 0;
            for (int i = 0; i < this.totalCount; i++) {
                boolean[] zArr = this.filter;
                if (zArr == null) {
                    if (this.checks[i]) {
                        this.selectedCount++;
                    }
                } else if (zArr[i]) {
                    this.filteredCount++;
                    if (this.checks[i]) {
                        this.selectedCount++;
                    }
                }
            }
        }

        public TLObject first() {
            for (int i = 0; i < this.totalCount; i++) {
                boolean[] zArr = this.filter;
                if (zArr == null || zArr[i]) {
                    return this.options.get(i);
                }
            }
            return null;
        }

        public void updateTitle() {
            String name;
            String string;
            String string2;
            if (this.totalCount == 0) {
                return;
            }
            TLObject tLObjectFirst = first();
            if (tLObjectFirst instanceof TLRPC.User) {
                name = UserObject.getForcedFirstName((TLRPC.User) tLObjectFirst);
            } else {
                name = ContactsController.formatName(tLObjectFirst);
            }
            int i = this.type;
            if (i == 0) {
                this.title = LocaleController.getString(C2797R.string.DeleteReportSpam);
                return;
            }
            if (i == 1) {
                if (isExpandable()) {
                    string2 = LocaleController.getString(C2797R.string.DeleteAllMessagesFromUsers);
                } else {
                    string2 = LocaleController.formatString(C2797R.string.DeleteAllFrom, name);
                }
                this.title = string2;
                return;
            }
            if (i == 3) {
                if (isExpandable()) {
                    string = LocaleController.getString(C2797R.string.DeleteAllReactionsFromUsers);
                } else {
                    string = LocaleController.formatString(C2797R.string.DeleteAllReactionsFrom, name);
                }
                this.title = string;
                return;
            }
            if (i == 2) {
                if (DeleteMessagesBottomSheet.this.restrict) {
                    this.title = isExpandable() ? LocaleController.getString(C2797R.string.DeleteRestrictUsers) : LocaleController.formatString(C2797R.string.DeleteRestrict, name);
                } else {
                    this.title = isExpandable() ? LocaleController.getString(C2797R.string.DeleteBanUsers) : LocaleController.formatString(C2797R.string.DeleteBan, name);
                }
            }
        }

        public void collapseOrExpand() {
            this.collapsed = !this.collapsed;
            DeleteMessagesBottomSheet.this.adapter.update(true);
        }

        public void toggleCheck(int i) {
            boolean[] zArr = this.filter;
            if (zArr == null || zArr[i]) {
                boolean[] zArr2 = this.checks;
                boolean z = zArr2[i];
                zArr2[i] = !z;
                int i2 = this.selectedCount;
                if (!z) {
                    this.selectedCount = i2 + 1;
                } else {
                    this.selectedCount = i2 - 1;
                }
                DeleteMessagesBottomSheet.this.adapter.update(true);
            }
        }

        public boolean areAllSelected() {
            boolean[] zArr;
            for (int i = 0; i < this.totalCount; i++) {
                if (!this.checks[i] || ((zArr = this.filter) != null && !zArr[i])) {
                    return false;
                }
            }
            return true;
        }

        public boolean isOneSelected() {
            boolean[] zArr;
            for (int i = 0; i < this.totalCount; i++) {
                if (this.checks[i] && ((zArr = this.filter) == null || zArr[i])) {
                    return true;
                }
            }
            return false;
        }

        public void toggleAllChecks() {
            setAllChecks(!isOneSelected());
        }

        public void setAllChecks(boolean z) {
            setAllChecks(z, true);
        }

        public void setAllChecks(boolean z, boolean z2) {
            Arrays.fill(this.checks, z);
            updateCounters();
            if (z2) {
                DeleteMessagesBottomSheet.this.adapter.update(true);
            }
        }

        public void forEachSelected(Utilities.IndexedConsumer<TLObject> indexedConsumer) {
            boolean[] zArr;
            for (int i = 0; i < this.totalCount; i++) {
                if (this.checks[i] && ((zArr = this.filter) == null || zArr[i])) {
                    indexedConsumer.accept(this.options.get(i), i);
                }
            }
        }

        public void forEach(Utilities.IndexedConsumer<TLObject> indexedConsumer) {
            for (int i = 0; i < this.totalCount; i++) {
                boolean[] zArr = this.filter;
                if (zArr == null || zArr[i]) {
                    indexedConsumer.accept(this.options.get(i), i);
                }
            }
        }
    }

    public DeleteMessagesBottomSheet(BaseFragment baseFragment, TLRPC.Chat chat, ArrayList<MessageObject> arrayList, ArrayList<TLObject> arrayList2, TLRPC.ChannelParticipant[] channelParticipantArr, long j, int i, int i2, boolean z, Runnable runnable) {
        TLRPC.TL_chatBannedRights tL_chatBannedRights;
        TLRPC.TL_chatBannedRights tL_chatBannedRights2;
        super(baseFragment.getContext(), baseFragment, false, false, false, true, BottomSheetWithRecyclerListView.ActionBarType.SLIDING, baseFragment.getResourceProvider());
        this.restrict = false;
        this.participantMessageCountsLoading = false;
        this.participantMessageCountsLoaded = false;
        this.sendMediaCollapsed = true;
        this.restrictUserCollapsed = true;
        this.restrictUserDeleteAllMessages = false;
        this.restrictUserDeleteAllReactions = false;
        this.shiftDp = 10.0f;
        setBackgroundColor(getThemedColor(Theme.key_windowBackgroundGray));
        setShowHandle(true);
        fixNavigationBar();
        this.takeTranslationIntoAccount = true;
        this.isReactionOnlyMode = z;
        RecyclerListView recyclerListView = this.recyclerListView;
        int i3 = this.backgroundPaddingLeft;
        recyclerListView.setPadding(i3, this.headerTotalHeight, i3, AndroidUtilities.m1036dp(63.0f));
        this.recyclerListView.setClipToPadding(false);
        this.recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i4, float f, float f2) {
                this.f$0.lambda$new$0(view, i4, f, f2);
            }
        });
        this.takeTranslationIntoAccount = true;
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet.1
            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            public void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onMoveAnimationUpdate(viewHolder);
                ((BottomSheet) DeleteMessagesBottomSheet.this).containerView.invalidate();
            }
        };
        defaultItemAnimator.setSupportsChangeAnimations(false);
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        defaultItemAnimator.setDurations(350L);
        this.recyclerListView.setItemAnimator(defaultItemAnimator);
        this.recyclerListView.setSections();
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(getContext(), true, this.resourcesProvider);
        this.actionButton = buttonWithCounterView;
        buttonWithCounterView.setRound();
        this.actionButton.setText(LocaleController.getString(C2797R.string.DeleteProceedBtn));
        this.actionButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(view);
            }
        });
        this.containerView.addView(this.actionButton, LayoutHelper.createFrameMarginPx(-1, 48.0f, 87, this.backgroundPaddingLeft + AndroidUtilities.m1036dp(10.0f), 0, this.backgroundPaddingLeft + AndroidUtilities.m1036dp(10.0f), AndroidUtilities.m1036dp(10.0f)));
        this.inChat = chat;
        this.isForum = ChatObject.isForum(chat);
        this.messages = arrayList;
        this.mergeDialogId = j;
        this.topicId = i;
        this.mode = i2;
        this.onDelete = runnable;
        this.defaultBannedRights = chat.default_banned_rights;
        TLRPC.TL_chatBannedRights tL_chatBannedRights3 = new TLRPC.TL_chatBannedRights();
        this.bannedRights = tL_chatBannedRights3;
        TLRPC.TL_chatBannedRights tL_chatBannedRights4 = this.defaultBannedRights;
        if (tL_chatBannedRights4.view_messages) {
            tL_chatBannedRights3.view_messages = true;
        }
        if (tL_chatBannedRights4.send_messages) {
            tL_chatBannedRights3.send_messages = true;
        }
        if (tL_chatBannedRights4.send_media) {
            tL_chatBannedRights3.send_media = true;
        }
        if (tL_chatBannedRights4.send_stickers) {
            tL_chatBannedRights3.send_stickers = true;
        }
        if (tL_chatBannedRights4.send_gifs) {
            tL_chatBannedRights3.send_gifs = true;
        }
        if (tL_chatBannedRights4.send_games) {
            tL_chatBannedRights3.send_games = true;
        }
        if (tL_chatBannedRights4.send_inline) {
            tL_chatBannedRights3.send_inline = true;
        }
        if (tL_chatBannedRights4.embed_links) {
            tL_chatBannedRights3.embed_links = true;
        }
        if (tL_chatBannedRights4.send_polls) {
            tL_chatBannedRights3.send_polls = true;
        }
        if (tL_chatBannedRights4.invite_users) {
            tL_chatBannedRights3.invite_users = true;
        }
        if (tL_chatBannedRights4.change_info) {
            tL_chatBannedRights3.change_info = true;
        }
        if (tL_chatBannedRights4.pin_messages) {
            tL_chatBannedRights3.pin_messages = true;
        }
        if (tL_chatBannedRights4.manage_topics) {
            tL_chatBannedRights3.manage_topics = true;
        }
        if (tL_chatBannedRights4.send_photos) {
            tL_chatBannedRights3.send_photos = true;
        }
        if (tL_chatBannedRights4.send_videos) {
            tL_chatBannedRights3.send_videos = true;
        }
        if (tL_chatBannedRights4.send_audios) {
            tL_chatBannedRights3.send_audios = true;
        }
        if (tL_chatBannedRights4.send_docs) {
            tL_chatBannedRights3.send_docs = true;
        }
        if (tL_chatBannedRights4.send_voices) {
            tL_chatBannedRights3.send_voices = true;
        }
        if (tL_chatBannedRights4.send_roundvideos) {
            tL_chatBannedRights3.send_roundvideos = true;
        }
        if (tL_chatBannedRights4.send_plain) {
            tL_chatBannedRights3.send_plain = true;
        }
        if (tL_chatBannedRights4.send_reactions) {
            tL_chatBannedRights3.send_reactions = true;
        }
        MessagesController.getInstance(this.currentAccount).getMainSettings();
        this.report = new Action(0, arrayList2);
        this.deleteAll = new Action(1, arrayList2);
        this.deleteAllReactions = new Action(3, arrayList2);
        this.isSingleUsersMode = arrayList2.size() == 1;
        this.monoforum = ChatObject.isMonoForum(chat);
        if (ChatObject.canBlockUsers(chat)) {
            this.banFilter = new boolean[arrayList2.size()];
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList2.size()) {
                    break;
                }
                TLRPC.ChannelParticipant channelParticipant = i4 < channelParticipantArr.length ? channelParticipantArr[i4] : null;
                if ((chat.creator || (!(channelParticipant instanceof TLRPC.TL_channelParticipantAdmin) && !(channelParticipant instanceof TLRPC.TL_channelParticipantCreator))) && (!(channelParticipant instanceof TLRPC.TL_channelParticipantBanned) || (tL_chatBannedRights2 = channelParticipant.banned_rights) == null || !isBanned(tL_chatBannedRights2))) {
                    this.banFilter[i4] = true;
                }
                i4++;
            }
            this.restrictFilter = new boolean[arrayList2.size()];
            if (hasAnyDefaultRights()) {
                int i5 = 0;
                while (i5 < arrayList2.size()) {
                    TLRPC.ChannelParticipant channelParticipant2 = i5 < channelParticipantArr.length ? channelParticipantArr[i5] : null;
                    if (!(arrayList2.get(i5) instanceof TLRPC.Chat) && ((!(channelParticipant2 instanceof TLRPC.TL_channelParticipantBanned) || (tL_chatBannedRights = channelParticipant2.banned_rights) == null || canBeRestricted(tL_chatBannedRights)) && this.banFilter[i5])) {
                        this.restrictFilter[i5] = true;
                        this.canRestrict = true;
                    }
                    i5++;
                }
            }
            this.participantsBannedRights = (ArrayList) Arrays.stream(channelParticipantArr).map(new Function() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DeleteMessagesBottomSheet.$r8$lambda$FIvSAJdGQOuakkUWlmKDlVelxWg((TLRPC.ChannelParticipant) obj);
                }
            }).collect(Collectors.toCollection(new ExportMapper$$ExternalSyntheticLambda2()));
            Action action = new Action(2, arrayList2);
            this.banOrRestrict = action;
            action.setFilter(this.banFilter);
        } else {
            this.banOrRestrict = new Action(2, new ArrayList(0));
        }
        this.adapter.update(false);
        this.actionBar.setTitle(getTitle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i, float f, float f2) {
        UItem item = this.adapter.getItem(i - 1);
        if (item == null) {
            return;
        }
        onClick(item, view, i, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        proceed();
    }

    public static /* synthetic */ TLRPC.TL_chatBannedRights $r8$lambda$FIvSAJdGQOuakkUWlmKDlVelxWg(TLRPC.ChannelParticipant channelParticipant) {
        if (channelParticipant == null) {
            return null;
        }
        return channelParticipant.banned_rights;
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet
    public void onContainerLayout(int i, int i2, int i3, int i4) {
        super.onContainerLayout(i, i2, i3, i4);
        Rect rect = AndroidUtilities.rectTmp2;
        rect.set(0, 0, this.recyclerListView.getMeasuredWidth(), this.recyclerListView.getMeasuredHeight() - AndroidUtilities.m1036dp(34.0f));
        this.recyclerListView.setClipBounds(rect);
    }

    private static boolean isBanned(TLRPC.TL_chatBannedRights tL_chatBannedRights) {
        return tL_chatBannedRights.view_messages;
    }

    private boolean hasAnyDefaultRights() {
        TLRPC.TL_chatBannedRights tL_chatBannedRights = this.defaultBannedRights;
        if (tL_chatBannedRights.send_messages && tL_chatBannedRights.send_media && tL_chatBannedRights.send_stickers && tL_chatBannedRights.send_gifs && tL_chatBannedRights.send_games && tL_chatBannedRights.send_inline && tL_chatBannedRights.embed_links && tL_chatBannedRights.send_polls && tL_chatBannedRights.send_reactions && tL_chatBannedRights.change_info && tL_chatBannedRights.invite_users && tL_chatBannedRights.pin_messages) {
            return ((tL_chatBannedRights.manage_topics || !this.isForum) && tL_chatBannedRights.send_photos && tL_chatBannedRights.send_videos && tL_chatBannedRights.send_roundvideos && tL_chatBannedRights.send_audios && tL_chatBannedRights.send_voices && tL_chatBannedRights.send_docs && tL_chatBannedRights.send_plain) ? false : true;
        }
        return true;
    }

    public static TLRPC.TL_chatBannedRights bannedRightsOr(TLRPC.TL_chatBannedRights tL_chatBannedRights, TLRPC.TL_chatBannedRights tL_chatBannedRights2) {
        if (tL_chatBannedRights == null) {
            return tL_chatBannedRights2;
        }
        if (tL_chatBannedRights2 == null) {
            return tL_chatBannedRights;
        }
        TLRPC.TL_chatBannedRights tL_chatBannedRights3 = new TLRPC.TL_chatBannedRights();
        boolean z = true;
        tL_chatBannedRights3.view_messages = tL_chatBannedRights.view_messages || tL_chatBannedRights2.view_messages;
        tL_chatBannedRights3.send_messages = tL_chatBannedRights.send_messages || tL_chatBannedRights2.send_messages;
        tL_chatBannedRights3.send_media = tL_chatBannedRights.send_media || tL_chatBannedRights2.send_media;
        tL_chatBannedRights3.send_stickers = tL_chatBannedRights.send_stickers || tL_chatBannedRights2.send_stickers;
        tL_chatBannedRights3.send_gifs = tL_chatBannedRights.send_gifs || tL_chatBannedRights2.send_gifs;
        tL_chatBannedRights3.send_games = tL_chatBannedRights.send_games || tL_chatBannedRights2.send_games;
        tL_chatBannedRights3.send_inline = tL_chatBannedRights.send_inline || tL_chatBannedRights2.send_inline;
        tL_chatBannedRights3.embed_links = tL_chatBannedRights.embed_links || tL_chatBannedRights2.embed_links;
        tL_chatBannedRights3.send_polls = tL_chatBannedRights.send_polls || tL_chatBannedRights2.send_polls;
        tL_chatBannedRights3.send_reactions = tL_chatBannedRights.send_reactions || tL_chatBannedRights2.send_reactions;
        tL_chatBannedRights3.change_info = tL_chatBannedRights.change_info || tL_chatBannedRights2.change_info;
        tL_chatBannedRights3.invite_users = tL_chatBannedRights.invite_users || tL_chatBannedRights2.invite_users;
        tL_chatBannedRights3.pin_messages = tL_chatBannedRights.pin_messages || tL_chatBannedRights2.pin_messages;
        tL_chatBannedRights3.manage_topics = tL_chatBannedRights.manage_topics || tL_chatBannedRights2.manage_topics;
        tL_chatBannedRights3.send_photos = tL_chatBannedRights.send_photos || tL_chatBannedRights2.send_photos;
        tL_chatBannedRights3.send_videos = tL_chatBannedRights.send_videos || tL_chatBannedRights2.send_videos;
        tL_chatBannedRights3.send_roundvideos = tL_chatBannedRights.send_roundvideos || tL_chatBannedRights2.send_roundvideos;
        tL_chatBannedRights3.send_audios = tL_chatBannedRights.send_audios || tL_chatBannedRights2.send_audios;
        tL_chatBannedRights3.send_voices = tL_chatBannedRights.send_voices || tL_chatBannedRights2.send_voices;
        tL_chatBannedRights3.send_docs = tL_chatBannedRights.send_docs || tL_chatBannedRights2.send_docs;
        if (!tL_chatBannedRights.send_plain && !tL_chatBannedRights2.send_plain) {
            z = false;
        }
        tL_chatBannedRights3.send_plain = z;
        return tL_chatBannedRights3;
    }

    private boolean canBeRestricted(TLRPC.TL_chatBannedRights tL_chatBannedRights) {
        if (!tL_chatBannedRights.send_stickers && !this.defaultBannedRights.send_stickers) {
            return true;
        }
        if (!tL_chatBannedRights.send_gifs && !this.defaultBannedRights.send_gifs) {
            return true;
        }
        if (!tL_chatBannedRights.send_games && !this.defaultBannedRights.send_games) {
            return true;
        }
        if (!tL_chatBannedRights.send_inline && !this.defaultBannedRights.send_inline) {
            return true;
        }
        if (!tL_chatBannedRights.embed_links && !tL_chatBannedRights.send_plain) {
            TLRPC.TL_chatBannedRights tL_chatBannedRights2 = this.defaultBannedRights;
            if (!tL_chatBannedRights2.embed_links && !tL_chatBannedRights2.send_plain) {
                return true;
            }
        }
        if (!tL_chatBannedRights.send_polls && !this.defaultBannedRights.send_polls) {
            return true;
        }
        if (!tL_chatBannedRights.send_reactions && !this.defaultBannedRights.send_reactions) {
            return true;
        }
        if (!tL_chatBannedRights.change_info && !this.defaultBannedRights.change_info) {
            return true;
        }
        if (!tL_chatBannedRights.invite_users && !this.defaultBannedRights.invite_users) {
            return true;
        }
        if (!tL_chatBannedRights.pin_messages && !this.defaultBannedRights.pin_messages) {
            return true;
        }
        if (!tL_chatBannedRights.manage_topics && !this.defaultBannedRights.manage_topics && this.isForum) {
            return true;
        }
        if (!tL_chatBannedRights.send_photos && !this.defaultBannedRights.send_photos) {
            return true;
        }
        if (!tL_chatBannedRights.send_videos && !this.defaultBannedRights.send_videos) {
            return true;
        }
        if (!tL_chatBannedRights.send_roundvideos && !this.defaultBannedRights.send_roundvideos) {
            return true;
        }
        if (!tL_chatBannedRights.send_audios && !this.defaultBannedRights.send_audios) {
            return true;
        }
        if (!tL_chatBannedRights.send_voices && !this.defaultBannedRights.send_voices) {
            return true;
        }
        if (tL_chatBannedRights.send_docs || this.defaultBannedRights.send_docs) {
            return (tL_chatBannedRights.send_plain || this.defaultBannedRights.send_plain) ? false : true;
        }
        return true;
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public CharSequence getTitle() {
        if (this.isReactionOnlyMode) {
            if (this.restrictUserDeleteAllMessages) {
                return LocaleController.getString(C2797R.string.DeleteMessagesOptionsTitleAll);
            }
            if (this.restrictUserDeleteAllReactions) {
                return LocaleController.getString(C2797R.string.DeleteReactionOptionsTitleAll);
            }
            return LocaleController.formatPluralString("DeleteReactionOptionsTitle", 1, new Object[0]);
        }
        ArrayList<MessageObject> arrayList = this.messages;
        final int[] iArr = {arrayList != null ? arrayList.size() : 0};
        if (this.participantMessageCounts != null && this.participantMessageCountsLoaded) {
            this.deleteAll.forEachSelected(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.IndexedConsumer
                public final void accept(Object obj, int i) {
                    this.f$0.lambda$getTitle$3(iArr, (TLObject) obj, i);
                }
            });
        }
        return LocaleController.formatPluralString("DeleteOptionsTitle", iArr[0], new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTitle$3(int[] iArr, TLObject tLObject, int i) {
        iArr[0] = iArr[0] + this.participantMessageCounts[i];
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
        UniversalAdapter universalAdapter = new UniversalAdapter(recyclerListView, getContext(), this.currentAccount, getBaseFragment().getClassGuid(), true, new Utilities.Callback2() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, this.resourcesProvider);
        this.adapter = universalAdapter;
        universalAdapter.setApplyBackground(false);
        return this.adapter;
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog
    public void show() {
        super.show();
        Bulletin.hideVisible();
    }

    @Override // org.telegram.p035ui.Components.BottomSheetWithRecyclerListView
    public boolean canHighlightChildAt(View view, float f, float f2) {
        return !(view instanceof CollapseTextCell);
    }

    private int getSendMediaSelectedCount() {
        TLRPC.TL_chatBannedRights tL_chatBannedRights = this.bannedRights;
        int i = (tL_chatBannedRights.send_photos || this.defaultBannedRights.send_photos) ? 0 : 1;
        if (!tL_chatBannedRights.send_videos && !this.defaultBannedRights.send_videos) {
            i++;
        }
        if (!tL_chatBannedRights.send_stickers && !this.defaultBannedRights.send_stickers) {
            i++;
        }
        if (!tL_chatBannedRights.send_audios && !this.defaultBannedRights.send_audios) {
            i++;
        }
        if (!tL_chatBannedRights.send_docs && !this.defaultBannedRights.send_docs) {
            i++;
        }
        if (!tL_chatBannedRights.send_voices && !this.defaultBannedRights.send_voices) {
            i++;
        }
        if (!tL_chatBannedRights.send_roundvideos && !this.defaultBannedRights.send_roundvideos) {
            i++;
        }
        if (!tL_chatBannedRights.embed_links) {
            TLRPC.TL_chatBannedRights tL_chatBannedRights2 = this.defaultBannedRights;
            if (!tL_chatBannedRights2.embed_links && !tL_chatBannedRights.send_plain && !tL_chatBannedRights2.send_plain) {
                i++;
            }
        }
        if (!tL_chatBannedRights.send_polls && !this.defaultBannedRights.send_polls) {
            i++;
        }
        return (tL_chatBannedRights.send_reactions || this.defaultBannedRights.send_reactions) ? i : i + 1;
    }

    private void updateParticipantMessageCounts() {
        if (this.participantMessageCountsLoading) {
            return;
        }
        this.participantMessageCountsLoading = true;
        int i = this.deleteAll.totalCount;
        this.participantMessageCounts = new int[i];
        final int[] iArr = {i};
        for (final int i2 = 0; i2 < this.deleteAll.totalCount; i2++) {
            TLRPC.TL_messages_search tL_messages_search = new TLRPC.TL_messages_search();
            tL_messages_search.peer = MessagesController.getInputPeer(this.inChat);
            tL_messages_search.f1368q = _UrlKt.FRAGMENT_ENCODE_SET;
            final TLRPC.InputPeer inputPeer = MessagesController.getInputPeer(this.deleteAll.options.get(i2));
            tL_messages_search.from_id = inputPeer;
            tL_messages_search.flags |= 1;
            tL_messages_search.filter = new TLRPC.TL_inputMessagesFilterEmpty();
            tL_messages_search.limit = 1;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_messages_search, new RequestDelegate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda5
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$updateParticipantMessageCounts$6(inputPeer, i2, iArr, tLObject, tL_error);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParticipantMessageCounts$6(final TLRPC.InputPeer inputPeer, final int i, final int[] iArr, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateParticipantMessageCounts$5(tLObject, inputPeer, i, iArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParticipantMessageCounts$5(TLObject tLObject, final TLRPC.InputPeer inputPeer, int i, int[] iArr) {
        if (tLObject instanceof TLRPC.TL_messages_channelMessages) {
            this.participantMessageCounts[i] = ((TLRPC.TL_messages_channelMessages) tLObject).count - ((int) this.messages.stream().filter(new Predicate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda22
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MessageObject.peersEqual(inputPeer, ((MessageObject) obj).messageOwner.from_id);
                }
            }).count());
        }
        int i2 = iArr[0] - 1;
        iArr[0] = i2;
        if (i2 == 0) {
            this.participantMessageCountsLoading = false;
            this.participantMessageCountsLoaded = true;
            updateTitleAnimated();
        }
    }

    private boolean allDefaultMediaBanned() {
        TLRPC.TL_chatBannedRights tL_chatBannedRights = this.defaultBannedRights;
        return tL_chatBannedRights.send_photos && tL_chatBannedRights.send_videos && tL_chatBannedRights.send_stickers && tL_chatBannedRights.send_audios && tL_chatBannedRights.send_docs && tL_chatBannedRights.send_voices && tL_chatBannedRights.send_roundvideos && tL_chatBannedRights.embed_links && tL_chatBannedRights.send_polls && tL_chatBannedRights.send_reactions;
    }

    private void fillAction(final ArrayList<UItem> arrayList, final Action action) {
        if (action.isPresent()) {
            boolean zIsExpandable = action.isExpandable();
            int i = action.type;
            if (!zIsExpandable) {
                arrayList.add(UItem.asRoundCheckbox(i, action.title).setChecked(action.selectedCount > 0));
                return;
            }
            String str = action.title;
            int count = action.selectedCount;
            if (count <= 0) {
                count = action.getCount();
            }
            arrayList.add(UItem.asUserGroupCheckbox(i, str, String.valueOf(count)).setChecked(action.selectedCount > 0).setCollapsed(action.collapsed).setClickCallback(new View.OnClickListener() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$fillAction$7(action, view);
                }
            }));
            if (action.collapsed) {
                return;
            }
            action.forEach(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda21
                @Override // org.telegram.messenger.Utilities.IndexedConsumer
                public final void accept(Object obj, int i2) {
                    ArrayList arrayList2 = arrayList;
                    DeleteMessagesBottomSheet.Action action2 = action;
                    arrayList2.add(UItem.asUserCheckbox((action2.type << 24) | i2, (TLObject) obj).setChecked(action2.checks[i2]).setPad(1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillAction$7(Action action, View view) {
        saveScrollPosition();
        action.collapseOrExpand();
        applyScrolledPosition(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fillItems(java.util.ArrayList<org.telegram.p035ui.Components.UItem> r9, final org.telegram.p035ui.Components.UniversalAdapter r10) {
        /*
            Method dump skipped, instruction units count: 1011
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.DeleteMessagesBottomSheet.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$9(UniversalAdapter universalAdapter, View view) {
        this.restrictUserCollapsed = !this.restrictUserCollapsed;
        universalAdapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$10(int i, UniversalAdapter universalAdapter, View view) {
        if (allDefaultMediaBanned()) {
            new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(C2797R.string.UserRestrictionsCantModify)).setMessage(LocaleController.getString(C2797R.string.UserRestrictionsCantModifyDisabled)).setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null).create().show();
            return;
        }
        boolean z = i <= 0;
        TLRPC.TL_chatBannedRights tL_chatBannedRights = this.bannedRights;
        tL_chatBannedRights.send_media = !z;
        tL_chatBannedRights.send_photos = !z;
        tL_chatBannedRights.send_videos = !z;
        tL_chatBannedRights.send_stickers = !z;
        tL_chatBannedRights.send_gifs = !z;
        tL_chatBannedRights.send_inline = !z;
        tL_chatBannedRights.send_games = !z;
        tL_chatBannedRights.send_audios = !z;
        tL_chatBannedRights.send_docs = !z;
        tL_chatBannedRights.send_voices = !z;
        tL_chatBannedRights.send_roundvideos = !z;
        tL_chatBannedRights.embed_links = !z;
        tL_chatBannedRights.send_polls = !z;
        tL_chatBannedRights.send_reactions = !z;
        onRestrictionsChanged();
        universalAdapter.update(true);
    }

    private int getRestrictToggleTextKey() {
        boolean zIsExpandable = this.banOrRestrict.isExpandable();
        boolean z = this.restrict;
        if (zIsExpandable) {
            if (z) {
                return C2797R.string.DeleteToggleBanUsers;
            }
            return C2797R.string.DeleteToggleRestrictUsers;
        }
        if (z) {
            return C2797R.string.DeleteToggleBanUser;
        }
        return C2797R.string.DeleteToggleRestrictUser;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void onRestrictionsChanged() {
        /*
            r5 = this;
            boolean r0 = r5.restrict
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L19
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            boolean r0 = r0.isPresent()
            if (r0 == 0) goto L19
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            int r0 = r0.selectedCount
            if (r0 <= 0) goto L16
            r0 = r2
            goto L17
        L16:
            r0 = r1
        L17:
            r5.banChecked = r0
        L19:
            boolean r0 = r5.restrict
            if (r0 == 0) goto L2f
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            boolean r0 = r0.isPresent()
            if (r0 == 0) goto L2f
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            int r3 = r0.selectedCount
            if (r3 != 0) goto L2f
            r0.toggleAllChecks()
            goto L4b
        L2f:
            boolean r0 = r5.restrict
            if (r0 != 0) goto L4b
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            boolean r0 = r0.isPresent()
            if (r0 == 0) goto L4b
            boolean r0 = r5.banChecked
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r3 = r5.banOrRestrict
            int r4 = r3.selectedCount
            if (r4 <= 0) goto L45
            r4 = r2
            goto L46
        L45:
            r4 = r1
        L46:
            if (r0 == r4) goto L4b
            r3.toggleAllChecks()
        L4b:
            boolean r0 = r5.restrict
            if (r0 != 0) goto L60
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            boolean r0 = r0.isPresent()
            if (r0 == 0) goto L60
            org.telegram.ui.Components.DeleteMessagesBottomSheet$Action r0 = r5.banOrRestrict
            int r0 = r0.selectedCount
            if (r0 <= 0) goto L5e
            r1 = r2
        L5e:
            r5.banChecked = r1
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.DeleteMessagesBottomSheet.onRestrictionsChanged():void");
    }

    private void onDeleteAllChanged() {
        if (this.participantMessageCountsLoaded) {
            updateTitleAnimated();
        } else {
            updateParticipantMessageCounts();
        }
    }

    private void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.viewType;
        if (i2 == 37) {
            int i3 = uItem.f1708id;
            int i4 = i3 >>> 24;
            int i5 = 16777215 & i3;
            if (i4 == 0) {
                this.report.toggleCheck(i5);
                return;
            }
            if (i4 == 1) {
                this.deleteAll.toggleCheck(i5);
                onDeleteAllChanged();
                return;
            } else if (i3 == 3) {
                this.deleteAllReactions.toggleCheck(i5);
                onDeleteAllChanged();
                return;
            } else {
                if (i4 == 2) {
                    this.banOrRestrict.toggleCheck(i5);
                    return;
                }
                return;
            }
        }
        int i6 = 0;
        if (i2 != 36 && i2 != 35) {
            if (i2 != 39) {
                if (i2 == 40) {
                    this.sendMediaCollapsed = !this.sendMediaCollapsed;
                    saveScrollPosition();
                    this.adapter.update(true);
                    applyScrolledPosition(true);
                    return;
                }
                if (uItem.f1708id != 100) {
                    if (i2 == 38) {
                        boolean z = this.restrict;
                        this.restrict = !z;
                        this.banOrRestrict.setFilter(!z ? this.restrictFilter : this.banFilter);
                        this.adapter.update(true);
                        onRestrictionsChanged();
                        return;
                    }
                    return;
                }
                this.restrictUserCollapsed = false;
                boolean z2 = !this.restrictUserDeleteAllMessages;
                this.restrictUserDeleteAllMessages = z2;
                this.restrictUserDeleteAllReactions = z2;
                saveScrollPosition();
                this.adapter.update(true);
                applyScrolledPosition(true);
                updateTitleAnimated();
                return;
            }
            if (uItem.locked) {
                new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(C2797R.string.UserRestrictionsCantModify)).setMessage(LocaleController.getString(C2797R.string.UserRestrictionsCantModifyDisabled)).setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null).create().show();
                return;
            }
            int i7 = uItem.f1708id;
            if (i7 == 2) {
                this.bannedRights.invite_users = !r6.invite_users;
                onRestrictionsChanged();
            } else if (i7 == 3) {
                this.bannedRights.pin_messages = !r6.pin_messages;
                onRestrictionsChanged();
            } else if (i7 == 4) {
                this.bannedRights.change_info = !r6.change_info;
                onRestrictionsChanged();
            } else if (i7 == 5) {
                this.bannedRights.manage_topics = !r6.manage_topics;
                onRestrictionsChanged();
            } else if (i7 == 0) {
                this.bannedRights.send_plain = !r6.send_plain;
                onRestrictionsChanged();
            }
            this.adapter.update(true);
            return;
        }
        int i8 = uItem.f1708id;
        if (i8 == 0) {
            this.report.toggleAllChecks();
            return;
        }
        if (i8 == 1) {
            this.deleteAll.toggleAllChecks();
            onDeleteAllChanged();
            return;
        }
        if (i8 == 3) {
            this.deleteAllReactions.toggleAllChecks();
            onDeleteAllChanged();
            return;
        }
        if (i8 == 2) {
            this.banOrRestrict.toggleAllChecks();
            return;
        }
        if (i2 == 35) {
            if (uItem.locked) {
                new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(C2797R.string.UserRestrictionsCantModify)).setMessage(LocaleController.getString(C2797R.string.UserRestrictionsCantModifyDisabled)).setPositiveButton(LocaleController.getString(C2797R.string.f1162OK), null).create().show();
                return;
            }
            if (i8 == 6) {
                this.bannedRights.send_photos = !r6.send_photos;
                onRestrictionsChanged();
            } else if (i8 == 7) {
                this.bannedRights.send_videos = !r6.send_videos;
                onRestrictionsChanged();
            } else if (i8 == 9) {
                this.bannedRights.send_audios = !r6.send_audios;
                onRestrictionsChanged();
            } else if (i8 == 8) {
                this.bannedRights.send_docs = !r6.send_docs;
                onRestrictionsChanged();
            } else if (i8 == 11) {
                this.bannedRights.send_roundvideos = !r6.send_roundvideos;
                onRestrictionsChanged();
            } else if (i8 == 10) {
                this.bannedRights.send_voices = !r6.send_voices;
                onRestrictionsChanged();
            } else if (i8 == 15) {
                this.bannedRights.send_reactions = !r6.send_reactions;
                onRestrictionsChanged();
            } else if (i8 == 12) {
                TLRPC.TL_chatBannedRights tL_chatBannedRights = this.bannedRights;
                boolean z3 = !tL_chatBannedRights.send_stickers;
                tL_chatBannedRights.send_inline = z3;
                tL_chatBannedRights.send_gifs = z3;
                tL_chatBannedRights.send_games = z3;
                tL_chatBannedRights.send_stickers = z3;
                onRestrictionsChanged();
            } else if (i8 == 14) {
                TLRPC.TL_chatBannedRights tL_chatBannedRights2 = this.bannedRights;
                if (tL_chatBannedRights2.send_plain || this.defaultBannedRights.send_plain) {
                    while (true) {
                        if (i6 >= this.adapter.getItemCount()) {
                            break;
                        }
                        UItem item = this.adapter.getItem(i6);
                        if (item.viewType == 39 && item.f1708id == 0) {
                            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.recyclerListView.findViewHolderForAdapterPosition(i6 + 1);
                            if (viewHolderFindViewHolderForAdapterPosition != null) {
                                View view2 = viewHolderFindViewHolderForAdapterPosition.itemView;
                                float f3 = -this.shiftDp;
                                this.shiftDp = f3;
                                AndroidUtilities.shakeViewSpring(view2, f3);
                            }
                        } else {
                            i6++;
                        }
                    }
                    BotWebViewVibrationEffect.APP_ERROR.vibrate();
                    return;
                }
                tL_chatBannedRights2.embed_links = !tL_chatBannedRights2.embed_links;
                onRestrictionsChanged();
            } else if (i8 == 13) {
                this.bannedRights.send_polls = !r6.send_polls;
                onRestrictionsChanged();
            } else if (i8 == 101) {
                this.restrictUserDeleteAllMessages = !this.restrictUserDeleteAllMessages;
                updateTitleAnimated();
            } else if (i8 == 102) {
                this.restrictUserDeleteAllReactions = !this.restrictUserDeleteAllReactions;
                updateTitleAnimated();
            }
            this.adapter.update(true);
        }
    }

    private void performDelete() {
        final ArrayList<Integer> arrayList = (ArrayList) this.messages.stream().filter(new Predicate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.lambda$performDelete$11((MessageObject) obj);
            }
        }).map(new DeleteMessagesBottomSheet$$ExternalSyntheticLambda11()).collect(Collectors.toCollection(new ExportMapper$$ExternalSyntheticLambda2()));
        final ArrayList<Integer> arrayList2 = (ArrayList) this.messages.stream().filter(new Predicate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.lambda$performDelete$12((MessageObject) obj);
            }
        }).map(new DeleteMessagesBottomSheet$$ExternalSyntheticLambda11()).collect(Collectors.toCollection(new ExportMapper$$ExternalSyntheticLambda2()));
        if (this.isReactionOnlyMode) {
            if (!this.restrictUserDeleteAllReactions) {
                this.deleteAll.forEach(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda13
                    @Override // org.telegram.messenger.Utilities.IndexedConsumer
                    public final void accept(Object obj, int i) {
                        this.f$0.lambda$performDelete$13(arrayList, arrayList2, (TLObject) obj, i);
                    }
                });
            }
        } else {
            if (!arrayList.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).deleteMessages(arrayList, null, null, -this.inChat.f1245id, this.topicId, false, this.mode);
            }
            if (!arrayList2.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).deleteMessages(arrayList2, null, null, this.mergeDialogId, this.topicId, true, this.mode);
            }
        }
        this.banOrRestrict.forEachSelected(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda14
            @Override // org.telegram.messenger.Utilities.IndexedConsumer
            public final void accept(Object obj, int i) {
                this.f$0.lambda$performDelete$14((TLObject) obj, i);
            }
        });
        this.report.forEachSelected(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda15
            @Override // org.telegram.messenger.Utilities.IndexedConsumer
            public final void accept(Object obj, int i) {
                this.f$0.lambda$performDelete$17((TLObject) obj, i);
            }
        });
        boolean z = this.isSingleUsersMode;
        Action action = this.deleteAll;
        if (z) {
            action.forEach(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda16
                @Override // org.telegram.messenger.Utilities.IndexedConsumer
                public final void accept(Object obj, int i) {
                    this.f$0.lambda$performDelete$18((TLObject) obj, i);
                }
            });
        } else {
            action.forEachSelected(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda9
                @Override // org.telegram.messenger.Utilities.IndexedConsumer
                public final void accept(Object obj, int i) {
                    this.f$0.lambda$performDelete$19((TLObject) obj, i);
                }
            });
            this.deleteAllReactions.forEachSelected(new Utilities.IndexedConsumer() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda10
                @Override // org.telegram.messenger.Utilities.IndexedConsumer
                public final void accept(Object obj, int i) {
                    this.f$0.lambda$performDelete$20((TLObject) obj, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$performDelete$11(MessageObject messageObject) {
        TLRPC.Peer peer = messageObject.messageOwner.peer_id;
        return !(peer == null || peer.chat_id == (-this.mergeDialogId)) || this.mergeDialogId == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$performDelete$12(MessageObject messageObject) {
        TLRPC.Peer peer = messageObject.messageOwner.peer_id;
        if (peer == null) {
            return false;
        }
        long j = peer.chat_id;
        long j2 = this.mergeDialogId;
        return j == (-j2) && j2 != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDelete$13(ArrayList arrayList, ArrayList arrayList2, TLObject tLObject, int i) {
        long dialogId = DialogObject.getDialogId(tLObject);
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            MessagesController.getInstance(this.currentAccount).deleteReactionsFromMessage(-this.inChat.f1245id, dialogId, ((Integer) arrayList.get(i3)).intValue());
        }
        int size2 = arrayList2.size();
        while (i2 < size2) {
            Object obj = arrayList2.get(i2);
            i2++;
            MessagesController.getInstance(this.currentAccount).deleteReactionsFromMessage(this.mergeDialogId, dialogId, ((Integer) obj).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$performDelete$14(org.telegram.tgnet.TLObject r14, int r15) {
        /*
            r13 = this;
            org.telegram.tgnet.TLRPC$Chat r0 = r13.inChat
            long r1 = r0.f1245id
            boolean r0 = org.telegram.messenger.ChatObject.isMonoForum(r0)
            if (r0 == 0) goto L20
            int r0 = r13.currentAccount
            org.telegram.tgnet.TLRPC$Chat r3 = r13.inChat
            boolean r0 = org.telegram.messenger.ChatObject.canManageMonoForum(r0, r3)
            if (r0 == 0) goto L20
            org.telegram.tgnet.TLRPC$Chat r0 = r13.inChat
            long r3 = r0.linked_monoforum_id
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 == 0) goto L20
            r6 = r3
            goto L21
        L20:
            r6 = r1
        L21:
            boolean r0 = r13.restrict
            if (r0 == 0) goto L61
            org.telegram.tgnet.TLRPC$TL_chatBannedRights r0 = r13.bannedRights
            java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_chatBannedRights> r1 = r13.participantsBannedRights
            java.lang.Object r15 = r1.get(r15)
            org.telegram.tgnet.TLRPC$TL_chatBannedRights r15 = (org.telegram.tgnet.TLRPC.TL_chatBannedRights) r15
            org.telegram.tgnet.TLRPC$TL_chatBannedRights r10 = bannedRightsOr(r0, r15)
            boolean r15 = r14 instanceof org.telegram.tgnet.TLRPC.User
            if (r15 == 0) goto L4a
            int r15 = r13.currentAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r15)
            r8 = r14
            org.telegram.tgnet.TLRPC$User r8 = (org.telegram.tgnet.TLRPC.User) r8
            r11 = 0
            org.telegram.ui.ActionBar.BaseFragment r12 = r13.getBaseFragment()
            r9 = 0
            r5.setParticipantBannedRole(r6, r8, r9, r10, r11, r12)
            return
        L4a:
            boolean r15 = r14 instanceof org.telegram.tgnet.TLRPC.Chat
            if (r15 == 0) goto L88
            int r15 = r13.currentAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r15)
            r9 = r14
            org.telegram.tgnet.TLRPC$Chat r9 = (org.telegram.tgnet.TLRPC.Chat) r9
            r11 = 0
            org.telegram.ui.ActionBar.BaseFragment r12 = r13.getBaseFragment()
            r8 = 0
            r5.setParticipantBannedRole(r6, r8, r9, r10, r11, r12)
            return
        L61:
            boolean r15 = r14 instanceof org.telegram.tgnet.TLRPC.User
            if (r15 == 0) goto L75
            int r13 = r13.currentAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r13)
            r8 = r14
            org.telegram.tgnet.TLRPC$User r8 = (org.telegram.tgnet.TLRPC.User) r8
            r10 = 0
            r11 = 0
            r9 = 0
            r5.deleteParticipantFromChat(r6, r8, r9, r10, r11)
            return
        L75:
            boolean r15 = r14 instanceof org.telegram.tgnet.TLRPC.Chat
            if (r15 == 0) goto L88
            int r13 = r13.currentAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r13)
            r9 = r14
            org.telegram.tgnet.TLRPC$Chat r9 = (org.telegram.tgnet.TLRPC.Chat) r9
            r10 = 0
            r11 = 0
            r8 = 0
            r5.deleteParticipantFromChat(r6, r8, r9, r10, r11)
        L88:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p035ui.Components.DeleteMessagesBottomSheet.lambda$performDelete$14(org.telegram.tgnet.TLObject, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDelete$17(final TLObject tLObject, int i) {
        ArrayList<Integer> arrayList = (ArrayList) this.messages.stream().filter(new Predicate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.lambda$performDelete$15((MessageObject) obj);
            }
        }).filter(new Predicate() { // from class: org.telegram.ui.Components.DeleteMessagesBottomSheet$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DeleteMessagesBottomSheet.m11381$r8$lambda$3BUJMsPYBIw3A0wJOsUYrKE8Cs(tLObject, (MessageObject) obj);
            }
        }).map(new DeleteMessagesBottomSheet$$ExternalSyntheticLambda11()).collect(Collectors.toCollection(new ExportMapper$$ExternalSyntheticLambda2()));
        if (this.isReactionOnlyMode && (tLObject instanceof TLRPC.User) && arrayList.size() == 1) {
            TLRPC.TL_messages_reportReaction tL_messages_reportReaction = new TLRPC.TL_messages_reportReaction();
            tL_messages_reportReaction.peer = MessagesController.getInputPeer(this.inChat);
            tL_messages_reportReaction.user_id = MessagesController.getInstance(this.currentAccount).getInputUser((TLRPC.User) tLObject);
            tL_messages_reportReaction.f1365id = arrayList.get(0).intValue();
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_messages_reportReaction, null);
            return;
        }
        TLRPC.TL_channels_reportSpam tL_channels_reportSpam = new TLRPC.TL_channels_reportSpam();
        tL_channels_reportSpam.channel = MessagesController.getInputChannel(this.inChat);
        if (tLObject instanceof TLRPC.User) {
            tL_channels_reportSpam.participant = MessagesController.getInputPeer((TLRPC.User) tLObject);
        } else if (tLObject instanceof TLRPC.Chat) {
            tL_channels_reportSpam.participant = MessagesController.getInputPeer((TLRPC.Chat) tLObject);
        }
        tL_channels_reportSpam.f1294id = arrayList;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_reportSpam, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$performDelete$15(MessageObject messageObject) {
        TLRPC.Peer peer = messageObject.messageOwner.peer_id;
        return (peer == null || peer.chat_id == (-this.mergeDialogId)) ? false : true;
    }

    /* JADX INFO: renamed from: $r8$lambda$3BUJMsPYBIw3A0wJOsUYrK-E8Cs, reason: not valid java name */
    public static /* synthetic */ boolean m11381$r8$lambda$3BUJMsPYBIw3A0wJOsUYrKE8Cs(TLObject tLObject, MessageObject messageObject) {
        return tLObject instanceof TLRPC.User ? messageObject.messageOwner.from_id.user_id == ((TLRPC.User) tLObject).f1407id : (tLObject instanceof TLRPC.Chat) && messageObject.messageOwner.from_id.user_id == ((TLRPC.Chat) tLObject).f1245id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDelete$18(TLObject tLObject, int i) {
        if (this.restrictUserDeleteAllMessages) {
            if (tLObject instanceof TLRPC.User) {
                MessagesController.getInstance(this.currentAccount).deleteUserChannelHistory(this.inChat, (TLRPC.User) tLObject, null, 0);
            } else if (tLObject instanceof TLRPC.Chat) {
                MessagesController.getInstance(this.currentAccount).deleteUserChannelHistory(this.inChat, null, (TLRPC.Chat) tLObject, 0);
            }
        }
        if (this.restrictUserDeleteAllReactions) {
            if (tLObject instanceof TLRPC.User) {
                MessagesController.getInstance(this.currentAccount).deleteUserChannelAllReactions(this.inChat, (TLRPC.User) tLObject, null);
            } else if (tLObject instanceof TLRPC.Chat) {
                MessagesController.getInstance(this.currentAccount).deleteUserChannelAllReactions(this.inChat, null, (TLRPC.Chat) tLObject);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDelete$19(TLObject tLObject, int i) {
        if (tLObject instanceof TLRPC.User) {
            MessagesController.getInstance(this.currentAccount).deleteUserChannelHistory(this.inChat, (TLRPC.User) tLObject, null, 0);
        } else if (tLObject instanceof TLRPC.Chat) {
            MessagesController.getInstance(this.currentAccount).deleteUserChannelHistory(this.inChat, null, (TLRPC.Chat) tLObject, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDelete$20(TLObject tLObject, int i) {
        if (tLObject instanceof TLRPC.User) {
            MessagesController.getInstance(this.currentAccount).deleteUserChannelAllReactions(this.inChat, (TLRPC.User) tLObject, null);
        } else if (tLObject instanceof TLRPC.Chat) {
            MessagesController.getInstance(this.currentAccount).deleteUserChannelAllReactions(this.inChat, null, (TLRPC.Chat) tLObject);
        }
    }

    private void savePreferences() {
        SharedPreferences.Editor editorEdit = MessagesController.getInstance(this.currentAccount).getMainSettings().edit();
        editorEdit.putBoolean("delete_report", this.report.areAllSelected());
        editorEdit.putBoolean("delete_deleteAll", this.deleteAll.areAllSelected());
        editorEdit.putBoolean("delete_ban", !this.restrict && this.banOrRestrict.areAllSelected());
        editorEdit.apply();
    }

    @Override // org.telegram.p035ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* JADX INFO: renamed from: dismiss */
    public void lambda$new$0() {
        savePreferences();
        super.lambda$new$0();
    }

    private void proceed() {
        lambda$new$0();
        Runnable runnable = this.onDelete;
        if (runnable != null) {
            runnable.run();
        }
        int i = this.report.selectedCount;
        String strConcat = _UrlKt.FRAGMENT_ENCODE_SET;
        boolean z = false;
        if (i > 0) {
            strConcat = _UrlKt.FRAGMENT_ENCODE_SET + LocaleController.formatPluralString("UsersReported", this.report.selectedCount, new Object[0]);
        }
        if (this.banOrRestrict.selectedCount > 0) {
            if (!TextUtils.isEmpty(strConcat)) {
                strConcat = strConcat.concat("\n");
            }
            if (this.restrict) {
                strConcat = strConcat + LocaleController.formatPluralString("UsersRestricted", this.banOrRestrict.selectedCount, new Object[0]);
            } else {
                strConcat = strConcat + LocaleController.formatPluralString("UsersBanned", this.banOrRestrict.selectedCount, new Object[0]);
            }
        }
        if (this.isReactionOnlyMode && !this.restrictUserDeleteAllMessages) {
            z = true;
        }
        int i2 = this.banOrRestrict.selectedCount > 0 ? C2797R.raw.ic_admin : C2797R.raw.contact_check;
        if (TextUtils.isEmpty(strConcat)) {
            BulletinFactory.m1143of(getBaseFragment()).createSimpleBulletin(i2, LocaleController.getString(z ? C2797R.string.ReactionsDeleted : C2797R.string.MessagesDeleted)).show();
        } else {
            BulletinFactory.m1143of(getBaseFragment()).createSimpleBulletin(i2, LocaleController.getString(z ? C2797R.string.ReactionsDeleted : C2797R.string.MessagesDeleted), strConcat).show();
        }
        performDelete();
    }
}
