package com.salmac.host.service;

import com.salmac.host.dto.ScriptDTO;
import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.repo.ScriptRepo;
import com.salmac.host.routine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScriptService {
    @Autowired
    ScriptRepo scriptRepo;

    public List<ScriptDTO> getListOfScripts(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<ScriptEntity> scriptEntities = scriptRepo.findAll();
        List<ScriptDTO> ScriptDTOs = scriptEntities.stream().map(e -> new ScriptDTO(e)).collect(Collectors.toList());
        return ScriptDTOs;
        //return scriptRepo.findAll(pageable);
    }
    public Optional<ScriptEntity> getScriptById(Long scriptId){
        return scriptRepo.findById(scriptId);
    }

    public List<ScriptDTO> getListOfScriptsByOS(String OS){
        List<ScriptEntity> scriptEntities = scriptRepo.getScriptEntitiesByTargetOS(OS);
        List<ScriptDTO> ScriptDTOs = scriptEntities.stream().map(e -> new ScriptDTO(e)).collect(Collectors.toList());
        return ScriptDTOs;
        //return scriptRepo.findAll(pageable);
    }

    public void addScript(ScriptEntity scriptEntity){
        scriptRepo.save(scriptEntity);
    }

    public void updateScript(Long scriptId, ScriptEntity scriptEntity) throws ResourceNotFoundException {
        Optional<ScriptEntity> scriptOptional = scriptRepo.findById(scriptId);
        if (!scriptOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }
        scriptEntity.setId(scriptId);
        scriptRepo.save(scriptEntity);
    }

    public void removeScript(Long scriptId) throws ResourceNotFoundException {
        Optional<ScriptEntity> studentOptional = scriptRepo.findById(scriptId);
        if (!studentOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }
        scriptRepo.deleteById(scriptId);
    }
}
