package org.wmc.droolsdemo.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class SingletonScopeService {

    @Autowired
    private ProtoTypeScopeService protoTypeScopeService;
}
