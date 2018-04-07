# AndroidCodingChallenge

## Description: 
This application connects to StackOverflow Users API and retrieves the first page of data.

It displays that data in a RecyclerView with each user's username, badges and gravatar being displayed in a CardView

While the gravatar is being retrieved, the UI displays a loading animation over the ImageView

Each gravatar is stored locally in an SQLite database once downloaded

All data fetching is handled by background threads so the UI should always be responsive

## Architecture:

This application adheres to the MVP design pattern to deliver elegant, clean, maintainable and testable code
#### Model 
  RemoteDataSource - Retrieves users and downloads images 
  
  LocalDataSource - stores images in SQLite database for retrieval 

#### View
  MainView - the mainView to be displayed, displays errorMessages
  
  UserAdapter/UserRowView - handles displaying data in RecyclerView and CardView 

#### Presenter
  MainPresenter - handles calling RemoteDataSource and LocalDataSource on background threads, also pushes User data to the Adapter and error messages to the MainView
  
 User - struct-like object for passing data from model to view 
 
 
 ## Third Party Libraries:
 
 Gson - Used to parse JSON data fetched in RemoteDataSource without using Android libraries allowing for Unit testing on JVM
