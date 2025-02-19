# 12/23(월)

## activity_main.xml
![image](https://github.com/user-attachments/assets/8b44e6da-04dd-48d9-9263-d8de583177d0)

## MainActivity.java
```Java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void onClickedButtonMain(View view) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
```
먼저 간단하게 메인화면을 만들었다.  
인텐트를 이용하여 "매장" 또는 "포장"을 선택하면 다음 화면으로 넘어가서 메뉴를 고르는 화면이 나오도록 하였다.  

<br/><br/>

## activity_menu.xml
![image](https://github.com/user-attachments/assets/9fa42486-02b4-4204-b5b0-40ed23d33794)   
## activity_listitem.xml
![image](https://github.com/user-attachments/assets/0bbf66da-0c06-4341-aa94-4f386ad49b62)  
메뉴 화면의 좌측 하단에 장바구니를 만들었다.  
리스트뷰와 리스트뷰에 연결할 리스트 아이템을 디자인하였다.  

<br/><br/>

# 12/24(화)
## MenuActivity.java
```Java
public class MenuActivity extends AppCompatActivity {
    ListView basketList;
    String[] menuNameArray = {
            "에스프레소",
            "카페라떼",
            "블루레몬에이드",
            "크림치즈베이글",
            "크로와상",
            "라벤더카페브레베",
            "바닐라빈라떼",
    };
    int[] menuQuantityArray = {
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };
    int[] menuPriceArray = {
            7600,
            4800,
            6000,
            3800,
            3800,
            3000,
            3000
    };
    CustomList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        adapter = new CustomList(MenuActivity.this);

        basketList=(ListView)findViewById(R.id.basketList);
        basketList.setAdapter(adapter);
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context) {
            super(context, R.layout.activity_listitem, menuNameArray);
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.activity_listitem, null, true);

            textViewMenuName.setText(menuName.get(position));
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedPrice =             numberFormat.format(menuPrice.get(position)*menuQuantity.get(position)) + "원";
            textViewPrice.setText(formattedPrice);
            return rowView;
        }
    }
```
어댑터를 생성하여 배열을 리스트뷰에 연결하였다.  
어댑터는 CustomList 클래스로 정의하여 GetView의 동작을 Override해주었다.  
배열에는 먼저 임의로 커피 이름과 가격을 넣어놓고, 리스트뷰가 잘 만들어지는지 확인하였다.

<br/><br/>

# 12/26(목)
## MenuActivity.java
```Java

...

public class CustomList extends ArrayAdapter<String> {

        ...

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            ...

            textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
            buttonMinus = rowView.findViewById(R.id.buttonMinus);
            buttonPlus = rowView.findViewById(R.id.buttonPlus);
            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuQuantity.get(position) > 0) {
                        menuQuantity.set(position, menuQuantity.get(position) - 1);
                        textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
                        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                        String formattedPrice = numberFormat.format(menuPrice.get(position)*menuQuantity.get(position)) + "원";
                        textViewPrice.setText(formattedPrice);
                    }
                    notifyDataSetChanged();
                }
            });
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuQuantity.set(position, menuQuantity.get(position) + 1);
                    textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    String formattedPrice = numberFormat.format(menuPrice.get(position)*menuQuantity.get(position)) + "원";
                    textViewPrice.setText(formattedPrice);
                    notifyDataSetChanged();
                }
            });
```
장바구니에 들어있는 메뉴의 갯수를 조절하는 버튼(+/-)의 기능을 구현하였다.  
+버튼과 -버튼을 각각 이벤트리스너에 등록하여 수량이 수정되도록 하였다.  

<br/><br/>

# 12/27(금)
## activity_menu.xml
![image](https://github.com/user-attachments/assets/4a7db33c-7322-488f-af99-7fd13f7aa0d0)  
장바구니 리스트 우측에, 메뉴의 총 갯수와 가격, 전체삭제 버튼의 기능을 구현하였다.  

## MenuActivity.java
```Java
public class MenuActivity extends AppCompatActivity {
    ListView basketList;
    String[] menuNameArray = {
            "에스프레소",
            "카페라떼",
            "블루레몬에이드",
            "크림치즈베이글",
            "크로와상",
            "라벤더카페브레베",
            "바닐라빈라떼",
    };
    int[] menuQuantityArray = {
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };
    int[] menuPriceArray = {
            7600,
            4800,
            6000,
            3800,
            3800,
            3000,
            3000
    };
    ArrayList<String> menuName;
    ArrayList<Integer> menuQuantity;
    ArrayList<Integer> menuPrice;
    CustomList adapter;

    TextView textViewMenuName, textViewPrice, textViewTotalCount, textViewTotalPrice, textViewOption;
    Button buttonMinus, buttonPlus, buttonX, buttonAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 아이템을 동적으로 관리하기 위하여 배열을 어레이리스트로 만든다.
        menuName = new ArrayList<>(Arrays.asList(menuNameArray));
        menuQuantity = new ArrayList<>();
        for (int quantity : menuQuantityArray) {
            menuQuantity.add(quantity);
        }
        menuPrice = new ArrayList<>();
        for (int price : menuPriceArray) {
            menuPrice.add(price);
        }
        adapter = new CustomList(MenuActivity.this);

        // 메뉴를 눌렀을 때 배열에 아이템 추가하기
        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuName.add("카페라떼");
                menuQuantity.add(1);
                menuPrice.add(4800);
                adapter.notifyDataSetChanged();
            }
        });

        buttonAC = findViewById(R.id.buttonAC);
        textViewTotalCount = findViewById(R.id.textViewTotalCount);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);

        basketList=(ListView)findViewById(R.id.basketList);
        basketList.setAdapter(adapter);
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
            textViewPrice.setText(getPriceFormattedNumber(menuPrice, menuQuantity, position));

            // 총 메뉴의 갯수와 총 가격 구하기
            textViewTotalCount.setText("총 " + String.valueOf(menuName.size()) + "개 결제");
            int totalPrice = 0;
            for (int i = 0; i < menuPrice.size(); i++) {
                totalPrice += menuPrice.get(i)*menuQuantity.get(i);
            }
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedTotalPrice = numberFormat.format(totalPrice) + "원";
            textViewTotalPrice.setText(formattedTotalPrice);

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
                    if (menuQuantity.get(position) > 0) {
                        menuQuantity.set(position, menuQuantity.get(position) - 1);
                        textViewQuantity.setText(String.valueOf(menuQuantity.get(position)));
                        textViewPrice.setText(getPriceFormattedNumber(menuPrice, menuQuantity, position));
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
                    notifyDataSetChanged();
                }
            });
            buttonX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 리스트에서 해당 아이템 삭제
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
                    textViewTotalCount.setText("총 0개 결제");
                    textViewTotalPrice.setText("0원");
                    adapter.notifyDataSetChanged();
                }
            });
            return rowView;
        }

        private String getPriceFormattedNumber(ArrayList<Integer> Price, ArrayList<Integer> Quantity, int itemIndex) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedPrice = numberFormat.format(Price.get(itemIndex)*Quantity.get(itemIndex)) + "원";
            return formattedPrice;
        }

        private void removeItem(int itemIndex) {
            menuName.remove(itemIndex);
            menuQuantity.remove(itemIndex);
            menuPrice.remove(itemIndex);
        }
    }

}
```
장바구니 리스트를 동적으로 관리하기 위하여 배열을 ArrayList로 만들었다.  
장바구니 리스트의 추가/삭제 버튼의 기능을 구현하였다.  
임시로 버튼을 하나 만들어, 이 버튼을 눌렀을 때 장바구니 리스트에 "카페라떼"라는 메뉴가 하나씩 추가되도록 해보았다.  
리스트 우측에 삭제(X) 버튼을 누르면 해당 리스트(배열)이 삭제되도록 하였다.  
<br/>
장바구니 우측에 장바구니 리스트의 총 갯수와 총 가격을 표시해주고, 전체삭제 기능을 구현하였다.  
![kioskappgif1-min](https://github.com/user-attachments/assets/a9f65471-61dc-4c6e-b43e-8e826c2b2993)  

<br/><br/>

# 12/30(월)~12/31(화)
팀원들이 작업한 코드와 연결을 시도해보았다.
## activity_option.xml
![image](https://github.com/user-attachments/assets/e8e83fa7-e7be-4011-a3a8-2fe7957e7b89)  
메뉴를 한 가지 선택했을 때 나오는 옵션을 선택할 수 있는 화면이다.  
옵션이 4가지(핫/아이스, 사이즈, 시럽, 얼음양)가 있고, 옵션 4가지를 모두 선택하면 선택완료 버튼이 활성화된다.  
선택된 옵션에는 버튼에 청색 테두리가 생기도록 코드를 구현하였다.  
이 청색 테두리 스타일은 @drawable/button_border_selected.xml에 작성하였다.  

## MenuActivity.java
```Java
public class MenuActivity extends AppCompatActivity {

    ListView basketList;
    String[] menuNameArray = {};
    String[] menuOption1Array = {};
    String[] menuOption2Array = {};
    String[] menuOption3Array = {};
    String[] menuOption4Array = {};
    int[] menuQuantityArray = {};
    int[] menuPriceArray = {};
    ArrayList<String> menuName;
    ArrayList<String> menuOption1;
    ArrayList<String> menuOption2;
    ArrayList<String> menuOption3;
    ArrayList<String> menuOption4;
    ArrayList<Integer> menuQuantity;
    ArrayList<Integer> menuPrice;
    CustomList adapter;

    TextView textViewMenuName, textViewPrice, textViewTotalCount, textViewTotalPrice, textViewOption, selectMenuName, selectMenuPrice;
    Button buttonAddItem, buttonMinus, buttonPlus, buttonX, buttonAC, buttonAddItem1, buttonAddItem2, buttonAddItem3, buttonAddItem4;

    private boolean isHotSelected = false;
    private int SelectedHot = 0;
    private int SelectedHotPrice = 0;
    private boolean isSizeSelected = false;
    private int SelectedSize = 0;
    private int SelectedSizePrice = 0;
    private boolean isSyrupSelected = false;
    private int SelectedTopping = 0;
    private int SelectedToppingPrice = 0;
    private boolean isIceAmountSelected = false;
    private int SelectedIce = 0;
    private Button btnComplete;


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
        menuQuantity = new ArrayList<>();
        for (int quantity : menuQuantityArray) {
            menuQuantity.add(quantity);
        }
        menuPrice = new ArrayList<>();
        for (int price : menuPriceArray) {
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

        // 폰트를 가져온다.
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/NanumBarunGothic-YetHangul.ttf");
        btnComplete.setTypeface(customFont);
        btnComplete.setEnabled(false);

        // 2. 버튼 리스너 설정: 여러 버튼들에 대해 클릭 리스너 설정
        int[] buttonIds = {
                R.id.btn_hot, R.id.btn_ice,  // 핫/아이스 버튼
                R.id.btn_size_small, R.id.btn_size_medium, R.id.btn_size_large,  // 사이즈 선택 버튼
                R.id.topping1, R.id.topping2, R.id.topping3, // 시럽 선택 버튼
                R.id.etc1, R.id.etc2, R.id.etc3 // 얼음 양 선택 버튼
        };

        // 3. 각 버튼에 클릭 리스너를 설정: 버튼 클릭 시 onOptionClicked() 메서드 호출
        for (int id : buttonIds) {
            optionDialog.findViewById(id).setOnClickListener(MenuActivity.this::onOptionClicked); // 각 버튼에 클릭 리스너 연결
        }

        // 선택한 메뉴를 배열에 저장하고 화면에 보여준다.
        buttonAddItem1 = findViewById(R.id.buttonAddItem1);
        buttonAddItem2 = findViewById(R.id.buttonAddItem2);
        buttonAddItem3 = findViewById(R.id.buttonAddItem3);
        buttonAddItem4 = findViewById(R.id.buttonAddItem4);
        if (v.getId() == buttonAddItem1.getId()) {
            menuName.add("카페라떼");
            menuPrice.add(4800);
        } else if (v.getId() == buttonAddItem2.getId()) {
            menuName.add("에스프레소");
            menuPrice.add(3800);
        } else if (v.getId() == buttonAddItem3.getId()) {
            menuName.add("아메리카노");
            menuPrice.add(3300);
        } else if (v.getId() == buttonAddItem4.getId()) {
            menuName.add("레몬에이드");
            menuPrice.add(6000);
        }
        menuQuantity.add(1);
        selectMenuName.setText(String.valueOf(menuName.get(menuName.size() - 1)));
        selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1)));

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

        // 4. "선택완료" 버튼 클릭 시의 동작 설정
        btnComplete.setOnClickListener(view2 -> {
            // 모든 항목이 선택되었을 때만 선택 완료 버튼이 활성화됨
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) {
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
                resetSelections();
                adapter.notifyDataSetChanged();
                optionDialog.dismiss();
            }
        });
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

    // 5. 버튼 클릭 시 호출되는 메서드: 선택한 옵션에 따라 상태 갱신
    public void onOptionClicked(View view) {
        // 뷰(선택한 옵션)들은 activity_option.xml 에 있으므로 인플레이터로 가져온다.
        View optionView = getLayoutInflater().inflate(R.layout.activity_option, null);
        Button hotButton = optionView.findViewById(R.id.btn_hot);
        Button iceButton = optionView.findViewById(R.id.btn_ice);
        Button smallButton = optionView.findViewById(R.id.btn_size_small);
        Button mediumButton = optionView.findViewById(R.id.btn_size_medium);
        Button largeButton = optionView.findViewById(R.id.btn_size_large);
        Button topping1Button = optionView.findViewById(R.id.topping1);
        Button topping2Button = optionView.findViewById(R.id.topping2);
        Button topping3Button = optionView.findViewById(R.id.topping3);
        Button ice1Button = optionView.findViewById(R.id.etc1);
        Button ice2Button = optionView.findViewById(R.id.etc2);
        Button ice3Button = optionView.findViewById(R.id.etc3);

        // 6. 핫/아이스 선택 버튼 클릭 시
        if (view.getId() == hotButton.getId() || view.getId() == iceButton.getId()) {
            // 핫/아이스 선택 상태 갱신
            isHotSelected = true;

            // 핫 선택했는지 아이스 선택했는지 저장
                if (view.getId() == hotButton.getId()) {
                    SelectedHot = 1;
                    SelectedHotPrice = 0;
                } else if (view.getId() == iceButton.getId()) {
                    SelectedHot = 2;
                    SelectedHotPrice = 500;
                }

            // 7. 선택된 버튼에 스타일 적용
            updateButtonState(hotButton, view.getId() == hotButton.getId()); // 핫 버튼 스타일 갱신
            updateButtonState(iceButton, view.getId() == iceButton.getId()); // 아이스 버튼 스타일 갱신
        }
        // 8. 사이즈 버튼 클릭 시
        if (view.getId() == smallButton.getId() || view.getId() == mediumButton.getId() || view.getId() == largeButton.getId()) {
            // 사이즈 항목이 선택되었으면 true로 설정
            isSizeSelected = true;

            // 어떤 사이즈 선택했는지 저장
            if (view.getId() == smallButton.getId()) {
                SelectedSize = 1;
                SelectedSizePrice = 0;
            } else if (view.getId() == mediumButton.getId()) {
                SelectedSize = 2;
                SelectedSizePrice = 500;
            } else if (view.getId() == largeButton.getId()) {
                SelectedSize = 3;
                SelectedSizePrice = 1000;
            }

            // 9. 선택된 버튼에 스타일 적용
            updateButtonState(smallButton, view.getId() == smallButton.getId()); // 작은 사이즈 버튼 스타일 갱신
            updateButtonState(mediumButton, view.getId() == mediumButton.getId()); // 중간 사이즈 버튼 스타일 갱신
            updateButtonState(largeButton, view.getId() == largeButton.getId()); // 큰 사이즈 버튼 스타일 갱신
        }
        // 10. 시럽 버튼 클릭 시
        if (view.getId() == topping1Button.getId() || view.getId() == topping2Button.getId() || view.getId() == topping3Button.getId()) {
            // 시럽 항목이 선택되었으면 true로 설정
            isSyrupSelected = true;

            // 어떤 시럽 선택했는지 저장
            if (view.getId() == topping1Button.getId()) {
                SelectedTopping = 1;
                SelectedToppingPrice = 500;
            } else if (view.getId() == topping2Button.getId()) {
                SelectedTopping = 2;
                SelectedToppingPrice = 500;
            } else if (view.getId() == topping3Button.getId()) {
                SelectedTopping = 3;
                SelectedToppingPrice = 0;
            }

            // 11. 선택된 버튼에 스타일 적용
            updateButtonState(topping1Button, view.getId() == topping1Button.getId()); // 첫 번째 시럽 버튼 스타일 갱신
            updateButtonState(topping2Button, view.getId() == topping2Button.getId()); // 두 번째 시럽 버튼 스타일 갱신
            updateButtonState(topping3Button, view.getId() == topping3Button.getId()); // 세 번째 시럽 버튼 스타일 갱신
        }
        // 12. 얼음 양 버튼 클릭 시
        if (view.getId() == ice1Button.getId() || view.getId() == ice2Button.getId() || view.getId() == ice3Button.getId()) {
            // 얼음 양 항목이 선택되었으면 true로 설정
            isIceAmountSelected = true;

            // 얼음양 어떤거 선택했는지 저장
            if (view.getId() == ice1Button.getId()) {
                SelectedIce = 1;
            } else if (view.getId() == ice2Button.getId()) {
                SelectedIce = 2;
            } else if (view.getId() == ice3Button.getId()) {
                SelectedIce = 3;
            }

            // 13. 선택된 버튼에 스타일 적용
            updateButtonState(ice1Button, view.getId() == ice1Button.getId()); // 첫 번째 얼음 양 버튼 스타일 갱신
            updateButtonState(ice2Button, view.getId() == ice2Button.getId()); // 두 번째 얼음 양 버튼 스타일 갱신
            updateButtonState(ice3Button, view.getId() == ice3Button.getId()); // 세 번째 얼음 양 버튼 스타일 갱신
        }

        // 옵션 선택시마다 가격을 갱신해서 띄워준다.
        selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) +SelectedHotPrice +SelectedSizePrice +SelectedToppingPrice));

        // 14. 모든 항목이 선택되었으면 "선택완료" 버튼을 활성화
        if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected) {
            btnComplete.setEnabled(true); // "선택완료" 버튼을 활성화
        } else {
            btnComplete.setEnabled(false);  // 하나라도 선택되지 않으면 "선택완료" 버튼을 비활성화
        }
    }

    // 15. 버튼 스타일 업데이트 메서드: 선택된 버튼과 선택되지 않은 버튼에 대한 스타일 변경
    private void updateButtonState(View view, boolean isSelected) {
        if (view != null && view instanceof Button) {
        Button button = (Button) view; // 버튼 뷰를 Button 객체로 변환
            if (isSelected) {
                // 16. 버튼이 선택된 경우 스타일 적용
                button.setBackgroundResource(R.drawable.button_border_selected);  // 선택된 버튼 테두리
                button.setTextColor(getResources().getColor(android.R.color.black));  // 선택된 버튼의 텍스트 색상
                button.setTypeface(null, Typeface.BOLD);  // 선택된 버튼의 텍스트를 굵게 설정
            } else {
                // 17. 버튼이 선택되지 않은 경우 기본 스타일로 복귀
                button.setBackgroundResource(R.drawable.button_border);  // 기본 버튼 테두리
                button.setTextColor(getResources().getColor(android.R.color.black));  // 기본 버튼의 텍스트 색상
                button.setTypeface(null, Typeface.NORMAL);  // 기본 텍스트 스타일
            }
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
            textViewTotalCount.setText("총 " + String.valueOf(menuName.size()) + "개 결제");
            int totalPrice = 0;
            for (int i = 0; i < menuPrice.size(); i++) {
                totalPrice += menuPrice.get(i)*menuQuantity.get(i);
            }
            NumberFormat numberFormat = getNumberInstance(Locale.US);
            String formattedTotalPrice = numberFormat.format(totalPrice) + "원";
            textViewTotalPrice.setText(formattedTotalPrice);

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
                    notifyDataSetChanged();
                }
            });
            buttonX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 리스트에서 해당 아이템 삭제
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
        }
    }
}
```
장바구니 배열에 메뉴 옵션 항목 4가지를 추가하였다.  
장바구니에 아이템을 추가하는 버튼에 옵션 선택 화면을 연결하였다.  
액티비티로 전환하지 않고, Dialog로 커스텀 대화상자를 띄워 옵션 선택 화면 레이아웃을 연결하였다.  
옵션 버튼을 선택했을 시, 버튼 스타일이 적용되지 않는 문제가 생겼는데 당장 해결하지는 못하였고 일단 넘어가고 다른 기능들부터 구현을 하였다.  
어떤 옵션을 선택는지는 알 수 없지만, 옵션 4가지를 모두 선택하면 선택완료 버튼이 활성화되고 나머지 코드들이 제대로 작동하는 것을 확인하였다.  
장바구니에 아이템을 추가하는 버튼을 4개로 늘리고, 각 버튼마다 다른 아이템이 추가되도록 구현해보았다.  
옵션을 선택할 때마다 가격이 갱신되고, 메뉴 수량 변경, 메뉴 추가, 삭제를 했을 때 총 메뉴의 갯수와 총 가격이 갱신되도록 하였다.

<br/><br/>

# 1/2(목)~1/3(금)
결제 화면과 합쳤다.  
## activity_payment.xml
![image](https://github.com/user-attachments/assets/7c179fd6-9765-47ef-a73b-4b04ed1aa3ac)  
## activity_paymentlist.xml
![image](https://github.com/user-attachments/assets/d1571f9b-1d29-40b3-ae91-04a3922ab4b1)  
결제 버튼을 눌렀을 시 해당 화면으로 넘어온다.  
액티비티로 전환되도록 하였다.  

## MenuActivity.java
```Java
public class MenuActivity extends AppCompatActivity {

    ...

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
```
장바구니에 있었던 아이템들은 인텐트로 같이 전달된다.  

## PaymentActivity.Java
```Java
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
```
인텐트로 넘어온 배열을 결제화면 액티비티에서 받아서 리스트뷰에 연결해준다.  
결제화면 액티비티와 연결된 다른 액티비티들은 그대로 사용 가능했다.  

## MenuActivity.Java
```Java
public class MenuActivity extends AppCompatActivity {

    ...

    // 메뉴를 하나 골랐을 때 나타나는 옵션선택창
    public void buttonAddItem(View v) {
        // 커스텀 대화메뉴를 생성하여 띄운다.
        Dialog optionDialog = new Dialog(MenuActivity.this);
        optionDialog.setContentView(R.layout.activity_option);
        optionDialog.show();
        resetSelections();
        
        ...

        Button hotButton = optionDialog.findViewById(R.id.btn_hot);
        Button iceButton = optionDialog.findViewById(R.id.btn_ice);
        hotButton.setOnClickListener(view1 -> {
            isHotSelected = true;
            SelectedHot = 1;
            SelectedHotPrice = 0;
            setButtonStyle(hotButton, iceButton, iceButton);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        iceButton.setOnClickListener(view1 -> {
            isHotSelected = true;
            SelectedHot = 2;
            SelectedHotPrice = 500;
            setButtonStyle(iceButton, hotButton, hotButton);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });

        Button smallButton = optionDialog.findViewById(R.id.btn_size_small);
        Button mediumButton = optionDialog.findViewById(R.id.btn_size_medium);
        Button largeButton = optionDialog.findViewById(R.id.btn_size_large);
        smallButton.setOnClickListener(view1 -> {
            isSizeSelected = true;
            SelectedSize = 1;
            SelectedSizePrice = 0;
            setButtonStyle(smallButton, mediumButton, largeButton);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        mediumButton.setOnClickListener(view1 -> {
            isSizeSelected = true;
            SelectedSize = 2;
            SelectedSizePrice = 500;
            setButtonStyle(mediumButton, smallButton, largeButton);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        largeButton.setOnClickListener(view1 -> {
            isSizeSelected = true;
            SelectedSize = 3;
            SelectedSizePrice = 1000;
            setButtonStyle(largeButton, smallButton, mediumButton);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });

        Button topping1Button = optionDialog.findViewById(R.id.topping1);
        Button topping2Button = optionDialog.findViewById(R.id.topping2);
        Button topping3Button = optionDialog.findViewById(R.id.topping3);
        topping1Button.setOnClickListener(view1 -> {
            isSyrupSelected = true;
            SelectedTopping = 1;
            SelectedToppingPrice = 500;
            setButtonStyle(topping1Button, topping2Button, topping3Button);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        topping2Button.setOnClickListener(view1 -> {
            isSyrupSelected = true;
            SelectedTopping = 2;
            SelectedToppingPrice = 500;
            setButtonStyle(topping2Button, topping1Button, topping3Button);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        topping3Button.setOnClickListener(view1 -> {
            isSyrupSelected = true;
            SelectedTopping = 3;
            SelectedToppingPrice = 0;
            setButtonStyle(topping3Button, topping1Button, topping2Button);
            selectMenuPrice.setText(getPriceFormattedNumber(menuPrice.get(menuPrice.size() - 1) + SelectedHotPrice + SelectedSizePrice + SelectedToppingPrice));
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });

        Button ice1Button = optionDialog.findViewById(R.id.etc1);
        Button ice2Button = optionDialog.findViewById(R.id.etc2);
        Button ice3Button = optionDialog.findViewById(R.id.etc3);
        ice1Button.setOnClickListener(view1 -> {
            isIceAmountSelected = true;
            SelectedIce = 1;
            setButtonStyle(ice1Button, ice2Button, ice3Button);
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        ice2Button.setOnClickListener(view1 -> {
            isIceAmountSelected = true;
            SelectedIce = 2;
            setButtonStyle(ice2Button, ice1Button, ice3Button);
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });
        ice3Button.setOnClickListener(view1 -> {
            isIceAmountSelected = true;
            SelectedIce = 3;
            setButtonStyle(ice3Button, ice1Button, ice2Button);
            if (isHotSelected && isSizeSelected && isSyrupSelected && isIceAmountSelected)
                btnComplete.setEnabled(true);
        });

        ...

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
    
    ...

}
```
옵션 버튼을 눌렀을 시 버튼 스타일이 적용되지 않는 문제도 해결하였다.  
GPT에게 물어보니 버튼 뷰를 제대로 참조하지 못해서 생기는 문제라고 해서, 메뉴 추가 버튼을 눌렀을 때 뜨는 Dialog 안에서 옵션 선택들을 처리하도록 OnOptionClicked 코드를 buttonAddItem 안으로 옮긴 후 정리하였다.  

<br/><br/>

# 1/6(월)~1/7(화)
메뉴 화면에 탭 레이아웃과 뷰 페이저와 합쳤다.  
## activity_menu.xml
![image](https://github.com/user-attachments/assets/4e1d7dfd-7bc2-4ce6-9e56-f13400ec39e9)  
## fragment_2.xml
![image](https://github.com/user-attachments/assets/5be21d5c-652b-454f-94cf-1a90fcc313d0)  
탭 레이아웃으로 5개의 탭(추천, 커피, 논커피, 스무디, 주스)을 만들고, 각각의 탭에 프래그먼트(1~5, 총 5개)를 뷰 페이저로 연결하였다.  
## FragmentAdapter.Java
```Java
public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment1();
            case 1: return new Fragment2();
            case 2: return new Fragment3();
            case 3: return new Fragment4();
            case 4: return new Fragment5();
            default: return new Fragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // 총 프래그먼트 수
    }
}
```
## Fragment2.Java
```Java
public class Fragment2 extends Fragment {
    private MenuActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            activity = (MenuActivity) context;
        }
    }

    public static Fragment2 newInstance(MenuActivity activity) {
        Fragment2 fragment = new Fragment2();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);


        ImageButton buttonAddItem9 = view.findViewById(R.id.buttonAddItem9);
        buttonAddItem9.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem10 = view.findViewById(R.id.buttonAddItem10);
        buttonAddItem10.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem11 = view.findViewById(R.id.buttonAddItem11);
        buttonAddItem11.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem12 = view.findViewById(R.id.buttonAddItem12);
        buttonAddItem12.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem13 = view.findViewById(R.id.buttonAddItem13);
        buttonAddItem13.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem14 = view.findViewById(R.id.buttonAddItem14);
        buttonAddItem14.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem15 = view.findViewById(R.id.buttonAddItem15);
        buttonAddItem15.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        ImageButton buttonAddItem16 = view.findViewById(R.id.buttonAddItem16);
        buttonAddItem16.setOnClickListener(v -> {
            if (getActivity() instanceof MenuActivity) {
                ((MenuActivity) getActivity()).buttonAddItem(v);
            }
        });

        return view;
    }
}
```
## MenuActivity.Java
```Java
public class MenuActivity extends AppCompatActivity {

    ...

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ...

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

        ...

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
        
        ...
        
    }
}
```
메뉴 액티비티의 onCreate에서 탭 레이아웃과 뷰 페이저를 연결하였다.  
각 프래그먼트에서 메뉴 버튼을 하나 클릭하면, MenuActivity에서 구현해놓은 메뉴 추가 버튼(buttonAddItem)을 호출하여, 프래그먼트에서 선택한 버튼 Id에 맞는 메뉴 이름과 가격과 설명이 리스트에 추가된다.  
메뉴를 선택했을 때, 다른 프래그먼트에 있는 메뉴 버튼의 View는 null이 되어버리는 문제가 생겨서 if문의 각 조건에서 null체크를 추가하였다.  
메뉴는 메가커피 홈페이지를 참고하였다.  
(출처 https://mega-mgccoffee.com/)  

<br><br>


# 2/19(수)
백엔드 개발하기 <br>
![image](https://github.com/user-attachments/assets/04758558-afd0-4451-b94b-f86167b307a2) <br>
https://start.spring.io/ <br>
start.spring.io에서 프로젝트를 생성한다. <br>
dependency에는 `Lombok`, `Mustache`, `Spring Web`, `Spring Data JPA`, `PostgreSQL Dirver`를 추가한다. <br>
`IntelliJ IDEA`에서 프로젝트를 열어서 코드를 작성한다. <br>

## CoffeeDTO.Java
```Java
package com.example.KioskApp.dto;

import com.example.KioskApp.entity.CoffeeEntity;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CoffeeDTO {
    private Long id;
    private String coffeeName;
    private Integer coffeePrice;
    private Integer coffeeQuantity;
    private String optionHot;
    private String optionSize;
    private String optionTopping;
    private String optionIce;
    private Integer addedPriceHot;
    private Integer addedPriceSize;
    private Integer addedPriceTopping;

    public CoffeeEntity toEntity() {
        return new CoffeeEntity(id, coffeeName, coffeeQuantity,
                optionHot, optionSize, optionTopping, optionIce,
                (coffeePrice+addedPriceHot+addedPriceSize+addedPriceTopping)*coffeeQuantity);
    }
}
```

장바구니에 담은 커피에 대한 정보를 전송하기 위한 DTO와 toEntity를 만들어준다. <br>

## CoffeeEntity.Java
```Java
package com.example.KioskApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class CoffeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String coffeeName;
    @Column
    private Integer coffeeQuantity;
    @Column
    private String optionHot;
    @Column
    private String optionSize;
    @Column
    private String optionTopping;
    @Column
    private String optionIce;
    @Column
    private Integer totalPrice;

    public void patch(CoffeeEntity coffeeEntity) {
        if (coffeeEntity.coffeeName != null)
            this.coffeeName = coffeeEntity.coffeeName;
        if (coffeeEntity.coffeeQuantity != null)
            this.coffeeQuantity = coffeeEntity.coffeeQuantity;
        if (coffeeEntity.optionHot != null)
            this.optionHot = coffeeEntity.optionHot;
        if (coffeeEntity.optionSize != null)
            this.optionSize = coffeeEntity.optionSize;
        if (coffeeEntity.optionTopping != null)
            this.optionTopping = coffeeEntity.optionTopping;
        if (coffeeEntity.optionIce != null)
            this.optionIce = coffeeEntity.optionIce;
        if (coffeeEntity.totalPrice != null)
            this.totalPrice = coffeeEntity.totalPrice;
    }
}
```

커피 정보를 레파지토리에 저장하기 위한 Entity를 구현한다. <br>

## CoffeeRepository.Java
```Java
package com.example.KioskApp.repository;

import com.example.KioskApp.entity.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {
}
```

레파지토리 인터페이스를 구현한다. <br>

## CoffeeService.Java
```Java
package com.example.KioskApp.service;

import com.example.KioskApp.dto.CoffeeDTO;
import com.example.KioskApp.entity.CoffeeEntity;
import com.example.KioskApp.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // 모든 커피 엔티티 조회
    public Iterable<CoffeeEntity> index() {
        return coffeeRepository.findAll();
    }

    // 특정 커피 엔티티 조회
    public CoffeeEntity show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // 커피 엔티티 생성
    public CoffeeEntity create(CoffeeDTO coffeeDTO) {
        CoffeeEntity coffeeEntity = coffeeDTO.toEntity();
        if (coffeeEntity.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffeeEntity);
    }

    // 기존 커피 엔티티 수정
    public CoffeeEntity update(Long id, CoffeeDTO coffeeDTO) {
        CoffeeEntity coffeeEntity = coffeeDTO.toEntity();
        log.info("id: {}, coffee: {}", id, coffeeEntity.toString());

        CoffeeEntity target = coffeeRepository.findById(id).orElse(null);

        if (target == null || id != coffeeEntity.getId()) {
            log.info("잘못된 요청! id: {}, coffee: {}", id, coffeeEntity.toString());
            return null;
        }
        target.patch(coffeeEntity);
        return coffeeRepository.save(target);
    }

    // 특정 커피 엔티티 삭제
    public CoffeeEntity delete(Long id) {
        CoffeeEntity target = coffeeRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}
```

Service를 생성하고 DTO를 처리하기 위한 로직을 구현한다. <br>
