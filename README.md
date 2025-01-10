## 12/23(월)

# activity_main.xml
![image](https://github.com/user-attachments/assets/8b44e6da-04dd-48d9-9263-d8de583177d0)

# MainActivity.java
```
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
두 버튼 모두 아직까지는 다음 화면으로 넘어가는 기능만 구현했다.  

<br/><br/>

# activity_menu.xml
![image](https://github.com/user-attachments/assets/9fa42486-02b4-4204-b5b0-40ed23d33794)   
# activity_listitem.xml
![image](https://github.com/user-attachments/assets/0bbf66da-0c06-4341-aa94-4f386ad49b62)  
메뉴 화면의 좌측 하단에 장바구니를 만들었다.  
리스트뷰와 리스트뷰에 연결할 리스트 아이템을 디자인했다.  

<br/><br/>

## 12/24(화)
# MenuActivity
```
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
    String[] menuOption1Array = {
            "ICE",
            "ICE",
            "HOT",
            "ICE",
            "HOT",
            "HOT",
            "ICE",
    };
    String[] menuOption2Array = {
            "바닐라시럽토핑",
            "바닐라시럽토핑",
            "선택없음",
            "헤이즐넛시럽토핑",
            "선택없음",
            "헤이즐넛시럽토핑",
            "바닐라시럽토핑",
    };
    String[] menuOption3Array = {
            "얼음 많이",
            "얼음 적게",
            "얼음 적게",
            "얼음 많이",
            "얼음 조금",
            "얼음 없이",
            "얼음 없이",
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
배열에는 먼저 임의로 커피 이름과 가격을 넣어놓고, 리스트뷰가 잘 만들어지는지 확인하였다.

<br/><br/>

## 12/26(목)
