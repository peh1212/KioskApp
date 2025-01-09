##12/23(목)

#activity_main.xml
![image](https://github.com/user-attachments/assets/8b44e6da-04dd-48d9-9263-d8de583177d0)

#MainActivity.java
'''
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
'''

#activity_menu.xml
![image](https://github.com/user-attachments/assets/9fa42486-02b4-4204-b5b0-40ed23d33794)

