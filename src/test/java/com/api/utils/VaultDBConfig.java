package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.VaultImpl;
import io.github.jopenlibs.vault.response.LogicalResponse;
import io.qameta.allure.Step;

public class VaultDBConfig {

	private static VaultConfig vaultConfig;
	private static Vault vault;
	private static final Logger logger=LogManager.getLogger(VaultDBConfig.class);
	static {
		try {
			vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER")).token(System.getenv("VAULT_TOKEN")).build();
		} catch (VaultException e) {
			logger.error("Something went wrong with the vault config",e);
			e.printStackTrace();
		}

		vault = new VaultImpl(vaultConfig);
	}

	private VaultDBConfig() {

	}

	@Step("Retrieving the secret from the vault")
	public static String getSecretValue(String key) {
		LogicalResponse response=null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			logger.error("Something went wrong with reading vault response",e);
			return null;
		}

		Map<String, String> mapDataMap = response.getData();

		String secretValue=mapDataMap.get(key);
		logger.info("secret found in the vault");
		return secretValue;
	}

}
