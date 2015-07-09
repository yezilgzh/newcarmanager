package car.manager;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.h2.tools.Server;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	Logger log = Logger.getLogger(getClass());

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		opendbServer();
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setShellStyle(SWT.MIN | SWT.MAX);
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("散户管理系统");
	}

	/**
	 * 启动数据库服务器
	 */
	private void opendbServer() {
		try {
			Driver.load(ApplicationWorkbenchWindowAdvisor.class);
			startH2Server();
			log.info("系统初始化成功");
		} catch (Throwable e) {
			log.error("系统初始化失败", e);
		}
	}

	/**
	 * 启动数据库服务器
	 */
	private void startH2Server() {
		int count = 10;// 总尝试次数
		for (int i = 0; i < count; i++) {
			try {
				Server server = Server.createTcpServer(new String[] { "-tcp",
						"-tcpPort", "9093", "-tcpAllowOthers" });
				server.start();
				log.info("H2数据库启动成功[失败次数=" + i + "]");
				return;
			} catch (Throwable e) {
				log.error("H2数据库启动失败[失败次数=" + (i + 1) + "]", e);
				try {
					if (i == count - 1) {// 最后一次，不再睡，直接break
						break;
					}
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		MessageDialog.openError(getWindowConfigurer().getWindow().getShell(),
				"提示信息", "数据库启动失败，请检查9093端口是否被占用！");
		System.exit(0);
	}
}
