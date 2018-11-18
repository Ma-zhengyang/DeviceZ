package com.android.mazhengyang.device_z.fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mazhengyang.device_z.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class ToolTorchFragment extends BaseFragment {

    private static final String TAG = ToolTorchFragment.class.getSimpleName();

    private Camera camera;
    private Camera.Parameters parameters;

    private boolean isOn = false;

    @BindView(R.id.iv_flash)
    ImageView ivFlash;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_torch, null);
        ButterKnife.bind(this, view);

        if (camera == null) {
            camera = openCamera();//整个application退出后会重新执行，open时会强行关闭led
        }
        if (parameters == null) {
            if (camera != null) {
                parameters = camera.getParameters();
            }
        }

        Log.d(TAG, "onCreateView: camera=" + camera);
        Log.d(TAG, "onCreateView: parameters=" + parameters);

        if (parameters != null) {
            String currentMode = parameters.getFlashMode();
            Log.d(TAG, "onCreateView: currentMode=" + currentMode);
            if (Camera.Parameters.FLASH_MODE_TORCH.equals(currentMode)) {
                ivFlash.setImageResource(R.mipmap.flashlight_on);
                isOn = true;
            } else {
                ivFlash.setImageResource(R.mipmap.flashlight_off);
                isOn = false;
            }
        }

        ivFlash.setOnClickListener(flash);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private Camera openCamera() {

        int num = Camera.getNumberOfCameras();
        if (num <= 0) {
            return null;
        }

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < num; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    Camera camera = Camera.open(cameraInfo.facing);
                    return camera;
                } catch (Throwable e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "openCamera: " + e);
                    return null;
                }
            }
        }

        return null;
    }

    private boolean controlLed(boolean on) {
        if (camera == null) {
            camera = openCamera();
        }
        if (camera == null) {
            return false;
        }

        if (parameters == null) {
            parameters = camera.getParameters();
        }
        if (parameters == null) {
            return false;
        }

        List<String> supportedModes = parameters.getSupportedFlashModes();
        if (supportedModes != null) {
            if (!supportedModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                return false;
            } else {
                try {
                    if (on) {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    } else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    camera.setParameters(parameters);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "controlLed: " + e);
                    return false;
                }
            }
        } else {
            Log.e(TAG, "controlLed: supportedModes is null.");
        }
        return false;
    }


    private View.OnClickListener flash = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isOn) {
                if (controlLed(false)) {
                    ivFlash.setImageResource(R.mipmap.flashlight_off);
                    isOn = false;
                }
            } else {
                if (controlLed(true)) {
                    ivFlash.setImageResource(R.mipmap.flashlight_on);
                    isOn = true;
                }
            }
        }
    };

    @Override
    public boolean isRunning() {
        return false;
    }

}
