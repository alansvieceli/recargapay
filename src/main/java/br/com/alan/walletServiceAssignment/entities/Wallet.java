package br.com.alan.walletServiceAssignment.entities;

import br.com.alan.walletServiceAssignment.enums.WalletStatus;
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
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_code", length = 20, nullable = false, unique = true)
    private String walletCode;

    @Column(name = "client_id", columnDefinition = "UUID", nullable = false)
    private UUID clientId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletStatus status;

    @Builder.Default
    @Column(name = "balance", precision = 18, scale = 2, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("GMT"));

    @Builder.Default
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("GMT"));

    @PreUpdate
    public void setLastUpdated() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("GMT"));
    }
}
