package car.manager.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

import car.manager.Activator;
import car.manager.dto.CostProject;
import car.manager.dto.ProjectItem;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;

public class ProjectItemDialog extends Dialog {
	private Text text_cost;
	private Text text_remark;
	private IAfter<CostProject> operate;
	private Combo combo_itemname;
	private List<ProjectItem> projectItems = DataCache.getInstance()
			.getProjectItemDao().get();
	private Button btn_add;

	protected ProjectItemDialog(Shell parentShell, IAfter<CostProject> operate) {
		super(parentShell);
		this.operate = operate;
	}

	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("项目");
		parent.getShell()
				.setImage(
						Activator
								.getImage("platform:/plugin/car.customer.manager/icons/car.png"));
		Composite main = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) main.getLayout();
		gridLayout.numColumns = 2;
		Label lblNewLabel = new Label(main, 0);
		lblNewLabel.setLayoutData(new GridData(131072, 16777216, false, false,
				1, 1));
		lblNewLabel.setText("项目名称");

		this.combo_itemname = new Combo(main, 0);
		this.combo_itemname.setLayoutData(new GridData(4, 16777216, true,
				false, 1, 1));
		this.combo_itemname.setItems(getProjectItems());
		this.combo_itemname.setText("");

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
		lblNewLabel_2.setText("备注");

		this.text_remark = new Text(main, 2048);
		this.text_remark.setLayoutData(new GridData(4, 16777216, true, false,
				1, 1));
		new Label(main, 0);

		this.btn_add = new Button(main, 0);
		this.btn_add.setImage(ResourceManager.getPluginImage(
				"car.customer.manager", "icons/add.png"));
		GridData gd_btnNewButton = new GridData(131072, 16777216, false, false,
				1, 1);
		gd_btnNewButton.widthHint = 58;
		this.btn_add.setLayoutData(gd_btnNewButton);
		this.combo_itemname.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String name = ProjectItemDialog.this.combo_itemname.getText()
						.trim();
				if ((name == null) || ("".equals(name)))
					return;
				ProjectItem project = ProjectItemDialog.this.getItem(name);
				if (project != null) {
					ProjectItemDialog.this.text_cost.setText(String
							.valueOf(project.getCost()));
					ProjectItemDialog.this.text_remark.setText(project
							.getDesc());
				}
			}
		});
		this.btn_add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ((ProjectItemDialog.this.combo_itemname.getText().trim()
						.length() == 0)
						|| (ProjectItemDialog.this.text_cost.getText().trim()
								.length() == 0)) {
					MessageDialog.openInformation(
							ProjectItemDialog.this.getShell(), "提示信息",
							"项目名称或费用不能为空请检查！");
					return;
				}
				ProjectItem cp = new ProjectItem();
				cp.setName(ProjectItemDialog.this.combo_itemname.getText()
						.trim());
				cp.setCost(Integer.parseInt(ProjectItemDialog.this.text_cost
						.getText().trim()));
				cp.setDesc(ProjectItemDialog.this.text_remark.getText().trim());
				try {
					DataCache.getInstance().getProjectItemDao().insert(cp);
				} catch (Exception e1) {
					MessageDialog.openInformation(
							ProjectItemDialog.this.getShell(), "提示信息",
							"保养项目插入失败！");
					return;
				}
				MessageDialog.openInformation(
						ProjectItemDialog.this.getShell(), "提示信息", "["
								+ ProjectItemDialog.this.combo_itemname
										.getText().trim() + "]添加成功！");
			}
		});
		return main;
	}

	private String[] getProjectItems() {
		List items = new ArrayList();
		for (ProjectItem item : this.projectItems) {
			items.add(item.getName());
		}
		return (String[]) items.toArray(new String[0]);
	}

	private ProjectItem getItem(String name) {
		for (ProjectItem item : this.projectItems) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}

	protected void okPressed() {
		CostProject cp = new CostProject();
		cp.setProjectName(this.combo_itemname.getText().trim());
		cp.setCost(Integer.parseInt(this.text_cost.getText().trim()));
		cp.setRemark(this.text_remark.getText().trim());
		this.operate.afterOperate(cp);
		super.okPressed();
	}
}