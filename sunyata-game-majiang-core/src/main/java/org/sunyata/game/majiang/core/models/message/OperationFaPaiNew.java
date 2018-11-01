package org.sunyata.game.majiang.core.models.message;

import org.sunyata.game.contract.Commands;

/**
 * 通知客户端"吃碰杠胡"
 * <p/>
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 *
 * @author isnowfox消息生成器
 */
public class OperationFaPaiNew extends OperationCPGH {
    public static final int TYPE = Integer.parseInt(Commands.optFaPai);
    public static final int ID = 10;

    /**
     * 位置
     */
//    private int index;
//    private int pai;
//    private List<OperationCPGHInfo> operationCPGHInfos = new ArrayList<>();


//    public OperationFaPaiNew(OperationFaPai operationFaPai) {
//
//    }
//
//    public int getPai() {
//        return pai;
//    }
//
//    public OperationFaPaiNew setPai(int pai) {
//        this.pai = pai;
//        return this;
//    }


//    public List<OperationCPGHInfo> getOperationCPGHInfos() {
//        return operationCPGHInfos;
//    }

    //	public OperationCPGH setOperationCPGHInfos(List<OperationCPGHInfo> operationCPGHInfos) {
//		this.operationCPGHInfos = operationCPGHInfos;
//		return this;
//	}


//    public static OperationFaPaiNew toOperationCpgh(OperationFaPai operationFaPai) {
//        OperationFaPaiNew result = new OperationFaPaiNew();
//        if (operationFaPai.getAnGang() != null && operationFaPai.getAnGang().length > 0) {
//            result.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_AN_GANG).setPai(operationFaPai.getAnGang()));
//        }
//        if (operationFaPai.getMingGang() != null && operationFaPai.getMingGang().length > 0) {
//            result.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_XIAO_MING_GANG).setPai(operationFaPai
//                    .getMingGang()));
//        }
//        if (operationFaPai.getHu()) {
//            result.add(new OperationCPGHInfo().setOpt(OperationNames.OPT_HU).setPai(new int[]{operationFaPai.getPai()
//            }));
//        }
//        result.setPai(operationFaPai.getPai());
//        result.setIndex(operationFaPai.getIndex());
//        return result;
//    }


    //	/**
//	 * 3个一组一组
//	 */
//	private int[] chi;
//	private boolean isPeng;
//	private boolean isGang;
//	private boolean isHu;
//	private int pai;

    public OperationFaPaiNew() {

    }

//    public OperationFaPaiNew(int index, List<OperationCPGHInfo> operationCPGHInfos) {
//        this.index = index;
//        this.operationCPGHInfos = operationCPGHInfos;
////		this.chi = chi;
////		this.isPeng = isPeng;
////		this.isGang = isGang;
////		this.isHu = isHu;
////		this.pai = pai;
//    }
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
//    public int getIndex() {
//        return index;
//    }
//
//    /**
//     * 位置
//     */
//    public void setIndex(int index) {
//        this.index = index;
//    }

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
//    @Override
//    public String toString() {
//        return "OperationCPGH [index=" + index + "]";
//    }

    @Override
    public final int getMessageType() {
        return TYPE;
    }

//    @Override
//    public final int getMessageId() {
//        return ID;
//    }

//    @Override
//    public Object toPbObject() {
//        Room.OperationCPGH.Builder builder = Room.OperationCPGH.newBuilder();
////		builder.setIndex(getIndex()).addAllChi(toList(getChi()));
////		builder.setIsPeng(getIsPeng()).setIsGang(getIsGang()).setIsHu(getIsHu());
////		builder.setPai(getPai());
//        builder.setIndex(getIndex());
//        builder.setPai(getPai());
//        List<Room.OperationInfo> collect = getOperationCPGHInfos().stream().map(p -> {
//            Room.OperationInfo.Builder builder1 = Room.OperationInfo.newBuilder();
//            builder1.setOpt(p.getOpt()).addAllPai(toList(p.getPai()));
//            return builder1.build();
//        }).collect(Collectors.toList());
//        builder.addAllOperationInfo(collect);
//        return builder.build();
//    }

//    @Override
//    public byte[] toBytes() {
//        Room.OperationCPGH objectj = (Room.OperationCPGH) toPbObject();
//        return objectj.toByteArray();
//    }
}
