package self.cases.teams.entity;

import java.util.Date;

public class SysStatistics {
    private Long id;
    private Integer userCount;
    private Integer clubCount;
    private Integer activityCount;
    private Integer onlineCount;
    private Date updateTime;

    // 省略getter和setter方法
    // 省略toString方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getClubCount() {
        return clubCount;
    }

    public void setClubCount(Integer clubCount) {
        this.clubCount = clubCount;
    }

    public Integer getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Integer activityCount) {
        this.activityCount = activityCount;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysStatistics{" +
                "id=" + id +
                ", userCount=" + userCount +
                ", clubCount=" + clubCount +
                ", activityCount=" + activityCount +
                ", onlineCount=" + onlineCount +
                ", updateTime=" + updateTime +
                '}';
    }
}