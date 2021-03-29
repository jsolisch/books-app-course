# Books App
The Books App makes it possible to store informations about your books. The purpose of this app is to demonstrate the use of **Android Jetpack** components to simplify app development.

## Android Jetpack
More information on Andorid Jetpack can be found in:
[Android Jetpack](https://developer.android.com/jetpack)

## Module 01 Step 01: How to not do it!
In this step we will implement a basic app that allows us to add a book title and author to a list. **This implementation will be just wrong!**.

## Important to know
[Android lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)

## Problems with our implementation
- Every thing is in the MainActivity UI code and logic is mixed.
- We have a lot of boilerplate code (findViewByID)
- The android lifecycle is not respected. If we turn the device all data is gone.