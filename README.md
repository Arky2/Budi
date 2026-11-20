# BirthdayGreeting — приложение для YaOS

Поздравление с днём рождения на весь экран: видео с музыкальной дорожкой (`res/raw/greeting.mp4`) и текст поверх. Работает на Станции Дуо Макс, ТВ Станциях и телевизорах с YaOS. APK устанавливается через раздел «Мои приложения» в Яндекс.Диалогах.

> **Нюанс:** голосом Алисы из стороннего приложения говорить нельзя — публичного API нет. «Озвучку» можно повесить на аудиодорожку видео или на параллельный сценарий в «Доме с Алисой».

---

## 1. Структура проекта

```
BirthdayGreeting/
├── settings.gradle.kts
├── build.gradle.kts
└── app/
    ├── build.gradle.kts
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/
        │   ├── layout/
        │   │   ├── activity_main.xml
        │   │   └── activity_settings.xml
        │   ├── values/strings.xml
        │   └── raw/greeting.mp4        ← ваше видео с музыкой
        └── java/ru/example/birthdaygreeting/
            ├── MainActivity.kt
            ├── SettingsActivity.kt
            ├── AlarmReceiver.kt
            ├── BootReceiver.kt
            └── Scheduler.kt
```

---
