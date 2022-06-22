package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.Setting;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public Setting getFromId(Integer id){
        Optional<Setting> setting= settingRepository.findById(id);
        try {
            return setting.get();
        } catch (Exception e){
            throw new ResourceNotFoundException("setting", "id", id, e.toString());
        }
    }

    public Setting updateSetting(Integer settingId, Setting newSetting){
        Setting setting = this.getFromId(settingId);

        try{
            setting.setSound(newSetting.isSound());
        }catch (Exception e){
            System.out.println("[Exception] : SettingService : updateSetting 1 : "+e.toString());
        }
        try{
            setting.setPush(newSetting.isPush());
        }catch (Exception e){
            System.out.println("[Exception] : SettingService : updateSetting 2 : "+e.toString());
        }
        try{
            setting.setVibration(newSetting.isVibration());
        }catch (Exception e){
            System.out.println("[Exception] : SettingService : updateSetting 3 : "+e.toString());
        }

        return settingRepository.save(setting);

    }

}
