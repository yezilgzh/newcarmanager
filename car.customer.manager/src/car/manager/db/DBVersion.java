package car.manager.db;

import java.sql.Timestamp;

import car.manager.util.StringUtil;

public class DBVersion extends CommonDto
  implements Comparable<DBVersion>
{
  private String versionNum;
  private String path;
  private String desc;
  private Timestamp createTime;

  public DBVersion()
  {
  }

  public DBVersion(String versionNum, String path)
  {
    this.versionNum = versionNum;
    this.path = path;
  }

  public DBVersion(String versionNum, String path, Timestamp createTime, String desc)
  {
    this.versionNum = versionNum;
    this.path = path;
    this.createTime = createTime;
    this.desc = desc;
  }

  public String getVersionNum()
  {
    return this.versionNum;
  }

  public void setVersionNum(String versionNum)
  {
    this.versionNum = versionNum;
  }

  public String getPath()
  {
    return this.path;
  }

  public void setPath(String path)
  {
    this.path = path;
  }

  public String getDesc()
  {
    return this.desc;
  }

  public void setDesc(String desc)
  {
    this.desc = desc;
  }

  public Timestamp getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Timestamp createTime)
  {
    this.createTime = createTime;
  }

  public boolean greater(String otherVersionNum)
  {
    Long otherVersion = Long.valueOf(analyseVersion(otherVersionNum));
    Long thisVersion = Long.valueOf(analyseVersion(getVersionNum()));
    return thisVersion.longValue() > otherVersion.longValue();
  }

  public boolean less(String otherVersionNum)
  {
    Long otherVersion = Long.valueOf(analyseVersion(otherVersionNum));
    Long thisVersion = Long.valueOf(analyseVersion(getVersionNum()));
    return thisVersion.longValue() < otherVersion.longValue();
  }

  public int compareTo(DBVersion other)
  {
    Long otherVersion = Long.valueOf(analyseVersion(other.getVersionNum()));
    Long thisVersion = Long.valueOf(analyseVersion(getVersionNum()));
    return thisVersion.compareTo(otherVersion);
  }

  private static long analyseVersion(String version)
  {
    if (StringUtil.isBlank(version)) {
      return -1L;
    }
    do
    {
      version = version.substring(1);
    }
    while ((version.startsWith("_")) || (version.startsWith("-")));

    if (version.split("_").length >= 3) {
      throw new RuntimeException("版本号最多只能包含一个_:" + version);
    }

    if (version.split("_").length == 1) {
      version = version + "_0";
    }

    String[] arr = version.split("\\.|_");
    for (int i = 0; i < arr.length; i++) {
      arr[i] = String.format("%03d", new Object[] { Integer.valueOf(Integer.parseInt(arr[i])) });
    }
    String pureVersion = StringUtil.join(arr, "");
    return Long.parseLong(pureVersion);
  }

  public String toString()
  {
    return "DBVersion [versionNum=" + this.versionNum + ", path=" + this.path + 
      ", desc=" + this.desc + ", createTime=" + 
      StringUtil.getStandardDate(this.createTime) + "]";
  }
}