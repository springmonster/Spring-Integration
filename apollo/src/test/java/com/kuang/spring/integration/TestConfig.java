package com.kuang.spring.integration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

/**
 * -Dapp.id=apollo-quickstart -Denv=DEV -Ddev_meta=http://192.168.50.50:8080
 * <p>
 * -Dapp.id=mapp -Denv=DEV -Ddev_meta=http://192.168.50.50:8080
 */
public class TestConfig {

    public static void main(String[] args) throws InterruptedException {

//        Config config = ConfigService.getAppConfig();
//        String someKey = "sms.enable";
//
//        while (true) {
//            String value = config.getProperty(someKey, null);
//            System.out.printf("now: %s, sms.enable: %s%n", LocalDateTime.now().toString(), value);
//            Thread.sleep(3000L);
//        }

        Config config = ConfigService.getConfig("rabbitmq");

        String rabbitMQAddress = "rabbitmq.address";
        System.out.println(config.getProperty(rabbitMQAddress, null));

        String rabbitMQvHost = "rabbitmq.vhost";
        System.out.println(config.getProperty(rabbitMQvHost, null));
    }
}
