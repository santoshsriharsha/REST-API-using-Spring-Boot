package com.springboot.springbootrestapi.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SurveyResource {
	
	private SurveyService surveyService;

	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}
	
	
	//GET /surveys 
	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys(){
		return surveyService.retrieveAllSurveys();
	}
	
	@RequestMapping("/surveys/{id}")
	public Survey retrieveSurveyById(@PathVariable String id) {
		Survey survey = surveyService.retrieveSurveyById(id);
		
		if(survey==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return survey;
	}
	@RequestMapping("/surveys/{id}/questions")
	public List<Question> retrieveQuestionsBySurveyId(@PathVariable String id){
		Survey survey = surveyService.retrieveSurveyById(id);
		
		if(survey==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return survey.getQuestions();
	}
	
}
