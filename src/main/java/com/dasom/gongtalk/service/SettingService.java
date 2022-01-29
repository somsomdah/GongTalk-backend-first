package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.user.Setting;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.SettingRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public Setting getFromId(Integer id){
        Optional<Setting> setting= settingRepository.findById(id);
        if(setting.isEmpty()){
            throw new ResourceNotFoundException("Setting", "id", id);
        }

        return setting.get();
    }

    public Setting updateSetting(Integer settingId, Setting newSetting){
        Setting setting = this.getFromId(settingId);

        try{
            setting.setSilence(newSetting.isSilence());
        }catch (Exception e){
            System.out.println("Exception :updateSetting: "+e.toString());
        }
        try{
            setting.setPush(newSetting.isPush());
        }catch (Exception e){
            System.out.println("Exception :updateSetting: "+e.toString());
        }
        try{
            setting.setVibration(newSetting.isVibration());
        }catch (Exception e){
            System.out.println("Exception :updateSetting: "+e.toString());
        }

        return settingRepository.save(setting);

    }

}
