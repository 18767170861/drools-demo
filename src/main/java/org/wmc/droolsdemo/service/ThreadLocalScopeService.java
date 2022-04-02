package org.wmc.droolsdemo.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

@Service
@Scope("threadLocalScope")
public class ThreadLocalScopeService extends ApplicationObjectSupport {

}
