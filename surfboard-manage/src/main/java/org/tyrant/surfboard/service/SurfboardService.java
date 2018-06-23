package org.tyrant.surfboard.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tyrant.core.utils.DateUtils;
import org.tyrant.core.utils.IdFactoryUtils;
import org.tyrant.core.utils.excel.ExcelDataSource;
import org.tyrant.core.utils.excel.ExcelHeader;
import org.tyrant.core.utils.excel.ExcelUtils;
import org.tyrant.dao.Idao;
import org.tyrant.dao.pagination.CommonList;
import org.tyrant.dao.pagination.Search;
import org.tyrant.surfboard.dto.FileDTO;
import org.tyrant.surfboard.dto.SurfboardDTO;
import org.tyrant.surfboard.entity.SurfboardEntity;
import org.tyrant.surfboard.entity.SurfboardOperationLogEntity;

@Service
public class SurfboardService {
	private static Logger log = LoggerFactory.getLogger(SurfboardService.class);
	private static final int MAX_QRY_LIST_PAGESIZE = 20;
	private static final Set<String> ORDER_KEY_SET = new HashSet<>();
	static {
		//TODO 设置可排序字段
	}
	@Autowired
	Idao dao;
	@Value("${spring.file.temppath}")
	String tempFilePath;
	
	private CommonList<SurfboardEntity> qryList(Integer pageNo, Integer pageSize, String orderKey,
			Boolean ascFlag,  Boolean unlimitPageSize) throws Exception {
		Search search = new Search();
		search.setSearchSql("select * from sb_surfboard");
		search.setCountSql("select count(*) from sb_surfboard");
		search.setPageNo(pageNo);
		if (!unlimitPageSize) {
			search.setPageSize(Math.min(pageSize, MAX_QRY_LIST_PAGESIZE));
		}
		else {
			search.setPageSize(pageSize);
		}
		if (ORDER_KEY_SET.contains(orderKey)) {
			search.addOrder(ascFlag ? Search.ASC : Search.DESC, orderKey);
		}
		return dao.pagination(search, SurfboardEntity.class);
	}
	
	public CommonList<SurfboardEntity> qryList(Integer pageNo, Integer pageSize, String orderKey,
			Boolean ascFlag) throws Exception {
		return qryList(pageNo, pageSize, orderKey, ascFlag, false);
	}
	
	private CommonList<SurfboardOperationLogEntity> qryOperationLogList(Integer pageNo, Integer pageSize, String orderKey,
			Boolean ascFlag, Boolean unlimitPageSize) throws Exception {
		Search search = new Search();
		search.setSearchSql("select * from sb_surfboard_operation_log");
		search.setCountSql("select count(*) from sb_surfboard_operation_log");
		search.setPageNo(pageNo);
		if (!unlimitPageSize) {
			search.setPageSize(Math.min(pageSize, MAX_QRY_LIST_PAGESIZE));
		}
		else {
			search.setPageSize(pageSize);
		}
		if (ORDER_KEY_SET.contains(orderKey)) {
			search.addOrder(ascFlag ? Search.ASC : Search.DESC, orderKey);
		}
		return dao.pagination(search, SurfboardOperationLogEntity.class);
	}
	
	public CommonList<SurfboardOperationLogEntity> qryOperationLogList(Integer pageNo, Integer pageSize, String orderKey,
			Boolean ascFlag) throws Exception {
		return qryOperationLogList(pageNo, pageSize, orderKey, ascFlag, false);
	}

	public String addSurfboard(SurfboardDTO dto) throws SQLException {
		String id = IdFactoryUtils.generateId();
		SurfboardEntity entity = new SurfboardEntity();
		BeanUtils.copyProperties(dto, entity);
		entity.setId(id);
		entity.setCreateTime(DateUtils.currentTimeString());
		entity.setUpdateTime(entity.getCreateTime());
		dao.insert(entity, "sb_surfboard");
		return id;
	}

	public SurfboardEntity qrySurfboardDetail(String id) throws SQLException {
		return dao.queryObject(SurfboardEntity.class, "select * from sb_surfboard where id=?", id);
	}
	
	public String addSurfboardOperationLog(SurfboardOperationLogEntity entity) throws SQLException {
		entity.setId(IdFactoryUtils.generateId());
		entity.setCreateTime(DateUtils.currentTimeString());
		dao.insert(entity, "sb_surfboard_operation_log");
		return entity.getId();
	}

	public FileDTO exportList(String orderKey, Boolean ascFlag) {
		String filePath = ExcelUtils.generateExcel2007(SurfboardEntity.class, tempFilePath, UUID.randomUUID().toString(), new ExcelDataSource<SurfboardEntity>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<SurfboardEntity> getDataList(int pageNo, int pageSize) {
				try {
					return (List<SurfboardEntity>) qryList(pageNo, pageSize, orderKey, ascFlag, true);
				} catch (Exception e) {
					log.error("Exception", e);
				}
				return null;
			}

			@Override
			public String getValue(SurfboardEntity dto, ExcelHeader header) {
				header.getField().setAccessible(true);
				Object obj;
				try {
					obj = header.getField().get(dto);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return "";
				}
				return obj == null ? "" : obj.toString();
			}
			
		});
		return new FileDTO("surfboard.xlsx", filePath);
	}

	public FileDTO exportOperationLogList(String orderKey, Boolean ascFlag) {
		String filePath = ExcelUtils.generateExcel2007(SurfboardOperationLogEntity.class, tempFilePath, UUID.randomUUID().toString(), new ExcelDataSource<SurfboardOperationLogEntity>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<SurfboardOperationLogEntity> getDataList(int pageNo, int pageSize) {
				try {
					return (List<SurfboardOperationLogEntity>) qryOperationLogList(pageNo, pageSize, orderKey, ascFlag, true);
				} catch (Exception e) {
					log.error("Exception", e);
				}
				return null;
			}

			@Override
			public String getValue(SurfboardOperationLogEntity dto, ExcelHeader header) {
				header.getField().setAccessible(true);
				Object obj;
				try {
					obj = header.getField().get(dto);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return "";
				}
				return obj == null ? "" : obj.toString();
			}
			
		});
		return new FileDTO("surfboard_log.xlsx", filePath);
	}

}
