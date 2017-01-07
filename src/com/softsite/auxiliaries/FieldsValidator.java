package com.softsite.auxiliaries;

import java.util.Date;

public class FieldsValidator {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	
	public boolean apenasLetras(String name) {
	    return name.matches("^[ A-z]+$");
	}
	
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	@SuppressWarnings("deprecation")
	public String convertDateToString(Date date) {
		return date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
	}

	public static boolean validarCpf(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString()
				+ digito2.toString());
	}

	
	public String capitalizeFirstLetter(String original1) {

		String original = original1.toLowerCase();

		if (original == null || original.length() == 0) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

}
