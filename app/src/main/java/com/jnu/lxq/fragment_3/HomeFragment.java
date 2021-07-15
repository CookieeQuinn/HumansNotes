package com.jnu.lxq.fragment_3;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import com.jnu.lxq.R;
import com.jnu.lxq.dataprocessor.PayUpdateActivity;
import com.jnu.lxq.dataprocessor.Pay;
import com.jnu.lxq.dataprocessor.PayDataBank;
import com.jnu.lxq.dataprocessor.Receive;
import com.jnu.lxq.dataprocessor.ReceiveDataBank;
import com.jnu.lxq.dataprocessor.ReceiveUpdateActivity;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final int REQUEST_CODE_ADD_PAY = 200;
    private static final int REQUEST_CODE_ADD_RECEIVE = REQUEST_CODE_ADD_PAY + 1;

    Intent intent;
    private PayDataBank paydata;
    private ReceiveDataBank receivedata;

    private PayFragment.PayAdapter adapterPay;
    private ReceiveFragment.ReceiveAdapter adapterReceive;

    private CalendarView calendarView;

    private ListView listViewPay;
    private ListView listViewReceive;


    private int calendarFocusMonth;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String selecteddate;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        initdate();
        iniview(view);
        initcalendarview(view);
        initfab(view);
//        Toast.makeText(getContext(), "HOME", Toast.LENGTH_SHORT).show();

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_PAY:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    int position = data.getIntExtra("position", 0);
                    String pay_name = data.getStringExtra("name");
                    String pay_money = data.getStringExtra("money");
                    String pay_date = data.getStringExtra("date");
                    paydata.getPays().add(position, new Pay(pay_name, pay_money, pay_date));
                    paydata.Save();
                }
                break;
            case REQUEST_CODE_ADD_RECEIVE:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    int position = data.getIntExtra("position", 0);
                    String receive_name = data.getStringExtra("name");
                    String receive_money = data.getStringExtra("money");
                    String receive_date = data.getStringExtra("date");
                    int receive_reason = data.getIntExtra("reason",0);
                    receivedata.getReceive().add(position, new Receive(this.getContext(),receive_name, receive_money, receive_date,receive_reason));
//                    receivedata.getReceive().add(new Receive("张三","300","2020.11.11","金榜题名"));

                    receivedata.Save();

                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void initdate() {
        paydata = new PayDataBank(getContext());
        paydata.Load();
        receivedata = new ReceiveDataBank(getContext());
        receivedata.Load();
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH) +1;
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        selecteddate = mYear + "年"
                + mMonth + "月"
                + mDay + "日";
    }


    private void iniview(View view) {
        adapterPay = new PayFragment.PayAdapter(view.getContext(), R.layout.pay_record, paydata.getPays());
        View payView = View.inflate(this.getContext(), R.layout.fragment_pay, null);
        listViewPay = payView.findViewById(R.id.fragment_pay_list);
        listViewPay.setAdapter(adapterPay);

        adapterReceive = new ReceiveFragment.ReceiveAdapter(view.getContext(), R.layout.receive_record, receivedata.getReceive());
        View receiveView = View.inflate(this.getContext(), R.layout.fragment_receive, null);
        listViewReceive = receiveView.findViewById(R.id.fragment_receive_list);
        listViewReceive.setAdapter(adapterReceive);
    }

    private void initfab(View view) {

        final FloatingActionButton actionA = (FloatingActionButton) view.findViewById(R.id.action_a);
        final FloatingActionButton actionB = (FloatingActionButton) view.findViewById(R.id.action_b);


        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                intent = new Intent(v.getContext(), PayUpdateActivity.class);
                intent.putExtra("date", selecteddate);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_ADD_PAY);
            }
        });

        actionB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = 0;
                intent = new Intent(v.getContext(), ReceiveUpdateActivity.class);
                intent.putExtra("date", selecteddate);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_ADD_RECEIVE);
            }
        });
    }


    private void initcalendarview(View view) {
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int Month = month +1 ;
                selecteddate = year + "年"
                        + Month + "月"
                        + dayOfMonth + "日";
            }
        });
    }
}



