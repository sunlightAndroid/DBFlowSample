package me.sunlight.dbflow.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2018/01/10
 *     desc   :
 *     version:
 * </pre>
 */

@Database(name = AppDataBase.name, version = AppDataBase.version)
public class AppDataBase {

    public static final String name = "AppDataBase";

    public static final int version = 1;
}
