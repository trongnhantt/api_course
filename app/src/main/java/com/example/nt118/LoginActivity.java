public class LoginActivity extends AppCompatActivity {

    private EditText etStudentId, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        etStudentId = findViewById(R.id.etEmail); // Giữ nguyên ID trong layout
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String studentId = etStudentId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (studentId.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this,
                        "Vui lòng nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            RetrofitClient.getInstance()
                    .getAuthApi()
                    .login(new LoginRequest(studentId, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse.isSuccess()) {
                                    // Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this,
                                            "Đăng nhập thành công",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeCourseActivity.class));
                                    finish();
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(LoginActivity.this,
                                            loginResponse.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Xử lý lỗi HTTP
                                Toast.makeText(LoginActivity.this,
                                        "Lỗi server: " + response.code(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // Xử lý lỗi kết nối
                            Toast.makeText(LoginActivity.this,
                                    "Lỗi kết nối: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            t.printStackTrace(); // In log để debug
                        }
                    });
        });
    }
} 