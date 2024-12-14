
# Fleetify Report - Android Project

Fleetify Report is an Android application built with **Clean Architecture** that allows users to manage reports efficiently. This project is implemented using **Java**, **Hilt** for Dependency Injection, **RxJava** for reactive programming, **Retrofit** for networking, **Room** for offline storage, **Glide** for image loading, and **SwipeRefresh** for refreshing UI elements.

The app handles various data states (loading, success, error, empty) and is built to work offline by caching data using Room and synchronizing it with the server using Retrofit.

## Features

- **Search Report**: Allows users to search through the report data.
- **Report List**: Displays a list of reports with infinite scrolling.
- **Attachment View Dialog**: Shows report attachments in a dialog.
- **Create New Report**: Allows users to create new reports.
- **Offline Data Handling**: Supports offline data persistence with Room and updates data when the internet is available.
- **Pull to Refresh**: Users can refresh the report list using **SwipeRefresh**.

## Libraries and Tools

- **RxJava**: For handling asynchronous tasks and managing data streams.
- **Lombok**: Used to reduce boilerplate code, e.g., getters, setters, constructors.
- **Glide**: For image loading and caching.
- **SwipeRefresh**: For implementing pull-to-refresh behavior.
- **Room**: For local database and offline data storage.
- **Retrofit**: For making network requests and managing API communication.
- **Hilt**: For Dependency Injection (DI).
- **NetworkResourceBound**: For managing network data states (loading, success, error).

## Architecture

The app is organized using **Clean Architecture** principles:

- **Core**: Contains base DI setup, network communication (Retrofit), data sources, domain logic (use cases), and utility classes.
- **Features**: Each feature is organized in its own module with a focus on encapsulation and separation of concerns. Features include report creation, listing, searching, etc.
  
## Setup Instructions

### Prerequisites

1. **Android Studio**: You need Android Studio installed to run this project.
2. **Java 11** or higher: Ensure you're using Java 11 or later for compatibility with libraries.

### Clone the repository

```bash
git clone https://github.com/yourusername/fleetify-report.git
```

### Build the project

1. Open the project in **Android Studio**.
2. Sync the project with Gradle files.
3. Run the app on an emulator or device.

## Dependencies

```gradle
dependencies {
    // Hilt for Dependency Injection
    implementation 'com.google.dagger:hilt-android:2.44'
    annotationProcessor 'com.google.dagger:hilt-android-compiler:2.44'

    // Retrofit for network requests
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // RxJava for reactive programming
    implementation 'io.reactivex.rxjava2:rxjava:2.2.21'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

    // Room for local database
    implementation 'androidx.room:room-runtime:2.4.2'
    annotationProcessor 'androidx.room:room-compiler:2.4.2'

    // Glide for image loading
    implementation 'com.github.bumptech.glide:glide:4.13.0'

    // SwipeRefresh for pull-to-refresh behavior
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

    // Lombok for reducing boilerplate code
    implementation 'org.projectlombok:lombok:1.18.22'
    annotationProcessor 'org.projectlombok:lombok:1.18.22'
}
```

## Usage

Once the app is set up, you can interact with the following features:

1. **Search Report**: Tap on the search icon to start searching for reports.
2. **Report List**: Scroll through the list of reports, and it will auto-load more as you scroll down.
3. **Create New Report**: Tap on the "Create Report" button to add a new report.
4. **Attachment View Dialog**: Tap on a report to view any attachments associated with it.
5. **Offline Handling**: The app caches data in Room for offline use. When the network becomes available, it syncs the data.

## Offline Data Handling

The app manages data states efficiently by leveraging **Room** for offline storage and **Retrofit** for syncing data with the server. The app uses the **NetworkResourceBound** pattern to handle the data states as follows:

- **Loading**: The app fetches data from the local Room database.
- **Success**: When new data is fetched from the network, it's updated in the Room database.
- **Error**: In case of failure (e.g., no network), an error message is shown.

## Obfuscation

This project uses ProGuard/R8 for obfuscating the release version of the app to protect the code from reverse engineering. Ensure that you have enabled the appropriate configurations in your `build.gradle` file:

```gradle
buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

## Screenshots

Here are some screenshots of the Fleetify Report app in action:

| Screen 1               | Screen 2               | Screen 3               |
|------------------------|------------------------|------------------------|
| ![Splash Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040753_Fleetify%20Report.jpg) | ![Home Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040758_Fleetify%20Report.jpg) | ![Create Report Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040827_Fleetify%20Report.jpg) |

| Screen 4               | Screen 5               | Screen 6               |
|------------------------|------------------------|------------------------|
| ![Search Report Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040816_Fleetify%20Report.jpg) | ![Select Vehicle Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040831_Fleetify%20Report.jpg) | ![Select Attachment Source](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040837_Fleetify%20Report.jpg) |

| Screen 7               | Screen 8               | Screen 9               |
|------------------------|------------------------|------------------------|
| ![Picture taken result](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040918_Fleetify%20Report.jpg) | ![Camera Screen](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040843_Fleetify%20Report.jpg) | ![Loading Submission](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040926_Fleetify%20Report.jpg) |

| Screen 10              | Screen 11              | Screen 12              |
|------------------------|------------------------|------------------------|
| ![Success Submission](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040933_Fleetify%20Report.jpg) | ![Refresh layout](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040940_Fleetify%20Report.jpg) | ![Shimmer Loading](https://github.com/Avwaveaf/FleetifyReport/blob/main/screenshots/Screenshot_20241214-040942_Fleetify%20Report.jpg) |

