package com.example.kioskapp;

import static java.text.NumberFormat.getNumberInstance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    ArrayList<String> menuNames, menuOption1, menuOption2, menuOption3, menuOption4;
    ArrayList<Integer> menuQuantities, menuPrices, HotPrice, SizePrice, ToppingPrice;
    ListView payList;
    TextView payment_totalQuantity, payment_totalPrice;
    int totalcnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button CardPaymentButton = findViewById(R.id.cardPaymentButton);
        Button SimplePaymentButton = findViewById(R.id.simplePaymentButton);
        Button PointButton = findViewById(R.id.point_button);

        // 인텐트에서 결제 리스트 배열 가져오기
        Intent intent = getIntent();
        menuNames = intent.getStringArrayListExtra("menuName");
        menuQuantities = intent.getIntegerArrayListExtra("menuQuantity");
        menuPrices = intent.getIntegerArrayListExtra("menuPrice");
        totalcnt = intent.getIntExtra("totalcnt", 0);
        menuOption1 = intent.getStringArrayListExtra("menuOption1");
        menuOption2 = intent.getStringArrayListExtra("menuOption2");
        menuOption3 = intent.getStringArrayListExtra("menuOption3");
        menuOption4 = intent.getStringArrayListExtra("menuOption4");
        HotPrice = intent.getIntegerArrayListExtra("HotPrice");
        SizePrice = intent.getIntegerArrayListExtra("SizePrice");
        ToppingPrice = intent.getIntegerArrayListExtra("ToppingPrice");

        if (menuNames == null) menuNames = new ArrayList<String>();
        if (menuQuantities == null) menuQuantities = new ArrayList<Integer>();
        if (menuPrices == null) menuPrices = new ArrayList<Integer>();

        // 토탈갯수 토탈금액
        payment_totalQuantity = findViewById(R.id.payment_totalQuantity);
        payment_totalQuantity.setText(String.valueOf(totalcnt) +"개");
        payment_totalPrice = findViewById(R.id.payment_totalPrice);
        int totalPrice = 0;
        for (int i = 0; i < menuPrices.size(); i++) {
            totalPrice += menuPrices.get(i)*menuQuantities.get(i);
        }
        final TextView paymentPrice = findViewById(R.id.paymentPrice);
        paymentPrice.setText("주문 금액: " + getPriceFormattedNumber(totalPrice));
        payment_totalPrice.setText(getPriceFormattedNumber(totalPrice-1500));

        // 결제 리스트 어댑터와 리스트뷰 연결
        CustomList adapter = new CustomList(PaymentActivity.this, menuNames, menuQuantities, menuPrices);
        payList = (ListView) findViewById(R.id.lyj_recyclerViewItemResult);
        payList.setAdapter(adapter);

        CardPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCardPaymentDialog();
            }
        });

        SimplePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimplePaymentDialog();
            }
        });

        PointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 포인트 다이얼로그 액티비티로 이동
                Intent intent = new Intent(PaymentActivity.this, PointDialog.class);
                startActivity(intent); // PointDialog 액티비티로 이동
            }
        });
    }

    // 1234 -> 1,234원
    private String getPriceFormattedNumber(int Price) {
        NumberFormat numberFormat = getNumberInstance(Locale.US);
        String formattedPrice = numberFormat.format(Price) + "원";
        return formattedPrice;
    }

    //카드 결제창
    private void showCardPaymentDialog() {
        // 다이얼로그 생성
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_card_payment);

        // 다이얼로그 창 크기 조정
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        dialog.show();

        // 닫기 버튼 설정
        ImageView closeButton = dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(v -> {
            dialog.dismiss(); // 다이얼로그 닫기
            showPaymentScreen(); // 결제 화면으로 이동
        });
    }

    //간편 결제창
    private void showSimplePaymentDialog() {
        // 다이얼로그 생성
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_easy_payment);

        // 다이얼로그 창 크기 조정
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.show();

        // 닫기 버튼 설정
        ImageView closeButton = dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(v -> {
            dialog.dismiss(); // 다이얼로그 닫기
            showPaymentScreen(); // 결제 화면으로 이동
        });
    }

    private void showPaymentScreen() {

    }

    public class CustomList extends BaseAdapter {
        private final Activity context;
        private final ArrayList<String> menuNames;
        private final ArrayList<Integer> menuQuantities;
        private final ArrayList<Integer> menuPrices;

        public CustomList(Activity context, ArrayList<String> menuNames, ArrayList<Integer> menuQuantities, ArrayList<Integer> menuPrices) {
            this.context = context;
            this.menuNames = menuNames != null ? menuNames : new ArrayList<String>();
            this.menuQuantities = menuQuantities != null ? menuQuantities : new ArrayList<Integer>();
            this.menuPrices = menuPrices != null ? menuPrices : new ArrayList<Integer>();
        }

        @Override
        public int getCount() {
            return menuNames.size(); // 리스트의 크기 반환
        }

        @Override
        public Object getItem(int position) {
            return null;  // 필요 없으면 null 반환
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_paymentlist, null, true);

            final TextView payment_Name = rowView.findViewById(R.id.payment_Name);
            final TextView payment_Quantity = rowView.findViewById(R.id.payment_Quantity);
            final TextView payment_Price = rowView.findViewById(R.id.payment_Price);

            final TextView payment_OptionName1 = rowView.findViewById(R.id.payment_OptionName1);
            final TextView payment_OptionName2 = rowView.findViewById(R.id.payment_OptionName2);
            final TextView payment_OptionName3 = rowView.findViewById(R.id.payment_OptionName3);
            final TextView payment_OptionName4 = rowView.findViewById(R.id.payment_OptionName4);
            final TextView payment_OptionPrice1 = rowView.findViewById(R.id.payment_OptionPrice1);
            final TextView payment_OptionPrice2 = rowView.findViewById(R.id.payment_OptionPrice2);
            final TextView payment_OptionPrice3 = rowView.findViewById(R.id.payment_OptionPrice3);
            final TextView payment_OptionPrice4 = rowView.findViewById(R.id.payment_OptionPrice4);

            payment_Name.setText(menuNames.get(position));
            payment_Quantity.setText(String.valueOf(menuQuantities.get(position)) +"개");
            payment_Price.setText(getPriceFormattedNumber(menuPrices.get(position)*menuQuantities.get(position)));
            payment_OptionName1.setText("ㄴ " + menuOption1.get(position));
            payment_OptionName2.setText("ㄴ " + menuOption2.get(position));
            payment_OptionName3.setText("ㄴ " + menuOption3.get(position));
            payment_OptionName4.setText("ㄴ " + menuOption4.get(position));
            if (HotPrice.get(position) == 0) {
                payment_OptionPrice1.setText("");
            }
            else {

                payment_OptionPrice1.setText("+ " + getPriceFormattedNumber(HotPrice.get(position)));
            }
            if (SizePrice.get(position) == 0) {
                payment_OptionPrice2.setText("");
            }
            else {
                payment_OptionPrice2.setText("+ " + getPriceFormattedNumber(SizePrice.get(position)));
            }
            if (ToppingPrice.get(position) == 0) {
                payment_OptionPrice3.setText("");
            }
            else {
                payment_OptionPrice3.setText("+ " + getPriceFormattedNumber(ToppingPrice.get(position)));
            }
            payment_OptionPrice4.setText("");
            return rowView;
        }
    }
}