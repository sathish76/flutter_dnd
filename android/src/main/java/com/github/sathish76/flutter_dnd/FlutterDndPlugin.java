package com.github.sathish76.flutter_dnd;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.provider.Settings;

/**
 * FlutterDndPlugin
 */

public class FlutterDndPlugin implements FlutterPlugin, MethodCallHandler {

    private static NotificationManager notificationManager;
    private static Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(),
                "flutter_dnd");
        channel.setMethodCallHandler(new FlutterDndPlugin());
    }

    // This static function is optional and equivalent to onAttachedToEngine. It
    // supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new
    // Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith
    // to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith
    // will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both
    // be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_dnd");
        channel.setMethodCallHandler(new FlutterDndPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (!isAboveMarshmello()) {
            result.error("ERROR: INCOMPATIBLE_ANDROID_VERSION", "This methods required android version above 23", null);
            return;
        }
        switch (call.method) {
            case "isNotificationPolicyAccessGranted":
                result.success(isNotificationPolicyAccessGranted());
                break;
            case "gotoPolicySettings":
                gotoPolicySettings();
                result.success(null);
                break;
            case "setInterruptionFilter":
                int interruptionFilter = call.arguments();
                result.success(setInterruptionFilter(interruptionFilter));
                break;
            case "getCurrentInterruptionFilter":
                result.success(getCurrentInterruptionFilter());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private boolean isAboveMarshmello() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M;
    }

    private boolean isNotificationPolicyAccessGranted() {
        return notificationManager.isNotificationPolicyAccessGranted();
    }

    private void gotoPolicySettings() {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean setInterruptionFilter(int interruptionFilter) {
        if (notificationManager.isNotificationPolicyAccessGranted()) {
            notificationManager.setInterruptionFilter(interruptionFilter);
            return true;
        }
        return false;
    }

    private int getCurrentInterruptionFilter() {
        return notificationManager.getCurrentInterruptionFilter();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

}
