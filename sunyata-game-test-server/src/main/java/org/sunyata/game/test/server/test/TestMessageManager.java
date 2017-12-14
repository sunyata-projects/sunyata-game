package org.sunyata.game.test.server.test;

import org.sunyata.game.server.OctopusRequest;
import org.sunyata.game.server.OctopusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leo on 16/9/27.
 */
public class TestMessageManager {
    private static final Logger log = LoggerFactory.getLogger(TestMessageManager.class);

//    @Autowired
//    private SceneManager sceneManager;
//    @Autowired
//    private RoomService roomService;
//    @Autowired
//    private UserService userService;

    public void handler(OctopusRequest request, OctopusResponse response) {
        try {
            log.info(String.valueOf(request.getMessage().getCmd()));
        } catch (
                Throwable th
                )

        {

        }

    }

//    private Scene checkScene(PxMsg msg) {
//        Session<Scene> sceneSession = msg.getSession();
//        final Scene scene = sceneSession.get();
//        if (scene == null) {
//            log.error("未注册的Scene:{},sceneSession:{}", msg, sceneSession);
//            throw new ServerRuntimeException("未注册的Scene:" + msg + ",sceneSession:" + sceneSession);
//        }
//        return scene;
//    }
}
