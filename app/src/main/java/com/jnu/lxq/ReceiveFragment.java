package com.jnu.lxq;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveFragment extends Fragment {
    private ArrayList<Pay> payArrayList;
    private PayAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CONTEXT_MENU_ITEM_NEW = 1;
    private static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_NEW + 1;
    private static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_APPEND+1;
    private static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;

    private static final int REQUEST_CODE_ADD_CAT = 100;
    private static final int REQUEST_CODE_UPDATE_CAT = 101;



    public ReceiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pay, container, false);
        initData();
        initView(view);
        return view;
    }
    private void initView(View view) {
        adapter = new PayAdapter(this.getContext(), R.layout.pay_record,  payArrayList);
        ListView listViewPay=view.findViewById(R.id.fragment_pay_list);
        listViewPay.setAdapter(adapter);
//        this.registerForContextMenu(listViewPay);
    }

    private void initData() {
        payArrayList=new ArrayList<>();

        payArrayList.add(new Pay("张三","300","2020.11.11"));


    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        if(v==this.getActivity().findViewById(R.id.fragment_pay_list)) {
//            menu.setHeaderTitle("操作");
//            menu.add(1, CONTEXT_MENU_ITEM_NEW, 1, "新增");
//            menu.add(1, CONTEXT_MENU_ITEM_APPEND, 1, " 追加");
//            menu.add(1, CONTEXT_MENU_ITEM_UPDATE, 1, "修改");
//            menu.add(1, CONTEXT_MENU_ITEM_DELETE, 1, "删除");
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode) {
//
//            case REQUEST_CODE_ADD_CAT:
//                if (resultCode == RESULT_OK) {
//                    String cat_name = data.getStringExtra("cat_name");
//                    int position=data.getIntExtra("cat_position",0);
//                    dataBank.getCats().add(position,new Cat(cat_name, R.drawable.cat2));
//                    dataBank.Save();
//                    adapter.notifyDataSetChanged();
//                }
//                break;
//
//            case REQUEST_CODE_UPDATE_CAT:
//                if (resultCode == RESULT_OK) {
//                    String cat_name = data.getStringExtra("cat_name");
//                    int position=data.getIntExtra("cat_position",0);
//                    dataBank.getCats().get(position).setName(cat_name);
//                    dataBank.Save();
//                    adapter.notifyDataSetChanged();
//                }
//                break;
//
//            default:
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        Intent intent;
//        final int position=menuInfo.position;
//        switch(item.getItemId())
//        {
//            case CONTEXT_MENU_ITEM_NEW:
//                intent = new Intent(this.getContext(), InputUpdateActivity.class);
//                intent.putExtra("position",position);
//                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
//
//                break;
//            case CONTEXT_MENU_ITEM_APPEND:
//                intent = new Intent(this.getContext(), InputUpdateActivity.class);
//                intent.putExtra("position",position+1);
//                startActivityForResult(intent, REQUEST_CODE_ADD_CAT);
//                break;
//            case CONTEXT_MENU_ITEM_UPDATE:
//                intent = new Intent(this.getContext(), InputUpdateActivity.class);
//                intent.putExtra("position",position);
//                intent.putExtra("cat_name",dataBank.getCats().get(position).getName() );
//                startActivityForResult(intent, REQUEST_CODE_UPDATE_CAT);
//                break;
//            case CONTEXT_MENU_ITEM_DELETE:
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//                builder.setTitle("询问");
//                builder.setMessage("你确定要删除\""+dataBank.getCats().get(position).getName() + "\"？");
//                builder.setCancelable(true);
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dataBank.getCats().remove(position);
//                        dataBank.Save();
//                        adapter.notifyDataSetChanged();
//                    }
//                });  //正面的按钮（肯定）
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }); //反面的按钮（否定)
//                builder.create().show();
//
//
//                break;
//            default:
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }

    private static class PayAdapter extends ArrayAdapter<Pay> {
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