package com.api.utils;

import java.util.Map;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.VaultImpl;
import io.github.jopenlibs.vault.response.LogicalResponse;

public class VaultDemo {
	
	
	public static void main(String[] args) throws VaultException {
		
		
		VaultConfig vaultConfig=new VaultConfig()
				.address("http://52.204.140.221:8200/")
				.token("root")
				.build();
		
		Vault vault=new VaultImpl(vaultConfig);
		
		
		LogicalResponse response= vault.logical().read("secret/phoenix/qa/database");
		
		Map<String,String> mapDataMap= response.getData();
		
       System.out.println(mapDataMap.get("DB_URL"));
		
	}

}
