package com.apisense.bee.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.apisense.bee.R;
import com.apisense.bee.backend.AsyncTasksCallbacks;
import com.apisense.bee.backend.experiment.RetrieveInstalledExperimentsTask;
import fr.inria.bsense.APISENSE;
import fr.inria.bsense.appmodel.Experiment;
import fr.inria.bsense.service.BeeSenseServiceManager;
import org.json.JSONException;

import java.util.List;

public class AvailableExperimentsListAdapter extends ArrayAdapter<Experiment> {
    private final String TAG = getClass().getSimpleName();
    private List<Experiment> data;

    /**
     * Constructor
     *
     * @param context
     * @param layoutResourceId
     */
    public AvailableExperimentsListAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
    }

    /**
     * Constructor
     *
     * @param context
     * @param layoutResourceId
     * @param experiments
     *            list of experiments
     */
    public AvailableExperimentsListAdapter(Context context, int layoutResourceId, List<Experiment> experiments) {
        super(context, layoutResourceId, experiments);
        this.setDataSet(experiments);
    }

    /**
     * Change the dataSet of the adapter
     *
     * @param dataSet
     */
    public void setDataSet(List<Experiment> dataSet){
        this.data = dataSet;
    }

    /**
     * Get the size of experiment list
     *
     * @return the size of experiment list
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Get an experiment from position in the ListView
     *
     * @param position
     *            position in the ListView
     * @return an experiment
     */
    @Override
    public Experiment getItem(int position) {
        return data.get(position);
    }

    /**
     * Get the experiment ID
     *
     * @param position
     *            position in the ListView
     * @return the experiment ID
     */
    @Override
    public long getItemId(int position) {
        return Long.valueOf(getItem(position).id);
    }

    /**
     * Prepare view with data.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_experimentelement, null);

        Experiment item = getItem(position);

        Log.v(TAG, "View asked (as a listItem)for Experiment: " + item);

        TextView title = (TextView) convertView.findViewById(R.id.experimentelement_sampletitle);
        title.setText(item.niceName);
        title.setTypeface(null, Typeface.BOLD);

        TextView company = (TextView) convertView.findViewById(R.id.experimentelement_company);
        company.setText(" by " + item.organization);

        TextView description = (TextView) convertView.findViewById(R.id.experimentelement_short_desc);
        description.setText(item.description);

        if (isSubscribedExperiment(item)){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.orange_light));
        }
        return convertView;
    }

    private boolean isSubscribedExperiment(Experiment exp) {
        // TODO: Improve this (At least with an asynchTask)
        // At the moment => if the experiment is installed, then the user subscribed to it
        Experiment currentExperiment;
        boolean result = false;
        try {
            currentExperiment = APISENSE.apisense().getBSenseMobileService().getExperiment(exp.name);
            if (currentExperiment != null) {
                result = true;
            }
        } catch (Exception e) {
            Log.e(TAG, "An error occured while looking for experiment (" + exp.name + "): " + e.getMessage());
        }
        return result;
    }
}