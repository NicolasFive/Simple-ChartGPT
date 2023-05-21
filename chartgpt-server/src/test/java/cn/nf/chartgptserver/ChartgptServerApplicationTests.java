package cn.nf.chartgptserver;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class ChartgptServerApplicationTests {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("Admin@2023"));
    }
}
