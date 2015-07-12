package car.manager.db;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class JarDBInitializer extends AbstractDBInitializer {
	private static Log log = LogFactory.getLog(JarDBInitializer.class);
	private String jarFilename;

	public JarDBInitializer(String jarFilename) {
		this("DBVERSION", jarFilename);
	}

	public JarDBInitializer(String initialTable, String jarFilename) {
		super(initialTable);
		this.jarFilename = jarFilename;
	}

	protected List<String> getAllFileName() throws Throwable {
		List nameList = new ArrayList();
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(this.jarFilename);
			log.info("sql.script.path=" + jarFile.getName());
			Enumeration listJar = jarFile.entries();
			while (listJar.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) listJar.nextElement();
				if (jarEntry.getName().endsWith(".sql"))
					nameList.add(jarEntry.getName());
			}
			List localList1 = nameList;
			return localList1;
		} catch (Throwable e) {
			throw e;
		} finally {
			if (jarFile != null)
				jarFile.close();
		}
	}

	protected InputStream getFileInStream(String filename) throws Throwable {
		JarFile jarFile = new JarFile(this.jarFilename);
		JarEntry jarEntry = new JarEntry(filename);
		return jarFile.getInputStream(jarEntry);
	}

	protected String getSqlFilePath(String name) {
		return new File(this.jarFilename).getAbsolutePath() + "!" + name;
	}

	public static void main(String[] args) {
		JarDBInitializer dt = new JarDBInitializer(
				"E:/liushuosvn/HedgeTradingSim/dist/stp-studio-sim.jar");
		dt.installAndUpgrade();
	}
}