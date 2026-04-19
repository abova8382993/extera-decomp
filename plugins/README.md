# exteraGram plugins

Плагины для exteraGram, использующие декомпилированный код из `sources/`
в качестве справки.

## `stereo_voice.plugin`

**Что делает.** Заставляет exteraGram воспроизводить голосовые сообщения
и круглые видео (кружки) в стерео вместо моно — дублирует единственный
моно-канал в оба стерео-канала ещё на уровне ExoPlayer, до создания
`AudioTrack`.

**Зачем.** Голосовые сообщения и часть кружков кодируются как mono PCM.
На стерео-колонках Android обычно делает микс автоматически, но
часть Bluetooth-наушников и некоторые аудио-маршруты в системе
пускают моно-сигнал только в один канал. Плагин форсирует стерео-выход
на уровне `DefaultAudioSink`, что гарантирует одинаковый сигнал в
обоих каналах.

### Как это работает (технически)

1. В exteraGram голосовые/кружки воспроизводятся через
   `org.telegram.ui.Components.VideoPlayer`, который построен на
   ExoPlayer.
2. ExoPlayer отдаёт декодированный PCM в
   `com.google.android.exoplayer2.audio.DefaultAudioSink`, вызывая
   `configure(Format format, int specifiedBufferSize, int[] outputChannels)`.
3. Встроенный `ChannelMappingAudioProcessor` внутри `DefaultAudioSink`
   умеет по карте каналов дублировать вход в несколько выходов —
   но стандартно он получает `null` (карты нет) и ничего не делает.
4. Плагин вешается Xposed-хуком на `DefaultAudioSink.configure`.
   Если формат `audio/raw` и `channelCount == 1`, третий аргумент
   (`int[]`) подменяется на `new int[]{0, 0}`. Тогда
   `ChannelMappingAudioProcessor` выдаёт 2-канальный PCM, а
   `AudioTrack` создаётся как `CHANNEL_OUT_STEREO`.

Код хука: [`stereo_voice.plugin`](./stereo_voice.plugin).
Ключевые места в декомпе:

- Путь воспроизведения голосовых / кружков — `MediaController.audioPlayer`
  типа `VideoPlayer`: `sources/org/telegram/messenger/MediaController.java`.
- Обёртка над ExoPlayer — `sources/org/telegram/p026ui/Components/VideoPlayer.java`.
- Сам sink и `ChannelMappingAudioProcessor` —
  `sources/com/google/android/exoplayer2/audio/DefaultAudioSink.java`
  и `sources/com/google/android/exoplayer2/audio/ChannelMappingAudioProcessor.java`.

### Что не затрагивается

- Голосовые и видеозвонки (VoIP): идут через WebRTC
  (`org.webrtc.voiceengine.*`), минуя ExoPlayer.
- Стерео-источники (музыка, обычные видео со стерео-дорожкой):
  хук срабатывает только при `channelCount == 1`.
- Не-PCM форматы (offload / passthrough): хук работает только в ветке
  `audio/raw`, где `channel-map` реально используется.

### Установка

1. Собрать / открыть exteraGram версии `12.5.1+` с включённым плагин-движком.
2. `exteraGram Preferences` → `Plugins` → включить плагин-движок.
3. Положить `stereo_voice.plugin` в папку плагинов exteraGram и включить
   его в списке.
4. В настройках плагина убедиться, что `Стерео вместо моно` включено.

### Ограничения

- Требует SDK плагинов `>=1.4.3.6` и exteraGram `>=12.5.1`.
- Если в какой-то будущей версии ExoPlayer сигнатура
  `DefaultAudioSink.configure` изменится, хук перестанет срабатывать —
  плагин просто ничего не сделает, поломки воспроизведения быть не должно.
