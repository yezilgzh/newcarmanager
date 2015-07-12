package car.manager;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.h2.tools.Server;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{
  Logger log = Logger.getLogger(getClass());

  public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
  {
    super(configurer);
  }

  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
  {
    return new ApplicationActionBarAdvisor(configurer);
  }

  public void preWindowOpen() {
    opendbServer();
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setShellStyle(1152);
    configurer.setShowCoolBar(true);
    configurer.setShowStatusLine(false);
    configurer.setTitle("ɢ������ϵͳ");
  }

  private void opendbServer()
  {
    try
    {
      Driver.load(ApplicationWorkbenchWindowAdvisor.class);
      startH2Server();
      this.log.info("ϵͳ��ʼ���ɹ�");
    } catch (Throwable e) {
      this.log.error("ϵͳ��ʼ��ʧ��", e);
    }
  }

  private void startH2Server()
  {
    int count = 10;
    for (int i = 0; i < count; ) {
      try {
        Server server = Server.createTcpServer(new String[] { "-tcp", 
          "-tcpPort", "9093", "-tcpAllowOthers" });
        server.start();
        this.log.info("H2���ݿ������ɹ�[ʧ�ܴ���=" + i + "]");
        return;
      } catch (Throwable e) {
        this.log.error("H2���ݿ�����ʧ��[ʧ�ܴ���=" + (i + 1) + "]", e);
        try {
          if (i == count - 1) {
            break;
          }
          Thread.sleep(2000L);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        i++;
      }

    }

    MessageDialog.openError(getWindowConfigurer().getWindow().getShell(), 
      "��ʾ��Ϣ", "���ݿ�����ʧ�ܣ�����9093�˿��Ƿ�ռ�ã�");
    System.exit(0);
  }
}