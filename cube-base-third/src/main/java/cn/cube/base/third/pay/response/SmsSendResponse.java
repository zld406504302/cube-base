package cn.cube.base.third.pay.response;

/**
 *
 */
public class SmsSendResponse {
    // 手机号码
    private String phone;
    // 发送是否成功
    private boolean status;
    // 发送上限
    private int limit;
    // 已经发送次数
    private int count;
    // 语音发送上限
    private int voiceLimit;
    // 语音已经发送次数
    private int voiceCount;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getVoiceLimit() {
        return voiceLimit;
    }

    public void setVoiceLimit(int voiceLimit) {
        this.voiceLimit = voiceLimit;
    }

    public int getVoiceCount() {
        return voiceCount;
    }

    public void setVoiceCount(int voiceCount) {
        this.voiceCount = voiceCount;
    }

    @Override
    public String toString() {
        return "SmsResponse{" +
                "phone='" + phone + '\'' +
                ", status=" + status +
                ", limit=" + limit +
                ", count=" + count +
                ", voiceLimit=" + voiceLimit +
                ", voiceCount=" + voiceCount +
                '}';
    }

}
