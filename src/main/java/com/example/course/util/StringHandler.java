package com.example.course.util;

public class StringHandler {
    public static boolean isNullOrEmptyOrBlank(String str) {
        // Kiểm tra chuỗi null hoặc rỗng hoặc chỉ toàn khoảng trắng
        System.out.println("Check null: " + str);
        return str == null || str.trim().isEmpty();
    }

    public static String extractNumberAtIndex(String input, int n) {
        StringBuilder numberBuilder = new StringBuilder();
        int count = 0; // Đếm số lượng số đã tìm thấy

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                numberBuilder.append(c); // Thêm số vào StringBuilder
            } else {
                // Nếu đã tìm thấy số và gặp ký tự không phải là số, kiểm tra xem có cần tăng số
                // đếm hay không
                if (numberBuilder.length() > 0) {
                    count++;
                    if (count > n) {
                        return numberBuilder.toString(); // Trả về số đã tìm thấy
                    }
                    numberBuilder.setLength(0); // Reset StringBuilder để tìm số tiếp theo
                }
            }
        }

        // Kiểm tra số cuối cùng nếu chuỗi kết thúc bằng số
        if (numberBuilder.length() > 0) {
            count++;
            if (count > n) {
                return numberBuilder.toString(); // Trả về số đã tìm thấy
            }
        }

        return null; // Nếu không tìm thấy số thứ n
    }

}
