package com.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Pojos.EmployeePOJO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetEmployeeList {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
	}

	@Test(priority = 1, enabled = true)
	public static void getEmployeeList() throws IOException {

		// Get the Employee list
		Response response = RestAssured.given().when().get("/employees");

		// Check Response code is equal to 200 (Success)
		response.then().statusCode(200);

		// Deserialize the response body to Employee POJO
		ArrayList<EmployeePOJO> employeeList = response.jsonPath().get("data");
		System.out.println("Total No. of Records fetch: " + employeeList.size());

		response.body().prettyPrint();
	}

	@Test(priority = 2, enabled = true)
	public static void getEmployeePerId() throws IOException {

		int empId = 2;
		// Get the Employee per Id
		Response response = RestAssured.given().when().get("/employee/" + empId);

		response.body().prettyPrint();
		// Check Response code is equal to 200 (Success)
		response.then().statusCode(200);

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(response.asString(), JsonObject.class);
		ObjectMapper mapper = new ObjectMapper();
		
		EmployeePOJO employee = mapper.readValue(jsonObject.getAsJsonObject("data").toString(), EmployeePOJO.class);
		
		AssertJUnit.assertEquals(employee.getEmployeeName(), "Garrett Winters");
		AssertJUnit.assertEquals(employee.getEmployeeSalary().toString(), "170750");
		AssertJUnit.assertEquals(employee.getEmployeeAge().toString(), "63");
	}
}
