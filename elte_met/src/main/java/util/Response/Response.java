/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.Response;

import exception.FormException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author norbert.varjasi
 */
public class Response {
    public static ResponseEntity<Object> create(Object content, HttpStatus status) {
        try {
            ResponseEntity<Object> response = create(content);
            return ResponseEntity.status(status).body(response.getBody());
        } catch (Exception ex) {
            Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionResponse response = new ExceptionResponse(new Exception("Could not create Response, exception occured: " + ex.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response );
        }
    }
    
    public static ResponseEntity<Object> create(Object content) {
        try {
            AbstractResponse response;
            HttpStatus status;
            if (content instanceof FormException) {
                response = new FormResponse((FormException) content);
                status = HttpStatus.CONFLICT;
            } else if (content instanceof Exception) {
                response = new ExceptionResponse((Exception) content);
                status = HttpStatus.CONFLICT;
            } else {
                response = new DataResponse(content);
                status = HttpStatus.OK;
            }

            return ResponseEntity.status(HttpStatus.OK).body( response );
        } catch (Exception ex) {
            Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionResponse response = new ExceptionResponse(new Exception("Could not create Response, exception occured: " + ex.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response );
        }
    }
    
    public static ResponseEntity<Object> create() {
        List<Object> list = new ArrayList<>();
        DataResponse response = new DataResponse(list);
        return ResponseEntity.ok().body(response);
    }
}
