package car.manager.dialog;

import java.util.LinkedList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.Activator;
import car.manager.dao.ColorDao;
import car.manager.dao.ConsumerDao;
import car.manager.dto.Consumer;
import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;
import car.manager.lsn.ListenerManager;
import car.manager.util.StringUtil;

public class CarReceptionDialog extends Dialog {
	private Table table;
	private Text text_carno;
	private Text text_name;
	private Text text_phone;
	private Group group_consumer;
	private Consumer cs;
	private Text text_type;
	private Button btn_addProject;
	private LinkedList<CostProject> costItems = new LinkedList<CostProject>();
	private TableViewer tableViewer;
	private IAfter<InStoreRecord> operate;
	private Label label_account;
	private Label label_exist;
	private InStoreRecord isr;
	private Composite main;
	private Button btn_man;
	private Button btn_woman;
	private Combo combo_color;
	private Button btn_addColor;
	private Consumer consumer;
	private Group group_carno;
	private Button btn_history;

	public CarReceptionDialog(Shell parentShell, IAfter<InStoreRecord> operate) {
		super(parentShell);
		this.operate = operate;
	}

	protected Control createDialogArea(Composite parent) {
		if (this.isr == null)
			parent.getShell().setText("新车接待");
		else
			parent.getShell().setText("订单详情");
		parent.getShell()
				.setImage(
						Activator
								.getImage("platform:/plugin/car.customer.manager/icons/car.png"));
		this.main = ((Composite) super.createDialogArea(parent));
		GridLayout gridLayout = (GridLayout) this.main.getLayout();
		this.label_exist = new Label(this.main, 0);
		this.label_exist.setVisible(false);
		this.label_exist.setText("客户已存在");
		this.label_exist.setLayoutData(new GridData(16777216, 16777216, false,
				false, 1, 1));
		this.label_exist.setForeground(SWTResourceManager.getColor(3));

		this.group_carno = new Group(this.main, 0);
		this.group_carno.setText("车牌信息");
		this.group_carno.setLayout(new GridLayout(3, false));
		this.group_carno.setLayoutData(new GridData(4, 16777216, true, false,
				1, 1));

		Label lblNewLabel = new Label(this.group_carno, 0);
		lblNewLabel.setLayoutData(new GridData(131072, 16777216, false, false,
				1, 1));
		lblNewLabel.setText("车牌号");

		this.text_carno = new Text(this.group_carno, 2048);
		this.text_carno.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		this.btn_history = new Button(this.group_carno, 0);
		this.btn_history.setText("保养记录");
		this.btn_history.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String carno = CarReceptionDialog.this.text_carno.getText()
						.trim();
				ConsumerDao cdao = DataCache.getInstance().getService()
						.getConsumerDao();
				Consumer cs = cdao.get(carno);
				if (cs == null) {
					MessageDialog.openInformation(
							CarReceptionDialog.this.getShell(), "提示信息", "["
									+ carno + "]为首次到店，未找到对应的进店记录！");
					return;
				}
				new HistoryRecordDialog(CarReceptionDialog.this.getShell(),
						carno).open();
			}
		});
		this.group_consumer = new Group(this.main, 0);
		this.group_consumer.setText("客户信息");
		this.group_consumer.setLayout(new GridLayout(5, false));
		GridData gd_group_1 = new GridData(4, 16777216, false, false, 1, 1);
		gd_group_1.heightHint = 95;
		this.group_consumer.setLayoutData(gd_group_1);

		Label lblNewLabel_1 = new Label(this.group_consumer, 0);
		lblNewLabel_1.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_1.setText("客户姓名");

		this.text_name = new Text(this.group_consumer, 2048);
		this.text_name.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_2 = new Label(this.group_consumer, 0);
		lblNewLabel_2.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_2.setText("手机号");

		this.text_phone = new Text(this.group_consumer, 2048);
		this.text_phone.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));
		new Label(this.group_consumer, 0);

		Label lblNewLabel_7 = new Label(this.group_consumer, 0);
		lblNewLabel_7.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_7.setText("车型");

		this.text_type = new Text(this.group_consumer, 2048);
		this.text_type.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_3 = new Label(this.group_consumer, 0);
		lblNewLabel_3.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_3.setText("颜色");

		this.combo_color = new Combo(this.group_consumer, 0);
		this.combo_color.setLayoutData(new GridData(4, 16777216, true, false,
				1, 1));
		this.combo_color.setItems(DataCache.getInstance().getColorDao().get());
		this.combo_color.setText("");
		this.btn_addColor = new Button(this.group_consumer, 0);
		GridData gd_btnNewButton = new GridData(16384, 16777216, false, false,
				1, 1);
		gd_btnNewButton.widthHint = 38;
		this.btn_addColor.setLayoutData(gd_btnNewButton);
		this.btn_addColor.setImage(ResourceManager.getPluginImage(
				"car.customer.manager", "icons/add.png"));
		this.btn_addColor.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String color = CarReceptionDialog.this.combo_color.getText()
						.trim();
				ColorDao dao = DataCache.getInstance().getColorDao();
				try {
					dao.insert(color);
				} catch (Exception e1) {
					MessageDialog.openError(getShell(), "提示信息", color
							+ "可能已存在，请检查");
					return;
				}
				MessageDialog.openInformation(getShell(), "提示信息", color
						+ "添加成功！");
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Label lblNewLabel_4 = new Label(this.group_consumer, 0);
		lblNewLabel_4.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_4.setText("性别");

		Composite composite_1 = new Composite(this.group_consumer, 0);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(4, 16777216, false, false, 1, 1);
		gd_composite_1.heightHint = 23;
		composite_1.setLayoutData(gd_composite_1);

		this.btn_man = new Button(composite_1, 16);
		this.btn_man.setSelection(true);
		this.btn_man.setText("男");

		this.btn_woman = new Button(composite_1, 16);
		this.btn_woman.setText("女");
		new Label(this.group_consumer, 0);
		new Label(this.group_consumer, 0);
		new Label(this.group_consumer, 0);

		Group group_2 = new Group(this.main, 0);
		group_2.setText("保养项目");
		group_2.setLayout(new GridLayout(2, false));
		group_2.setLayoutData(new GridData(4, 4, true, true, 1, 1));

		this.tableViewer = new TableViewer(group_2, 67584);
		this.table = this.tableViewer.getTable();
		GridData gd_table = new GridData(4, 4, false, true, 1, 1);
		gd_table.widthHint = 525;
		this.table.setLayoutData(gd_table);
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(151);
		tblclmnNewColumn.setText("项目名称");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(184);
		tblclmnNewColumn_1.setText("费用");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(193);
		tblclmnNewColumn_2.setText("备注");

		Composite composite = new Composite(group_2, 0);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(4, 4, true, false, 1, 1));
		new Label(composite, 0);

		this.btn_addProject = new Button(composite, 0);
		this.btn_addProject.setLayoutData(new GridData(4, 16777216, true,
				false, 1, 1));
		this.btn_addProject.setText("增加");
		this.btn_addProject.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ProjectItemDialog(getShell(), new IAfter<CostProject>() {
					public void afterOperate(CostProject t) {
						CarReceptionDialog.this.costItems.add(t);
						CarReceptionDialog.this.tableViewer
								.setInput(CarReceptionDialog.this.costItems);
						int account = 0;
						for (CostProject c : CarReceptionDialog.this.costItems) {
							account += c.getCost();
						}
						CarReceptionDialog.this.label_account.setText(String
								.valueOf(account));
						CarReceptionDialog.this.tableViewer.refresh();
					}
				}).open();
			}
		});
		new Label(composite, 0);

		Button btnNewButton_1 = new Button(composite, 0);
		btnNewButton_1.setLayoutData(new GridData(4, 16777216, false, false, 1,
				1));
		btnNewButton_1.setText("删除");

		Group group_3 = new Group(this.main, 0);
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(4, 4, true, false, 1, 1));

		Label lblNewLabel_5 = new Label(group_3, 0);
		lblNewLabel_5.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel_5.setText("费用总计");
		this.label_account = new Label(group_3, 0);
		this.label_account.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		this.label_account.setForeground(SWTResourceManager.getColor(3));
		this.label_account.setAlignment(131072);
		GridData gd_label_account = new GridData(16384, 16777216, false, false,
				1, 1);
		gd_label_account.widthHint = 92;
		this.label_account.setLayoutData(gd_label_account);
		this.text_carno.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String carno = CarReceptionDialog.this.text_carno.getText()
						.trim().toUpperCase();
				CarReceptionDialog.this.cs = DataCache.getInstance()
						.getService().getConsumerDao().get(carno);
				if (CarReceptionDialog.this.cs != null) {
					CarReceptionDialog.this.text_name.setText(StringUtil
							.filterNull(CarReceptionDialog.this.cs.getName()));
					CarReceptionDialog.this.text_phone.setText(StringUtil
							.filterNull(CarReceptionDialog.this.cs.getPhone()));
					CarReceptionDialog.this.combo_color.setText(StringUtil
							.filterNull(CarReceptionDialog.this.cs.getColor()));
					CarReceptionDialog.this.text_type.setText(StringUtil
							.filterNull(CarReceptionDialog.this.cs.getCartype()));
					CarReceptionDialog.this.label_exist.setVisible(true);
				}

				CarReceptionDialog.this.text_carno.setText(carno);
			}
		});
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new CostItemsLabelProvider());
		this.tableViewer.setInput(this.costItems);
		this.tableViewer.refresh();
		init();
		return this.main;
	}

	private void init() {
		if (this.isr != null) {
			this.text_carno.setText(this.isr.getConsumer().getCarno());
			this.text_name.setText(StringUtil.filterNull(this.isr.getConsumer()
					.getName()));
			this.text_phone.setText(StringUtil.filterNull(this.isr
					.getConsumer().getPhone()));
			this.combo_color.setText(StringUtil.filterNull(this.isr
					.getConsumer().getColor()));
			this.text_type.setText(StringUtil.filterNull(this.isr.getConsumer()
					.getCartype()));
			boolean sex = this.isr.getConsumer().getSex() == 0;
			this.btn_man.setSelection(sex);
			this.btn_woman.setSelection(!sex);
			this.tableViewer.setInput(this.isr.getCostProjects());
			this.tableViewer.refresh();
			this.main.setEnabled(false);
			int totalCost = 0;
			for (CostProject cp : this.isr.getCostProjects()) {
				totalCost += cp.getCost();
			}
			this.label_account.setText(String.valueOf(totalCost));
		}

		if (this.consumer != null) {
			this.text_carno.setText(this.consumer.getCarno());
			this.text_name.setText(StringUtil.filterNull(this.consumer
					.getName()));
			this.text_phone.setText(StringUtil.filterNull(this.consumer
					.getPhone()));
			this.combo_color.setText(StringUtil.filterNull(this.consumer
					.getColor()));
			this.text_type.setText(StringUtil.filterNull(this.consumer
					.getCartype()));
			boolean sex = this.consumer.getSex() == 0;
			this.btn_man.setSelection(sex);
			this.btn_woman.setSelection(!sex);
			this.group_consumer.setEnabled(false);
			this.group_carno.setEnabled(false);
		}
	}

	protected void okPressed() {
		if (this.isr != null) {
			super.okPressed();
			return;
		}
		InStoreRecord isr = new InStoreRecord();
		isr.setRemark("");
		String carNo = this.text_carno.getText().trim();
		if (this.cs == null) {
			this.cs = new Consumer();
			this.cs.setCarno(carNo);
			this.cs.setName(this.text_name.getText().trim());
			this.cs.setPhone(this.text_phone.getText().trim());
			this.cs.setCartype(this.text_type.getText().trim());
			this.cs.setColor(this.combo_color.getText().trim());
			this.cs.setSex(this.btn_man.getSelection() ? 0 : 1);
		}
		isr.setConsumer(this.cs);
		isr.setCostProjects(this.costItems);
		DataCache.getInstance().getService().insert(isr);
		this.operate.afterOperate(isr);
		ListenerManager.fireListener("car.customer.manager.customerview");
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(670, 720);
	}

	class CostItemsLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		CostItemsLabelProvider() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if ((element instanceof CostProject)) {
				CostProject cp = (CostProject) element;
				if (columnIndex == 0)
					return cp.getProjectName();
				if (columnIndex == 1)
					return String.valueOf(cp.getCost());
				if (columnIndex == 2) {
					return cp.getRemark();
				}
			}
			return null;
		}
	}
}