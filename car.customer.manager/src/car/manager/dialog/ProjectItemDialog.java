package car.manager.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import car.manager.IAfter;
import car.manager.dto.CostProject;

public class ProjectItemDialog extends Dialog {
	private Text text_cost;
	private Text text_remark;
	private IAfter<CostProject> operate;
	private Combo combo_itemname;

	protected ProjectItemDialog(Shell parentShell, IAfter<CostProject> operate) {
		super(parentShell);
		this.operate = operate;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) main.getLayout();
		gridLayout.numColumns = 2;
		Label lblNewLabel = new Label(main, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("项目名称");

		combo_itemname = new Combo(main, SWT.NONE);
		combo_itemname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		combo_itemname.setItems(new String[] { "洗车", "封釉" });

		Label lblNewLabel_1 = new Label(main, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("费用");

		text_cost = new Text(main, SWT.BORDER);
		text_cost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblNewLabel_2 = new Label(main, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setText("备注");

		text_remark = new Text(main, SWT.BORDER);
		text_remark.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		return main;
	}

	@Override
	protected void okPressed() {
		CostProject cp = new CostProject();
		cp.setProjectName(combo_itemname.getText().trim());
		cp.setCost(Integer.parseInt(text_cost.getText().trim()));
		cp.setRemark(text_remark.getText().trim());
		operate.afterOperate(cp);
		super.okPressed();
	}
}
