package car.manager.action;

import org.eclipse.jface.action.Action;

import car.manager.viewpart.UserRecordsQueryViewPart;

public class QueryCostomerAction extends Action
{
  private UserRecordsQueryViewPart part;

  public QueryCostomerAction(UserRecordsQueryViewPart part)
  {
    this.part = part;
  }

  public void run()
  {
  }
}