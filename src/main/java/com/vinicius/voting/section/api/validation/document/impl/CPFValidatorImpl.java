package com.vinicius.voting.section.api.validation.document.impl;

import com.vinicius.voting.section.api.validation.document.ObjectValidator;

public class CPFValidatorImpl implements ObjectValidator<String> {

    @Override
    public boolean isValid(final String cpf) {
        if (cpf != null && cpf.trim().length() != 0) {
            String cpfNumber = this.extractNumbers(cpf);
            if (!this.sizeIsValid(cpfNumber)) {
                return false;
            } else if (this.hasSameDigits(cpfNumber)) {
                return false;
            } else {
                String cpfGerado = cpfNumber.substring(0, 9);
                cpfGerado = cpfGerado.concat(this.generateDigit(cpfGerado));
                cpfGerado = cpfGerado.concat(this.generateDigit(cpfGerado));
                return cpfGerado.equals(cpfNumber);
            }
        } else {
            return false;
        }
    }

    private String extractNumbers(String value) {
        return value.replace("-", "").replace(".", "");
    }

    private boolean sizeIsValid(String cpf) {
        return cpf.length() == 11;
    }

    private boolean hasSameDigits(String cpf) {
        char primDig = 48;
        char[] charCpf = cpf.toCharArray();

        for (char c : charCpf) {
            if (c != primDig) {
                return false;
            }
        }

        return true;
    }

    private String generateDigit(String cpf) {
        int digit = 0;
        int mult = cpf.length() + 1;
        char[] charCpf = cpf.toCharArray();

        for(int i = 0; i < cpf.length(); ++i) {
            digit += (charCpf[i] - 48) * mult--;
        }

        if (digit % 11 < 2) {
            digit = 0;
        } else {
            digit = 11 - digit % 11;
        }

        return String.valueOf(digit);
    }
}
