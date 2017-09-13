package com.android.calc;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinancialFragThre#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinancialFragThre extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.bt_data_start)
    TextView btDataStart;

    @BindView(R.id.year_rate)
    TextView yearrateTv;

    @BindView(R.id.bt_data_end)
    TextView btDataEnd;

    @BindView(R.id.input_return_rate)
    EditText inputReturnRate;

    @BindView(R.id.input_capital)
    EditText inputCapital;

    @BindView(R.id.calc_reset_thr)
    Button calcResetThr;

    @BindView(R.id.calc_start)
    Button calcStart;

    Unbinder unbinder;

    long startTime, endTime;
    private SimpleDateFormat mSimpleDate;
    boolean bt_start=false;
    int mYear , mMonth, mDay;
    double rate;
    Calendar mCalendar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public FinancialFragThre() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinancialFragThre.
     */
    // TODO: Rename and change types and number of parameters
    public static FinancialFragThre newInstance(String param1, String param2) {
        FinancialFragThre fragment = new FinancialFragThre();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financial_frag_thre, container, false);
        unbinder = ButterKnife.bind(this, view);
        mSimpleDate = new SimpleDateFormat("yyyy-MM-dd");
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        btDataStart.setText(mSimpleDate.format(mCalendar.getTime()));
        btDataEnd.setText(mSimpleDate.format(mCalendar.getTime()));
        startTime= mCalendar.getTime().getTime();
        endTime = mCalendar.getTime().getTime();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_data_start, R.id.bt_data_end, R.id.calc_reset_thr, R.id.calc_start})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt_data_start:
                bt_start = true;
                showDataDialog();
                break;
            case R.id.bt_data_end:
                bt_start = false;
                showDataDialog();
                break;
            case R.id.calc_reset_thr:
                inputCapital.setText("");
                inputReturnRate.setText("");
                btDataStart.setText(mSimpleDate.format(mCalendar.getTime()));
                btDataEnd.setText(mSimpleDate.format(mCalendar.getTime()));
                yearrateTv.setText("0.0%");
                break;
            case R.id.calc_start:
                rate = calc_complex_rate();
                if(rate == 0){
                    yearrateTv.setText("0.0%");
                }else{
                    DecimalFormat df = new DecimalFormat(".##");
                    String rates = df.format(rate);
                    yearrateTv.setText(rates+"%");
                }
                break;
        }
    }

    private double calc_complex_rate(){
        double years,totalRate, principal,total;

        if(endTime <= startTime) return 0;
        if(TextUtils.isEmpty(inputCapital.getEditableText().toString())
                || TextUtils.isEmpty(inputReturnRate.getText().toString())){
            return 0;
        }

        years = (endTime-startTime)/1000.00/60.00/60.00/24.00/365;
        totalRate = Double.valueOf(inputReturnRate.getText().toString());
        principal = Double.valueOf(inputCapital.getText().toString());
        total = principal*totalRate/100+principal;
        Log.d("fht","years = "+years+" , totalRate ="+totalRate+" , principal ="+principal+", total="+total);
        return 100*(Math.pow(total/principal,1.0/years)-1);

    }




    private void showDataDialog(){

        new DatePickerDialog(getContext(), mDateListener,mYear, mMonth,mDay).show();
    }

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Log.d("fht", "year ="+year+" , month = "+month+" , day ="+day);
            String time = year+"-"+(month+1)+"-"+day;
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(year,month,day);
            try {
                if (bt_start) {
                    btDataStart.setText(mSimpleDate.format(tempCalendar.getTime()));
                    startTime = tempCalendar.getTimeInMillis();
                } else {
                    btDataEnd.setText(mSimpleDate.format(tempCalendar.getTime()));
                    endTime = tempCalendar.getTimeInMillis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
