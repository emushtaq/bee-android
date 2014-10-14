package com.apisense.bee;

import fr.inria.apisense.view.notification.NotificationActivity;
import fr.inria.asl.android.facade.*;
import fr.inria.asl.android.facade.system.ApplicationFacade;
import fr.inria.asl.android.facade.system.LogCatFacade;
import fr.inria.asl.android.facade.wv.WebViewFacade;
import fr.inria.asl.android.sensor.vs.PlaceFacade;
import fr.inria.asl.facade.NavizonFacade;
import fr.inria.asl.facade.OpenCellIdFacade;
import fr.inria.asl.rhino.RhinoEngineDescriptor;
import fr.inria.bsense.APISENSE;
import fr.inria.bsense.BeeSenseApplication;
import fr.inria.bsense.facade.SQLDatabaseFacade;
import fr.inria.bsense.facade.SQLMemoryDatabaseFacade;
import fr.inria.bsense.facade.TrackFacade;
import fr.inria.bsense.facade.survey.SurveyFacade;
import fr.inria.bsense.facade.view.ViewFacade;
import fr.inria.bsense.service.NotificationService;

public class BeeApplication extends BeeSenseApplication {
    // Constants

    // Urls
    public static final String BEE_DEFAULT_URL = "http://beta.apisense.io/hive";

    // Asynchronous Tasks Return values
    public static final int ASYNC_SUCCESS = 0;
    public static final int ASYNC_ERROR = -1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void OnBsenseServiceConnected() {
        try {
            // Install interpreter: Javascript
            APISENSE.apisMobileService().bsInstallInterpreter(RhinoEngineDescriptor.class);

            // Register facade
            APISENSE.apisMobileService().registerPrivacyFacade("Wifi", "Collect WIFI info", String.valueOf(R.drawable.ic_sensor_wifi_on), String.valueOf(R.drawable.ic_sensor_wifi_off), WifiFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Batterie", "Collect battery state", String.valueOf(R.drawable.ic_sensor_battery_on), String.valueOf(R.drawable.ic_sensor_battery_off), BatteryFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Localisation", "Collect position", String.valueOf(R.drawable.ic_sensor_geoloc_on), String.valueOf(R.drawable.ic_sensor_geoloc_off), LocationFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Bluetooth", "Collect bluetooth info", String.valueOf(R.drawable.ic_sensor_bluetooth_on), String.valueOf(R.drawable.ic_sensor_bluetooth_off), BluetoothFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Réseau", "Collect telephony network info", String.valueOf(R.drawable.ic_sensor_network_on), String.valueOf(R.drawable.ic_sensor_network_off), TelephonyFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Capteur", "Collect sensor info", String.valueOf(R.drawable.ic_sensor_on), String.valueOf(R.drawable.ic_sensor_off), SensorFacade.class);
            APISENSE.apisMobileService().registerPrivacyFacade("Bruit sonore","Collect noise info", String.valueOf(R.drawable.ic_sensor_on), String.valueOf(R.drawable.ic_sensor_off), SoundFacade.class);

            APISENSE.apisMobileService().registerFacade(WebViewFacade.class);
            APISENSE.apisMobileService().registerFacade(ViewFacade.class);
            APISENSE.apisMobileService().registerFacade(NetworkFacade.class);
            APISENSE.apisMobileService().registerFacade(LatenceFacade.class);
            APISENSE.apisMobileService().registerFacade(EventFacade.class);
            APISENSE.apisMobileService().registerFacade(LogFacade.class);
            APISENSE.apisMobileService().registerFacade(TrackFacade.class);
            APISENSE.apisMobileService().registerFacade(SurveyFacade.class);
            APISENSE.apisMobileService().registerFacade(PlaceFacade.class);
            APISENSE.apisMobileService().registerFacade(AndroidFacade.class);
            APISENSE.apisMobileService().registerFacade(SQLDatabaseFacade.class);
            APISENSE.apisMobileService().registerFacade(SQLMemoryDatabaseFacade.class);
            APISENSE.apisMobileService().registerFacade(ApplicationFacade.class);
            APISENSE.apisMobileService().registerFacade(LogCatFacade.class);
            APISENSE.apisMobileService().registerFacade(OpenCellIdFacade.class);
            APISENSE.apisMobileService().registerFacade(NavizonFacade.class);
            APISENSE.apisMobileService().registerFacade(SoundFacade.class);
//            APISENSE.apisMobileService().registerFacade(FeedzFacade.class);

            NotificationService.NOTIFICATION_ACTIVITY = NotificationActivity.class;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
