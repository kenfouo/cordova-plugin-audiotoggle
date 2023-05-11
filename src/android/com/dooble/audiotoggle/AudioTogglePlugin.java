package com.dooble.audiotoggle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

public class AudioTogglePlugin extends CordovaPlugin {
	public static final String ACTION_SET_AUDIO_MODE = "setAudioMode";
	public static final String IS_HEAD_SET_ENABLED = "isHeadsetEnabled";
	public static final String SET_ALL_APP_FILLES_SETTING = "switchToAllAppFillesSettings";

	@Override
	public boolean execute(String action, JSONArray args,
						   CallbackContext callbackContext) throws JSONException {
		if (action.equals(ACTION_SET_AUDIO_MODE)) {
			if (!setAudioMode(args.getString(0))) {
				callbackContext.error("Invalid audio mode");
				return false;
			}
			return true;
		}

		if (action.equals(IS_HEAD_SET_ENABLED)) {

			Boolean bool =  isHeadsetEnabled();
			if (bool)
				callbackContext.success("headsetAdded");

			if (!bool)
				callbackContext.success("headsetRemoved");

			return true;
		}

		if (action.equals(SET_ALL_APP_FILLES_SETTING)) {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

				if (!Environment.isExternalStorageManager()) {
					//switchToAllAppFillesSettings();

					//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
						//ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1); //permission request code is just an int
					//} else {
						//ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); //permisison request code is just an int
					//}

					callbackContext.success("false");
				}
				else
				{
					callbackContext.success("true");
				}
				return true;
			}
			else{
				callbackContext.success("true");
			}
			
			return true;
		}


		callbackContext.error("Invalid action");
		return false;
	}

	public boolean setAudioMode(String mode) {
		Context context = webView.getContext();
		AudioManager audioManager =
				(AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

		if (mode.equals("earpiece")) {
			audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
			audioManager.setSpeakerphoneOn(false);
			return true;
		} else if (mode.equals("speaker")) {
			audioManager.setMode(AudioManager.STREAM_MUSIC);
			audioManager.setSpeakerphoneOn(true);
			return true;
		} else if (mode.equals("ringtone")) {
			audioManager.setMode(AudioManager.MODE_RINGTONE);
			audioManager.setSpeakerphoneOn(true);
			return true;
		} else if (mode.equals("normal")) {
			audioManager.setMode(AudioManager.MODE_NORMAL);
			audioManager.setSpeakerphoneOn(false);
			return true;
		}

		return false;
	}

	private boolean isHeadsetEnabled() {
		final AudioManager audioManager = (AudioManager) cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);
		return audioManager.isWiredHeadsetOn() ||
			audioManager.isBluetoothA2dpOn() ||
			audioManager.isBluetoothScoOn();
	}

	public void switchToAllAppFillesSettings() {

        //logDebug("Switch to wireless Settings");
        //Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        //cordova.getActivity().startActivity(settingsIntent);

/*         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			
			try {
				Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
				Uri.parse("package:" + BuildConfig.APPLICATION_ID));

				cordova.getActivity().startActivity(settingsIntent);
			} catch (Exception ex) {

				Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				cordova.getActivity().startActivity(intent);
			}
        } */
    }

}