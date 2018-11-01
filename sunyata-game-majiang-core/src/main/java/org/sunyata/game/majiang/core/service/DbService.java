package org.sunyata.game.majiang.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.game.majiang.core.models.majiang.ChapterEndResult;

/**
 * 数据库操作相关
 *
 * @author leo on 2017/12/28.
 */
@Component
public class DbService {
    @Autowired
    private AsyncDbService asyncDbService;

//    @Autowired
//    private RoomResultDao roomResultDao;


    public void save(ChapterEndResult endResult, int roomId, String checkId, int sceneId) {
//        RoomResultDO result = new RoomResultDO();
//        result.setLastPai(endResult.isLastPai());
//        result.setRoomId(roomId);
//        result.setCreateDate(new Date());
//        result.setFangPaoIndex(endResult.getFangPaoIndex());
//        result.setGangShangHua(endResult.isGangShangHua());
//        if (endResult.getHuiEr() != null) {
//            result.setHuiEr(Arrays.stream(endResult.getHuiEr()).mapToInt(Pai::getIndex).toArray());
//        } else {
//            result.setHuiEr(null);
//        }
//
//        result.setHuPai(endResult.isHuPai());
//        result.setHuPaiIndex(endResult.getHuPaiIndex());
//        result.setIsZiMo(endResult.isZiMo());
//
//        result.setRoomCheckId(checkId);
//        result.setSceneId(sceneId);
//        result.setZhuangIndex(endResult.getZhuangIndex());
//
//
//        result.setLeft(endResult.getLeft().stream().mapToInt(r -> (r == null ? Pai.NOT_PAI_INDEX : r.getIndex())).toArray());
//
//
//        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
//        for (int i = 0; i < userPaiInfos.length; i++) {
//            UserPaiInfo info = userPaiInfos[i];
//            try {
//                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "score" + i).getWriteMethod().invoke(
//                        result, info.getScore()
//                );
//                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "user" + i).getWriteMethod().invoke(
//                        result, info.getUserId()
//                );
//                BeanUtils.getPropertyDescriptor(RoomResultDO.class, "userName" + i).getWriteMethod().invoke(
//                        result, info.getUserName()
//                );
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        result.setUserPaiInfos(endResult.getUserPaiInfos());
//        asyncDbService.executeRoom(roomId, () -> {
//            roomResultDao.insert(result);
//        });
    }
}
