package car.manager;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{
  public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
  {
    super(configurer);
  }

  protected void fillCoolBar(ICoolBarManager coolBar)
  {
    coolBar.add(new Separator("additions"));
  }
}