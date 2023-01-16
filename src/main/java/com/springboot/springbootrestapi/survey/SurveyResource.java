package com.springboot.springbootrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@RequestMapping("/surveys/{id}/questions/{questionId}")
	public Question retrieveQuestionsByQuestionId(@PathVariable String id, @PathVariable String questionId){
		Question question = surveyService.retrieveSurveyByQuestionId(id, questionId);
		
		if(question==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return question;
	}
	
	@RequestMapping(value = "/surveys/{id}/questions", method=RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String id, @RequestBody Question question){
		String questionId = surveyService.addNewSurveyQuestion(id, question);
		
		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location).build();
	}
}
