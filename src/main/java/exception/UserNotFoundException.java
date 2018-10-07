/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author norbert.oszlanyi
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User Not Found") //404
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -3332292346834265371L;

	public UserNotFoundException(long id){
		super("UserNotFoundException with id="+id);
	}
}