//package org.sunyata.game.login; /**
// * Created by leo on 17/4/18.
// */
//
//import EnableGameServer;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.sunyata.game.state.manager.config.EnableOctopusState;
//
//@SpringBootApplication
//@EnableDiscoveryClient
////@EnableScheduling
////@EnableQuarkClient
////@EnableThriftClient
//@EnableOctopusState
//@EnableGameServer
//public class Application {
//
////    @Autowired
////    BusinessManager businessManager;
//
//    public static void main(String[] args) throws InterruptedException {
//        new SpringApplicationBuilder(Application.class).web(true).run(args);
//    }
//
//    //
////    @Bean
////    public AlwaysSampler defaultSampler() {
////        return new AlwaysSampler();
////    }
////    @PostConstruct
////    public void initCoordination() {
////
////    }
//
////    @Bean
////    BusinessManager businessManager() throws Exception {
////        BusinessManager c = new MultipleThreadBusinessManager();
////        c.setScanPackage("org.sunyata.quark.embed.demo.springcloud.components");
////        c.setServiceLocator(SpringServiceLocator.class);
////        c.setEventPublisher(SpringEventEventPublisher.class);
////        c.initialize();
//////        c.register(ParallelBusinessComponent.class);
//////        c.register(SingleBusinessComponent.class);
//////        c.register(TwoBusinessComponent.class);
////        return c;
////    }
//}
