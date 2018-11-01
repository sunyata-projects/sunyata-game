package org.sunyata.game.majiang.core.models.majiang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JiaFanComputeResultInfo {
    private int nums;
    private List<JiaFanTypeInterface> withIns;
    private List<JiaFanTypeInterface> withOuts;
    private JiaFanTypeInterface jiaFan;
    private FanInfo jiaFanInfo;
    private boolean disable;

    public int getNums() {
        return nums;
    }

    public JiaFanComputeResultInfo setNums(int nums) {
        this.nums = nums;
        return this;
    }

    public List<JiaFanTypeInterface> getWithIns() {
        return withIns;
    }

    public JiaFanComputeResultInfo setWithIns(List<JiaFanTypeInterface> withIns) {
        this.withIns = withIns;
        return this;
    }

    public List<JiaFanTypeInterface> getWithOuts() {
        return withOuts;
    }

    public JiaFanComputeResultInfo setWithOuts(List<JiaFanTypeInterface> withOuts) {
        this.withOuts = withOuts;
        return this;
    }

    public JiaFanComputeResultInfo addWithOut(JiaFanTypeInterface jiaFanTypeInterface) {
        if (this.withOuts == null) {
            this.withOuts = new ArrayList<>();
        }
        this.withOuts.add(jiaFanTypeInterface);
        return this;
    }

    public JiaFanComputeResultInfo addWithOut(JiaFanTypeInterface... jiaFanTypeInterfaces) {
        if (this.withOuts == null) {
            this.withOuts = new ArrayList<>();
        }
        this.withOuts.addAll(Arrays.asList(jiaFanTypeInterfaces));
        return this;
    }

    public void setJiaFan(JiaFanTypeInterface jiaFan) {
        this.jiaFan = jiaFan;
    }

    public JiaFanTypeInterface getJiaFan() {
        return jiaFan;
    }

    public void setJiaFanInfo(FanInfo jiaFanInfo) {
        this.jiaFanInfo = jiaFanInfo;
    }

    public FanInfo getJiaFanInfo() {
        return jiaFanInfo;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable() {
        return disable;
    }
}
