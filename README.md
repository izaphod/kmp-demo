# Sequenia KMP

Кроссплатформенное демо-приложение, разработанное с использованием Kotlin Multiplatform.

## Архитектура проекта

Проект использует архитектурный подход Model-View-ViewModel (MVVM) с разделением на слои:

- **Presentation Layer**: Отвечает за отображение UI и обработку пользовательских взаимодействий
- **Domain Layer**: Содержит бизнес-логику приложения
- **Data Layer**: Управляет получением и хранением данных

## Структура проекта

```
sequenia-kmp/
├── androidApp/           # Android-приложение
├── composeApp/           # KMP-библиотека с общей логикой и UI
├── iosApp/               # iOS-приложение
├── gradle/               # Конфигурация Gradle
├── build.gradle.kts      # Общая конфигурация сборки
├── settings.gradle.kts   # Настройки проекта Gradle
└── README.md             # Документация проекта
```

### composeApp

Основная KMP-библиотека, содержащая общую логику для Android и iOS:

- **src/commonMain/kotlin**: Общий код для всех платформ
- **src/androidMain/kotlin**: Код, специфичный для Android
- **src/iosMain/kotlin**: Код, специфичный для iOS

### androidApp

Android-приложение, использующее composeApp как зависимость. Содержит минимальную конфигурацию и запускает общий UI.

### iosApp

iOS-приложение на Swift, которое интегрирует KMP-модуль через фреймворк ComposeApp. Использует `MainViewControllerKt.MainViewController()` для отображения общего UI.

## Требования к рабочему окружению

### Android

1. Android Studio Otter 3 Feature Drop (2025.2.3) или новее
2. Kotlin Multiplatform plugin для IntelliJ IDEA и Android Studio
3. JVM v.17 или новее

### iOS

1. macOS 15.0 (Sequoia) или новее
2. Xcode 26.1 или новее
3. iOS Simulator

## Технологический стек

### Core

- Kotlin Multiplatform (v2.3.0)
- Compose Multiplatform (v1.10.0)
- Kotlin Serialization (v1.10.0)
- Kotlin Coroutines (v1.10.2)

### UI & Navigation

- Jetpack Compose
- Navigation 3
- Coil 3 (v3.3.0)
- Material 3

### Dependency Injection

- Koin (v4.2.0-beta4)

### Networking & Data

- Ktor (v3.4.0)
- Room (v2.8.4)
- DataStore Preferences (v1.2.0)

## Сборка и запуск

### Android

1. Открыть проект в Android Studio
2. Выбрать конфигурацию запуска "androidApp"
3. Запустить на устройстве или эмуляторе

### iOS

1. Открыть проект в Android Studio или `iosApp/iosApp.xcworkspace` в Xcode
2. При использовании Android Studio выбрать конфигурацию запуска "iosApp"
3. Выбрать симулятор или устройство
4. Запустить приложение

## Генерация схемы базы данных

Схема базы данных Room генерируется автоматически при сборке. Файлы схемы находятся в `composeApp/schemas/`.

## Версионирование зависимостей

Версии зависимостей управляются через `gradle/libs.versions.toml`.
