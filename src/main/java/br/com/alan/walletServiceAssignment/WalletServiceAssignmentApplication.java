package br.com.alan.walletServiceAssignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WalletServiceAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletServiceAssignmentApplication.class, args);
    }

}
