package agarwal.shashwat.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNo;
    private Button otpOnSMS, otpOnWhatsApp;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    final List<SlideItem> slideItems = new ArrayList<>();
  @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent=new Intent(this,ProductActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otpOnSMS = findViewById(R.id.otpSMS);

        phoneNo = findViewById(R.id.phoneNumberEditText);
        viewPager2 = findViewById(R.id.viewPagerImage);


        slideItems.add(new SlideItem(R.drawable.groceryimage001));
        slideItems.add(new SlideItem(R.drawable.groceryimage002));
        slideItems.add(new SlideItem(R.drawable.groceryimage003));
        slideItems.add(new SlideItem(R.drawable.groceryimage004));

        viewPager2.setAdapter(new ViewPagerAdapter(slideItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        /*
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f +r +0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);*/

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable,3000);
            }
        });

        phoneNo.setText("+91");
        Selection.setSelection(phoneNo.getText(), phoneNo.getText().length());


        phoneNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("+91")) {
                    phoneNo.setText("+91");
                    Selection.setSelection(phoneNo.getText(), phoneNo.getText().length());

                }

            }
        });


        otpOnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phoneNo.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    phoneNo.setError("Valid Number is required");
                    phoneNo.requestFocus();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), verifyPhone.class);
                intent.putExtra("phoneNo", phoneNo.getText().toString().trim());
                startActivity(intent);
            }
        });

    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
             viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };


}
