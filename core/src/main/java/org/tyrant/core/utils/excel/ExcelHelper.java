package org.tyrant.core.utils.excel;

import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

public class ExcelHelper<DTO> {

	private List<ExcelHeader> headers;

	private ExcelDataSource<DTO> dataSource;

	private Integer pageSize;
	
	private Properties localProperties;

	private static Integer SHEET_MAX_ROW = 1048576;
	
	public ExcelHelper(List<ExcelHeader> headers, ExcelDataSource<DTO> dataSource, Integer pageSize) {
		this.headers = headers;
		this.dataSource = dataSource;
		this.pageSize = pageSize;
	}
	
	public void setProperties(Properties localProperties) {
		this.localProperties = localProperties;
	}
	
	public SXSSFWorkbook exportExcel2007(String title) {
		if (dataSource == null || headers == null || pageSize <= 0) {
			return null;
		}
		List<DTO> datas = null;
		int current = 1;
		int currentRowCount = 0;
		SXSSFWorkbook wb = new SXSSFWorkbook(1000);
		Sheet currentSheet = wb.createSheet(title + "_" + 0);
		Row currentRow = null;
		currentRow = createRow(currentSheet, currentRowCount++);
		for (int i = 0; i < headers.size(); i++) {
			Cell cell = currentRow.createCell(i);
			String cellTitle = getHeadTitle(headers.get(i).getTitle());
			cell.setCellValue(cellTitle);
		}
		while (true) {
			datas = dataSource.getDataList(current, pageSize);
			if (datas == null || datas.size() == 0) {
				return wb;
			}
			for (DTO data : datas) {
				if (currentRowCount % SHEET_MAX_ROW == 0) {
					currentSheet = wb.createSheet(title + "_" + (currentRowCount / SHEET_MAX_ROW));
				}
				currentRow = createRow(currentSheet, currentRowCount++);
				for (int i = 0; i < headers.size(); i++) {
					Cell cell = currentRow.createCell(i);
					headers.get(i).getField().setAccessible(true);
					cell.setCellValue(dataSource.getValue(data, headers.get(i)));
				}
			}
			current++;
		}
	}

	private String getHeadTitle(String title) {
		String cellTitle = title;
		if (localProperties != null) {
			cellTitle = localProperties.getProperty(cellTitle);
		}
		return StringUtils.isEmpty(cellTitle) ? title : cellTitle;
	}

	public HSSFWorkbook exportExcel2003(String title) {
		if (dataSource == null || headers == null || pageSize <= 0) {
			return null;
		}
		List<DTO> datas = null;
		int current = 1;
		int currentRowCount = 0;
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet currentSheet = wb.createSheet(title + "_" + 0);
		Row currentRow = null;
		currentRow = createRow(currentSheet, currentRowCount++);
		for (int i = 0; i < headers.size(); i++) {
			Cell cell = currentRow.createCell(i);
			String cellTitle = getHeadTitle(headers.get(i).getTitle());
			cell.setCellValue(cellTitle);
		}
		while (true) {
			datas = dataSource.getDataList(current, pageSize);
			if (datas == null || datas.size() == 0) {
				return wb;
			}
			for (DTO data : datas) {
				if (currentRowCount % SHEET_MAX_ROW == 0) {
					currentSheet = wb.createSheet(title + "_" + (currentRowCount / SHEET_MAX_ROW));
				}
				currentRow = createRow(currentSheet, currentRowCount++);
				for (int i = 0; i < headers.size(); i++) {
					Cell cell = currentRow.createCell(i);
					headers.get(i).getField().setAccessible(true);
					cell.setCellValue(dataSource.getValue(data, headers.get(i)));
				}
			}
			current++;
		}
	}

	private Row createRow(Sheet currentSheet, int currentRowCount) {
		return currentSheet.createRow(currentRowCount % SHEET_MAX_ROW);
	}

}
