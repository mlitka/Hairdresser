package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.HairServiceDTO;
import com.litkowska.martyna.hairdresser.app.dto.UserDTO;
import com.litkowska.martyna.hairdresser.app.model.HairService;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;
import com.litkowska.martyna.hairdresser.app.service.HairServiceService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
@CrossOrigin
public class HairServiceController {
    @Autowired
    private HairServiceService hairServiceService;
    @Autowired
    private UserController userController;

    @RequestMapping(value = "/auth/services", method = RequestMethod.GET)
    public ResponseEntity<?> getAllHairServices(){
        try {
            List<HairService> hairServices = (List<HairService>) hairServiceService.findAll();
            if(hairServices.size()==0){
                return new ResponseEntity<>("no hair services found in database", HttpStatus.NOT_FOUND);
            }
            List<HairServiceDTO> hairServiceDTOs = hairServices.stream()
                    .map(hairService -> new HairServiceDTO(hairService)).collect(Collectors.toList());
            return new ResponseEntity<>(hairServiceDTOs, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/rest/services/available", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotHiddenHairServices(){
        try {
            List<HairService> availableHairServices = (List<HairService>) hairServiceService.findAllNotHidden();
            if(availableHairServices.size()==0){
                return new ResponseEntity<>("no available hair services found in database", HttpStatus.NOT_FOUND);
            }
            List<HairServiceDTO> hairdresserDTOs = availableHairServices.stream()
                    .map(hairService -> new HairServiceDTO(hairService)).collect(Collectors.toList());
            return new ResponseEntity<>(hairdresserDTOs, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/services", method = RequestMethod.POST)
    public ResponseEntity<?> addHairService(@RequestBody final HairServiceDTO hairServiceDTO, final HttpServletRequest request){
        try{
            UserDTO user = (UserDTO) userController.getLoggedUser(request).getBody();
            if(user.getRole()== AuthRole.ADMIN){
                HairService hairService = hairServiceService.save(hairServiceDTO);
                return new ResponseEntity<>(hairService, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("could not save hair service", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/services/hide/{hairServiceId}", method = RequestMethod.PUT)
    public ResponseEntity<?> hideHairService(@PathVariable("hairServiceId") final long hairServiceId, final HttpServletRequest request){
        try{
            UserDTO user = (UserDTO) userController.getLoggedUser(request).getBody();
            if(user.getRole()== AuthRole.ADMIN){
                hairServiceService.hide(hairServiceId);
                return new ResponseEntity<>("hair service "+hairServiceId+" is now hidden", HttpStatus.OK);
            }
            return new ResponseEntity<>("could not hide hair service", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/services/show/{hairServiceId}", method = RequestMethod.PUT)
    public ResponseEntity<?> showHairService(@PathVariable("hairServiceId") final long hairServiceId, final HttpServletRequest request){
        try{
            UserDTO user = (UserDTO) userController.getLoggedUser(request).getBody();
            if(user.getRole()== AuthRole.ADMIN){
                hairServiceService.show(hairServiceId);
                return new ResponseEntity<>("hair service "+hairServiceId+" is now showed", HttpStatus.OK);
            }
            return new ResponseEntity<>("could not show hair service", HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
