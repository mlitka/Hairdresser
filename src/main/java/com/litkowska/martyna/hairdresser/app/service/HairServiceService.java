package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.dto.HairServiceDTO;
import com.litkowska.martyna.hairdresser.app.model.HairService;
import com.litkowska.martyna.hairdresser.app.repository.HairServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martyna on 26.10.2016.
 */
@Service
public class HairServiceService {
    @Autowired
    private HairServiceRepository hairServiceRepository;

    public Iterable<HairService> findAll(){
        return hairServiceRepository.findAll();
    }

    public Iterable<HairService> findAllNotHidden(){
        return hairServiceRepository.findByIsHidden(false);
    }

    public HairService save(final HairServiceDTO hairServiceDTO){
        if(checkNotNull(hairServiceDTO)){
            HairService hairService = new HairService();
            hairService.setName(hairServiceDTO.getName());
            hairService.setDuration(hairServiceDTO.getDuration());
            if(hairServiceDTO.getPriceRange()!=null && !hairServiceDTO.getPriceRange().isEmpty()){
            hairService.setPriceRange(hairServiceDTO.getPriceRange());
            }
            return hairServiceRepository.save(hairService);
        }
        return null;
    }

    public void hide(final long hairServiceId){
        HairService hairService = hairServiceRepository.findOne(hairServiceId);
        if(hairService!=null){
            hairService.setHidden(true);
            hairServiceRepository.save(hairService);
        }
    }

    public void show(final long hairServiceId){
        HairService hairService = hairServiceRepository.findOne(hairServiceId);
        if(hairService!=null){
            hairService.setHidden(false);
            hairServiceRepository.save(hairService);
        }
    }

    private boolean checkNotNull(final HairServiceDTO hairServiceDTO){
        return hairServiceDTO.getName()!=null && !hairServiceDTO.getName().isEmpty()
                && hairServiceDTO.getDuration()!=-1;
    }
}
