package me.sunlight.dbflow;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2018/01/10
 *     desc   :
 *     version:
 * </pre>
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
