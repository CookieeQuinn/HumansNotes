package com.jnu.lxq.fragment_3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.lxq.dataprocessor.PayDataBank;
import com.jnu.lxq.dataprocessor.PayUpdateActivity;
import com.jnu.lxq.R;
import com.jnu.lxq.dataprocessor.Pay;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} fac
 * tory method to
 * create an instance of this fragment.
 */

public class PayFragment extends Fragment  {
    private static final int CONTEXT_MENU_ITEM_NEW = 1;
    private static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_NEW + 1;
    private static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_APPEND+1;
    private static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    private static final int REQUEST_CODE_ADD_CAT = 100;
    private static final int REQUEST_CODE_UPDATE_CAT = 101;

    private PayDataBank paydata;
    private PayAdapter adapter;
    private ListView listViewPay;

    public PayFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pay, container, false);
        paydata = new PayDataBank(this.getContext());
        lazyLoad(view);
//        Toast.makeText(getContext(),"PAY",Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
            lazyLoad(this.getView());
//            Toast.makeText(getContext(),"PAYRESUNE",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Toast.makeText(getContext(),"onDestroyView-P",Toast.LENGTH_SHORT).show();
    }
    private void initData() {
        paydata.Load();
        if(0==paydata.getPays().size())
        {
            paydata.getPays().add(new Pay("张三","300","2020年11月11日"));
            paydata.Save();
        }
    }
    private void initView(View view) {
        adapter = new PayAdapter(view.getContext(), R.layout.pay_record,  paydata.getPays());
        listViewPay = view.findViewById(R.id.fragment_pay_list);
        listViewPay.setAdapter(adapter);
        this.registerForContextMenu(listViewPay);
    }


    private View lazyLoad(View view) {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调
        // 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
            initData();
            initView(view);
//            Toast.makeText(getContext(),"LAZY-P",Toast.LENGTH_SHORT).show();
            //数据加载完毕,恢复标记,防止重复加载
        return  view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v== Objects.requireNonNull(this.getActivity()).findViewById(R.id.fragment_pay_list)) {
            menu.setHeaderTitle("操作");
            menu.add(1, CONTEXT_MENU_ITEM_NEW, 1, "新增");
            menu.add(1, CONTEXT_MENU_ITEM_APPEND, 1, " 追加");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent;
        final int position=menuInfo.position;
        switch(item.getItemId())
        {
            case CONTEXT_MENU_ITEM_NEW:
                intent = new Intent(this.getContext(), PayUpdateActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
                break;
            case CONTEXT_MENU_ITEM_APPEND:
                intent = new Intent(this.getContext(), PayUpdateActivity.class);
                intent.putExtra("position",position+1);
                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
                break;
            case CONTEXT_MENU_ITEM_UPDATE:
                intent = new Intent(this.getContext(), PayUpdateActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("name",paydata.getPays().get(position).getName() );
                intent.putExtra("money",paydata.getPays().get(position).getMoney() );
                intent.putExtra("date",paydata.getPays().get(position).getDate());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_CAT);
                break;
            case CONTEXT_MENU_ITEM_DELETE:

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("询问");
                builder.setMessage("你确定要删除\""+paydata.getPays().get(position).getName() + "\"？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        paydata.getPays().remove(position);
                        paydata.Save();
                        adapter.notifyDataSetChanged();
                    }
                });  //正面的按钮（肯定）
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }); //反面的按钮（否定)
                builder.create().show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    switch (requestCode) {

        default:case REQUEST_CODE_ADD_CAT:
            if (resultCode == RESULT_OK) {
                assert data != null;
                int position = data.getIntExtra("position",0);
                String pay_name = data.getStringExtra("name");
                String pay_money = data.getStringExtra("money");
                String pay_date = data.getStringExtra("date");
                paydata.getPays().add(position,new Pay(pay_name,pay_money ,pay_date));
                paydata.Save();
                adapter.notifyDataSetChanged();
            }
            break;

        case REQUEST_CODE_UPDATE_CAT:
            if (resultCode == RESULT_OK) {
                assert data != null;
                int position=data.getIntExtra("position",0);
                String pay_name = data.getStringExtra("name");
                String pay_money = data.getStringExtra("money");
                String pay_date = data.getStringExtra("date");

                paydata.getPays().get(position).setName(pay_name);
                paydata.getPays().get(position).setMoney(pay_money);
                paydata.getPays().get(position).setDate(pay_date);
                paydata.Save();
                adapter.notifyDataSetChanged();
            }
            break;



    }
    super.onActivityResult(requestCode, resultCode, data);
}

    public static class PayAdapter extends ArrayAdapter<Pay> {
        private final int resourceId;
        public PayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Pay> objects) {
            super(context, resource,objects);
            this.resourceId=resource;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Pay pay = getItem(position);//获取当前项的实例
            View view;
            if(null==convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view=convertView;
            ((TextView) view.findViewById(R.id.textView_pay_person)).setText(pay.getName());
            ((TextView) view.findViewById(R.id.textView_pay_money)).setText(pay.getMoney());
            ((TextView) view.findViewById(R.id.textView_pay_date)).setText(pay.getDate());

            return view;
        }
    }
}