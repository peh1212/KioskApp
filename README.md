## 12/23(목)

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
"매장" 또는 "포장"을 선택하면 다음 화면으로 넘어가서 메뉴를 고르는 화면이 나온다.  
두 버튼 모두 아직까지는 다음 화면으로 넘어가는 기능만 구현했다.  

<br/><br/>

# activity_menu.xml
![image](https://github.com/user-attachments/assets/9fa42486-02b4-4204-b5b0-40ed23d33794)   
# activity_listitem.xml
![image](https://github.com/user-attachments/assets/0bbf66da-0c06-4341-aa94-4f386ad49b62)  
메뉴 화면의 좌측 하단에 장바구니를 만들었다.  
리스트뷰와 리스트뷰에 연결할 리스트 아이템을 디자인했다.  
