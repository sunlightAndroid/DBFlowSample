package me.sunlight.dbflow.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import me.sunlight.dbflow.db.AppDataBase;

/**
 * <pre>
 *     author : 戈传光
 *     e-mail : 1944633835@qq.com
 *     time   : 2018/01/10
 *     desc   :
 *     version:
 * </pre>
 */
@Table(database = AppDataBase.class)
public class User extends BaseModel {

    //  主键
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

    @Column
    public int age;


    public  void insertData(String name,int age){

        this.name=name;
        this.age=age;
    }
}
