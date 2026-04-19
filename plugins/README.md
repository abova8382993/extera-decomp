# Stereo Round Videos plugin

Плагин для exteraGram, который заставляет **круглые видео** (кружки)
писать звук в стерео вместо моно. Голосовые сообщения не затрагиваются
(см. ниже почему).

Файл: [`stereo_round.plugin`](./stereo_round.plugin).

## Что делает плагин

По умолчанию `org.telegram.ui.Components.InstantCameraView$VideoRecorder.prepareEncoder`
инициализирует запись звука так:

```java
int minBufferSize = AudioRecord.getMinBufferSize(48000, CHANNEL_IN_MONO, PCM_16BIT); // 1
int bufferSize = 49152 < minBufferSize ? ((minBufferSize / 2048) + 1) * 4096 : 49152;
...
AudioRecord recorder = new AudioRecord(DEFAULT, 48000, CHANNEL_IN_MONO, PCM_16BIT, bufferSize); // 2
...
MediaFormat fmt = new MediaFormat();
fmt.setString("mime", "audio/mp4a-latm");
fmt.setInteger("sample-rate", 48000);
fmt.setInteger("channel-count", 1); // 3
```

Точки `1`, `2`, `3` находятся в
[`InstantCameraView.java:3653, 3684, 3699`](../sources/org/telegram/p026ui/Components/InstantCameraView.java).

Плагин подменяет три значения через Xposed-хуки:

| Хук                                     | Было           | Стало              |
| --------------------------------------- | -------------- | ------------------ |
| `AudioRecord.getMinBufferSize`          | `CHANNEL_IN_MONO (16)` | `CHANNEL_IN_STEREO (12)` |
| `new AudioRecord(..., ch, ...)`         | `CHANNEL_IN_MONO (16)` | `CHANNEL_IN_STEREO (12)` |
| `MediaFormat.setInteger("channel-count")` | `1`            | `2`                |

Каждый хук срабатывает **только когда стек вызова содержит
`InstantCameraView`** — чтобы не задеть другие места в Android/клиенте,
где тоже создаются `AudioRecord` / `MediaFormat` (VoIP, уведомления,
плееры и т.д.).

## Почему голосовые остаются моно

Голосовые сообщения пишутся через `MediaController` и кодируются
**нативным** Opus-энкодером:

- [`MediaController.java:347-352`](../sources/org/telegram/messenger/MediaController.java)
  — `startRecord(String path, int sampleRate)` и `writeFrame(ByteBuffer, int)`
  объявлены как `native`.
- Тело этих функций живёт в нативной библиотеке (`TMessagesProj/jni/audio.c`
  в upstream Telegram, которую форкает exteraGram). В ней количество
  каналов энкодера зашито в исходниках:

  ```c
  inopt.channels = 1;
  header.channels = 1;
  _encoder = opus_encoder_create(coding_rate, 1, OPUS_APPLICATION_VOIP, &result);
  ```

Плагин не может изменить нативный код. Чтобы получить стерео-голосовые,
нужно патчить `audio.c` и пересобирать `.so` — это уже не плагин, а
модификация форка приложения.

Плюс сам протокол Telegram для voice-сообщений
(`TL_documentAttributeAudio.voice = true`) исторически подразумевает
одноканальное OGG Opus, так что даже при патче нативки стерео-голосовые
могут отображаться странно в других клиентах.

## Оговорки про реальный звук

- `MediaCodec` AAC-энкодер принимает `channel-count = 2` на всех
  современных Android-устройствах, поэтому само кодирование не
  упадёт.
- Получится ли **настоящее** стерео, зависит от телефона:
  - У аппаратов с mic-array (Pixel, многие Samsung флагманы) стерео
    будет реальным.
  - У аппаратов с одним основным микрофоном Android обычно дублирует
    сигнал в оба канала — выйдет «центрированное стерео» (по сути
    моно, но в стерео-контейнере).

## Настройки плагина

В настройках плагина есть переключатель **«Писать кружки в стерео»**.
Если записанные кружки звучат странно — выключите его, и запись сразу
вернётся к стандартному моно-поведению без перезапуска приложения.

## Требования

- exteraGram `>= 12.5.1`
- Plugin SDK `>= 1.4.3.3`

## История

Изначальная версия PR пыталась решить задачу со стороны воспроизведения
(`DefaultAudioSink` → `AudioTrack` c дублированием моно-канала). Это
было недоразумением: задача — стерео-**запись**. Текущая реализация
работает строго на стороне записи, только для `InstantCameraView`.
