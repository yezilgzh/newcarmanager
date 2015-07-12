import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;

public class Dialog1 extends Dialog {

	protected Dialog1(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite cp = (Composite) super.createDialogArea(parent);
		
		Button btnNewButton = new Button(cp, SWT.NONE);
		btnNewButton.setImage(ResourceManager.getPluginImage("car.customer.manager", "icons/ac_login.png"));
		btnNewButton.setText("New Button");
		
		Label lblNewLabel = new Label(cp, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setText("New Label");
		
		Composite composite = new Composite(cp, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		return cp;
	}
}
