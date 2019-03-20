package com.dropshipping.util;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ValidaCNPJ {
	
	private static List<String> cnpjNotValid = null;

    private ValidaCNPJ() {
		super();
	}
    
    static {
		populate();
	}

	public static boolean isCNPJ(String cnpj) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		
        if (cnpj.length() != 14)
            return(false);
        
        if (cnpjNotValid.contains(cnpj)) return false;

        char dig13;
        char dig14;
        int r;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            r = calculoDigitoVerificador(cnpj, 11);
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

// Calculo do 2o. Digito Verificador
            r = calculoDigitoVerificador(cnpj, 12);
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

// Verifica se os dígitos calculados conferem com os dígitos informados.
            return verificaDigitosCalculados(cnpj, dig13, dig14);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

	private static int calculoDigitoVerificador(String cnpj, int caracterCNPJ) {
		int sm;
		int r;
		int num;
		int peso;
		sm = 0;
		peso = 2;
		for (int i = caracterCNPJ ;i>=0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
		    num =(cnpj.charAt(i) - 48);
		    sm = sm + (num * peso);
		    peso = peso + 1;
		    if (peso == 10)
		        peso = 2;
		}

		r = sm % 11;
		return r;
	}

	private static boolean verificaDigitosCalculados(String cnpj, char dig13, char dig14) {
		if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
		    return(true);
		else return(false);
	}

    public static String imprimeCNPJ(String cnpj) {
// máscara do CNPJ: 99.999.999.9999-99
        return(cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
        		cnpj.substring(5, 8) + "." + cnpj.substring(8, 12) + "-" +
        		cnpj.substring(12, 14));
    }
    
    private static void populate() {
		if (cnpjNotValid != null) return;

		cnpjNotValid = new ArrayList<>();
		cnpjNotValid.add("00000000000000");
		cnpjNotValid.add("11111111111111");
		cnpjNotValid.add("22222222222222");
		cnpjNotValid.add("33333333333333");
		cnpjNotValid.add("44444444444444");
		cnpjNotValid.add("55555555555555");
		cnpjNotValid.add("66666666666666");
		cnpjNotValid.add("77777777777777");
		cnpjNotValid.add("88888888888888");
		cnpjNotValid.add("99999999999999");
	}
}
