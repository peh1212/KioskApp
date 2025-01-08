package com.example.kioskapp;

import static java.text.NumberFormat.getNumberInstance;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    ListView basketList;
    String[] menuNameArray = {}, menuOption1Array = {}, menuOption2Array = {}, menuOption3Array = {}, menuOption4Array = {}, coffeeInfoArray = {};
    int[] menuQuantityArray = {}, menuPriceArray = {}, SelectedHotPriceArray = {}, SelectedSizePriceArray = {}, SelectedToppingPriceArray = {};
    ArrayList<String> menuName, menuOption1, menuOption2, menuOption3, menuOption4, coffeeInfo;
    ArrayList<Integer> menuQuantity, menuPrice, HotPrice, SizePrice, ToppingPrice;
    private int SelectedHotPrice = 0, SelectedSizePrice = 0, SelectedToppingPrice = 0;
    CustomList adapter;

    TextView textViewMenuName, textViewPrice, textViewTotalCount, textViewTotalPrice, textViewOption, selectMenuName, selectMenuPrice, TextViewCoffeeInfo;
    Button buttonAddItem, buttonMinus, buttonPlus, buttonX, buttonAC, buttonPayment;
    ImageButton buttonAddItem1, buttonAddItem2, buttonAddItem3, buttonAddItem4, buttonAddItem5, buttonAddItem6, buttonAddItem7, buttonAddItem8;
    ImageButton buttonAddItem9, buttonAddItem10, buttonAddItem11, buttonAddItem12, buttonAddItem13, buttonAddItem14, buttonAddItem15, buttonAddItem16;
    ImageButton buttonAddItem17, buttonAddItem18, buttonAddItem19, buttonAddItem20, buttonAddItem21, buttonAddItem22, buttonAddItem23, buttonAddItem24;

    private boolean isHotSelected = false, isSizeSelected = false, isSyrupSelected = false, isIceAmountSelected = false;
    private int SelectedHot = 0, SelectedSize = 0, SelectedTopping = 0, SelectedIce = 0;
    int totalcnt = 0;
    private Button btnComplete;
    ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 아이템을 동적으로 관리하기 위하여 배열을 어레이리스트로 만든다.
        menuName = new ArrayList<>(Arrays.asList(menuNameArray));
        menuOption1 = new ArrayList<>(Arrays.asList(menuOption1Array));
        menuOption2 = new ArrayList<>(Arrays.asList(menuOption2Array));
        menuOption3 = new ArrayList<>(Arrays.asList(menuOption3Array));
        menuOption4 = new ArrayList<>(Arrays.asList(menuOption4Array));
        coffeeInfo = new ArrayList<>(Arrays.asList(coffeeInfoArray));
        menuQuantity = new ArrayList<>();
        for (int quantity : menuQuantityArray) {
            menuQuantity.add(quantity);
        }
        menuPrice = new ArrayList<>();
        for (int price : menuPriceArray) {
            menuPrice.add(price);
        }
        HotPrice = new ArrayList<>();
        for (int price : SelectedHotPriceArray) {
            menuPrice.add(price);
        }
        SizePrice = new ArrayList<>();
        for (int price : SelectedSizePriceArray) {
            menuPrice.add(price);
        }
        ToppingPrice = new ArrayList<>();
        for (int price : SelectedToppingPriceArray) {
            menuPrice.add(price);
        }

        // 어댑터를 생성하여 배열을 리스트뷰에 연결한다. CustomList 는 직접 만들것
        adapter = new CustomList(MenuActivity.this);
        basketList=(ListView)findViewById(R.id.basketList);
        basketList.setAdapter(adapter);

        // activity_menu.xml 에 있는 버튼과 텍스트뷰 등 아이디 가져오기.
        buttonAC = findViewById(R.id.buttonAC);
        textViewTotalCount = findViewById(R.id.textViewTotalCount);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonPayment = findViewById(R.id.buttonPayment);


        // 탭레이아웃+뷰페이저
        viewPager = findViewById(R.id.viewpager);
        FragmentStateAdapter fragmentAdapter = new FragmentAdapter(this);
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.Taplayout1);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // 탭 제목 설정
            String[] titles = {"추천", "커피", "논커피", "스무디", "주스"};
            tab.setText(titles[position]);
        }).attach();

        ImageButton homeButton = findViewById(R.id.bt1);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


    }

    // 메뉴를 하나 골랐을 때 나타나는 옵션선택창
    public void buttonAddItem(View v) {
        // 커스텀 대화메뉴를 생성하여 띄운다.
        Dialog optionDialog = new Dialog(MenuActivity.this);
        optionDialog.setContentView(R.layout.activity_option);
        optionDialog.show();
        resetSelections();

        // activity_option.xml 에 있는 버튼과 텍스트뷰 등 아이디 가져오기.
        selectMenuPrice = optionDialog.findViewById(R.id.selectMenuPrice);
        selectMenuName = optionDialog.findViewById(R.id.selectMenuName);
        btnComplete = optionDialog.findViewById(R.id.btn_complete);
        TextViewCoffeeInfo = optionDialog.findViewById(R.id.TextViewCoffeeInfo);

        // 폰트를 가져온다.
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/NanumBarunGothic-YetHangul.ttf");
        btnComplete.setTypeface(customFont);
        btnComplete.setEnabled(false);

        // 선택한 메뉴를 배열에 저장하고 화면에 보여준다.
        buttonAddItem1 = findViewById(R.id.buttonAddItem1);
        buttonAddItem2 = findViewById(R.id.buttonAddItem2);
        buttonAddItem3 = findViewById(R.id.buttonAddItem3);
        buttonAddItem4 = findViewById(R.id.buttonAddItem4);
        buttonAddItem5 = findViewById(R.id.buttonAddItem5);
        buttonAddItem6 = findViewById(R.id.buttonAddItem6);
        buttonAddItem7 = findViewById(R.id.buttonAddItem7);
        buttonAddItem8 = findViewById(R.id.buttonAddItem8);

        buttonAddItem9 = findViewById(R.id.buttonAddItem9);
        buttonAddItem10 = findViewById(R.id.buttonAddItem10);
        buttonAddItem11 = findViewById(R.id.buttonAddItem11);
        buttonAddItem12 = findViewById(R.id.buttonAddItem12);
        buttonAddItem13 = findViewById(R.id.buttonAddItem13);
        buttonAddItem14 = findViewById(R.id.buttonAddItem14);
        buttonAddItem15 = findViewById(R.id.buttonAddItem15);
        buttonAddItem16 = findViewById(R.id.buttonAddItem16);

        buttonAddItem17 = findViewById(R.id.buttonAddItem17);
        buttonAddItem18 = findViewById(R.id.buttonAddItem18);
        buttonAddItem19 = findViewById(R.id.buttonAddItem19);
        buttonAddItem20 = findViewById(R.id.buttonAddItem20);
        buttonAddItem21 = findViewById(R.id.buttonAddItem21);
        buttonAddItem22 = findViewById(R.id.buttonAddItem22);
        buttonAddItem23 = findViewById(R.id.buttonAddItem23);
        buttonAddItem24 = findViewById(R.id.buttonAddItem24);

        if (v == buttonAddItem1 && buttonAddItem1 != null) {
            menuName.add("피넛츠 카페라떼");
            menuPrice.add(6000);
            coffeeInfo.add("비건음료 아몬드 브리즈에 땅콩, 호두, 헤이즐넛이 만나 부드럽고 고소한 풍미를 느낄 수 있는 겨울시즌 한정라떼");
        } else if (v == buttonAddItem2 && buttonAddItem2 != null) {
            menuName.add("피넛츠 카페라떼");
            menuPrice.add(6000);
            coffeeInfo.add("비건 음료 아몬드브리즈에 땅콩, 호두, 헤이즐넛이 만나 부드럽고 고소한 풍미를 느낄 수 있는 겨울시즌 한정 라떼");
        } else if (v == buttonAddItem3 && buttonAddItem3 != null) {
            menuName.add("초코 눈송이 피스타치오 프라페");
            menuPrice.add(7000);
            coffeeInfo.add("피스타치오 나무 위에 초코 눈송이가 와르르! 와작와작! 초콜릿을 깨서 고소한 피스타치오 프라페와 섞은 후 식감 좋은 초콜릿 아몬드와 함께 즐기는 달콤하고 고소한 피스타치오 프라페");
        } else if (v == buttonAddItem4 && buttonAddItem4 != null) {
            menuName.add("피스타치오 포레스트 라떼");
            menuPrice.add(7000);
            coffeeInfo.add("피스타치오 숲 속 산타가 좋아하는 은은한 향의 피스타치오 라떼에 쌉싸름한 블렌딩 커피를 추가해 더욱 고소하게 즐길 수 있는 따뜻한 라떼");
        } else if (v == buttonAddItem5 && buttonAddItem5 != null) {
            menuName.add("빨간 코 루돌프의 상큼 리치티");
            menuPrice.add(6500);
            coffeeInfo.add("하얀 눈 속을 달리는 빨간 코 루돌프와 닮은 리치티. 리치, 라임, 망고스틴 베이스에 레드 커런트, 로즈마리를 더한 상큼한 과일티");
        } else if (v == buttonAddItem6 && buttonAddItem6 != null) {
            menuName.add("산타 코코넛 애플티 라떼");
            menuPrice.add(6500);
            coffeeInfo.add("산타클로스의 빨간 모자와 하얀 수염을 닮은 겨울 티 라떼. 달콤하고 부드러운 코코넛과 상큼한 파인애플 베이스에 달콤하고 향긋한 애플티를 더한 티라떼");
        } else if (v == buttonAddItem7 && buttonAddItem7 != null) {
            menuName.add("하츄핑 딸기 밀크쉐이크");
            menuPrice.add(7500);
            coffeeInfo.add("하츄핑은 딸기를 좋아해! 내사랑을 듬뿍듬뿍 줄게~츄! 딸기가득 부드러운 밀크쉐이크 위에 귀여운 초코 하츄핑이 뿅!");
        } else if (v == buttonAddItem8 && buttonAddItem8 != null) {
            menuName.add("빤짝핑 망고 밀크쉐이크");
            menuPrice.add(7500);
            coffeeInfo.add("빤짝핑은 망고를 좋아해! 어떻게 알았냐면~ 빤짝하고 빛났어 망고가득 부드러운 밀크쉐이크 위에 귀여운 초코 빤짝핑이 뿅!");
        } else if (v == buttonAddItem9 && buttonAddItem9 != null) {
            menuName.add("피넛츠 카페라떼");
            menuPrice.add(6000);
            coffeeInfo.add("비건음료 아몬드 브리즈에 땅콩, 호두, 헤이즐넛이 만나 부드럽고 고소한 풍미를 느낄 수 있는 겨울시즌 한정라떼");
        } else if (v == buttonAddItem10 && buttonAddItem10 != null) {
            menuName.add("피넛츠 카페");
            menuPrice.add(6000);
            coffeeInfo.add("비건 음료 아몬드브리즈에 땅콩, 호두, 헤이즐넛이 만나 부드럽고 고소한 풍미를 느낄 수 있는 겨울시즌 한정 라떼");
        } else if (v == buttonAddItem11 && buttonAddItem11 != null) {
            menuName.add("초코 눈송이 피스타치오 프라페");
            menuPrice.add(7000);
            coffeeInfo.add("피스타치오 나무 위에 초코 눈송이가 와르르! 와작와작! 초콜릿을 깨서 고소한 피스타치오 프라페와 섞은 후 식감 좋은 초콜릿 아몬드와 함께 즐기는 달콤하고 고소한 피스타치오 프라페");
        } else if (v == buttonAddItem12 && buttonAddItem12 != null) {
            menuName.add("피스타치오 포레스트 라떼");
            menuPrice.add(7000);
            coffeeInfo.add("피스타치오 숲 속 산타가 좋아하는 은은한 향의 피스타치오 라떼에 쌉싸름한 블렌딩 커피를 추가해 더욱 고소하게 즐길 수 있는 따뜻한 라떼");
        } else if (v == buttonAddItem13 && buttonAddItem13 != null) {
            menuName.add("빨간 코 루돌프의 상큼 리치티");
            menuPrice.add(6500);
            coffeeInfo.add("하얀 눈 속을 달리는 빨간 코 루돌프와 닮은 리치티. 리치, 라임, 망고스틴 베이스에 레드 커런트, 로즈마리를 더한 상큼한 과일티");
        } else if (v == buttonAddItem14 && buttonAddItem14 != null) {
            menuName.add("산타 코코넛 애플티 라떼");
            menuPrice.add(6500);
            coffeeInfo.add("산타클로스의 빨간 모자와 하얀 수염을 닮은 겨울 티 라떼. 달콤하고 부드러운 코코넛과 상큼한 파인애플 베이스에 달콤하고 향긋한 애플티를 더한 티라떼");
        } else if (v == buttonAddItem15 && buttonAddItem15 != null) {
            menuName.add("하츄핑 딸기 밀크쉐이크");
            menuPrice.add(7500);
            coffeeInfo.add("하츄핑은 딸기를 좋아해! 내사랑을 듬뿍듬뿍 줄게~츄! 딸기가득 부드러운 밀크쉐이크 위에 귀여운 초코 하츄핑이 뿅!");
        } else if (v == buttonAddItem16 && buttonAddItem16 != null) {
            menuName.add("빤짝핑 망고 밀크쉐이크");
            menuPrice.add(7500);
            coffeeInfo.add("빤짝핑은 망고를 좋아해! 어떻게 알았냐면~ 빤짝하고 빛났어 망고가득 부드러운 밀크쉐이크 위에 귀여운 초코 빤짝핑이 뿅!");
        } else if (v == buttonAddItem17 && buttonAddItem17 != null) {
            menuName.add("디카페인 에스프레소");
            menuPrice.add(4500);
            coffeeInfo.add("디카페인으로 만나는 메가MGC커피 에스프레소");
        } else if (v == buttonAddItem18 && buttonAddItem18 != null) {
            menuName.add("디카페인 아메리카노");
            menuPrice.add(5000);
            coffeeInfo.add("향과 풍미 그대로 카페인만을 낮춰 민감한 분들도 안심하고 매일매일 즐길 수 있는 디카페인 커피");
        } else if (v == buttonAddItem19 && buttonAddItem19 != null) {
            menuName.add("디카페인 꿀아메리카노");
            menuPrice.add(5500);
            coffeeInfo.add("디카페인 아메리카노의 묵직한 바디감에 달콤한 사양벌꿀이 소프트하게 어우러진 커피.");
        } else if (v == buttonAddItem20 && buttonAddItem20 != null) {
            menuName.add("디카페인 카페라떼");
            menuPrice.add(5500);
            coffeeInfo.add("디카페인 에스프레소와 부드러운 우유가 어우러져 고소한 풍미를 완성한 라떼.");
        } else if (v == buttonAddItem21 && buttonAddItem21 != null) {
            menuName.add("디카페인 카푸치노");
            menuPrice.add(6000);
            coffeeInfo.add("디카페인 에스프레소와 부드러운 우유가 어우러져 고소한 풍미를 완성한 카푸치노.");
        } else if (v == buttonAddItem22 && buttonAddItem22 != null) {
            menuName.add("디카페인 바닐라라떼");
            menuPrice.add(6000);
            coffeeInfo.add("디카페인으로 즐기는 바닐라의 짙은 향과 풍부한 폼 밀크의 조화가 인상적인 달콤한 라떼.");
        } else if (v == buttonAddItem23 && buttonAddItem23 != null) {
            menuName.add("디카페인 헤이즐넛 라떼");
            menuPrice.add(6000);
            coffeeInfo.add("부드러운 카페라떼에 헤이즐넛의 풍부한 향과 달콤함을 담아 향긋하게 즐길 수 있는 디카페인 라떼.");
        } else if (v == buttonAddItem24 && buttonAddItem24 != null) {
            menuName.add("디카페인 카라멜마끼아또");
            menuPrice.add(6500);
            coffeeInfo.add("폼 밀크 속에 진한 디카페인 에스프레소와 달콤한 카라멜을 가미해 부드럽게 즐기는 커피");
        }

        menuQuantity.add(1);
        selectMenuName.setText(String.valueOf(menuName.get(menuName.size() - 1)));
        selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1)));
        TextViewCoffeeInfo.setText(String.valueOf(coffeeInfo.get(coffeeInfo.size() - 1)));

        // 뒤로가기 버튼으로 창을 닫았을 경우, 배열에 먼저 저장한것들 다시 지우기
        optionDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // 뒤로가기 버튼 눌렀을 때 동작을 여기에 구현
                    // 예: optionDialog.dismiss();  // Dialog를 닫지 않음
                    // return true;  // 뒤로가기를 막기 위해 true를 리턴
                    menuName.remove(menuName.size() - 1);
                    menuPrice.remove(menuPrice.size() - 1);
                    menuQuantity.remove(menuQuantity.size() - 1);
                    resetSelections();
                    optionDialog.dismiss(); // 뒤로가기는 가능하게 하는데 뒤로가기버튼의 동작으로 처리하지 않고 dismiss로 창을 정상적으로 종료시켜준다.
                    return true;  // 뒤로가기를 허용하려면 false를 리턴
                }
                return false;
            }
        });

        Button hotButton = optionDialog.findViewById(R.id.btn_hot);
        Button iceButton = optionDialog.findViewById(R.id.btn_ice);
            hotButton.setOnClickListener(view1 -> {
                isHotSelected = true;
                SelectedHot = 1;
                SelectedHotPrice = 0;
                setButtonStyle(hotButton, iceButton, iceButton);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            iceButton.setOnClickListener(view1 -> {
                isHotSelected = true;
                SelectedHot = 2;
                SelectedHotPrice = 500;
                setButtonStyle(iceButton, hotButton, hotButton);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });

        Button smallButton = optionDialog.findViewById(R.id.btn_size_small);
        Button mediumButton = optionDialog.findViewById(R.id.btn_size_medium);
        Button largeButton = optionDialog.findViewById(R.id.btn_size_large);
            smallButton.setOnClickListener(view1 -> {
                isSizeSelected = true;
                SelectedSize = 1;
                SelectedSizePrice = 0;
                setButtonStyle(smallButton, mediumButton, largeButton);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            mediumButton.setOnClickListener(view1 -> {
                isSizeSelected = true;
                SelectedSize = 2;
                SelectedSizePrice = 500;
                setButtonStyle(mediumButton, smallButton, largeButton);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            largeButton.setOnClickListener(view1 -> {
                isSizeSelected = true;
                SelectedSize = 3;
                SelectedSizePrice = 1000;
                setButtonStyle(largeButton, smallButton, mediumButton);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });

        Button topping1Button = optionDialog.findViewById(R.id.topping1);
        Button topping2Button = optionDialog.findViewById(R.id.topping2);
        Button topping3Button = optionDialog.findViewById(R.id.topping3);
            topping1Button.setOnClickListener(view1 -> {
                isSyrupSelected = true;
                SelectedTopping = 1;
                SelectedToppingPrice = 500;
                setButtonStyle(topping1Button, topping2Button, topping3Button);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            topping2Button.setOnClickListener(view1 -> {
                isSyrupSelected = true;
                SelectedTopping = 2;
                SelectedToppingPrice = 500;
                setButtonStyle(topping2Button, topping1Button, topping3Button);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            topping3Button.setOnClickListener(view1 -> {
                isSyrupSelected = true;
                SelectedTopping = 3;
                SelectedToppingPrice = 0;
                setButtonStyle(topping3Button, topping1Button, topping2Button);
                selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });

        Button ice1Button = optionDialog.findViewById(R.id.etc1);
        Button ice2Button = optionDialog.findViewById(R.id.etc2);
        Button ice3Button = optionDialog.findViewById(R.id.etc3);
            ice1Button.setOnClickListener(view1 -> {
                isIceAmountSelected = true;
                SelectedIce = 1;
                setButtonStyle(ice1Button, ice2Button, ice3Button);
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            ice2Button.setOnClickListener(view1 -> {
                isIceAmountSelected = true;
                SelectedIce = 2;
                setButtonStyle(ice2Button, ice1Button, ice3Button);
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });
            ice3Button.setOnClickListener(view1 -> {
                isIceAmountSelected = true;
                SelectedIce = 3;
                setButtonStyle(ice3Button, ice1Button, ice2Button);
                if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) btnComplete.setEnabled(true);
            });


        // 4. "선택완료" 버튼 클릭 시의 동작 설정
        btnComplete.setOnClickListener(view2 -> {
            // 모든 항목이 선택되었을 때만 선택 완료 버튼이 활성화됨

            // 배열에 옵션정보들 저장 후 선택완료처리
            // 핫/아이스
            if (SelectedHot == 1) {
                menuOption1.add("HOT");
            } else if (SelectedHot == 2) {
                menuOption1.add("ICE");
            }
            // 어떤 사이즈
            if (SelectedSize == 1) {
                menuOption2.add("SMALL");
            } else if (SelectedSize == 2) {
                menuOption2.add("MEDIUM");
            } else if (SelectedSize == 3) {
                menuOption2.add("LARGE");
            }
            // 어떤 시럽
            if (SelectedTopping == 1) {
                menuOption3.add("바닐라 시럽");
            } else if (SelectedTopping == 2) {
                menuOption3.add("헤이즐넛 시럽");
            } else if (SelectedTopping == 3) {
                menuOption3.add("시럽 없음");
            }
            // 얼음 얼마나
            if (SelectedIce == 1) {
                menuOption4.add("얼음양 없이");
            } else if (SelectedIce == 2) {
                menuOption4.add("얼음양 보통");
            } else if (SelectedIce == 3) {
                menuOption4.add("얼음양 많이");
            }
            menuPrice.set(menuPrice.size() - 1, menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice);
            HotPrice.add(0);
            SizePrice.add(0);
            ToppingPrice.add(0);
            HotPrice.set(HotPrice.size() -1, SelectedHotPrice);
            SizePrice.set(SizePrice.size() -1, SelectedSizePrice);
            ToppingPrice.set(ToppingPrice.size() -1, SelectedToppingPrice);

            totalcnt += 1;
            adapter.notifyDataSetChanged();
            optionDialog.dismiss();
        });
    }

    private void setButtonStyle(Button setSelected, Button removeSelected1, Button removeSelected2) {
        setSelected.setBackgroundResource(R.drawable.button_border_selected);
        setSelected.setTextColor(getResources().getColor(android.R.color.black));
        setSelected.setTypeface(null, Typeface.BOLD);
        removeSelected1.setBackgroundResource(R.drawable.button_border);
        removeSelected1.setTextColor(getResources().getColor(android.R.color.black));
        removeSelected1.setTypeface(null, Typeface.NORMAL);
        removeSelected2.setBackgroundResource(R.drawable.button_border);
        removeSelected2.setTextColor(getResources().getColor(android.R.color.black));
        removeSelected2.setTypeface(null, Typeface.NORMAL);
    }

    // 옵션선택창을 처음 열었을 때 초기값 설정
    private void resetSelections() {
        isHotSelected = false;
        isSizeSelected = false;
        isSyrupSelected = false;
        isIceAmountSelected = false;
        SelectedHot = 0;
        SelectedSize = 0;
        SelectedTopping = 0;
        SelectedIce = 0;
        SelectedHotPrice = 0;
        SelectedSizePrice = 0;
        SelectedToppingPrice = 0;
    }

    // 1234 -> 1,234원
    private String getPriceFormattedNumber(int Price) {
        NumberFormat numberFormat = getNumberInstance(Locale.US);
        String formattedPrice = numberFormat.format(Price) + "원";
        return formattedPrice;
    }

    // 결제버튼 눌렀을 시 결제창으로 화면이 넘어가는 온클릭이벤트
    public void onClickedButtonPayment(View view) {
        if (menuName.size() < 1) {
            Toast.makeText(this, "메뉴를 선택해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MenuActivity.this, PaymentActivity.class);
            // 인텐트를 전달할 때, 장바구니 정보에 대한 배열을 같이 넘겨준다.
                // 장바구니 정보
                intent.putStringArrayListExtra("menuName", menuName);
                intent.putIntegerArrayListExtra("menuQuantity", menuQuantity);
                intent.putIntegerArrayListExtra("menuPrice", menuPrice);
                // 총 갯수
                intent.putExtra("totalcnt", totalcnt);
                // 옵션 종류와 가격
                intent.putStringArrayListExtra("menuOption1", menuOption1);
                intent.putStringArrayListExtra("menuOption2", menuOption2);
                intent.putStringArrayListExtra("menuOption3", menuOption3);
                intent.putStringArrayListExtra("menuOption4", menuOption4);
                intent.putIntegerArrayListExtra("HotPrice", HotPrice);
                intent.putIntegerArrayListExtra("SizePrice", SizePrice);
                intent.putIntegerArrayListExtra("ToppingPrice", ToppingPrice);
            startActivity(intent); // PointDialog 액티비티로 이동
        }
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.activity_listitem, menuName);
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.activity_listitem, null, true);

            final TextView textViewMenuName = rowView.findViewById(R.id.textViewMenuName);
            final TextView textViewOption = rowView.findViewById(R.id.textViewOption);
            final TextView textViewPrice = rowView.findViewById(R.id.textViewPrice);
            final TextView textViewQuantity = rowView.findViewById(R.id.textViewQuantity);
            textViewMenuName.setText(menuName.get(position));
            textViewOption.setText(menuOption1.get(position) +" + "+ menuOption2.get(position) +" + "+ menuOption3.get(position) +" + "+ menuOption4.get(position));
            textViewPrice.setText(getPriceFormattedNumber(menuPrice, menuQuantity, position));

            // 리스트 홀수번째 아이템 배경설정
            if (position % 2 != 1) {
                rowView.setBackgroundColor(Color.parseColor("#55CCDEF0"));
                }

            // 총 메뉴의 갯수와 총 가격 구하기
            int totalPrice = 0;
            for (int i = 0; i < menuPrice.size(); i++) {
                totalPrice += menuPrice.get(i)*menuQuantity.get(i);
            }
            NumberFormat numberFormat = getNumberInstance(Locale.US);
            String formattedTotalPrice = numberFormat.format(totalPrice) + "원";
            textViewTotalPrice.setText(formattedTotalPrice);
            textViewTotalCount.setText("총 " + String.valueOf(totalcnt) + "개 결제");

            // 리스트에 아이템이 0개가 되었을 때 0개라고 표시해준다.
            if (menuName.size() == 0) {
                textViewTotalCount.setText("총 0개 결제");
                textViewTotalPrice.setText("0원");
            }

            textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
            buttonMinus = rowView.findViewById(R.id.buttonMinus);
            buttonPlus = rowView.findViewById(R.id.buttonPlus);
            buttonX = rowView.findViewById(R.id.buttonX);
            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuQuantity.get(position) > 1) {
                        menuQuantity.set(position, menuQuantity.get(position) - 1);
                        textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
                        textViewPrice.setText(getPriceFormattedNumber(menuPrice, menuQuantity, position));
                        totalcnt -= 1;
                        textViewTotalCount.setText("총 " + String.valueOf(totalcnt) + "개 결제");
                    }
                    if (menuName.size() == 0) {
                        textViewTotalCount.setText("총 0개 결제");
                        textViewTotalPrice.setText("0원");
                    }
                    notifyDataSetChanged();
                }
            });
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuQuantity.set(position, menuQuantity.get(position) + 1);
                    textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
                    textViewPrice.setText(getPriceFormattedNumber(menuPrice, menuQuantity, position));
                    totalcnt += 1;
                    textViewTotalCount.setText("총 " + String.valueOf(totalcnt) + "개 결제");
                    notifyDataSetChanged();
                }
            });
            buttonX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 리스트에서 해당 아이템 삭제
                    totalcnt -= menuQuantity.get(position);
                    removeItem(position);
                    if (menuName.size() == 0) {
                        textViewTotalCount.setText("총 0개 결제");
                        textViewTotalPrice.setText("0원");
                    }
                    // 어댑터 업데이트
                     notifyDataSetChanged();
                 }
            });
            buttonAC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    while (!menuName.isEmpty()) {
                        removeItem(0);
                    }
                    totalcnt = 0;
                    textViewTotalCount.setText("총 0개 결제");
                    textViewTotalPrice.setText("0원");
                    adapter.notifyDataSetChanged();
                }
            });
            return rowView;
        }

        private String getPriceFormattedNumber(ArrayList<Integer> Price, ArrayList<Integer> Quantity, int itemIndex) {
            NumberFormat numberFormat = getNumberInstance(Locale.US);
            String formattedPrice = numberFormat.format(Price.get(itemIndex)*Quantity.get(itemIndex)) + "원";
            return formattedPrice;
        }

        private void removeItem(int itemIndex) {
            menuName.remove(itemIndex);
            menuQuantity.remove(itemIndex);
            menuPrice.remove(itemIndex);
            menuOption1.remove(itemIndex);
            menuOption2.remove(itemIndex);
            menuOption3.remove(itemIndex);
            HotPrice.remove(itemIndex);
            SizePrice.remove(itemIndex);
            ToppingPrice.remove(itemIndex);
        }
    }
}
