package com.example.kioskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PointDialog extends AppCompatActivity {
    private TextView pointNumberTextView;
    private  String inputString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_point);


        pointNumberTextView = findViewById(R.id.point_number);
        // 포인트 적립 완료 버튼 누를시
        Button PointOkButton = findViewById(R.id.point_ok);

        // 숫자 버튼들의 ID를 배열 저장
        int[] buttonlds = {
                R.id.number1, R.id.number2, R.id.number3,
                R.id.number4, R.id.number5, R.id.number6,
                R.id.number7, R.id.number8, R.id.number9,
                R.id.number0
        };

        // 숫자 버튼을 하나씩 찾아서 클릭 리스너를 설정
        for (int id : buttonlds){
            Button button = findViewById(id);
            button.setOnClickListener(v -> onNumberButtonClick((Button) v));
        }

        ImageButton backspaceButton = findViewById(R.id.backspace_button);
        backspaceButton.setOnClickListener(v -> onBackspaceButtonClick());

        TextView clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v-> onClearButtonClick());

        // '포인트 적립 안함' 버튼 처리
        Button denyButton = findViewById(R.id.point_deny);
        denyButton.setOnClickListener(v -> {
            finish(); // PointDialog 액티비티를 종료, MainActivity로 돌아감.
        });

        // 포인트 다이얼로그 창에서 완료 버튼을 누르면 포인트 다이얼로그2 창으로 넘어감
        PointOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpointDialog2();
            }
        });
    }


    //숫자 버튼 클릭 시 호출
    private void onNumberButtonClick(Button button){
        String number = button.getText().toString();
        inputString += number;
        pointNumberTextView.setText(inputString);
    }

    //클리어 버튼 클릭시 호출
    private void onClearButtonClick(){
        inputString = "";
        pointNumberTextView.setText(inputString);
    }

    //백스페이스 버튼 클릭시 호출
    private void onBackspaceButtonClick(){
        if(!inputString.isEmpty()){
            inputString = inputString.substring(0, inputString.length() - 1);
            pointNumberTextView.setText(inputString);
        }
    }

    private void showpointDialog2() {
        // 번호를 전달할 Intent 생성
        Intent intent = new Intent(this, PointDialog2.class);
        intent.putExtra("phone_number", inputString);  // 입력된 전화번호를 전달
        startActivity(intent);  // PointDialog2로 이동
        finish();  // 현재 Dialog 종료
    }
}
