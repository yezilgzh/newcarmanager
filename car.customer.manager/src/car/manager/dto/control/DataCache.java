package car.manager.dto.control;

import java.util.LinkedList;
import java.util.List;

import car.manager.dao.RecordService;
import car.manager.dto.InStoreRecord;

/**
 * 
 * @author root
 * 
 */
public class DataCache {
	private static DataCache instance = new DataCache();

	private List<InStoreRecord> records = new LinkedList<InStoreRecord>();
	private RecordService service = new RecordService();
	{
		records = service.getTodayRecords();
	}

	public static DataCache getInstance() {
		return instance;
	}

	public RecordService getService() {
		return service;
	}

	public List<InStoreRecord> getRecords() {
		return records;
	}

	public void addRecord(InStoreRecord record) {
		records.add(record);
	}

}
