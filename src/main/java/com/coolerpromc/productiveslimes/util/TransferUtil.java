package com.coolerpromc.productiveslimes.util;

import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class TransferUtil {
    public static boolean canInsert(EnergyHandler handler){
        try(Transaction tx = Transaction.open(null)){
            return handler.insert(1000, tx) > 0;
        }
    }

    public static boolean canExtract(EnergyHandler handler) {
        try (Transaction tx = Transaction.open(null)) {
            return handler.extract(1000, tx) > 0;
        }
    }
}