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
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAllOpinions(){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllOpinions();
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            List<OpinionDTO> opinionDTOs = opinionList.stream().map(opinion -> new OpinionDTO(opinion)).collect(Collectors.toList());
            return new ResponseEntity<>(opinionDTOs, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinions/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllClientOpinions(@PathVariable("userId") final long userId){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllClientsOpinions(userId);
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            List<OpinionDTO> opinionDTOs = opinionList.stream().map(opinion -> new OpinionDTO(opinion)).collect(Collectors.toList());
            return new ResponseEntity<>(opinionDTOs, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get returning given number of the best opinions from database
     * @return
     */
    @RequestMapping(value = "/rest/opinions/{count}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOpinionsCount(@PathVariable("count") final int count){
        try{
            List<Opinion> opinionList = (List<Opinion>) opinionService.getAllOpinions();
            if(opinionList.size()==0){
                return new ResponseEntity<>("no opinions found in database", HttpStatus.NOT_FOUND);
            }
            Collections.reverseOrder();
            List<OpinionDTO> opinionDTOs;
            if(count<opinionList.size()) {
                opinionDTOs = opinionList.subList(0, count).stream().map(opinion -> new OpinionDTO(opinion)).collect(Collectors.toList());
            } else {
                opinionDTOs = opinionList.stream().map(opinion -> new OpinionDTO(opinion)).collect(Collectors.toList());
            }
            return new ResponseEntity<>(opinionDTOs, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinions", method = RequestMethod.POST)
    public ResponseEntity<?> addOpinion(@RequestBody OpinionDTO opinionDTO){
        try{
            Opinion opinion = opinionService.save(opinionDTO);
            if(opinion!=null){
                return new ResponseEntity<>("opinion saved", HttpStatus.OK);
            }
            return new ResponseEntity<>("could not add opinion", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/opinion/delete/{opinionId}", method = RequestMethod.POST)
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
