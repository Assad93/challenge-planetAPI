package br.com.projetos.apiplanetas.controller.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.projetos.apiplanetas.exception.PlanetNotFoundException;
import br.com.projetos.apiplanetas.exception.handler.MessageExceptionHandler;

@ControllerAdvice
public class PlanetControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MessageExceptionHandler> blankField() {
		MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.BAD_REQUEST.value(), "Todos os campos são obrigatórios");
		
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<MessageExceptionHandler> methodNotSupported() {
		MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Esse caminho não existe");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(PlanetNotFoundException.class)
	public ResponseEntity<MessageExceptionHandler> planetNotFound() {
		MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Planeta não encontrado");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(UnirestException.class)
	public ResponseEntity<MessageExceptionHandler> uniRest() {
		MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Problema interno no servidor");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
