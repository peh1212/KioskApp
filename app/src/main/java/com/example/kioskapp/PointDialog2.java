package com.example.kioskapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PointDialog2 extends AppCompatActivity {

    private TextView phoneNumberTextView;
    private Button confirmButton;
    private CheckBox marketingCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_point2); // 수정된 부분

        showDialog();
    }

    private void showDialog() {
        // 다이얼로그 생성
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_point2);

        phoneNumberTextView = dialog.findViewById(R.id.point_number_rec);
        confirmButton = dialog.findViewById(R.id.point_terms);
        marketingCheckBox = dialog.findViewById(R.id.checkBox);

        // Intent로부터 전달받은 전화번호 가져오기
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phone_number");

        // 전화번호 TextView에 표시
        phoneNumberTextView.setText(phoneNumber);

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener(v -> {
            // 확인 버튼 클릭 시 MainActivity로 돌아가기
            goBackToMainActivity();
        });

        // 다이얼로그 창 크기 조정 (기본 크기 설정)
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9); // 너비
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.8); // 높이
        dialog.getWindow().setLayout(width, height); // 다이얼로그 크기 설정

        // 다이얼로그 창의 위치 설정 (화면 중앙)
        dialog.getWindow().setGravity(android.view.Gravity.CENTER);

        dialog.show(); // 다이얼로그 표시
    }

    // 적립 후 MainActivity(주문내역)로 돌아가는 메소드
    private void goBackToMainActivity() {
        // MainActivity로 돌아가는 Intent 생성
        Intent intent = new Intent(PointDialog2.this, MainActivity.class);
        startActivity(intent);  // MainActivity로 이동
        finish();  // 현재 PointDialog2 종료
    }
}
