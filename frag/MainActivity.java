package com.example.ananttask;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Fragment androidfragment=new Frag1();
//        this.setDefaultFragment(androidfragment);

        if (savedInstanceState == null) {
            // Instance of first fragment
            Frag1 firstFragment = new Frag1();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.frag_first, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction


            Frag2 secondfragement= new Frag2();
            FragmentTransaction st=getSupportFragmentManager().beginTransaction();
            st.add(R.id.frag_sec,secondfragement);
            st.commit();


            Frag3 thirdfragment=new Frag3();
            FragmentTransaction tf=getSupportFragmentManager().beginTransaction();
            tf.add(R.id.frag_third,thirdfragment);
            tf.commit();
        }


//
//        if (null == savedInstanceState) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frag_first, Frag1.newInstance())
//                    .commit();
//        }
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Frag1 hello = new Frag1();
//        fragmentTransaction.add(R.id.frag_first, hello, "HELLO");
//        fragmentTransaction.commit();
    }
}
