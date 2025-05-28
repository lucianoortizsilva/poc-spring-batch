package com.lucianoortizsilva.migration.entrypoint.amqp;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtil {
	
	public static Set<String> getErros(final Exception e) {
		final Set<String> mensagens = new HashSet<>();
		Throwable currentException = e;
		int count = 0;
		final int maxCausas = 5;
		while (currentException != null && count < maxCausas) {
			final String errorMessage = currentException.getMessage();
			if (isNotEmpty(errorMessage) && mensagens.add(errorMessage)) {
				count++;
			}
			currentException = currentException.getCause();
		}
		return mensagens;
	}
}