# mirrAR-makeup-android-SDK

### How to import mirrAR-makeup android SDK
Step 1: copy makeup-codebase-release.aar and makeup-engine-release.aar into `app/libs` folder

Step 2: add dependencies in `app gradle`

```
  dependencies {
	...
	...
	...
	implementation 'com.google.code.gson:gson:2.8.9'
    	implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    	implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    	implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2'
    	implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'
    	implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
    	implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
		
	implementation files('libs/makeup-codebase-release.aar')
	implementation files('libs/makeup-engine-release.aar')
	}
  ```
  <sub>*Clean Build after all the above steps.*</sub>
  
  
  Step 3: Enable view binding by copying mentioned code in `app gradle`
  
  ```
  buildFeatures {
   viewBinding true
  }
  ```
  
  Step 4: Add the following permissions/feature declaration in the manifest file

```
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.INTERNET"/>
<uses-feature android:name="android.hardware.camera" />
```
<sub>*At this point, the app environment is all set to use MirrAR makeup SDK :)*</sub>



### Initializing makeup SDK and calling functions

Step 1. Check camera permission 

MirrAR SDK needs a camera feed to work, that's why check camera permission before initialising the SDK


Copy this command to check whether the camera permission is allowed or not
```
ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
```


Override `onRequestPermissionsResult` method to get the response from the user for permission check
```
override fun onRequestPermissionsResult(
   requestCode: Int,
   permissions: Array<out String>,
   grantResults: IntArray
) {
   super.onRequestPermissionsResult(requestCode, permissions, grantResults)

   var allowed = true
   for (i in permissions.indices) {
       if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
           allowed = false
           break
       }
   }
}
```

Step 2. Initializing SDK

(i) Assign Your brand ID on a static variable called `BRAND_ID`, for api calls
 ```
 AppConstraints.BRAND_ID = "BRAND_ID_BRAND_ID_BRAND_ID"
 ```

(ii) Past this piece of code on activity class after checking the camera permission
```
makeUpFragment = MakeUpFragment(this)
supportFragmentManager.beginTransaction()
                      .replace(R.id.container, makeUpFragment)
                      .commit()
```

(iii) Add `FrameLayout` into xml layout file
```
<FrameLayout
   android:id="@+id/container"
   android:layout_width="match_parent"
   android:layout_height="match_parent"/>
```

Step 3. (i) Apply makeup effect

You can call the makeUpFragment.applyMakeupEffect() method to apply the makeup.
```
  var sku = "LIP_COLOR_001"
  var category = "lipstick"
  makeUpFragment.applyMakeupEffect(category, sku)
```

(ii) Remove makeup effect

You can call the makeUpFragment.removeMakeupEffect() method to remove the applied makeup.
```
  var effect = Effect()
  makeUpFragment.removeMakeupEffect(effect)
```

(iii) Enable/Disable compare makeup filter
```
makeUpFragment.isNeededCompareTool(b: Boolean)
```

(iv) Apply makeup filter on static image
```
makeUpFragment.switchToGallery(bitmap: Bitmap)
```
*If you are on gallery mode and want to switch back to camera mode, just call `makeUpFragment.switchToLiveCamera()` to get back on the camera feed.*


Step 4. Callback 

Implement the `IMakeupCallback` interface on `MakeUpFragmet` parent activity and override all of their functions
```
 override fun OnClear() {

    }

    override fun OnEffectCreated(effect: Effect) {
        effectMap[effect.GetName()] = effect
    }

    override fun OnEffectDisplayed(effect: Effect) {

    }

    override fun OnEffectHidden(effect: Effect) {

    }

    override fun OnEffectRemoved(effect: Effect) {
        effectMap.remove(effect.GetName())
    }

    override fun OnEffectUpdated(effect: Effect) {

    }

    override fun OnStart() {

    }
```


<sub>** Awesome 🥳 you have successfully integrated the SDK</sub>

