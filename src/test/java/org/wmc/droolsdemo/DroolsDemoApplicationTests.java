package org.wmc.droolsdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wmc.droolsdemo.entity.Animal;
import org.wmc.droolsdemo.entity.Cat;
import org.wmc.droolsdemo.entity.People;
import org.wmc.droolsdemo.entity.Sensor;

import java.util.ArrayList;


@Slf4j
@SpringBootTest
class DroolsDemoApplicationTests {

    @Autowired
    private KieSession session;

    @Autowired
    private KieBase kieBase;

    @Test
    public void people() {
        log.info("KieSession：{}", session.getClass());
        log.info("KieSession：{}", session);
        //KieSession kieSession = kieBase.newKieSession();
        People people = new People();
        people.setName("达");
        people.setSex(0);
        people.setDrlType("people");
        session.insert(people);//插入
        session.fireAllRules();//执行规则
    }

    @AfterEach
    public void runDispose() {
        log.info("释放资源.............");
        session.dispose();//释放资源
    }

    @Test
    public void cat() {
        log.info("KieSession1：{}", session.getClass());
        log.info("KieSession1：{}", session);
        Cat cat = new Cat();
        cat.setName("金");
        cat.setSex(1);
        session.insert(cat);//插入
        session.fireAllRules();//执行规则
    }

    /**
     * 执行测试，在三组数据中，2个people满足条件，执行两次
     * People(sex=0, name=秋, drlType=from)
     * People(sex=1, name=达, drlType=from)
     */
    @Test
    public void from() {
        People p1 = new People(1,"达","from");
        People p2 = new People(0,"秋","from");
        People p3 = new People(3,"金","from");
        Animal animal = new Animal();
        animal.setPeoples(new ArrayList<>());
        animal.getPeoples().add(p1);
        animal.getPeoples().add(p2);
        animal.getPeoples().add(p3);
        session.insert(animal);//插入
        session.fireAllRules();//执行规则
    }

    /**
     * 执行测试，正确打印出匹配的实事，其中 sex=3的“金” 没有匹配到结果中
     * collect执行成功，匹配结果为：[People(sex=0, name=冬, drlType=collect), People(sex=1, name=夏, drlType=collect),
     * People(sex=0, name=春, drlType=collect), People(sex=0, name=秋, drlType=collect),People(sex=1, name=达, drlType=collect)]
     */
    @Test
    public void collect() {
        session.insert(new People(1, "达","collect"));
        session.insert(new People(0, "秋","collect"));
        session.insert(new People(0, "春","collect"));
        session.insert(new People(1, "夏","collect"));
        session.insert(new People(0, "冬","collect"));
        session.insert(new People(3, "金","collect"));
        session.fireAllRules();//执行规则
    }

    /**
     * 用于遍历数据集对数据项执行自定义或预设动作并返回结果。
     * accumulate成功执行，平均温度为：8.946
     */
    @Test
    public void accumulate() {
        session.insert(new Sensor("达", 8.26));
        session.insert(new Sensor("秋", 7.12));
        session.insert(new Sensor("春", 3.24));
        session.insert(new Sensor("夏", 6.32));
        session.insert(new Sensor("冬", 12.23));
        session.insert(new Sensor("金", 10.8));

        session.fireAllRules();//执行规则
    }

    /**
     * 自定义 accunmulate
     *
     */
    @Test
    public void diyaccumulate() {
        session.insert(new People(1, "达",26,"diyaccumulate"));
        session.insert(new People(0, "秋",18,"diyaccumulate"));
        session.insert(new People(0, "春",38,"diyaccumulate"));
        session.insert(new People(1, "夏",90,"diyaccumulate"));
        session.insert(new People(0, "冬",55,"diyaccumulate"));
        session.insert(new People(3, "金",12,"diyaccumulate"));

        session.fireAllRules();//执行规则
    }
}
