package com.tala.account.utils;

import com.tala.account.persistence.repos.TransactionRepo;

import java.util.Random;

public class Util {

    public static String createTransactionRef(TransactionRepo transactionRepo) {
        boolean found = false;

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String ref = "";
        while (!found) {
            ref = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            if (!transactionRepo.findDistinctByTransactionRef(ref).isPresent())
                found = true;
        }
        return ref;
    }
}
