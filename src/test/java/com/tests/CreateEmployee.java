package com.tests;

import java.io.IOException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Pojos.CreateEmployeePOJO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateEmployee {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
	}

	@Test(priority = 1, enabled = true)
	public static Integer createEmployeeData() throws IOException {
		
		Response response = RestAssured.given().contentType("application/json")
				.body("{\"name\":\"test\",\"salary\":\"123\",\"age\":\"23\"}")
				.post("/create");
		
		response.then().statusCode(200);
		response.getBody().prettyPrint();
		
		String status = response.jsonPath().get("status");		
		AssertJUnit.assertEquals(status, "success");

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(response.asString(), JsonObject.class);
		
		CreateEmployeePOJO addEmp = gson.fromJson(jsonObject.getAsJsonObject("data"), CreateEmployeePOJO.class);
		
		AssertJUnit.assertEquals(addEmp.getName(), "test");
		AssertJUnit.assertEquals(addEmp.getSalary(), "123");
		AssertJUnit.assertEquals(addEmp.getAge(), "23");
		
		return addEmp.getId();
	}
}
