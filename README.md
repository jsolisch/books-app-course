# Books App
The Books App makes it possible to store informations about your books. The purpose of this app is to demonstrate the use of **Android Jetpack** components to simplify app development.

## Android Jetpack
More information on Andorid Jetpack can be found in:
[Android Jetpack](https://developer.android.com/jetpack)

## Module 01 Step 03: UI and logic separation through a ViewModel 
What did we improve?
- We added a ViewModel
- We separated UI code from logic code
- We now respect the android life cycle 

## ModelView documentation
[Model View](https://developer.android.com/topic/libraries/architecture/viewmodel)
[Life Data](https://developer.android.com/topic/libraries/architecture/livedata)
[Data Bindings](https://developer.android.com/topic/libraries/data-binding)

## Import to note
**Never use UI view in your ViewModel! Since the goal is to separate UI code and logic code!**
**It is also highly likely that you will leak memory!!!** 

## Problems with our implementation
- Our data dose not persist after application restarts.