package br.com.alan.walletServiceAssignment.validators;

import br.com.alan.walletServiceAssignment.annotations.ValidWalletStatus;
import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class WalletStatusValidator implements ConstraintValidator<ValidWalletStatus, WalletStatus> {

    @Override
    public boolean isValid(WalletStatus status, ConstraintValidatorContext context) {
        try {
            return status != null && EnumSet.allOf(WalletStatus.class).contains(status);
        } catch (Exception e) {
            return false;
        }
    }
}
