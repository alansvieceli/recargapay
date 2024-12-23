package br.com.alan.walletServiceAssignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_operations")
public class WalletOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id")
    private Long walletId;

    @Column(name = "transaction_id", columnDefinition = "UUID", nullable = false)
    private UUID transactionId;

    @Column(name = "operation", nullable = false)
    @Enumerated(EnumType.STRING)
    private br.com.alan.walletServiceAssignment.enums.WalletOperation operation;

    @Builder.Default
    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("GMT"));
}
