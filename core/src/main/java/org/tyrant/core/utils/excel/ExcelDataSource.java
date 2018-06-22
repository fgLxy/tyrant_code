package org.tyrant.core.utils.excel;

import java.util.List;

public interface ExcelDataSource<DTO> {
	List<DTO> getDataList(int page, int pageSize);

	String getValue(DTO dto, ExcelHeader header);
}

