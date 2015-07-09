package car.manager.viewpart;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.IAfter;
import car.manager.dialog.CarReceptionDialog;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.util.DynamicViewerSorter;
import car.manager.util.TimeUtil;

public class CustomerManagerViewPart extends ViewPart {
	public static final String ID = "car.customer.manager.customerview";
	private Table table;
	private Button btn_add;
	private Button btn_editor;
	private TableViewer tableViewer;
	private DynamicViewerSorter sorter = new DynamicViewerSorter(
			InStoreRecord.class);
	private Label label_profit;

	public CustomerManagerViewPart() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Group group_1 = new Group(composite, SWT.NONE);
		group_1.setText("\u529F\u80FD");
		group_1.setLayout(new GridLayout(2, false));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_group_1.heightHint = 40;
		group_1.setLayoutData(gd_group_1);

		btn_add = new Button(group_1, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnNewButton.widthHint = 99;
		btn_add.setLayoutData(gd_btnNewButton);
		btn_add.setText("\u65B0\u8F66\u63A5\u5F85");
		btn_add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new CarReceptionDialog(parent.getShell(),
						new IAfter<InStoreRecord>() {

							@Override
							public void afterOperate(InStoreRecord record) {
								DataCache.getInstance().addRecord(record);
								refreshViewer();
							}
						}).open();
			}
		});
		// btn_editor = new Button(group_1, SWT.NONE);
		// GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER,
		// false,
		// false, 1, 1);
		// gd_btnNewButton_1.widthHint = 91;
		// btn_editor.setLayoutData(gd_btnNewButton_1);
		// btn_editor.setText("\u4FEE\u6539\u8BA2\u5355");
		// btn_editor.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		//
		// }
		// });
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.H_SCROLL);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_5 = tableViewerColumn_5.getColumn();
		tblclmnNewColumn_5.setWidth(170);
		tblclmnNewColumn_5.setText("\u63A5\u5F85\u65F6\u95F4");
		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("\u8F66\u724C\u53F7");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("\u8F66\u4E3B\u59D3\u540D");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(84);
		tblclmnNewColumn_2.setText("\u8F66\u578B");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(85);
		tblclmnNewColumn_3.setText("\u989C\u8272");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_4.setWidth(138);
		tblclmnNewColumn_4.setText("\u624B\u673A\u53F7");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_6 = tableViewerColumn_6.getColumn();
		tblclmnNewColumn_6.setWidth(100);
		tblclmnNewColumn_6.setText("\u8D39\u7528\u603B\u8BA1");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_7 = tableViewerColumn_7.getColumn();
		tblclmnNewColumn_7.setWidth(137);
		tblclmnNewColumn_7.setText("\u4FDD\u517B\u9879\u76EE");

		Composite composite_1 = new Composite(group, SWT.NONE);
		composite_1.setLayout(new GridLayout(25, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager
				.getFont("΢���ź�", 15, SWT.NORMAL));
		lblNewLabel.setText("\u603B\u6536\u5165");

		label_profit = new Label(composite_1, SWT.NONE);
		label_profit.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_profit.setFont(SWTResourceManager.getFont("΢���ź�", 15,
				SWT.NORMAL));
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel_1.widthHint = 101;
		label_profit.setLayoutData(gd_lblNewLabel_1);

		Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("΢���ź�", 15,
				SWT.NORMAL));
		lblNewLabel_2.setText("\u5143");
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new CustomerLabelProvider());
		tableViewer.setSorter(sorter);
		sorter.setSortColumn("Time", false);

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object select = ((StructuredSelection) event.getSelection())
						.getFirstElement();
				InStoreRecord ir = (InStoreRecord) select;
				new CarReceptionDialog(parent.getShell(),
						new IAfter<InStoreRecord>() {

							@Override
							public void afterOperate(InStoreRecord record) {

							}
						}, ir).open();

			}
		});
		refreshViewer();
	}

	public void refreshViewer() {
		List<InStoreRecord> input = DataCache.getInstance().getRecords();
		tableViewer.setInput(input);
		int account = 0;
		for (InStoreRecord ir : input) {
			account += ir.getCost();

		}
		label_profit.setText(String.valueOf(account));
		tableViewer.refresh();
	}

	@Override
	public void setFocus() {

	}

	class CustomerLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof InStoreRecord) {
				InStoreRecord ir = (InStoreRecord) element;
				if (columnIndex == 0) {
					return TimeUtil.getStandardDate(ir.getTime());
				} else if (columnIndex == 1) {
					return ir.getConsumer().getCarno();
				} else if (columnIndex == 2) {
					return ir.getConsumer().getName();
				} else if (columnIndex == 3) {
					return ir.getConsumer().getCartype();
				} else if (columnIndex == 4) {
					return ir.getConsumer().getColor();
				} else if (columnIndex == 5) {
					return ir.getConsumer().getPhone();
				} else if (columnIndex == 6) {
					return ir.getCost() + "";
				} else if (columnIndex == 7) {
					return ir.getProjects();
				}
			}

			return null;
		}

	}

}
