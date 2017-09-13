package com.android.calc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinancialFragTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinancialFragTwo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.input_every_year)
    EditText inputEveryYear;
    @BindView(R.id.input_expect_rate)
    EditText inputExpectRate;
    @BindView(R.id.input_save_year)
    EditText inputSaveYear;
    @BindView(R.id.calc_reset_thr)
    Button calcResetThr;
    @BindView(R.id.calc_start)
    Button calcStart;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FinancialFragTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinancialFragTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FinancialFragTwo newInstance(String param1, String param2) {
        FinancialFragTwo fragment = new FinancialFragTwo();
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
        View view = inflater.inflate(R.layout.fragment_financial_frag_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.calc_reset_thr, R.id.calc_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calc_reset_thr:
                inputEveryYear.setText("");
                inputExpectRate.setText("");
                inputSaveYear.setText("");
                totalMoney.setText("0.0");
                break;
            case R.id.calc_start:
                DecimalFormat df = new DecimalFormat(".##");
                double total = calc_every_year_total();
                String totalStr = df.format(total);
                totalMoney.setText(totalStr);
                break;
        }
    }


    private double calc_every_year_total(){
        if(TextUtils.isEmpty(inputEveryYear.getText().toString())
                || TextUtils.isEmpty(inputExpectRate.getText().toString())
                || TextUtils.isEmpty(inputSaveYear.getText().toString())){
            return 0.0;
        }

        double principal = Double.valueOf(inputEveryYear.getText().toString());
        double rate = Double.valueOf(inputExpectRate.getText().toString());
        double years = Double.valueOf(inputSaveYear.getText().toString());
        double total=0.0;
        if(years<1){
            return 0.0;
        }
        for(int i=1; i<= years;i++){
            total=(principal+total)*(1+rate/100);
        }
        return total;

    }

}
