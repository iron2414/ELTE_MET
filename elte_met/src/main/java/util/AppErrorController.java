package util;

import exception.PageNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import util.Response.Response;

import java.util.Enumeration;


/**
 * Basic Controller which is called for unhandled errors
 */
@Controller
public class AppErrorController implements ErrorController {

    /**
     * Error Attributes in the app.Application
     */
    private final ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    private class ErrorObject {
        public int errorCode;
    }

    /**
     * Controller for the Error Controller
     *
     * @param errorAttributes
     */
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Supports the HTML Error View
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "application/json")
    public ResponseEntity<Object> errorJson(HttpServletRequest request) {

//        Enumeration<String> attrnames = request.getAttributeNames();
//        while(attrnames.hasMoreElements()) {
//            String name = attrnames.nextElement();
//            System.out.println(name);
//        }

        return Response.create(getErrorMessage(request), getStatusCode(request));
    }

    private HttpStatus getStatusCode(HttpServletRequest httpRequest) {
        Object status = httpRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());

        return HttpStatus.valueOf(statusCode);
    }

    private Object getErrorMessage(HttpServletRequest httpRequest) {
        Object status = httpRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new PageNotFoundException();
            } else {
                //Ez csak akkor kaphatjuk meg ha lekezeletlen hibát kapunk
                Exception e = (Exception) httpRequest.getAttribute("javax.servlet.error.exception");
                if (null != e) {
                    return e;
                }
                String msg = (String)httpRequest.getAttribute("javax.servlet.error.message");
                if (null != msg && !msg.equals("")) {
                    return new Exception( msg );
                }
                return new Exception("Unknown error found, statusCode=" + statusCode);
            }
        }
        return new Exception("Unknown error, no HttpStatus found");
    }


    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    public String getErrorPath() {
        return ERROR_PATH;
    }

}