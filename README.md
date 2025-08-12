# Expense Tracker

A modern, offline-first Android application for tracking personal expenses. Built entirely with Kotlin and Jetpack Compose, this app provides a clean, intuitive interface to log, view, and analyze your spending habits.

## Features

-   **Log Expenses:** Easily add new expenses with details like title, amount, category (Food, Travel, Staff, Utility), and optional notes.
-   **Dynamic Filtering:** View expenses for today, the last 7 days, or a custom date range using a date picker.
-   **Flexible Grouping:** Organize your expense list by the time they were added or see a summary grouped by category.
-   **Interactive Dashboard:** The home screen features a summary card for total expenses and a filterable list of all transactions.
-   **Insightful Reports:** Generate a detailed 7-day report with a bar chart visualizing daily totals and a breakdown of spending by category.
-   **PDF Export:** Export your financial reports to a PDF document for sharing or archiving, saved directly to your device's Downloads folder.
-   **Customizable Theming:** Instantly switch between Light, Dark, and System default themes to suit your preference.
-   **Offline First:** All data is stored locally on your device using a Room database, ensuring full functionality without an internet connection.

## Architecture & Tech Stack

This project is built using modern Android development practices and libraries.

-   **Language:** [Kotlin](https://kotlinlang.org/)
-   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative UI.
-   **Architecture:** MVVM (Model-View-ViewModel) to separate business logic from the UI.
-   **Database:** [Room](https://developer.android.com/training/data-storage/room) for persistent local data storage.
-   **Asynchronous Operations:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for managing background threads and reactive data streams.
-   **Dependency Injection:** [Koin](https://insert-koin.io/) for managing dependencies.
-   **Navigation:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for handling in-app navigation.
-   **Theming:** Material 3 with custom color schemes and typography.
-   **User Preferences:** [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for persisting the selected theme.

## Project Structure

The project follows a clean architecture pattern, organized into layers for better separation of concerns and maintainability.

-   `data`: Contains repository implementations, Room database definitions (DAO, entities), and data models. It's the single source of truth for the app's data.
-   `domain`: Defines the business logic, including repository interfaces and core data models (e.g., `Category`, `AppTheme`). This layer is independent of the Android framework.
-   `ui`: Holds all the Jetpack Compose screens (`HomeScreen`, `AddExpenseScreen`, `ReportScreen`), their respective ViewModels, and UI event/state classes.
-   `designsystem`: A dedicated package for reusable UI components, typography, colors, and theming, ensuring a consistent look and feel across the app.
-   `di`: Koin modules for providing dependencies across the application.

