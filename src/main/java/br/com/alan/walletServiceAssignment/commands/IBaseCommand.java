package br.com.alan.walletServiceAssignment.commands;

public sealed interface IBaseCommand permits
        CreatedCommand,
        BasicOperationsCommand,
        UpdatedStatusCommand,
        TransferCommand {}

