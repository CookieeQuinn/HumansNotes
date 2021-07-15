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

import com.jnu.lxq.R;

import com.jnu.lxq.dataprocessor.ReasonDataBank;
import com.jnu.lxq.dataprocessor.Receive;
import com.jnu.lxq.dataprocessor.ReceiveDataBank;
import com.jnu.lxq.dataprocessor.ReceiveUpdateActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ReceiveFragment extends Fragment {
    private static final int CONTEXT_MENU_ITEM_NEW = 1;
    private static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_NEW + 1;
    private static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_APPEND+1;
    private static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    private static final int REQUEST_CODE_ADD_CAT = 100;
    private static final int REQUEST_CODE_UPDATE_CAT = 101;

    private ReceiveDataBank receivedata;
    private ReceiveAdapter adapter;
    private ListView listViewReceive;

    private ReasonDataBank reasonDataBank;
    private ArrayList<String> list;

    public ReceiveFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReceiveFragment newInstance(String param1, String param2) {
        ReceiveFragment fragment = new ReceiveFragment();
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
        View view=inflater.inflate(R.layout.fragment_receive, container, false);
        receivedata = new ReceiveDataBank(this.getContext());
        reasonDataBank = new ReasonDataBank(this.getContext());
        lazyLoad(view);
//        Toast.makeText(getContext(),"RECEIVE",Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
            lazyLoad(this.getView());
//            Toast.makeText(getContext(),"RECEIVERESUNE",Toast.LENGTH_SHORT).show();
    }

    private View lazyLoad(View view) {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调
        // 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        initData();
        initView(view);
//            Toast.makeText(getContext(),"LAZY-R",Toast.LENGTH_SHORT).show();
        return  view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Toast.makeText(getContext(),"onDestroyView-R",Toast.LENGTH_SHORT).show();
    }
    private void initView(View view) {
        adapter = new ReceiveAdapter(view.getContext(), R.layout.receive_record,  receivedata.getReceive());
        listViewReceive=view.findViewById(R.id.fragment_receive_list);
        listViewReceive.setAdapter(adapter);
        this.registerForContextMenu(listViewReceive);
    }

    private void initData() {
        reasonDataBank.Load();
        list = reasonDataBank.getReasonList();
        receivedata.Load();
        if(0==receivedata.getReceive().size())
        {
            receivedata.getReceive().add(new Receive(this.getContext(),"李四", "300","2020年11月11日",0));
            receivedata.Save();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v==this.getActivity().findViewById(R.id.fragment_receive_list)) {
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
                intent = new Intent(this.getContext(), ReceiveUpdateActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
                break;
            case CONTEXT_MENU_ITEM_APPEND:
                intent = new Intent(this.getContext(), ReceiveUpdateActivity.class);
                intent.putExtra("position",position+1);
                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
                break;
            case CONTEXT_MENU_ITEM_UPDATE:
                intent = new Intent(this.getContext(), ReceiveUpdateActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("name",receivedata.getReceive().get(position).getName() );
                intent.putExtra("money",receivedata.getReceive().get(position).getMoney() );
                intent.putExtra("date",receivedata.getReceive().get(position).getDate());
                intent.putExtra("reason",receivedata.getReceive().get(position).getReason_position());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_CAT);
                break;
            case CONTEXT_MENU_ITEM_DELETE:

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("询问");
                builder.setMessage("你确定要删除\""+receivedata.getReceive().get(position).getName() + "\"？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        receivedata.getReceive().remove(position);
                        receivedata.Save();
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

            case REQUEST_CODE_ADD_CAT:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    int position = data.getIntExtra("position",0);
                    String receive_name = data.getStringExtra("name");
                    String receive_money = data.getStringExtra("money");
                    String receive_date = data.getStringExtra("date");
                    int receive_reason = data.getIntExtra("reason",0);
                    receivedata.getReceive().add(position,new Receive(this.getContext(),receive_name,receive_money ,receive_date,receive_reason));
                    receivedata.Save();
                    adapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE_UPDATE_CAT:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    int position=data.getIntExtra("position",0);
                    String receive_name = data.getStringExtra("name");
                    String receive_money = data.getStringExtra("money");
                    String receive_date = data.getStringExtra("date");
                    int receive_reason = data.getIntExtra("reason",0);

                    receivedata.getReceive().get(position).setName(receive_name);
                    receivedata.getReceive().get(position).setMoney(receive_money);
                    receivedata.getReceive().get(position).setDate(receive_date);
                    receivedata.getReceive().get(position).setReason_position(receive_reason);
                    receivedata.getReceive().get(position).setReason(list.get(receive_reason));
                    receivedata.Save();
                    adapter.notifyDataSetChanged();
                }
                break;

            default:

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class ReceiveAdapter extends ArrayAdapter<Receive> {
        private final int resourceId;
        public ReceiveAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Receive> objects) {
            super(context, resource,objects);
            this.resourceId=resource;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Receive receive = getItem(position);//获取当前项的实例
            View view;
            if(null==convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view=convertView;
            ((TextView) view.findViewById(R.id.textView_receive_person)).setText(receive.getName());
            ((TextView) view.findViewById(R.id.textView_receive_money)).setText(receive.getMoney());
            ((TextView) view.findViewById(R.id.textView_receive_date)).setText(receive.getDate());
            ((TextView) view.findViewById(R.id.textView_receive_reason)).setText(receive.getReason());
            return view;
        }
    }
}