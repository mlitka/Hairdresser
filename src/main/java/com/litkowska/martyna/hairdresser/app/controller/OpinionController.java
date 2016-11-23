package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.OpinionDTO;
import com.litkowska.martyna.hairdresser.app.model.Opinion;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
import com.litkowska.martyna.hairdresser.app.service.OpinionService;
import com.litkowska.martyna.hairdresser.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by Martyna on 19.11.2016.
 */
@RestController
@CrossOrigin
public class OpinionController {

    @Autowired
    private OpinionService opinionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/opinions", method = RequestMethod.GET)
//    @CrossOrigin
    public ResponseEntity<?> getAllOpinions(){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllOpinions();
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(opinionList, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinions/user/{clientId}", method = RequestMethod.GET)
//    @CrossOrigin("*")
    public ResponseEntity<?> getAllClientOpinions(@PathVariable("clientId") final long clientId){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllClientsOpinions(clientId);
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(opinionList, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get returning given number of the best opinions from database
     * @return
     */
    @RequestMapping(value = "/rest/opinions/{count}", method = RequestMethod.GET)
//    @CrossOrigin("*")
    public ResponseEntity<?> getAllOpinionsCount(@PathVariable("count") final int count){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllOpinions();
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            Collections.reverseOrder();
            return new ResponseEntity<>(opinionList.subList(0,count), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinion/add", method = RequestMethod.POST)
//    @CrossOrigin("*")
    public ResponseEntity<?> addOpinion(@RequestBody OpinionDTO opinionDTO){
        try{
            Opinion opinion = opinionService.save(opinionDTO);
            if(opinion!=null){
                return new ResponseEntity<>(opinion, HttpStatus.OK);
            }
            return new ResponseEntity<>("could not add opinion", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinion/delete/{opinionId}", method = RequestMethod.POST)
//    @CrossOrigin("*")
    public ResponseEntity<?> deleteOpinion(@PathVariable("opinionId") final long opinionId){
        try{
            User user = userService.getCurrentLoggedUser();
            if(user.getRole()== AuthRole.ADMIN || opinionService.checkIfUserCanDeleteOpinion(user, opinionId)){
                if(opinionService.delete(opinionId)){
                    return new ResponseEntity<>("deleted opinion: "+opinionId, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("could not delete opinion: "+opinionId, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
