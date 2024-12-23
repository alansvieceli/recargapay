package br.com.alan.walletServiceAssignment.repositories;

import br.com.alan.walletServiceAssignment.entities.WalletOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletOperationRepository extends JpaRepository<WalletOperation, Long> {}
