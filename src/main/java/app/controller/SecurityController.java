package app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import util.JsonRequestMapping;
import util.Response.Response;

@Controller
public class SecurityController {

	@JsonRequestMapping(path="/session_init")
	public @ResponseBody ResponseEntity<Object> sessionInit() {
		return Response.create();
	}
}