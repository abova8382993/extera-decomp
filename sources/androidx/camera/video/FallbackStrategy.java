package androidx.camera.video;

/* JADX INFO: loaded from: classes4.dex */
public abstract class FallbackStrategy {
    static final FallbackStrategy NONE = new AutoValue_FallbackStrategy_RuleStrategy(Quality.NONE, 0);

    private FallbackStrategy() {
    }

    public static FallbackStrategy higherQualityOrLowerThan(Quality quality) {
        return new AutoValue_FallbackStrategy_RuleStrategy(quality, 1);
    }

    public static abstract class RuleStrategy extends FallbackStrategy {
        public abstract Quality getFallbackQuality();

        public abstract int getFallbackRule();

        public RuleStrategy() {
            super();
        }
    }
}
