package com.example;

import java.util.Map;

import io.quarkus.test.junit.QuarkusTestProfile;

public class OtherTestProfile implements QuarkusTestProfile {
	@Override
	public Map<String, String> getConfigOverrides() {
		return Map.of(
			"quarkus.hibernate-orm.sql-load-script", "no-file",
			"someprop", "anotherval"
		);
	}

	@Override
	public String getConfigProfile() {
		return "other";
	}

	@Override
	public boolean disableGlobalTestResources() {
		return true;
	}
}
