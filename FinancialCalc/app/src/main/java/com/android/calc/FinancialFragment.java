package com.android.calc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinancialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinancialFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.finan_total)
    TextView finanTotal;
    @BindView(R.id.input_money)
    EditText inputMoney;
    @BindView(R.id.input_rate)
    EditText inputRate;
    @BindView(R.id.input_years)
    EditText inputYears;
    @BindView(R.id.calc_sum)
    Button calcSum;
    @BindView(R.id.calc_reset)
    Button calcReset;

    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private double principal;
    private double rate;
    private double years;
    private double total;
    private DecimalFormat decimalFormat;

    private OnFragmentInteractionListener mListener;

    public FinancialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinancialFragment.
     */
    public static FinancialFragment newInstance(String param1, String param2) {

        FinancialFragment fragment = new FinancialFragment();

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

        Log.d("FHT", "OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("FHT", "OnCreateView");
        View view = inflater.inflate(R.layout.fragment_financial, container, false);
        unbinder = ButterKnife.bind(this, view);
        
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("FHT", "OnAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("FHT", "OnDetach");
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d("FHT", "OnDestroyView");
    }






    @OnClick({R.id.calc_sum, R.id.calc_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.calc_sum:
                total = calc_total();
                if(total == 0){
                    finanTotal.setText("0");
                }else {
                    DecimalFormat df = new DecimalFormat(".##");

                    String total_format = df.format(total);
                    finanTotal.setText(total_format);
                }
                break;
            case R.id.calc_reset:
                inputMoney.setText("");
                inputYears.setText("");
                inputRate.setText("");
                finanTotal.setText("0");

                break;
        }
    }

    private double calc_total(){
        Log.d("fht","money ="+ inputMoney.getText().toString() +" years ="+inputYears.getText().toString()+" rate ="+inputRate.getText().toString());
        if(TextUtils.isEmpty(inputMoney.getText().toString())
                || TextUtils.isEmpty(inputRate.getText().toString())
                || TextUtils.isEmpty(inputYears.getText().toString())){
            return 0;
        }

        principal = Double.valueOf(inputMoney.getText().toString());
        years = Double.valueOf(inputYears.getText().toString());
        rate = Double.valueOf(inputRate.getText().toString());

        total = principal*Math.pow((1+rate/100),years);
        return total;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
