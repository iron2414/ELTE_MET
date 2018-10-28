package app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.JsonRequestMapping;
import util.Response.Response;

@Controller
public class SecurityController {

	@JsonRequestMapping(path="/session_init")
	public @ResponseBody ResponseEntity<Object> sessionInit() {
		return Response.create();
	}

	public static ResponseEntity<Object> getAction(Object object)
	{
		try {
			if(null == object) {
				throw new Exception("Entity with this id not found");
			}
			return Response.create(object);
		} catch (Exception e) {
			return Response.create(e);
		}
	}
}