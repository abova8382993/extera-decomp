package org.telegram.p029ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2888R;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p029ui.ActionBar.ActionBar;
import org.telegram.p029ui.ActionBar.BaseFragment;
import org.telegram.p029ui.ActionBar.Theme;
import org.telegram.p029ui.Cells.DividerCell;
import org.telegram.p029ui.Cells.GraySectionCell;
import org.telegram.p029ui.Cells.HeaderCell;
import org.telegram.p029ui.Cells.InviteUserCell;
import org.telegram.p029ui.Cells.LetterSectionCell;
import org.telegram.p029ui.Cells.ShadowSectionCell;
import org.telegram.p029ui.Cells.TextCell;
import org.telegram.p029ui.Cells.UserCell;
import org.telegram.p029ui.Components.ContactsEmptyView;
import org.telegram.p029ui.Components.LayoutHelper;
import org.telegram.p029ui.Components.RecyclerListView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import p022j$.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ContactsAdapter extends RecyclerListView.SectionsAdapter {
    private final int currentAccount = UserConfig.selectedAccount;
    private boolean disableSections;
    BaseFragment fragment;
    private boolean hasPhonebook;
    private final LongSparseArray ignoreUsers;
    public boolean includeSearch;
    private final boolean isAdmin;
    private final boolean isChannel;
    private boolean isEmpty;
    public boolean isEmptyWithMainTabs;
    private final Context mContext;
    private final boolean needPhonebook;
    private ArrayList onlineContacts;
    private final int onlyUsers;
    private final LongSparseArray selectedContacts;
    private int sortType;

    public ContactsAdapter(Context context, BaseFragment baseFragment, int i, boolean z, LongSparseArray longSparseArray, LongSparseArray longSparseArray2, int i2) {
        this.mContext = context;
        this.onlyUsers = i;
        this.needPhonebook = z;
        this.ignoreUsers = longSparseArray;
        this.selectedContacts = longSparseArray2;
        this.isAdmin = i2 != 0;
        this.isChannel = i2 == 2;
        this.fragment = baseFragment;
    }

    public void setDisableSections(boolean z) {
        this.disableSections = z;
    }

    public void setSortType(int i, boolean z) {
        this.sortType = i;
        if (i == 2) {
            if (this.onlineContacts == null || z) {
                this.onlineContacts = new ArrayList(ContactsController.getInstance(this.currentAccount).contacts);
                long j = UserConfig.getInstance(this.currentAccount).clientUserId;
                int size = this.onlineContacts.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    if (((TLRPC.TL_contact) this.onlineContacts.get(i2)).user_id == j) {
                        this.onlineContacts.remove(i2);
                        break;
                    }
                    i2++;
                }
            }
            sortOnlineContacts();
            return;
        }
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void sortOnlineContacts() {
        if (this.onlineContacts == null) {
            return;
        }
        try {
            final int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
            final MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            Collections.sort(this.onlineContacts, new Comparator() { // from class: org.telegram.ui.Adapters.ContactsAdapter$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ContactsAdapter.$r8$lambda$ELiMIR3niEAcM_m7UV_uqfYJfuE(messagesController, currentTime, (TLRPC.TL_contact) obj, (TLRPC.TL_contact) obj2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {
            FileLog.m1136e(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ int $r8$lambda$ELiMIR3niEAcM_m7UV_uqfYJfuE(org.telegram.messenger.MessagesController r2, int r3, org.telegram.tgnet.TLRPC.TL_contact r4, org.telegram.tgnet.TLRPC.TL_contact r5) {
        /*
            long r0 = r5.user_id
            java.lang.Long r5 = java.lang.Long.valueOf(r0)
            org.telegram.tgnet.TLRPC$User r5 = r2.getUser(r5)
            long r0 = r4.user_id
            java.lang.Long r4 = java.lang.Long.valueOf(r0)
            org.telegram.tgnet.TLRPC$User r2 = r2.getUser(r4)
            r4 = 50000(0xc350, float:7.0065E-41)
            r0 = 0
            if (r5 == 0) goto L28
            boolean r1 = r5.self
            if (r1 == 0) goto L21
            int r5 = r3 + r4
            goto L29
        L21:
            org.telegram.tgnet.TLRPC$UserStatus r5 = r5.status
            if (r5 == 0) goto L28
            int r5 = r5.expires
            goto L29
        L28:
            r5 = r0
        L29:
            if (r2 == 0) goto L38
            boolean r1 = r2.self
            if (r1 == 0) goto L31
            int r3 = r3 + r4
            goto L39
        L31:
            org.telegram.tgnet.TLRPC$UserStatus r2 = r2.status
            if (r2 == 0) goto L38
            int r3 = r2.expires
            goto L39
        L38:
            r3 = r0
        L39:
            if (r5 <= 0) goto L43
            if (r3 <= 0) goto L43
            if (r5 <= r3) goto L40
            goto L5f
        L40:
            if (r5 >= r3) goto L61
            goto L55
        L43:
            if (r5 >= 0) goto L4d
            if (r3 >= 0) goto L4d
            if (r5 <= r3) goto L4a
            goto L5f
        L4a:
            if (r5 >= r3) goto L61
            goto L55
        L4d:
            if (r5 >= 0) goto L51
            if (r3 > 0) goto L55
        L51:
            if (r5 != 0) goto L57
            if (r3 == 0) goto L57
        L55:
            r2 = -1
            return r2
        L57:
            if (r3 >= 0) goto L5b
            if (r5 > 0) goto L5f
        L5b:
            if (r3 != 0) goto L61
            if (r5 == 0) goto L61
        L5f:
            r2 = 1
            return r2
        L61:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.Adapters.ContactsAdapter.$r8$lambda$ELiMIR3niEAcM_m7UV_uqfYJfuE(org.telegram.messenger.MessagesController, int, org.telegram.tgnet.TLRPC$TL_contact, org.telegram.tgnet.TLRPC$TL_contact):int");
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public Object getItem(int i, int i2) {
        int i3;
        if (this.isEmptyWithMainTabs && i == 1 && i2 > 1 && i2 - 2 < ContactsController.getInstance(this.currentAccount).phoneBookContacts.size()) {
            return ContactsController.getInstance(this.currentAccount).phoneBookContacts.get(i3);
        }
        if (getItemViewType(i, i2) == 2) {
            return "Header";
        }
        HashMap<String, ArrayList<TLRPC.TL_contact>> map = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict : ContactsController.getInstance(this.currentAccount).usersSectionsDict;
        ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
        if (this.onlyUsers != 0 && !this.isAdmin) {
            if (i < arrayList.size()) {
                ArrayList<TLRPC.TL_contact> arrayList2 = map.get(arrayList.get(i));
                if (i2 < arrayList2.size()) {
                    return MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(arrayList2.get(i2).user_id));
                }
            }
            return null;
        }
        if (i == 0) {
            return null;
        }
        if (this.sortType != 2) {
            int i4 = i - 1;
            if (i4 < arrayList.size()) {
                ArrayList<TLRPC.TL_contact> arrayList3 = map.get(arrayList.get(i4));
                if (i2 < arrayList3.size()) {
                    return MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(arrayList3.get(i2).user_id));
                }
                return null;
            }
        } else if (i == 1) {
            if (i2 < this.onlineContacts.size()) {
                return MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(((TLRPC.TL_contact) this.onlineContacts.get(i2)).user_id));
            }
            return null;
        }
        if (!this.needPhonebook || i2 < 0 || i2 >= ContactsController.getInstance(this.currentAccount).phoneBookContacts.size()) {
            return null;
        }
        return ContactsController.getInstance(this.currentAccount).phoneBookContacts.get(i2);
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public int getHash(int i, int i2) {
        return Objects.hash(Integer.valueOf(i * (-49612)), getItem(i, i2));
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public boolean isEnabled(RecyclerView.ViewHolder viewHolder, int i, int i2) {
        if (this.isEmptyWithMainTabs) {
            return i == 1 && i2 > 1;
        }
        HashMap<String, ArrayList<TLRPC.TL_contact>> map = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict : ContactsController.getInstance(this.currentAccount).usersSectionsDict;
        ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
        if (this.onlyUsers != 0 && !this.isAdmin) {
            return !this.isEmpty && i2 < map.get(arrayList.get(i)).size();
        }
        if (i == 0) {
            return this.isAdmin ? i2 < 1 : this.needPhonebook ? i2 < 2 : i2 < 3;
        }
        if (this.isEmpty) {
            return false;
        }
        if (this.sortType == 2) {
            return i != 1 || i2 < this.onlineContacts.size();
        }
        int i3 = i - 1;
        return i3 >= arrayList.size() || i2 < map.get(arrayList.get(i3)).size();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0066 A[RETURN] */
    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getSectionCount() {
        /*
            r6 = this;
            r0 = 0
            r6.isEmpty = r0
            int r1 = r6.sortType
            r2 = 1
            r3 = 2
            if (r1 != r3) goto L13
            java.util.ArrayList r1 = r6.onlineContacts
            boolean r1 = r1.isEmpty()
            r6.isEmpty = r1
        L11:
            r1 = r2
            goto L31
        L13:
            int r1 = r6.onlyUsers
            if (r1 != r3) goto L20
            int r1 = r6.currentAccount
            org.telegram.messenger.ContactsController r1 = org.telegram.messenger.ContactsController.getInstance(r1)
            java.util.ArrayList<java.lang.String> r1 = r1.sortedUsersMutualSectionsArray
            goto L28
        L20:
            int r1 = r6.currentAccount
            org.telegram.messenger.ContactsController r1 = org.telegram.messenger.ContactsController.getInstance(r1)
            java.util.ArrayList<java.lang.String> r1 = r1.sortedUsersSectionsArray
        L28:
            int r1 = r1.size()
            if (r1 != 0) goto L31
            r6.isEmpty = r2
            goto L11
        L31:
            int r4 = r6.onlyUsers
            if (r4 != 0) goto L37
            int r1 = r1 + 1
        L37:
            boolean r4 = r6.isAdmin
            if (r4 == 0) goto L3d
            int r1 = r1 + 1
        L3d:
            int r4 = r6.currentAccount
            org.telegram.messenger.ContactsController r4 = org.telegram.messenger.ContactsController.getInstance(r4)
            java.util.ArrayList<org.telegram.messenger.ContactsController$Contact> r4 = r4.phoneBookContacts
            boolean r4 = r4.isEmpty()
            r5 = r4 ^ 1
            r6.hasPhonebook = r5
            boolean r5 = r6.isEmpty
            if (r5 == 0) goto L5e
            boolean r5 = r6.needPhonebook
            if (r5 == 0) goto L5e
            boolean r5 = r6.isAdmin
            if (r5 != 0) goto L5e
            int r5 = r6.onlyUsers
            if (r5 != 0) goto L5e
            r0 = r2
        L5e:
            r6.isEmptyWithMainTabs = r0
            if (r0 == 0) goto L66
            if (r4 != 0) goto L65
            return r3
        L65:
            return r2
        L66:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p029ui.Adapters.ContactsAdapter.getSectionCount():int");
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public int getCountForSection(int i) {
        return getCountForSectionInternal(i);
    }

    private int getCountForSectionInternal(int i) {
        if (this.isEmptyWithMainTabs) {
            if (i == 0) {
                return (this.includeSearch ? 1 : 0) + 1;
            }
            if (i == 1) {
                return ContactsController.getInstance(this.currentAccount).phoneBookContacts.size() + 2;
            }
            return 0;
        }
        HashMap<String, ArrayList<TLRPC.TL_contact>> map = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict : ContactsController.getInstance(this.currentAccount).usersSectionsDict;
        ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
        if (this.onlyUsers == 0 || this.isAdmin) {
            if (i == 0) {
                return this.isEmpty ? (this.includeSearch ? 1 : 0) + 2 : this.isAdmin ? (this.includeSearch ? 1 : 0) + 3 : this.needPhonebook ? (this.includeSearch ? 1 : 0) + 4 : (this.includeSearch ? 1 : 0) + 4;
            }
            if (this.isEmpty) {
                return 1;
            }
            if (this.sortType != 2) {
                int i2 = i - 1;
                if (i2 < arrayList.size()) {
                    int size = map.get(arrayList.get(i2)).size();
                    arrayList.size();
                    return size;
                }
            } else if (i == 1) {
                if (this.onlineContacts.isEmpty()) {
                    return 0;
                }
                return this.onlineContacts.size();
            }
        } else {
            if (this.isEmpty) {
                return 1;
            }
            if (i < arrayList.size()) {
                int size2 = map.get(arrayList.get(i)).size();
                return (i != arrayList.size() - 1 || this.needPhonebook) ? size2 + 1 : size2;
            }
        }
        if (this.needPhonebook) {
            return ContactsController.getInstance(this.currentAccount).phoneBookContacts.size();
        }
        return 0;
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public View getSectionHeaderView(int i, View view) {
        if (this.onlyUsers == 2) {
            HashMap<String, ArrayList<TLRPC.TL_contact>> map = ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict;
        } else {
            HashMap<String, ArrayList<TLRPC.TL_contact>> map2 = ContactsController.getInstance(this.currentAccount).usersSectionsDict;
        }
        ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
        if (view == null) {
            view = new LetterSectionCell(this.mContext);
        }
        LetterSectionCell letterSectionCell = (LetterSectionCell) view;
        if (this.sortType == 2 || this.disableSections || this.isEmpty) {
            letterSectionCell.setLetter(_UrlKt.FRAGMENT_ENCODE_SET);
            return view;
        }
        if (this.onlyUsers != 0 && !this.isAdmin) {
            if (i < arrayList.size()) {
                letterSectionCell.setLetter(arrayList.get(i));
                return view;
            }
            letterSectionCell.setLetter(_UrlKt.FRAGMENT_ENCODE_SET);
            return view;
        }
        if (i == 0) {
            letterSectionCell.setLetter(_UrlKt.FRAGMENT_ENCODE_SET);
            return view;
        }
        int i2 = i - 1;
        if (i2 < arrayList.size()) {
            letterSectionCell.setLetter(arrayList.get(i2));
            return view;
        }
        letterSectionCell.setLetter(_UrlKt.FRAGMENT_ENCODE_SET);
        return view;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View headerCell;
        View view;
        if (i == 0) {
            UserCell userCell = new UserCell(this.mContext, 58, 1, false);
            userCell.setCallCellStyle(58);
            headerCell = userCell;
        } else if (i == 1) {
            headerCell = new TextCell(this.mContext);
        } else if (i == 2) {
            headerCell = new GraySectionCell(this.mContext);
        } else if (i != 3) {
            if (i == 4) {
                FrameLayout frameLayout = new FrameLayout(this.mContext) { // from class: org.telegram.ui.Adapters.ContactsAdapter.2
                    @Override // android.widget.FrameLayout, android.view.View
                    protected void onMeasure(int i2, int i3) {
                        ContactsAdapter contactsAdapter = ContactsAdapter.this;
                        if (contactsAdapter.isEmptyWithMainTabs && contactsAdapter.hasPhonebook) {
                            super.onMeasure(i2, i3);
                            return;
                        }
                        int size = View.MeasureSpec.getSize(i3);
                        if (size == 0) {
                            size = viewGroup.getMeasuredHeight();
                        }
                        if (size == 0) {
                            size = (AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.statusBarHeight;
                        }
                        int iM1124dp = AndroidUtilities.m1124dp(50.0f);
                        int iM1124dp2 = ContactsAdapter.this.onlyUsers != 0 ? 0 : AndroidUtilities.m1124dp(30.0f) + iM1124dp;
                        if (!ContactsAdapter.this.isAdmin && !ContactsAdapter.this.needPhonebook) {
                            iM1124dp2 += iM1124dp;
                        }
                        int paddingTop = (size - viewGroup.getPaddingTop()) - viewGroup.getPaddingBottom();
                        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(iM1124dp2 < paddingTop ? paddingTop - iM1124dp2 : 0, TLObject.FLAG_30));
                    }
                };
                frameLayout.addView(new ContactsEmptyView(this.mContext), LayoutHelper.createFrame(-1, -2, 17));
                frameLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                frameLayout.setTag(-33024);
                view = frameLayout;
            } else if (i == 7) {
                headerCell = new HeaderCell(this.mContext, Theme.key_windowBackgroundWhiteBlueHeader, 21, 14, 5, false, null);
            } else if (i == 8) {
                headerCell = new InviteUserCell(this.mContext, false);
            } else if (i == 9) {
                View view2 = new View(this.mContext) { // from class: org.telegram.ui.Adapters.ContactsAdapter.1
                    @Override // android.view.View
                    protected void onMeasure(int i2, int i3) {
                        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1124dp(52.0f), TLObject.FLAG_30));
                    }
                };
                view2.setId(9);
                view2.setTag(-33024);
                view = view2;
            } else {
                headerCell = new ShadowSectionCell(this.mContext);
            }
            headerCell = view;
        } else {
            DividerCell dividerCell = new DividerCell(this.mContext);
            dividerCell.setPadding(AndroidUtilities.m1124dp(LocaleController.isRTL ? 28.0f : 72.0f), AndroidUtilities.m1124dp(8.0f), AndroidUtilities.m1124dp(LocaleController.isRTL ? 72.0f : 28.0f), AndroidUtilities.m1124dp(8.0f));
            headerCell = dividerCell;
        }
        return new RecyclerListView.Holder(headerCell);
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public void onBindViewHolder(int i, int i2, RecyclerView.ViewHolder viewHolder) {
        int i3;
        ArrayList<TLRPC.TL_contact> arrayList;
        if (i != 0 || !this.includeSearch) {
            i3 = i2;
        } else if (i2 == 0) {
            return;
        } else {
            i3 = i2 - 1;
        }
        int itemViewType = viewHolder.getItemViewType();
        int i4 = 7;
        if (itemViewType == 0) {
            UserCell userCell = (UserCell) viewHolder.itemView;
            userCell.storyParams.drawSegments = false;
            if (this.sortType != 2 && !this.disableSections) {
                i4 = 58;
            }
            userCell.setAvatarPadding(i4, 1);
            if (this.sortType == 2) {
                arrayList = this.onlineContacts;
            } else {
                arrayList = (this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict : ContactsController.getInstance(this.currentAccount).usersSectionsDict).get((this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray).get(i - ((this.onlyUsers == 0 || this.isAdmin) ? 1 : 0)));
            }
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(arrayList.get(i3).user_id));
            userCell.setMutual(user.mutual_contact);
            userCell.setData(user, null, null, 0);
            userCell.setChecked(this.selectedContacts.indexOfKey(user.f1825id) >= 0, false);
            LongSparseArray longSparseArray = this.ignoreUsers;
            if (longSparseArray != null) {
                if (longSparseArray.indexOfKey(user.f1825id) >= 0) {
                    userCell.setAlpha(0.5f);
                    return;
                } else {
                    userCell.setAlpha(1.0f);
                    return;
                }
            }
            return;
        }
        if (itemViewType != 1) {
            if (itemViewType == 2) {
                GraySectionCell graySectionCell = (GraySectionCell) viewHolder.itemView;
                int i5 = this.sortType;
                if (i5 == 0) {
                    graySectionCell.setText(LocaleController.getString(C2888R.string.Contacts));
                    return;
                } else if (i5 == 1) {
                    graySectionCell.setText(LocaleController.getString(C2888R.string.SortedByName));
                    return;
                } else {
                    graySectionCell.setText(LocaleController.getString(C2888R.string.SortedByLastSeen));
                    return;
                }
            }
            if (itemViewType == 4) {
                viewHolder.itemView.setPadding(0, AndroidUtilities.m1124dp(!this.hasPhonebook ? 96.0f : 25.0f), 0, AndroidUtilities.m1124dp(18.0f));
                return;
            }
            if (itemViewType != 7) {
                if (itemViewType != 8) {
                    return;
                }
                InviteUserCell inviteUserCell = (InviteUserCell) viewHolder.itemView;
                int i6 = i3 - 2;
                if (i6 < 0 || i6 >= ContactsController.getInstance(this.currentAccount).phoneBookContacts.size()) {
                    return;
                }
                inviteUserCell.setUser(ContactsController.getInstance(this.currentAccount).phoneBookContacts.get(i6), null);
                return;
            }
            HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
            if (this.isEmptyWithMainTabs && i3 == 1 && i == 1) {
                headerCell.setText(LocaleController.getString(C2888R.string.InviteFriends));
                return;
            } else if (this.sortType == 1) {
                headerCell.setText(LocaleController.getString(C2888R.string.SortedByName));
                return;
            } else {
                headerCell.setText(LocaleController.getString(C2888R.string.SortedByLastSeen));
                return;
            }
        }
        TextCell textCell = (TextCell) viewHolder.itemView;
        if (this.needPhonebook || !this.isAdmin) {
            int i7 = Theme.key_windowBackgroundWhiteBlackText;
            textCell.setColors(i7, i7);
        } else {
            int i8 = Theme.key_telegram_color_text;
            textCell.setColors(i8, i8);
        }
        if (i == 0) {
            if (this.needPhonebook) {
                if (i3 == 0) {
                    textCell.setTextAndValueAndColorfulIcon(LocaleController.getString(C2888R.string.InviteFriends), _UrlKt.FRAGMENT_ENCODE_SET, false, C2888R.drawable.settings_invite, -14899731, -15431455, false);
                    return;
                } else {
                    if (i3 == 1) {
                        textCell.setTextAndValueAndColorfulIcon(LocaleController.getString(C2888R.string.RecentCalls), _UrlKt.FRAGMENT_ENCODE_SET, false, C2888R.drawable.settings_calls, -11154873, -14175180, false);
                        return;
                    }
                    return;
                }
            }
            if (this.isAdmin) {
                if (this.isChannel) {
                    textCell.setTextAndIcon((CharSequence) LocaleController.getString(C2888R.string.ChannelInviteViaLink), C2888R.drawable.msg_link2, false);
                    return;
                } else {
                    textCell.setTextAndIcon((CharSequence) LocaleController.getString(C2888R.string.InviteToGroupByLink), C2888R.drawable.msg_link2, false);
                    return;
                }
            }
            if (i3 == 0) {
                textCell.setTextAndValueAndColorfulIcon(LocaleController.getString(C2888R.string.NewGroup), _UrlKt.FRAGMENT_ENCODE_SET, false, C2888R.drawable.settings_group, -14899731, -15431455, false);
                return;
            } else {
                if (i3 == 1) {
                    textCell.setTextAndValueAndColorfulIcon(LocaleController.getString(C2888R.string.NewChannel), _UrlKt.FRAGMENT_ENCODE_SET, false, C2888R.drawable.settings_channel, -11154873, -14175180, false);
                    return;
                }
                return;
            }
        }
        ContactsController.Contact contact = ContactsController.getInstance(this.currentAccount).phoneBookContacts.get(i3);
        String str = contact.first_name;
        if (str != null && contact.last_name != null) {
            textCell.setText(contact.first_name + " " + contact.last_name, false);
            return;
        }
        if (str != null && contact.last_name == null) {
            textCell.setText(str, false);
        } else {
            textCell.setText(contact.last_name, false);
        }
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SectionsAdapter
    public int getItemViewType(int i, int i2) {
        if (i == 0 && this.includeSearch) {
            if (i2 == 0) {
                return 9;
            }
            i2--;
        }
        if (this.isEmptyWithMainTabs) {
            if (i == 0) {
                return 4;
            }
            if (i != 1) {
                return 8;
            }
            if (i2 == 0) {
                return 5;
            }
            return i2 == 1 ? 7 : 8;
        }
        HashMap<String, ArrayList<TLRPC.TL_contact>> map = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).usersMutualSectionsDict : ContactsController.getInstance(this.currentAccount).usersSectionsDict;
        ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
        if (this.onlyUsers != 0 && !this.isAdmin) {
            if (this.isEmpty) {
                return 4;
            }
            return i2 < map.get(arrayList.get(i)).size() ? 0 : 3;
        }
        if (i == 0) {
            if (this.isAdmin) {
                if (i2 == 1) {
                    return 5;
                }
                if (i2 == 2) {
                    int i3 = this.sortType;
                    return (i3 == 1 || i3 == 2) ? 7 : 2;
                }
            } else if (this.needPhonebook) {
                if (i2 < 2) {
                    return 1;
                }
                if (i2 == 2) {
                    return 5;
                }
                if (i2 == 3) {
                    if (this.isEmpty) {
                        return 5;
                    }
                    int i4 = this.sortType;
                    return (i4 == 1 || i4 == 2) ? 7 : 2;
                }
            } else {
                if (i2 == 2) {
                    return 5;
                }
                if (i2 == 3) {
                    if (this.isEmpty) {
                        return 5;
                    }
                    int i5 = this.sortType;
                    return (i5 == 1 || i5 == 2) ? 7 : 2;
                }
            }
        } else {
            if (this.isEmpty) {
                return 4;
            }
            if (this.sortType != 2) {
                int i6 = i - 1;
                if (i6 < arrayList.size()) {
                    return i2 < map.get(arrayList.get(i6)).size() ? 0 : 3;
                }
            } else if (i == 1) {
                return i2 < this.onlineContacts.size() ? 0 : 3;
            }
        }
        return 1;
    }

    @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
    public String getLetter(int i) {
        if (this.includeSearch) {
            if (i == 0) {
                return null;
            }
            i--;
        }
        if (this.sortType != 2 && !this.isEmpty) {
            ArrayList<String> arrayList = this.onlyUsers == 2 ? ContactsController.getInstance(this.currentAccount).sortedUsersMutualSectionsArray : ContactsController.getInstance(this.currentAccount).sortedUsersSectionsArray;
            int sectionForPosition = getSectionForPosition(i);
            if (sectionForPosition == -1) {
                sectionForPosition = arrayList.size() - 1;
            }
            if (this.onlyUsers != 0 && !this.isAdmin) {
                if (sectionForPosition >= 0 && sectionForPosition < arrayList.size()) {
                    return arrayList.get(sectionForPosition);
                }
            } else if (sectionForPosition > 0 && sectionForPosition <= arrayList.size()) {
                return arrayList.get(sectionForPosition - 1);
            }
        }
        return null;
    }

    @Override // org.telegram.ui.Components.RecyclerListView.FastScrollAdapter
    public void getPositionForScrollProgress(RecyclerListView recyclerListView, float f, int[] iArr) {
        iArr[0] = (int) (getItemCount() * f);
        iArr[1] = 0;
    }
}
