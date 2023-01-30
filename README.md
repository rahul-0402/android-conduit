# android-conduit
An Android application built using Clean Architecture and Model-View-Intent (MVI) design patterns.

### Features
1. User authentication
2. Article creation and publishing
3. Article viewing
4. User profile

### Technical Details
1. Clean architecture
2. Model-View-Intent pattern for handling UI interactions and state management
3. [Coroutines](https://developer.android.com/kotlin/coroutines) for asynchronous programming
4. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
5. [Retrofit](https://square.github.io/retrofit/) for consuming REST APIs
6. [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore) is a improved data storage solution aimed at replacing SharedPreferences
6. [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)

## Architecture
Clean Architecture is an architecture pattern for developing software that separates the app into different layers based on responsibility.

**Data layer**: This layer is responsible for managing data sources such as databases, APIs, and shared preferences. It is the source of truth for the data in the app, and it should be independent of the rest of the app so that it can be easily swapped out or modified.

**Domain layer**: This layer contains the business logic and rules for the app. It is responsible for processing data from the data layer, performing calculations, and generating results. It should be free of any Android or UI dependencies.

**UI (or presentation) layer**: This layer is responsible for presenting the data to the user and handling user interactions. It communicates with the domain layer to get the data it needs and updates the UI based on the data received. This layer is highly dependent on the Android framework and should be the outermost layer in the architecture.

## Model-View-Intent (MVI)
Model-View-Intent (MVI) is a reactive design pattern for building user interfaces. It separates the app into three parts: Model, View, and Intent.

**Model**: Represents the state of the UI and data.

**View**: Responsible for rendering the UI and emitting user interactions as intents.

**Intent**: Describes user actions and triggers updates to the model.

With MVI, the UI state and user interactions are managed in a reactive and declarative way, making it easier to write and maintain the code.

## Contributing
If you want to contribute to the project, please feel free to submit a pull request.
