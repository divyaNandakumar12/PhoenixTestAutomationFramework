package com.api.utils;

import java.util.Map;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.VaultImpl;
import io.github.jopenlibs.vault.response.LogicalResponse;

public class VaultDBConfig {

	private static VaultConfig vaultConfig;
	private static Vault vault;

	static {
		try {
			vaultConfig = new VaultConfig().address(System.getenv("VAULT_SERVER")).token(System.getenv("VAULT_TOKEN")).build();
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vault = new VaultImpl(vaultConfig);
	}

	private VaultDBConfig() {

	}

	public static String getSecretValue(String key) {
		LogicalResponse response=null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		Map<String, String> mapDataMap = response.getData();

		String secretValue=mapDataMap.get(key);
		
		return secretValue;
	}

}
