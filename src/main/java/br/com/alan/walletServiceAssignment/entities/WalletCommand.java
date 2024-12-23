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
@Table(name = "wallet_commands")
public class WalletCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "command", nullable = false)
    private String command;

    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @Column(name = "wallet_code", length = 20, nullable = false)
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

}
