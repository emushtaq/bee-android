package com.apisense.bee.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apisense.bee.BeeApplication;
import com.apisense.bee.R;
import com.apisense.bee.callbacks.OnCropStarted;
import com.apisense.bee.utils.CropPermissionHandler;
import com.apisense.bee.utils.SensorsDrawer;
import com.apisense.sdk.APISENSE;
import com.apisense.sdk.core.preferences.Sensor;
import com.apisense.sdk.core.store.Crop;

import java.util.Set;

import butterknife.BindView;
import butterknife.Unbinder;

public class CommonDetailsFragment extends BaseFragment {

    @BindView(R.id.crop_detail_title) TextView nameView;
    @BindView(R.id.crop_detail_owner_and_version) TextView organizationView;
    @BindView(R.id.crop_detail_description) TextView descriptionView;
    @BindView(R.id.crop_sensors_detail_container) LinearLayout stingGridView;

    protected Crop crop;
    protected APISENSE.Sdk apisenseSdk;
    protected Unbinder unbinder;
    protected CropPermissionHandler cropPermissionHandler;

    private Set<Sensor> mAvailableSensors;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_common_details, container, false);
        setHasOptionsMenu(true);

        apisenseSdk = ((BeeApplication) getActivity().getApplication()).getSdk();
        mAvailableSensors = apisenseSdk.getPreferencesManager().retrieveAvailableSensors();

        retrieveBundle();
        cropPermissionHandler = prepareCropPermissionHandler();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayExperimentInformation();
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        cropPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Private methods

    protected CropPermissionHandler prepareCropPermissionHandler() {
        return new CropPermissionHandler(getActivity(), crop, new OnCropStarted(getActivity()));
    }

    private void retrieveBundle() {
        Bundle bundle = this.getArguments();
        crop = bundle.getParcelable("crop");
    }

    private void displayExperimentInformation() {
        nameView.setText(getString(R.string.exp_details_name, crop.getName()));
        organizationView.setText(getString(R.string.exp_details_organization, crop.getOwner()) +
                " - " + getString(R.string.exp_details_version, crop.getVersion()));
        descriptionView.setText(crop.getShortDescription());

        SensorsDrawer.draw(getContext(), mAvailableSensors, crop.getUsedStings(), stingGridView);
    }
}
