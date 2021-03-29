# Books App
The Books App makes it possible to store informations about your books. The purpose of this app is to demonstrate the use of **Android Jetpack** components to simplify app development.

## Android Jetpack
More information on Andorid Jetpack can be found in:
[Android Jetpack](https://developer.android.com/jetpack)

## Module 01 Step 02: Improved: How to not do it with DataBindings!
What did we improve?
- We added a basic structure.
- We implemented basic data bindings.
- We removed findById boilerplate code.

## DataBindings documentation
[Data Bindings](https://developer.android.com/topic/libraries/data-binding)

## Problems with our implementation
- Every thing is in the MainActivity UI code and logic is mixed.
- The android lifecycle is not respected. If we turn the device all data is gone.