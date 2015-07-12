package car.manager.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.dao.ProjectItemDao;
import car.manager.dto.ProjectItem;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;
import car.manager.util.StringUtil;

public class AddOrUpdateProjectItemDialog extends Dialog {
	private Text text_name;
	private IAfter<Object> after;
	private Text text_cost;
	private Text text_desc;
	private ProjectItem item;

	public AddOrUpdateProjectItemDialog(Shell parentShell, IAfter<Object> after) {
		super(parentShell);
		this.after = after;
	}

	public AddOrUpdateProjectItemDialog(Shell parentShell,
			IAfter<Object> after, ProjectItem item) {
		super(parentShell);
		this.after = after;
		this.item = item;
	}

	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("增加保养条目");
		Composite main = (Composite) super.createDialogArea(parent);
		main.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(main, 0);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 10, 0));
		lblNewLabel.setLayoutData(new GridData(16777216, 16777216, false,
				false, 1, 1));
		lblNewLabel.setText("项目名称");

		this.text_name = new Text(main, 2048);
		this.text_name.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_1 = new Label(main, 0);
		lblNewLabel_1.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_1.setText("费用");

		this.text_cost = new Text(main, 2048);
		this.text_cost.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_2 = new Label(main, 0);
		lblNewLabel_2.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_2.setText("描述");

		this.text_desc = new Text(main, 2048);
		this.text_desc.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));
		init();
		return main;
	}

	private void init() {
		if (this.item != null) {
			this.text_name.setText(StringUtil.filterNull(this.item.getName()));
			this.text_cost.setText(StringUtil.filterNull(String
					.valueOf(this.item.getCost())));
			this.text_desc.setText(StringUtil.filterNull(this.item.getDesc()));
		}
	}

	protected void okPressed() {
		if ((this.text_name.getText().trim().length() == 0)
				|| (this.text_cost.getText().trim().length() == 0)) {
			MessageDialog.openError(getShell(), "提示信息", "项目名称或费用不能为空，请检查！");
			return;
		}
		if (this.item == null) {
			this.item = new ProjectItem();
		}
		this.item.setName(this.text_name.getText().trim());
		this.item.setCost(Integer.parseInt(this.text_cost.getText().trim()));
		this.item.setDesc(this.text_desc.getText().trim());
		ProjectItemDao dao = DataCache.getInstance().getProjectItemDao();
		if (this.item.getId() == null)
			try {
				dao.insert(this.item);
			} catch (Exception e) {
				MessageDialog.openError(getShell(), "提示信息", "增加保养条目出错！");
				return;
			}
		else {
			try {
				dao.update(this.item);
			} catch (Exception e) {
				MessageDialog.openError(getShell(), "提示信息", "更新保养条目出错！");
				return;
			}
		}
		this.after.afterOperate(null);
		super.okPressed();
	}
}