package com.tests;

import java.io.IOException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class deleteAddedEmployee extends CreateEmployee {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
	}

	@Test(priority = 1, enabled = true)
	public static void deleteEmployee() throws IOException {

		int getAddedEmp = createEmployeeData();

		Response response = RestAssured.given().contentType("application/json").when().delete("/delete/" + getAddedEmp);

		response.then().statusCode(200);
		response.getBody().prettyPrint();
		
		String status = response.jsonPath().get("status");
		AssertJUnit.assertEquals(status, "success");
		System.out.println("Response Status: " + status);
		
		System.out.println("Delete Message: " + response.jsonPath().get("message"));

	}
	
	@Test(priority = 2, enabled = true)
	public static void deleteEmployeePeId() throws IOException {

		int empId = 0;

		Response response = RestAssured.given().contentType("application/json").when().delete("/delete/" + empId);

		response.getBody().prettyPrint();
		response.then().statusCode(400);
		
		String status = response.jsonPath().get("status");
		AssertJUnit.assertEquals(status, "error");
		System.out.println("Response Status: " + status);
		
		System.out.println("Delete Message: " + response.jsonPath().get("message"));

	}
}
