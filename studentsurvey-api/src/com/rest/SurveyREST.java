package com.rest;

import java.util.List;
import com.entity.Survey;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * This class is a POJO class of the RESTful web application that 
 * uses JAX-RS to annotate and simplify development. 
 * 
 * @author riyavashisht
 *
 */
@Path("/")
public class SurveyREST {
	
	private static final String PERSISTENCE_UNIT_NAME = "SurveyREST";
	private EntityManager em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
	
	@GET
	@Consumes({"*/*"})
	public List<Survey> getAll() {
		List<Survey> surveys = em.createQuery("from Survey", Survey.class).getResultList();
		return surveys;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("newsurvey")
	public Survey createSurvey(
			@FormParam("date")String date,
			@FormParam("fname")String fname,
			@FormParam("lname")String lname,
			@FormParam("street")String street,
			@FormParam("city")String city,
			@FormParam("state")String state,
			@FormParam("zip")String zip,
			@FormParam("email")String email,
			@FormParam("pnumber")String pnumber,
			@FormParam("checkboxes")String checkboxes,
			@FormParam("choice")String choice,
			@FormParam("recommendation")String recommendation
			) {
		
		Survey survey = new Survey();
		survey.setDate(date);
		survey.setFirstName(fname);
		survey.setLastName(lname);
		survey.setStreetName(street);
		survey.setCity(city);
		survey.setState(state);
		survey.setZipCode(zip);
		survey.setEmail(email);
		survey.setPhoneNumber(pnumber);
		survey.setCheckedBoxes(checkboxes);
		survey.setChoice(choice);
		survey.setRecommendation(recommendation);
		System.out.println(survey.toString());
		em.getTransaction().begin();
		em.persist(survey);
		em.getTransaction().commit();
		em.close();
		
		return survey;
	}
}
