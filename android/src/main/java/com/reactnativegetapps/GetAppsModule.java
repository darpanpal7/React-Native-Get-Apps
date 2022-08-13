package com.reactnativegetapps;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.helper.Utility;

import java.io.File;
import java.util.List;

@ReactModule(name = GetAppsModule.NAME)
public class GetAppsModule extends ReactContextBaseJavaModule {
    public static final String NAME = "GetApps";
    private final ReactApplicationContext reactContext;

    public GetAppsModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getUpiApps(Promise promise) {
      try {
        PackageManager pm = this.reactContext.getPackageManager();
        Uri uri = Uri.parse("upi://pay");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        List<ResolveInfo> pList = pm.queryIntentActivities(intent, 0);
        WritableArray list = Arguments.createArray();
        for(int i = 0; i < pList.size(); i += 1) {
          ResolveInfo packageInfo = pList.get(i);
          WritableMap appInfo = Arguments.createMap();
          appInfo.putString("packageName", packageInfo.activityInfo.packageName);
          list.pushMap(appInfo);
        }
        promise.resolve(list);
      } catch (Exception ex) {
        promise.reject(ex);
      }
    }

  @ReactMethod
  public void getApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableArray list = Arguments.createArray();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        WritableMap appInfo = Arguments.createMap();
        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
          appInfo.putString("packageName", packageInfo.packageName);
          list.pushMap(appInfo);
        }
      }
      promise.resolve(list);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }

  @ReactMethod
  public void getAllApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableArray list = Arguments.createArray();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        WritableMap appInfo = Arguments.createMap();
          appInfo.putString("packageName", packageInfo.packageName);
          list.pushMap(appInfo);
      }
      promise.resolve(list);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }



  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
      promise.resolve(a * b);
  }

}
