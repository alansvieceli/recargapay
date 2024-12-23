package br.com.alan.walletServiceAssignment.controllers;

import br.com.alan.walletServiceAssignment.dtos.*;
import br.com.alan.walletServiceAssignment.enums.WalletOperation;
import br.com.alan.walletServiceAssignment.usecases.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Wallet")
@RestController()
@RequestMapping(value = "${api.prefix}/${api.version}/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class WalletController {

    private final CreateUseCase walletCreateUseCase;
    private final StatusUseCase walletStatusUseCase;
    private final UpdateStatusUseCase walletUpdateStatusUseCase;
    private final SimpleOperationUseCase walletOperationUseCase;
    private final BalanceUseCase wallateBalanceUseCase;
    private final TransferUseCase walletTransferUseCase;

    @Operation(summary = "create a wallet", description = "Endpoint that is responsible for creating a wallet for a user")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CreatedDTO create(@RequestBody @Valid final CreateDTO body) {
        return walletCreateUseCase.execute(body);
    }

    @Operation(summary = "get status of a wallet", description = "Endpoint responsible for obtaining the status of a wallet")
    @GetMapping(value = "/{wallet-code}/status")
    @Hidden
    @ResponseStatus(HttpStatus.OK)
    public StatusDTO status(@PathVariable("wallet-code") String walletCode) {
        return walletStatusUseCase.execute(walletCode);
    }

    @Operation(summary = "update status of a wallet", description = "Endpoint responsible for updating the status of a wallet")
    @PatchMapping(value = "/{wallet-code}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public void updateStatus(
            @PathVariable("wallet-code") String walletCode,
            @RequestBody @Valid final UpdateStatusDTO body) {
        walletUpdateStatusUseCase.execute(walletCode, body);
    }

    @Operation(summary = "get a wallet balance", description = "Endpoint responsible for getting a wallet balance")
    @GetMapping(value = "/{wallet-code}/balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceDTO balance(
            @PathVariable("wallet-code") String walletCode,
            @RequestParam(value = "end-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return wallateBalanceUseCase.execute(walletCode, endDate);
    }

    @Operation(summary = "deposits an amount in the wallet", description = "Endpoint responsible for depositing an amount into the wallet")
    @PostMapping(value = "/{wallet-code}/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransferredDTO deposit(
            @PathVariable("wallet-code") String walletCode,
            @RequestBody @Valid final TransactionValuetDTO body) {
        return walletOperationUseCase.execute(walletCode, WalletOperation.DEPOSIT, body);
    }

    @Operation(summary = "withdraw an amount in the wallet", description = "Endpoint responsible for obtaining the status of a wallet")
    @PostMapping(value = "/{wallet-code}/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransferredDTO withdraw(
            @PathVariable("wallet-code") String walletCode,
            @RequestBody @Valid final TransactionValuetDTO body) {
        return walletOperationUseCase.execute(walletCode, WalletOperation.WITHDRAW, body);
    }

    @Operation(summary = "get status of a wallet", description = "Endpoint responsible for obtaining the status of a wallet")
    @PostMapping(value = "/{wallet-code}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public TransferredDTO transfer(
            @PathVariable("wallet-code") String walletCode,
            @RequestBody @Valid final TransferValuetDTO body
    ) {
        return walletTransferUseCase.execute(walletCode, body);
    }

}
