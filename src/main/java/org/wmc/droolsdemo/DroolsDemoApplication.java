package org.wmc.droolsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.wmc.droolsdemo.service.ProtoTypeScopeService;
import org.wmc.droolsdemo.service.SingletonScopeService;
import org.wmc.droolsdemo.service.ThreadLocalScopeService;

@SpringBootApplication
public class DroolsDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DroolsDemoApplication.class, args);

        /**
         * @Service
         * @Scope("threadLocalScope")
         * public class ThreadLocalScopeService extends ApplicationObjectSupport {
         *
         * }
         */
        ThreadLocalScopeService bean = run.getBean(ThreadLocalScopeService.class);
        // bean:org.wmc.droolsdemo.service.ScopeService@5099c59b
        System.out.println("bean:" + bean);

        new Thread(() -> {
            ThreadLocalScopeService bean1 = run.getBean(ThreadLocalScopeService.class);
            // bean1:org.wmc.droolsdemo.service.ScopeService@3b9f1ece
            System.out.println("bean1:" + bean1);
        }).start();

        /**
         * @Service
         * @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
         * public class ProtoTypeScopeService {
         * }
         */
        Object bean2 = run.getBean("protoTypeScopeService");
        Object bean3 = run.getBean("scopedTarget.protoTypeScopeService");
        ProtoTypeScopeService bean4 = run.getBean(ProtoTypeScopeService.class);

        // bean2:org.wmc.droolsdemo.service.ProtoTypeScopeService@22d7fd41,class:class org.wmc.droolsdemo.service.ProtoTypeScopeService$$EnhancerBySpringCGLIB$$11d6ed9c
        System.out.println("bean2:" + bean2 + ",class:" + bean2.getClass());

        // bean3:org.wmc.droolsdemo.service.ProtoTypeScopeService@2c748a15,class:class org.wmc.droolsdemo.service.ProtoTypeScopeService
        System.out.println("bean3:" + bean3 + ",class:" + bean3.getClass());

        // bean3:org.wmc.droolsdemo.service.ProtoTypeScopeService@19fd43da
        System.out.println("bean3:" + bean4);

        SingletonScopeService bean1 = run.getBean(SingletonScopeService.class);
        ProtoTypeScopeService protoTypeScopeService = bean1.getProtoTypeScopeService();
        ProtoTypeScopeService protoTypeScopeService1 = bean1.getProtoTypeScopeService();
        System.out.println("protoTypeScopeService:" + protoTypeScopeService);
        System.out.println("protoTypeScopeService1:" + protoTypeScopeService1);

    }

}
