package pl.java.scalatech.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;


import com.wordnik.swagger.annotations.ApiResponse;;

/**
 * @author Sławomir Borowiec 
 * Module name : Cep
 * Creating time :  8 wrz 2014 11:17:57
 
 */
@Controller
@RequestMapping("/api/cep/photo")
@Api(value = "Image", description = "image API")
@Slf4j
public class ImageController {

    @Autowired
    private org.springframework.core.io.Resource photo;

   
    @Autowired
    private org.springframework.core.io.Resource defaultPhoto;

    @ApiOperation(httpMethod = "GET", value = "getPhoto")
    @ApiResponses(value = {
            @ApiResponse(code=HttpServletResponse.SC_BAD_REQUEST, message = "BAD_REQUEST | request problem."),
            @ApiResponse(code=HttpServletResponse.SC_UNAUTHORIZED, message = "UNAUTHORIZED | The token is necessary."),
            @ApiResponse(code=HttpServletResponse.SC_ACCEPTED, message = "ACCEPTED_WAIT_FOR_THE_RESULT"),
            @ApiResponse(code=HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "INTERNAL_SERVER_ERROR | Internal application problem")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] getPhoto(@PathVariable("id") Integer photoId, Principal principal) throws IOException {
        byte[] content = null;
        content = photoToByte(photo);
        return content;
    }

    private byte[] photoToByte(org.springframework.core.io.Resource photo) throws IOException {
        byte content[] = new byte[(int) photo.contentLength()];
        photo.getInputStream().read(content);
        return content;
    }

}