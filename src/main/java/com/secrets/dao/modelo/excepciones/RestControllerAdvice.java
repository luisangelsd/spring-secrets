package com.secrets.dao.modelo.excepciones;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
	
	//-- Variables Globales
	Map<String, Object> errors;
	Map<String, Object> responseBody;
	
	
	
	//--------------------------------------------------------------------------
	
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> methodArgumentNotValidException( MethodArgumentNotValidException e){

       
        this.responseBody=new HashMap<>();
        this.errors=new HashMap<>();
        
      //-- Preparando Errores
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            this.errors.put(fieldName, errorMessage);
        });

        //-- Preparando ResponseBody
        this.responseBody.put("errors", errors);
		this.responseBody.put("timestamp", LocalDateTime.now());
		this.responseBody.put("code", e.hashCode());
		this.responseBody.put("send", "RestControllerAdvice");	
        return  this.responseBody;
    }
	
  //--------------------------------------------------------------------------
    
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RunTimeExceptionNotFound.class)
	public Map<String, Object> runTimeSecretNotFound(RunTimeExceptionNotFound e){
		
		 
		this.responseBody=new HashMap<>();
		 this.errors=new HashMap<>();
		 
		//-- Preparando Errores
		this.errors.put("message", e.getMessage());
		
		//-- Preparando ResponseBody
		this.responseBody.put("errors", errors);
		this.responseBody.put("timestamp", LocalDateTime.now());
		this.responseBody.put("code", e.hashCode());
		this.responseBody.put("send", "RestControllerAdvice");
		
		return this.responseBody;
	}

	//--------------------------------------------------------------------------
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> runtimeException(RuntimeException e){
		
		
		this.responseBody=new HashMap<>();
		this.errors=new HashMap<>();
		 
		//-- Preparando Errores
				this.errors.put("message", e.getMessage());
				
		//-- Preparando ResponseBody
		this.responseBody.put("errors", errors);
		this.responseBody.put("timestamp", LocalDateTime.now());
		this.responseBody.put("code", e.hashCode());
		this.responseBody.put("send", "RestControllerAdvice");
		
		return this.responseBody;
	}
	
	//--------------------------------------------------------------------------
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, Object> exception(Exception e){
		
		
		this.responseBody=new HashMap<>();
		this.errors=new HashMap<>();
		 
		//-- Preparando Errores
				this.errors.put("message", e.getMessage());
				
		//-- Preparando ResponseBody
		this.responseBody.put("errors", errors);
		this.responseBody.put("timestamp", LocalDateTime.now());
		this.responseBody.put("code", e.hashCode());
		this.responseBody.put("send", "RestControllerAdvice");
		
		return this.responseBody;
	}

}
