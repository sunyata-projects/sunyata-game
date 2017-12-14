package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;
import org.sunyata.game.contract.protobuf.room.Room;
import org.sunyata.game.server.message.AbstractMessage;
import org.sunyata.game.majiang.core.models.majiang.Pai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知客户端"吃碰杠胡"
 * <p/>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class OperationCPGH extends AbstractMessage {
    public static final int TYPE = Integer.parseInt(Commands.optCPGH);
    public static final int ID = 10;

    /**
     * 位置
     */
    protected int index;

    protected int pai;

    protected List<OperationCPGHInfo> operationCPGHInfos = new ArrayList<>();

    public OperationCPGHInfo getOperationInfo(String optName) {
        OperationCPGHInfo operationCPGHInfo = operationCPGHInfos.stream().filter(p -> p.getClass().getAnnotation(org.sunyata.game.majiang.core.models
                .majiang.actions.Action.class).value().equals(optName)).findFirst().orElse(null);
        return operationCPGHInfo;
    }

//    public OperationCPGH(OperationFaPai operationFaPai) {
//        if (operationFaPai.getAnGang() != null && operationFaPai.getAnGang().length > 0) {
//            this.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_AN_GANG).setPai(operationFaPai.getAnGang()));
//        }
//        if (operationFaPai.getMingGang() != null && operationFaPai.getMingGang().length > 0) {
//            this.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_XIAO_MING_GANG).setPai(operationFaPai
//                    .getMingGang()));
//        }
//        if (operationFaPai.getHu()) {
//            this.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_HU).setPai(new int[]{operationFaPai.getPai()
// }));
//        }
//        this.setPai(operationFaPai.getPai());
//        this.setIndex(operationFaPai.getIndex());
//    }

    public int getPai() {
        return pai;
    }

    public OperationCPGH setPai(int pai) {
        this.pai = pai;
        return this;
    }


    public List<OperationCPGHInfo> getOperationCPGHInfos() {
        return operationCPGHInfos;
    }


    //	public OperationCPGH setOperationCPGHInfos(List<OperationCPGHInfo> operationCPGHInfos) {
//		this.operationCPGHInfos = operationCPGHInfos;
//		return this;
//	}
    public OperationCPGH add(OperationCPGHInfo info, Pai pai, int currentIndex) {
        this.operationCPGHInfos.add(info);
        this.setIndex(currentIndex);
        this.setPai(pai.getIndex());
        return this;
    }

    //	/**
//	 * 3个一组一组
//	 */
//	private int[] chi;
//	private boolean isPeng;
//	private boolean isGang;
//	private boolean isHu;
//	private int pai;

    public OperationCPGH() {

    }

    public OperationCPGH(int index, List<OperationCPGHInfo> operationCPGHInfos) {
        this.index = index;
        this.operationCPGHInfos = operationCPGHInfos;
//		this.chi = chi;
//		this.isPeng = isPeng;
//		this.isGang = isGang;
//		this.isHu = isHu;
//		this.pai = pai;
    }
//
//    @Override
//    public void decode(Input in) throws IOException, ProtocolException {
//        index = in.readInt();
////		chi = in.readIntArray();
////		isPeng = in.readBoolean();
////		isGang = in.readBoolean();
////		isHu = in.readBoolean();
////		pai = in.readInt();
//    }
//
//    @Override
//    public void encode(Output out) throws IOException, ProtocolException {
//        out.writeInt(getIndex());
//        //out.writeIntArray(getChi());
////		out.writeBoolean(getIsPeng());
////		out.writeBoolean(getIsGang());
////		out.writeBoolean(getIsHu());
////		out.writeInt(getPai());
//    }

    /**
     * 位置
     */
    public int getIndex() {
        return index;
    }

    /**
     * 位置
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 3个一组一组
     */
//	public int[] getChi() {
//		return chi;
//	}

    /**
     * 3个一组一组
     */
//	public void setChi(int[] chi) {
//		this.chi = chi;
//	}
//
//	public boolean getIsPeng() {
//		return isPeng;
//	}
//
//	public void setIsPeng(boolean isPeng) {
//		this.isPeng = isPeng;
//	}
//
//	public boolean getIsGang() {
//		return isGang;
//	}
//
//	public void setIsGang(boolean isGang) {
//		this.isGang = isGang;
//	}
//
//	public boolean getIsHu() {
//		return isHu;
//	}
//
//	public void setIsHu(boolean isHu) {
//		this.isHu = isHu;
//	}
//
//	public int getPai() {
//		return pai;
//	}
//
//	public void setPai(int pai) {
//		this.pai = pai;
//	}
    @Override
    public String toString() {
        return "OperationCPGH [index=" + index + "]";
    }

    @Override
    public int getMessageType() {
        return TYPE;
    }

    @Override
    public int getMessageId() {
        return ID;
    }

    @Override
    public Object toPbObject() {
        Room.OperationCPGH.Builder builder = Room.OperationCPGH.newBuilder();
//		builder.setIndex(getIndex()).addAllChi(toList(getChi()));
//		builder.setIsPeng(getIsPeng()).setIsGang(getIsGang()).setIsHu(getIsHu());
//		builder.setPai(getPai());
        builder.setIndex(getIndex());
        builder.setPai(getPai());
        List<Room.OperationInfo> collect = getOperationCPGHInfos().stream().map(p -> {
            Room.OperationInfo.Builder builder1 = Room.OperationInfo.newBuilder();
            builder1.setOpt(p.getOpt()).addAllPai(toList(p.getPai()));
            return builder1.build();
        }).collect(Collectors.toList());
        builder.addAllOperationInfo(collect);
        return builder.build();
    }

    @Override
    public byte[] toBytes() {
        Room.OperationCPGH obj = (Room.OperationCPGH) toPbObject();
        return obj.toByteArray();

    }
}
