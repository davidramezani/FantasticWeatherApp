package com.david.fantasticweatherapp.ui.locationdialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.databinding.LocationBottomDialogBinding;
import com.david.fantasticweatherapp.ui.main.MainActivity;
import com.david.fantasticweatherapp.ui.main.MainViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LocationBottomDialog extends BottomSheetDialogFragment {

    private LocationBottomDialogBinding mBinding;
    private LocationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.MyBottomDialogStyle);
        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding =LocationBottomDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        viewModel.getLocation();
        observeLocation();
    }

    private void initUI() {

        mBinding.btnSaveLocation.setOnClickListener(v -> {
            viewModel.updateLocation(viewModel.defaultLocation);
            dismiss();
        });

        mBinding.etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.defaultLocation.cityName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mBinding.etLat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.defaultLocation.lat = Double.valueOf(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mBinding.etLon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.defaultLocation.lon = Double.valueOf(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void observeLocation() {
        viewModel.getLiveLocationData().observe(getViewLifecycleOwner(), locationData -> {
            mBinding.etCity.setText(locationData.cityName);
            mBinding.etLat.setText(String.valueOf(locationData.lat));
            mBinding.etLon.setText(String.valueOf(locationData.lon));
        });
    }
}
