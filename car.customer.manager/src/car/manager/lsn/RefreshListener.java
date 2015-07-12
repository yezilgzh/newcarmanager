package car.manager.lsn;

public abstract interface RefreshListener
{
  public abstract String getKey();

  public abstract void refresh();
}