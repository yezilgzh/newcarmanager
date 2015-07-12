package car.manager;

import java.util.MissingResourceException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.osgi.framework.BundleContext;

import car.manager.servlet.QuerUserRecordsServlet;
import car.manager.servlet.QuerUsersServlet;

public class Activator extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "car.customer.manager";
	private static Activator plugin;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Server server = new Server(6969);
		Context root = new Context(server, "/", 1);
		root.addServlet(new ServletHolder(new QuerUsersServlet()),
				"/web/carquery");
		root.addServlet(new ServletHolder(new QuerUserRecordsServlet()),
				"/web/userrecords");
		server.start();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin("car.customer.manager", path);
	}

	public static Image getImage(String path) {
		if (path == null) {
			return null;
		}
		return getImage(getDefault().getImageRegistry(), path.toString());
	}

	public static Image getImage(ImageRegistry registry, String imgname) {
		try {
			Image img = registry.get(imgname);
			if (img == null) {
				ImageDescriptor desc = imageDescriptorFromPlugin(
						"car.customer.manager", imgname);
				registry.put(imgname, desc);
				img = registry.get(imgname);
			}
			return img;
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		return null;
	}
}