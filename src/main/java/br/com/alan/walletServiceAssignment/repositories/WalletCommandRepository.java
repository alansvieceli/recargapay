package br.com.alan.walletServiceAssignment.repositories;

import br.com.alan.walletServiceAssignment.entities.WalletCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCommandRepository extends JpaRepository<WalletCommand, Long> {}
