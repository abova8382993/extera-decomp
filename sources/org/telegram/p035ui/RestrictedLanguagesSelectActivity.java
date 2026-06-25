package org.telegram.p035ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chaquo.python.internal.Common;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import okhttp3.internal.url._UrlKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2797R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.TranslateController;
import org.telegram.messenger.Utilities;
import org.telegram.p035ui.ActionBar.ActionBar;
import org.telegram.p035ui.ActionBar.ActionBarMenuItem;
import org.telegram.p035ui.ActionBar.BaseFragment;
import org.telegram.p035ui.ActionBar.Theme;
import org.telegram.p035ui.ActionBar.ThemeDescription;
import org.telegram.p035ui.Cells.HeaderCell;
import org.telegram.p035ui.Cells.LanguageCell;
import org.telegram.p035ui.Cells.ShadowSectionCell;
import org.telegram.p035ui.Cells.TextCheckbox2Cell;
import org.telegram.p035ui.Components.EmptyTextProgressView;
import org.telegram.p035ui.Components.LayoutHelper;
import org.telegram.p035ui.Components.RecyclerListView;
import org.telegram.p035ui.Components.TranslateAlert2;

/* JADX INFO: loaded from: classes3.dex */
public class RestrictedLanguagesSelectActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private static boolean gotRestrictedLanguages;
    private static HashSet<String> restrictedLanguages;
    private ArrayList<TranslateController.Language> allLanguages;
    private EmptyTextProgressView emptyView;
    private HashSet<String> firstSelectedLanguages;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private ListAdapter searchListViewAdapter;
    private ArrayList<TranslateController.Language> searchResult;
    private HashSet<String> selectedLanguages;
    private int separatorRow;
    private final int type;

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    public RestrictedLanguagesSelectActivity() {
        this(0);
    }

    public RestrictedLanguagesSelectActivity(int i) {
        this.separatorRow = -1;
        this.type = i;
    }

    public static String normalizeRestrictedLanguage(String str) {
        String strNormalizeLanguageCode = TranslatorUtils.normalizeLanguageCode(str == null ? null : str.split("_")[0]);
        if (TextUtils.isEmpty(strNormalizeLanguageCode)) {
            return null;
        }
        int iIndexOf = strNormalizeLanguageCode.indexOf(45);
        return iIndexOf >= 0 ? strNormalizeLanguageCode.substring(0, iIndexOf) : strNormalizeLanguageCode;
    }

    private static String getDefaultRestrictedLanguage() {
        return normalizeRestrictedLanguage(TranslatorUtils.getResolvedTargetLanguageCode());
    }

    private static HashSet<String> sanitizeRestrictedLanguages(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<>();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String strNormalizeRestrictedLanguage = normalizeRestrictedLanguage(it.next());
            if (!TextUtils.isEmpty(strNormalizeRestrictedLanguage)) {
                hashSet.add(strNormalizeRestrictedLanguage);
            }
        }
        if (hashSet.isEmpty()) {
            return null;
        }
        return hashSet;
    }

    public static HashSet<String> getRestrictedLanguages() {
        if (!gotRestrictedLanguages) {
            restrictedLanguages = sanitizeRestrictedLanguages(MessagesController.getGlobalMainSettings().getStringSet("translate_button_restricted_languages", null));
            gotRestrictedLanguages = true;
        }
        if (restrictedLanguages == null) {
            restrictedLanguages = Sets.newHashSet();
            String defaultRestrictedLanguage = getDefaultRestrictedLanguage();
            if (!TextUtils.isEmpty(defaultRestrictedLanguage)) {
                restrictedLanguages.add(defaultRestrictedLanguage);
            }
        }
        return restrictedLanguages;
    }

    public static void invalidateRestrictedLanguages() {
        gotRestrictedLanguages = false;
    }

    public static void updateRestrictedLanguages(HashSet<String> hashSet, Boolean bool) {
        restrictedLanguages = sanitizeRestrictedLanguages(hashSet);
        gotRestrictedLanguages = true;
        SharedPreferences.Editor editorEdit = MessagesController.getGlobalMainSettings().edit();
        HashSet<String> hashSet2 = restrictedLanguages;
        if (hashSet2 == null) {
            editorEdit.remove("translate_button_restricted_languages");
        } else {
            editorEdit.putStringSet("translate_button_restricted_languages", hashSet2);
        }
        if (bool == null) {
            editorEdit.remove("translate_button_restricted_languages_changed");
        } else if (bool.booleanValue()) {
            editorEdit.putBoolean("translate_button_restricted_languages_changed", true);
        }
        editorEdit.apply();
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        if (this.type == 0) {
            this.firstSelectedLanguages = getRestrictedLanguages();
            this.selectedLanguages = getRestrictedLanguages();
        }
        fillLanguages();
        LocaleController.getInstance().loadRemoteLanguages(this.currentAccount);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.suggestedLangpack);
        return super.onFragmentCreate();
    }

    private void rebind(int i) {
        int adapterPosition;
        RecyclerView.Adapter adapter = this.listView.getAdapter();
        for (int i2 = 0; i2 < this.listView.getChildCount(); i2++) {
            RecyclerView.ViewHolder childViewHolder = this.listView.getChildViewHolder(this.listView.getChildAt(i2));
            if (childViewHolder != null && (adapterPosition = childViewHolder.getAdapterPosition()) != -1 && adapterPosition == i) {
                adapter.onBindViewHolder(childViewHolder, i);
                return;
            }
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
    }

    public static boolean toggleLanguage(String str, boolean z) {
        if (str == null) {
            return false;
        }
        String strNormalizeRestrictedLanguage = normalizeRestrictedLanguage(str);
        if (TextUtils.isEmpty(strNormalizeRestrictedLanguage)) {
            return false;
        }
        String defaultRestrictedLanguage = getDefaultRestrictedLanguage();
        HashSet<String> restrictedLanguages2 = getRestrictedLanguages();
        if (!z) {
            restrictedLanguages2.remove(strNormalizeRestrictedLanguage);
        } else {
            restrictedLanguages2.add(strNormalizeRestrictedLanguage);
        }
        if (restrictedLanguages2.size() == 1 && restrictedLanguages2.contains(defaultRestrictedLanguage)) {
            updateRestrictedLanguages(null, Boolean.FALSE);
        } else {
            updateRestrictedLanguages(restrictedLanguages2, Boolean.FALSE);
        }
        TranslateController.invalidateSuggestedLanguageCodes();
        return true;
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2797R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(this.type == 1 ? C2797R.string.TranslationTarget : C2797R.string.DoNotTranslate));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity.1
            public C65801() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    RestrictedLanguagesSelectActivity.this.finishFragment();
                }
            }
        });
        this.actionBar.createMenu().addItem(0, C2797R.drawable.outline_header_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItem.ActionBarMenuItemSearchListener() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity.2
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onSearchExpand() {
            }

            public C65812() {
            }

            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onSearchCollapse() {
                RestrictedLanguagesSelectActivity.this.search(null);
                if (RestrictedLanguagesSelectActivity.this.listView != null) {
                    RestrictedLanguagesSelectActivity.this.emptyView.setVisibility(8);
                    RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.listAdapter);
                }
            }

            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onTextChanged(EditText editText) {
                String string = editText.getText().toString();
                RestrictedLanguagesSelectActivity.this.search(string);
                int length = string.length();
                RestrictedLanguagesSelectActivity restrictedLanguagesSelectActivity = RestrictedLanguagesSelectActivity.this;
                if (length != 0) {
                    if (restrictedLanguagesSelectActivity.listView != null) {
                        RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.searchListViewAdapter);
                    }
                } else if (restrictedLanguagesSelectActivity.listView != null) {
                    RestrictedLanguagesSelectActivity.this.emptyView.setVisibility(8);
                    RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.listAdapter);
                }
            }
        }).setSearchFieldHint(LocaleController.getString(C2797R.string.Search));
        this.listAdapter = new ListAdapter(context, false);
        this.searchListViewAdapter = new ListAdapter(context, true);
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout2 = (FrameLayout) this.fragmentView;
        EmptyTextProgressView emptyTextProgressView = new EmptyTextProgressView(context);
        this.emptyView = emptyTextProgressView;
        emptyTextProgressView.setText(LocaleController.getString(C2797R.string.NoResult));
        this.emptyView.showTextView();
        this.emptyView.setShowAtCenter(true);
        frameLayout2.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        recyclerListView.setSections();
        this.listView.setEmptyView(this.emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setAdapter(this.listAdapter);
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.actionBar.setAdaptiveBackground(this.listView);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$createView$1(view, i);
            }
        });
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity.3
            public C65823() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1) {
                    AndroidUtilities.hideKeyboard(RestrictedLanguagesSelectActivity.this.getParentActivity().getCurrentFocus());
                }
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: renamed from: org.telegram.ui.RestrictedLanguagesSelectActivity$1 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C65801 extends ActionBar.ActionBarMenuOnItemClick {
        public C65801() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if (i == -1) {
                RestrictedLanguagesSelectActivity.this.finishFragment();
            }
        }
    }

    /* JADX INFO: renamed from: org.telegram.ui.RestrictedLanguagesSelectActivity$2 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C65812 extends ActionBarMenuItem.ActionBarMenuItemSearchListener {
        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onSearchExpand() {
        }

        public C65812() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onSearchCollapse() {
            RestrictedLanguagesSelectActivity.this.search(null);
            if (RestrictedLanguagesSelectActivity.this.listView != null) {
                RestrictedLanguagesSelectActivity.this.emptyView.setVisibility(8);
                RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.listAdapter);
            }
        }

        @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
        public void onTextChanged(EditText editText) {
            String string = editText.getText().toString();
            RestrictedLanguagesSelectActivity.this.search(string);
            int length = string.length();
            RestrictedLanguagesSelectActivity restrictedLanguagesSelectActivity = RestrictedLanguagesSelectActivity.this;
            if (length != 0) {
                if (restrictedLanguagesSelectActivity.listView != null) {
                    RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.searchListViewAdapter);
                }
            } else if (restrictedLanguagesSelectActivity.listView != null) {
                RestrictedLanguagesSelectActivity.this.emptyView.setVisibility(8);
                RestrictedLanguagesSelectActivity.this.listView.setAdapter(RestrictedLanguagesSelectActivity.this.listAdapter);
            }
        }
    }

    public /* synthetic */ void lambda$createView$1(View view, int i) {
        TranslateController.Language language;
        String str;
        ArrayList<TranslateController.Language> arrayList;
        if (getParentActivity() == null || this.parentLayout == null || !(view instanceof TextCheckbox2Cell)) {
            return;
        }
        int i2 = 0;
        boolean z = this.listView.getAdapter() == this.searchListViewAdapter;
        if (z && (arrayList = this.searchResult) != null) {
            language = arrayList.get(i);
        } else {
            int i3 = this.separatorRow;
            if (i3 >= 0 && i > i3) {
                i--;
            }
            language = (i < 0 || i >= this.allLanguages.size()) ? null : this.allLanguages.get(i);
        }
        if (language == null || (str = language.code) == null) {
            return;
        }
        if (this.type == 1) {
            TranslateAlert2.setToLanguage(str);
            finishFragment();
            return;
        }
        final String strNormalizeRestrictedLanguage = normalizeRestrictedLanguage(str);
        String defaultRestrictedLanguage = getDefaultRestrictedLanguage();
        boolean zContains = this.selectedLanguages.contains(strNormalizeRestrictedLanguage);
        HashSet<String> hashSet = this.selectedLanguages;
        if (zContains) {
            hashSet.removeIf(new Predicate() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RestrictedLanguagesSelectActivity.$r8$lambda$mTmfCUvxJwc7NFIoYNVr2bVIibM(strNormalizeRestrictedLanguage, (String) obj);
                }
            });
        } else {
            hashSet.add(strNormalizeRestrictedLanguage);
        }
        if (this.selectedLanguages.size() == 1 && this.selectedLanguages.contains(defaultRestrictedLanguage)) {
            updateRestrictedLanguages(null, null);
        } else {
            updateRestrictedLanguages(this.selectedLanguages, Boolean.TRUE);
        }
        if (z) {
            int i4 = 0;
            while (i2 < this.searchResult.size()) {
                if (TextUtils.equals(normalizeRestrictedLanguage(this.searchResult.get(i2).code), strNormalizeRestrictedLanguage)) {
                    rebind(i4);
                }
                i2++;
                i4++;
            }
        } else {
            int i5 = 0;
            while (i2 < this.allLanguages.size()) {
                if (i5 == this.separatorRow) {
                    i5++;
                }
                if (TextUtils.equals(normalizeRestrictedLanguage(this.allLanguages.get(i2).code), strNormalizeRestrictedLanguage)) {
                    rebind(i5);
                }
                i2++;
                i5++;
            }
        }
        MessagesController.getInstance(this.currentAccount).getTranslateController().checkRestrictedLanguagesUpdate();
    }

    public static /* synthetic */ boolean $r8$lambda$mTmfCUvxJwc7NFIoYNVr2bVIibM(String str, String str2) {
        return str2 != null && str2.equals(str);
    }

    /* JADX INFO: renamed from: org.telegram.ui.RestrictedLanguagesSelectActivity$3 */
    /* JADX INFO: loaded from: classes6.dex */
    public class C65823 extends RecyclerView.OnScrollListener {
        public C65823() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1) {
                AndroidUtilities.hideKeyboard(RestrictedLanguagesSelectActivity.this.getParentActivity().getCurrentFocus());
            }
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i != NotificationCenter.suggestedLangpack || this.listAdapter == null) {
            return;
        }
        fillLanguages();
        this.listAdapter.notifyDataSetChanged();
    }

    private void fillLanguages() {
        this.allLanguages = TranslateController.getLanguages();
        TranslateController.Language language = null;
        if (this.type == 1) {
            this.allLanguages = TranslatorUtils.getCurrentTargetLanguages();
            String toLanguage = TranslateAlert2.getToLanguage();
            boolean zIsTargetLanguageFollowApp = TranslatorUtils.isTargetLanguageFollowApp();
            TranslateController.Language language2 = new TranslateController.Language();
            language2.code = Common.ASSET_APP;
            language2.ownDisplayName = LocaleController.getString(C2797R.string.TranslationTargetApp);
            String resolvedTargetLanguageCode = TranslatorUtils.getResolvedTargetLanguageCode(Common.ASSET_APP);
            String strCapitalFirst = TranslateAlert2.capitalFirst(TranslateAlert2.languageName(resolvedTargetLanguageCode));
            if (TextUtils.isEmpty(strCapitalFirst)) {
                strCapitalFirst = resolvedTargetLanguageCode == null ? _UrlKt.FRAGMENT_ENCODE_SET : resolvedTargetLanguageCode.toUpperCase(Locale.US);
            }
            language2.displayName = strCapitalFirst;
            language2.f1182q = (language2.ownDisplayName + " " + language2.displayName).toLowerCase(Locale.US);
            if (!zIsTargetLanguageFollowApp && !TextUtils.isEmpty(toLanguage)) {
                int i = 0;
                while (true) {
                    if (i >= this.allLanguages.size()) {
                        break;
                    }
                    TranslateController.Language language3 = this.allLanguages.get(i);
                    if (TextUtils.equals(language3.code, toLanguage)) {
                        this.allLanguages.remove(i);
                        language = language3;
                        break;
                    }
                    i++;
                }
                if (language == null) {
                    language = new TranslateController.Language();
                    language.code = toLanguage;
                    String strCapitalFirst2 = TranslateAlert2.capitalFirst(TranslateAlert2.languageName(toLanguage));
                    language.displayName = strCapitalFirst2;
                    if (TextUtils.isEmpty(strCapitalFirst2)) {
                        language.displayName = toLanguage.toUpperCase(Locale.US);
                    }
                    String strCapitalFirst3 = TranslateAlert2.capitalFirst(TranslateAlert2.systemLanguageName(toLanguage, true));
                    language.ownDisplayName = strCapitalFirst3;
                    if (TextUtils.isEmpty(strCapitalFirst3)) {
                        language.ownDisplayName = language.displayName;
                    }
                    language.f1182q = (language.displayName + " " + language.ownDisplayName).toLowerCase(Locale.US);
                }
            }
            this.separatorRow = 0;
            this.allLanguages.add(0, language2);
            this.separatorRow++;
            if (language != null) {
                this.allLanguages.add(1, language);
                this.separatorRow++;
            }
            if (this.separatorRow <= 0) {
                this.separatorRow = -1;
                return;
            }
            return;
        }
        String defaultRestrictedLanguage = getDefaultRestrictedLanguage();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(this.firstSelectedLanguages);
        int i2 = 0;
        while (i2 < this.allLanguages.size()) {
            TranslateController.Language language4 = this.allLanguages.get(i2);
            String strNormalizeRestrictedLanguage = normalizeRestrictedLanguage(language4.code);
            if (TextUtils.equals(strNormalizeRestrictedLanguage, defaultRestrictedLanguage)) {
                arrayList2.remove(strNormalizeRestrictedLanguage);
                this.allLanguages.remove(i2);
                i2--;
                language = language4;
            } else if (this.firstSelectedLanguages.contains(strNormalizeRestrictedLanguage)) {
                arrayList.add(language4);
                arrayList2.remove(strNormalizeRestrictedLanguage);
                this.allLanguages.remove(i2);
                i2--;
            }
            i2++;
        }
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            TranslateController.Language language5 = new TranslateController.Language();
            String str = (String) arrayList2.get(i3);
            language5.code = str;
            String upperCase = str.toUpperCase();
            language5.displayName = upperCase;
            language5.ownDisplayName = upperCase;
            language5.f1182q = language5.code.toLowerCase();
            arrayList.add(language5);
        }
        this.separatorRow = 0;
        this.allLanguages.addAll(0, arrayList);
        this.separatorRow += arrayList.size();
        if (language != null) {
            this.allLanguages.add(0, language);
            this.separatorRow++;
        }
        if (this.separatorRow <= 0) {
            this.separatorRow = -1;
        }
    }

    public boolean isTargetChecked(String str) {
        if (TextUtils.equals(str, Common.ASSET_APP)) {
            return TranslatorUtils.isTargetLanguageFollowApp();
        }
        if (TranslatorUtils.isTargetLanguageFollowApp()) {
            return false;
        }
        return TextUtils.equals(str, TranslateAlert2.getToLanguage());
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            fillLanguages();
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void search(String str) {
        if (str == null) {
            this.searchResult = null;
        } else {
            processSearch(str);
        }
    }

    private void processSearch(String str) {
        String lowerCase = str.trim().toLowerCase();
        ArrayList<TranslateController.Language> arrayList = this.searchResult;
        if (arrayList == null) {
            this.searchResult = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        for (int i = 0; i < this.allLanguages.size(); i++) {
            TranslateController.Language language = this.allLanguages.get(i);
            if (language.f1182q.startsWith(lowerCase)) {
                this.searchResult.add(0, language);
            } else if (language.f1182q.contains(lowerCase)) {
                this.searchResult.add(language);
            }
        }
        this.searchListViewAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: loaded from: classes6.dex */
    public class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        private boolean search;

        public ListAdapter(Context context, boolean z) {
            this.mContext = context;
            this.search = z;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            boolean z = this.search;
            RestrictedLanguagesSelectActivity restrictedLanguagesSelectActivity = RestrictedLanguagesSelectActivity.this;
            if (!z) {
                return (restrictedLanguagesSelectActivity.separatorRow >= 0 ? 1 : 0) + RestrictedLanguagesSelectActivity.this.allLanguages.size();
            }
            if (restrictedLanguagesSelectActivity.searchResult == null) {
                return 0;
            }
            return RestrictedLanguagesSelectActivity.this.searchResult.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textCheckbox2Cell;
            if (i == 0) {
                textCheckbox2Cell = new TextCheckbox2Cell(this.mContext);
            } else if (i == 2) {
                HeaderCell headerCell = new HeaderCell(this.mContext);
                headerCell.setText(LocaleController.getString(C2797R.string.ChooseLanguages));
                textCheckbox2Cell = headerCell;
            } else {
                textCheckbox2Cell = new ShadowSectionCell(this.mContext);
            }
            return new RecyclerListView.Holder(textCheckbox2Cell);
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x0042 A[PHI: r3
  0x0042: PHI (r3v5 org.telegram.messenger.TranslateController$Language) = (r3v2 org.telegram.messenger.TranslateController$Language), (r3v6 org.telegram.messenger.TranslateController$Language) binds: [B:67:0x007c, B:56:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r6, int r7) {
            /*
                r5 = this;
                int r0 = r6.getItemViewType()
                r1 = 1
                if (r0 == 0) goto L10
                if (r0 == r1) goto Lb
                goto L82
            Lb:
                android.view.View r5 = r6.itemView
                org.telegram.ui.Cells.ShadowSectionCell r5 = (org.telegram.p035ui.Cells.ShadowSectionCell) r5
                return
            L10:
                android.view.View r6 = r6.itemView
                org.telegram.ui.Cells.TextCheckbox2Cell r6 = (org.telegram.p035ui.Cells.TextCheckbox2Cell) r6
                boolean r0 = r5.search
                r2 = 0
                r3 = 0
                if (r0 == 0) goto L44
                if (r7 < 0) goto L35
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19629$$Nest$fgetsearchResult(r0)
                int r0 = r0.size()
                if (r7 >= r0) goto L35
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19629$$Nest$fgetsearchResult(r0)
                java.lang.Object r0 = r0.get(r7)
                r3 = r0
                org.telegram.messenger.TranslateController$Language r3 = (org.telegram.messenger.TranslateController.Language) r3
            L35:
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19629$$Nest$fgetsearchResult(r0)
                int r0 = r0.size()
                int r0 = r0 - r1
                if (r7 != r0) goto L7f
            L42:
                r7 = r1
                goto L80
            L44:
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                int r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19631$$Nest$fgetseparatorRow(r0)
                if (r0 < 0) goto L56
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                int r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19631$$Nest$fgetseparatorRow(r0)
                if (r7 <= r0) goto L56
                int r7 = r7 + (-1)
            L56:
                if (r7 < 0) goto L7f
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19624$$Nest$fgetallLanguages(r0)
                int r0 = r0.size()
                if (r7 >= r0) goto L7f
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19624$$Nest$fgetallLanguages(r0)
                java.lang.Object r0 = r0.get(r7)
                r3 = r0
                org.telegram.messenger.TranslateController$Language r3 = (org.telegram.messenger.TranslateController.Language) r3
                org.telegram.ui.RestrictedLanguagesSelectActivity r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                java.util.ArrayList r0 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19624$$Nest$fgetallLanguages(r0)
                int r0 = r0.size()
                int r0 = r0 - r1
                if (r7 != r0) goto L7f
                goto L42
            L7f:
                r7 = r2
            L80:
                if (r3 != 0) goto L83
            L82:
                return
            L83:
                java.lang.String r0 = r3.ownDisplayName
                if (r0 != 0) goto L89
                java.lang.String r0 = r3.displayName
            L89:
                java.lang.String r4 = r3.displayName
                r7 = r7 ^ r1
                r6.setTextAndValue(r0, r4, r2, r7)
                org.telegram.ui.RestrictedLanguagesSelectActivity r7 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                int r7 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19632$$Nest$fgettype(r7)
                org.telegram.ui.RestrictedLanguagesSelectActivity r5 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.this
                if (r7 != r1) goto La3
                java.lang.String r7 = r3.code
                boolean r5 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19633$$Nest$misTargetChecked(r5, r7)
                r6.setChecked(r5)
                return
            La3:
                java.util.HashSet r5 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19630$$Nest$fgetselectedLanguages(r5)
                java.lang.String r7 = r3.code
                java.lang.String r7 = org.telegram.p035ui.RestrictedLanguagesSelectActivity.m19634$$Nest$smnormalizeRestrictedLanguage(r7)
                boolean r5 = r5.contains(r7)
                r6.setChecked(r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.RestrictedLanguagesSelectActivity.ListAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return (!this.search && i == RestrictedLanguagesSelectActivity.this.separatorRow) ? 1 : 0;
        }
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{LanguageCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"textView2"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"checkImage"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_featuredStickers_addedIcon));
        return arrayList;
    }

    public static void cleanup() {
        invalidateRestrictedLanguages();
        MessagesController.getGlobalMainSettings().edit().remove("translate_button_restricted_languages_changed").remove("translate_button_restricted_languages_version").remove("translate_button_restricted_languages").apply();
        checkRestrictedLanguages(false);
    }

    public static void checkRestrictedLanguages(boolean z) {
        boolean z2 = MessagesController.getGlobalMainSettings().getBoolean("translate_button_restricted_languages_changed", false);
        if (MessagesController.getGlobalMainSettings().getInt("translate_button_restricted_languages_version", 0) != 2 || (z && !z2)) {
            getExtendedDoNotTranslate(new Utilities.Callback() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    RestrictedLanguagesSelectActivity.$r8$lambda$fZBhqylQcfoDkpJieiBTBpFGTiE((HashSet) obj);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$fZBhqylQcfoDkpJieiBTBpFGTiE(HashSet hashSet) {
        String defaultRestrictedLanguage = getDefaultRestrictedLanguage();
        hashSet.addAll(getRestrictedLanguages());
        SharedPreferences.Editor editorEdit = MessagesController.getGlobalMainSettings().edit();
        if (hashSet.size() == 1 && TextUtils.equals((CharSequence) hashSet.iterator().next(), defaultRestrictedLanguage)) {
            editorEdit.remove("translate_button_restricted_languages");
        } else {
            editorEdit.putStringSet("translate_button_restricted_languages", hashSet);
        }
        editorEdit.putInt("translate_button_restricted_languages_version", 2).apply();
        invalidateRestrictedLanguages();
        for (int i = 0; i < 16; i++) {
            try {
                MessagesController.getInstance(i).getTranslateController().checkRestrictedLanguagesUpdate();
            } catch (Exception unused) {
            }
        }
    }

    public static void getExtendedDoNotTranslate(final Utilities.Callback<HashSet<String>> callback) {
        if (callback == null) {
            return;
        }
        final HashSet hashSet = new HashSet();
        Utilities.doCallbacks(new Utilities.Callback() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                RestrictedLanguagesSelectActivity.$r8$lambda$ItedNn7uVkAe3jnEOjch4daoks0(hashSet, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                RestrictedLanguagesSelectActivity.$r8$lambda$xVT5ksQ4xck1jPOwK9QlVoUsGrY(hashSet, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                RestrictedLanguagesSelectActivity.$r8$lambda$6DGdOuNr3aFPHx0pxeU5aiIj6DI(hashSet, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.RestrictedLanguagesSelectActivity$$ExternalSyntheticLambda4
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                callback.run(hashSet);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$ItedNn7uVkAe3jnEOjch4daoks0(HashSet hashSet, Runnable runnable) {
        try {
            addExtendedLanguage(hashSet, LocaleController.getInstance().getCurrentLocaleInfo().pluralLangCode);
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        runnable.run();
    }

    public static /* synthetic */ void $r8$lambda$xVT5ksQ4xck1jPOwK9QlVoUsGrY(HashSet hashSet, Runnable runnable) {
        try {
            addExtendedLanguage(hashSet, Resources.getSystem().getConfiguration().locale.getLanguage());
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        runnable.run();
    }

    public static /* synthetic */ void $r8$lambda$6DGdOuNr3aFPHx0pxeU5aiIj6DI(HashSet hashSet, Runnable runnable) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ApplicationLoader.applicationContext.getSystemService("input_method");
            Iterator<InputMethodInfo> it = inputMethodManager.getEnabledInputMethodList().iterator();
            while (it.hasNext()) {
                for (InputMethodSubtype inputMethodSubtype : inputMethodManager.getEnabledInputMethodSubtypeList(it.next(), true)) {
                    if ("keyboard".equals(inputMethodSubtype.getMode())) {
                        addExtendedLanguage(hashSet, inputMethodSubtype.getLocale());
                    }
                }
            }
        } catch (Exception e) {
            FileLog.m1048e(e);
        }
        runnable.run();
    }

    private static void addExtendedLanguage(HashSet<String> hashSet, String str) {
        String strNormalizeRestrictedLanguage = normalizeRestrictedLanguage(str);
        if (TextUtils.isEmpty(strNormalizeRestrictedLanguage) || TranslateAlert2.languageName(strNormalizeRestrictedLanguage) == null) {
            return;
        }
        hashSet.add(strNormalizeRestrictedLanguage);
    }

    @Override // org.telegram.p035ui.ActionBar.BaseFragment
    public void onInsets(int i, int i2, int i3, int i4) {
        this.listView.setPadding(0, 0, 0, i4);
        this.listView.setClipToPadding(false);
    }
}
