# Animal Restful API

This is an Android application that provides information about animals using a RESTful API. The app allows users to search for animals, view their details, and navigate between different fragments for more information.

## Features

- **Search functionality:** Users can search for animals by name.
- **Animal Details:** Displays animal details including name, description, image, and more.
- **Multi-language support:** The app is built with support for Hebrew (currently implemented in the project).
- **RecyclerView display:** Results are displayed in a list using a `RecyclerView`.
- **Navigation:** Users can navigate from the search fragment to an animal details fragment.

## Technologies Used

- **Android**: The application is built using Android SDK and Java.
- **Retrofit**: A type-safe HTTP client used to interact with the API.
- **ViewModel**: Architecture component for managing UI-related data lifecycle-consciously.
- **LiveData**: Used for observing data changes in real-time.
- **RecyclerView**: Used to display the list of animals.
- **Navigation Component**: For easy navigation between different fragments.

## API Details

This app interacts with the **API Ninjas Animal API** and the **Wikipedia API** to fetch animal data.

### API Endpoints

- **API Ninjas**: 
  - **GET /animals** - Fetches a list of animals based on the query parameter.
  - Example query: `https://api.api-ninjas.com/v1/animals?name={animal_name}`

- **Wikipedia API**: 
  - Used to fetch additional information about the animal, such as a description, summary, and related content from Wikipedia.

The app uses both APIs to gather comprehensive data about animals and display it to the user.

## Requirements

- Android Studio
- Java 8 or above
- Internet connection (to fetch data from the API)

## Installation

1. Clone the repository to your local machine:
   ```bash
    git clone https://github.com/DvirUliel/Animal_Restful_API.git
2. Open the project in **Android Studio**.
3. Sync the project with Gradle files by selecting **File > Sync Project with Gradle Files**.
4. Run the project on an emulator or a physical device.
