package br.com.alan.walletServiceAssignment.repositories;

import br.com.alan.walletServiceAssignment.entities.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;



public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletCode(String walletCode);

    @Query("SELECT w FROM Wallet w WHERE w.walletCode = :walletCode")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> findByWalletCodeForUpdate(@Param("walletCode") String walletCode);

    @Query(value = """
        SELECT 
            SUM(CASE 
                    WHEN wo.operation = 'WITHDRAW' THEN -wo.amount
                    WHEN wo.operation = 'DEPOSIT' THEN wo.amount
                    ELSE 0
                END) AS total_amount
        FROM wallets w  
        INNER JOIN wallet_operations wo ON wo.wallet_id = w.id 
        WHERE w.wallet_code = :walletCode
          AND wo.created_at <=  :endDate
    """, nativeQuery = true)
    BigDecimal findTotalAmountByWalletCode(
            @Param("walletCode") String walletCode,
            @Param("endDate") LocalDateTime endDate);
}
