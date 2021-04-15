# Books App
The Books App makes it possible to store informations about your books. The purpose of this app is to demonstrate the use of **Android Jetpack** components to simplify app development.

## Android Jetpack
More information on Andorid Jetpack can be found in:
[Android Jetpack](https://developer.android.com/jetpack)

## Module 04 Step 01: Using a REST API
What did we improve?
- We can now get books from a server over REST.

## Retrofit documentation
[Retrofit](https://square.github.io/retrofit/)

## Problems with this implementation
- Network code and DB code is cluttering the View Model
- We always request every thing from the server
- On every request we add the same books again

## What to do next?
- Fix our problems with a better structure and API design