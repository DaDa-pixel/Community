package self.cases.teams.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import self.cases.teams.entity.SysResourceUsage;
import self.cases.teams.entity.SysStatistics;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysStatsDao {
    // 插入资源使用记录
    int insertResourceUsage(SysResourceUsage usage);

    // 获取最近N条资源使用记录
    List<SysResourceUsage> getRecentResourceUsage(@Param("limit") int limit);

    // 获取最新的系统统计信息
    SysStatistics getLatestStatistics();

    // 更新系统统计信息
    int updateStatistics(SysStatistics statistics);

    // 获取指定时间范围内的资源使用数据
    List<SysResourceUsage> getResourceUsageBetween(@Param("startTime") Date startTime,
                                                   @Param("endTime") Date endTime);
}