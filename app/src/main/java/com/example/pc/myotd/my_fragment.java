package com.example.pc.myotd;

/*FRAGMENT INUTILIZZATO, ACCEDIAMO AL METEO DIRETTAMENTE.*/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;




public class my_fragment extends Fragment {


    public static  my_fragment newInstance() {
        my_fragment fragment = new  my_fragment();
        return fragment;
    }

    Button ClickMe;
    TextView tv;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public   my_fragment(){}

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
        View rootView= inflater.inflate(R.layout.fragment_my_fragment, container, false);
        ClickMe = (Button) rootView.findViewById(R.id.button);
        tv = (TextView) rootView.findViewById(R.id.textView2);


    ClickMe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(tv.getText().toString().contains("Hello")){
                tv.setText("Hi");
            }else tv.setText("Hello");
        }
    });
        return rootView;
    }
} // This is the end of our MyFragments Class


