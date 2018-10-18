package com.mea.config;

import java.util.List;

import com.mea.service.IBuildMessageService;
import com.mea.service.IBuildMessageServiceImpl;

public abstract class Configuration {
	
	private static List<String> positives = null;
	
	private static List<String> negatives = null;
	
	public static List<String> loadPositiveConfig() {
		if (positives == null) {
			IBuildMessageService messageService = new IBuildMessageServiceImpl();
			positives = messageService.getPositiveWords(Properties.POSTIVE_PATH);
		}
		return positives;
	}
	
	public static List<String> loadNegativeConfig() {
		if (negatives == null) {
			IBuildMessageService messageService = new IBuildMessageServiceImpl();
			negatives = messageService.getNegativeWords(Properties.NEGATIVE_PATH);
		}
		return negatives;
	}

}
