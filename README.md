# Holidays Android App - Proof-of-Value Project

The Holidays Android app is a proof-of-value project that allows users to perform various operations on a dataset of different countries' public holidays. The app follows the Clean-MVVM architecture with a Single Activity approach. It consists of two fragments - one displaying a list of countries and another where users can choose and perform operations on a pair of selected countries.

## Features

- Clean-MVVM architecture
- Single Activity approach
- Fragments: 
  1. Country List
  2. Operation Selection
- UI written in XML (with plans to migrate to Compose if time permits)
- No database (with plans to implement Room database if time permits)

## Dependencies

The app uses the following key dependencies:

- Hilt for dependency injection
- Coroutines for asynchronous operations
- java.time for date/time handling
- Moshi for JSON serialization

## Progress Estimate

The development process is divided into five days, with each day targeting specific tasks:

### Day 1
- Set up project structure and architecture
- Define dependencies and implement dependency injection with Hilt
- Create base classes and functionality
- Design and implement a simple XML-based UI

### Day 2
- Implement business rules and logic
- Create ViewModel and connect it to the UI

### Day 3
- Finalize XML-based UI and connect it to the underlying layers
- Implement core functionality for performing operations on holidays

### Day 4
- If Day 3 tasks are complete, migrate UI to Compose
- Otherwise, fill any remaining gaps in the functionality

### Day 5
- If both base functionality and Compose UI are complete, add Room database integration
- Otherwise, address any remaining gaps in the implementation

## Getting Started

To run the Holidays app locally, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or a physical device.
