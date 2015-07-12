package car.manager.db;

import java.io.Serializable;

public class CommonDto
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = -2156084633344393722L;
  private String primaryKey;

  public String getPrimaryKey()
  {
    return this.primaryKey;
  }

  public void setPrimaryKey(String primaryKey)
  {
    this.primaryKey = primaryKey;
  }

  public Object clone()
  {
    try
    {
      return super.clone(); } catch (CloneNotSupportedException e) {
    }
    return null;
  }
}