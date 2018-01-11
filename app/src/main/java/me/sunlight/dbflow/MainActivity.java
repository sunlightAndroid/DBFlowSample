package me.sunlight.dbflow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.sunlight.dbflow.db.model.User;
import me.sunlight.dbflow.db.model.User_Table;
import me.sunlight.dbflow.recycler.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_age)
    EditText etAge;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerView;

    private RecyclerAdapter<User> mAdapter;

    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter<User>() {
            @Override
            protected int getItemViewType(int position, User user) {
                return R.layout.item_users_recycler_list;
            }

            @Override
            protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
                return new ItemHolder(root);
            }
        });

        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, User user) {
                super.onItemLongClick(holder, user);

                //  删除某条记录
               SQLite.delete()
                        .from(User.class)
                        .where(User_Table.id.eq(user.id))
                        .query();

                mAdapter.delete(holder.getAdapterPosition());

            }

            @Override
            public void onItemClick(final RecyclerAdapter.ViewHolder holder, final User user) {
                super.onItemClick(holder, user);
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog,null);
                final EditText mDialogEtName = view.findViewById(R.id.dialog_et_name);
                final EditText mDialogEtAge = view.findViewById(R.id.dialog_et_age);

                mDialogEtName.setText(user.name);
                mDialogEtAge.setText(user.age+"");


                // 修改某条记录
                AlertDialog dialog = new AlertDialog.Builder(mContext)

                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String upadteName=mDialogEtName.getText().toString();

                                int updateAge = Integer.parseInt(mDialogEtAge.getText().toString());

                                SQLite.update(User.class).set(User_Table.name.eq(upadteName),User_Table.age.eq(updateAge))
                                        .where(User_Table.id.eq(user.id))
                                        .query();


                                mAdapter.update(SQLite.select()
                                                .from(User.class)
                                                .where(User_Table.id.eq(user.id))
                                                .querySingle()
                                        ,holder);
                            }
                        })
                        .create();

                dialog.setView(view);
                dialog.show();
            }
        });
    }

    class  ItemHolder extends RecyclerAdapter.ViewHolder<User>{
        @BindView(R.id.item_tv_name)
        TextView name;

        @BindView(R.id.item_tv_age)
        TextView age;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User user) {
            name.setText(user.name);
            age.setText(user.age+"");
        }
    }


    public void save(View view){
        // 保存一条记录

        String name =etName.getText().toString();
        String age = etAge.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "name 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "age 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.insertData(name,Integer.parseInt(age));

        boolean save = user.save();

        if(save){
            etName.setText("");
            etAge.setText("");
        }

        Toast.makeText(this, save?"保存成功":"保存失败", Toast.LENGTH_SHORT).show();
    }

    public void show(View view){

        //  查询所以的记录
        List<User> users = SQLite.select()
                .from(User.class)
                .queryList();

        mAdapter.clear();
        mAdapter.add(users);
    }
}
