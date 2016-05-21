package com.vanhackathon.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PasswordController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<String> consultaClientes() {
		return ResponseEntity.status(HttpStatus.OK).body("Testing ok!");
	}
}
