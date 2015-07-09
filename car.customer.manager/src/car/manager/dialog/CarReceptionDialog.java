package car.manager.dialog;

import java.util.LinkedList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.IAfter;
import car.manager.dao.ConsumerDao;
import car.manager.dto.Consumer;
import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.util.StringUtil;

/**
 * �³��Ӵ�Ի���
 * 
 * @author root
 * 
 */
public class CarReceptionDialog extends Dialog {
	private Table table;
	private Text text_carno;
	private Text text_name;
	private Text text_color;
	private Text text_phone;
	private ConsumerDao consumerDao = new ConsumerDao();
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

	public CarReceptionDialog(Shell parentShell, IAfter<InStoreRecord> operate) {
		super(parentShell);
		this.operate = operate;
	}

	public CarReceptionDialog(Shell parentShell, IAfter<InStoreRecord> operate,
			InStoreRecord isr) {
		super(parentShell);
		this.operate = operate;
		this.isr = isr;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		if (isr == null)
			parent.getShell().setText("新车接待");
		else
			parent.getShell().setText("订单详情");
		main = (Composite) super.createDialogArea(parent);
		label_exist = new Label(main, SWT.NONE);
		label_exist.setVisible(false);
		label_exist.setText("客户已存在");
		label_exist.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		label_exist.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		Group group = new Group(main, SWT.NONE);
		group.setText("\u8F66\u724C\u4FE1\u606F");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("\u8F66\u724C\u53F7");

		text_carno = new Text(group, SWT.BORDER);
		text_carno.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		group_consumer = new Group(main, SWT.NONE);
		group_consumer.setText("\u5BA2\u6237\u4FE1\u606F");
		group_consumer.setLayout(new GridLayout(4, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_group_1.heightHint = 95;
		group_consumer.setLayoutData(gd_group_1);

		Label lblNewLabel_1 = new Label(group_consumer, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("\u5BA2\u6237\u59D3\u540D");

		text_name = new Text(group_consumer, SWT.BORDER);
		text_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblNewLabel_2 = new Label(group_consumer, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setText("\u624B\u673A\u53F7");

		text_phone = new Text(group_consumer, SWT.BORDER);
		text_phone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblNewLabel_7 = new Label(group_consumer, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_7.setText("车型");

		text_type = new Text(group_consumer, SWT.BORDER);
		text_type.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblNewLabel_3 = new Label(group_consumer, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_3.setText("\u989C\u8272");

		text_color = new Text(group_consumer, SWT.BORDER);
		text_color.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Group group_2 = new Group(main, SWT.NONE);
		group_2.setText("\u4FDD\u517B\u9879\u76EE");
		group_2.setLayout(new GridLayout(2, false));
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tableViewer = new TableViewer(group_2, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_table.widthHint = 525;
		table.setLayoutData(gd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(151);
		tblclmnNewColumn.setText("\u9879\u76EE\u540D\u79F0");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(184);
		tblclmnNewColumn_1.setText("\u8D39\u7528");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(193);
		tblclmnNewColumn_2.setText("\u5907\u6CE8");

		Composite composite = new Composite(group_2, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		new Label(composite, SWT.NONE);

		btn_addProject = new Button(composite, SWT.NONE);
		btn_addProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		btn_addProject.setText("\u589E\u52A0");
		btn_addProject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ProjectItemDialog(parent.getShell(),
						new IAfter<CostProject>() {
							@Override
							public void afterOperate(CostProject t) {
								costItems.add(t);
								tableViewer.setInput(costItems);
								int account = 0;
								for (CostProject c : costItems) {
									account += c.getCost();
								}
								label_account.setText(String.valueOf(account));
								tableViewer.refresh();

							}
						}).open();
			}
		});

		new Label(composite, SWT.NONE);

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnNewButton_1.setText("\u5220\u9664");

		Group group_3 = new Group(main, SWT.NONE);
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1,
				1));

		Label lblNewLabel_5 = new Label(group_3, SWT.NONE);
		lblNewLabel_5.setFont(SWTResourceManager.getFont("΢���ź�", 15,
				SWT.NORMAL));
		lblNewLabel_5.setText("\u8D39\u7528\u603B\u8BA1");
		label_account = new Label(group_3, SWT.NONE);
		label_account.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_account.setAlignment(SWT.RIGHT);
		GridData gd_label_account = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_label_account.widthHint = 92;
		label_account.setLayoutData(gd_label_account);
		label_account.setFont(SWTResourceManager.getFont("΢���ź�", 15,
				SWT.NORMAL));
		text_carno.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String carno = text_carno.getText().trim();
				cs = consumerDao.get(carno);
				if (cs != null) {
					text_name.setText(StringUtil.filterNull(cs.getName()));
					text_phone.setText(StringUtil.filterNull(cs.getPhone()));
					text_color.setText(StringUtil.filterNull(cs.getColor()));
					text_type.setText(StringUtil.filterNull(cs.getCartype()));
					label_exist.setVisible(true);
				} else {
				}

			}
		});

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new CostItemsLabelProvider());
		tableViewer.setInput(costItems);
		tableViewer.refresh();
		init();
		return main;
	}

	private void init() {
		if (isr != null) {
			text_carno.setText(isr.getConsumer().getCarno());
			text_name.setText(StringUtil
					.filterNull(isr.getConsumer().getName()));
			text_phone.setText(StringUtil.filterNull(isr.getConsumer()
					.getPhone()));
			text_color.setText(StringUtil.filterNull(isr.getConsumer()
					.getColor()));
			text_type.setText(StringUtil.filterNull(isr.getConsumer()
					.getCartype()));
			tableViewer.setInput(isr.getCostProjects());
			tableViewer.refresh();
			main.setEnabled(false);
		}

	}

	@Override
	protected void okPressed() {
		if (isr != null) {
			super.okPressed();
			return;
		}
		InStoreRecord isr = new InStoreRecord();
		isr.setRemark("");
		String carNo = text_carno.getText().trim();
		if (cs == null) {
			cs = new Consumer();
			cs.setCarno(carNo);
			cs.setName(text_name.getText().trim());
			cs.setPhone(text_phone.getText().trim());
			cs.setCartype(text_type.getText().trim());
			cs.setColor(text_color.getText().trim());
		}
		isr.setConsumer(cs);
		isr.setCostProjects(costItems);
		DataCache.getInstance().getService().insert(isr);
		operate.afterOperate(isr);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(670, 720);
	}

	class CostItemsLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof CostProject) {
				CostProject cp = (CostProject) element;
				if (columnIndex == 0)
					return cp.getProjectName();
				else if (columnIndex == 1)
					return cp.getCost() + "";
				else if (columnIndex == 2)
					return cp.getRemark();
			}

			return null;
		}

	}

}
