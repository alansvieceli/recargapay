package br.com.alan.walletServiceAssignment.annotations;

import br.com.alan.walletServiceAssignment.validators.WalletStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WalletStatusValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWalletStatus {
    String message() default "Status inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
