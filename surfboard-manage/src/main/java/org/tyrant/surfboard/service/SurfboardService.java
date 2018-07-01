package org.tyrant.surfboard.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tyrant.core.utils.excel.ExcelDataSource;
import org.tyrant.core.utils.excel.ExcelHeader;
import org.tyrant.core.utils.excel.ExcelUtils;
import org.tyrant.dao.Idao;
import org.tyrant.dao.pagination.CommonList;
import org.tyrant.dao.pagination.Search;
import org.tyrant.dao.pagination.Search.Order;
import org.tyrant.surfboard.consts.Constants;
import org.tyrant.surfboard.dto.AddStorageDTO;
import org.tyrant.surfboard.dto.DelStorageDTO;
import org.tyrant.surfboard.dto.FileDTO;
import org.tyrant.surfboard.dto.SurfboardDTO;
import org.tyrant.surfboard.dto.SurfboardStateItemDTO;
import org.tyrant.surfboard.dto.SurfboardStateListDTO;
import org.tyrant.surfboard.entity.StorageEntity;
import org.tyrant.surfboard.entity.SurfboardEntity;
import org.tyrant.surfboard.entity.SurfboardReturnLogEntity;
import org.tyrant.surfboard.entity.SurfboardStateEntity;
import org.tyrant.surfboard.utils.CookieUtils;

@Service
public class SurfboardService {
	private static Logger log = LoggerFactory.getLogger(SurfboardService.class);
	private static final int MAX_QRY_LIST_PAGESIZE = 20;
	private static final Set<String> ORDER_KEY_SET = new HashSet<>();
	private static final Properties ZH_LOCALES = new Properties();
	private static final Properties EN_LOCALES = new Properties();
	static {
		try {
			ZH_LOCALES.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("zh.properties")));
			EN_LOCALES.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("en.properties")));
		} catch (IOException e) {
			log.error("读取国际化文件失败");
		}
	}
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
	
	public CommonList<SurfboardEntity> surfboardList(Integer pageNo, Integer pageSize, String orderKey,
			Boolean ascFlag) throws Exception {
		return qryList(pageNo, pageSize, orderKey, ascFlag, false);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Integer addSurfboard(SurfboardDTO dto) throws SQLException {
		String sql = "insert ignore into sb_surfboard(id,name,type,size,num,production_date,storage) values(?,?,?,?,?,?,?)";
		int rtn = dao.update(sql, dto.getId(), dto.getName(), dto.getType(), dto.getSize(), dto.getNum(), dto.getProductionDate(), dto.getStorage());
		SurfboardStateEntity entity = new SurfboardStateEntity();
		entity.setId(dto.getId());
		entity.setRentStatus(Constants.RENT_STATUS_IDLE);
		dao.insert(entity, "sb_surfboard_status");
		return rtn;
	}
	
	public Integer updateSurfboard(SurfboardDTO dto) throws SQLException {
		SurfboardEntity entity = new SurfboardEntity();
		BeanUtils.copyProperties(dto, entity);
		return dao.update(entity, "sb_surfboard");
	}

	public FileDTO exportList(String orderKey, Boolean ascFlag) throws IOException {
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

	public CommonList<StorageEntity> storageList(Integer pageNo, Integer pageSize) throws Exception {
		if (pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
			return totalStorageList();
		}
		Search search = new Search();
		search.setSearchSql("select * from sb_storage");
		search.setCountSql("select count(*) from sb_storage");
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		return dao.pagination(search, StorageEntity.class);
	}

	private CommonList<StorageEntity> totalStorageList() throws SQLException {
		String sql = "select * from sb_storage";
		List<StorageEntity> entitys = dao.queryObjects(StorageEntity.class, sql);
		CommonList<StorageEntity> rtn = new CommonList<>(entitys == null ? 0 : entitys.size(), 0, 0);
		rtn.setRecords(entitys);
		return rtn;
	}

	public AddStorageDTO addStorage(AddStorageDTO dto) throws SQLException {
		String sql = "insert ignore into sb_storage(name) values(?)";
		dao.update(sql, dto.getName());
		return dto;
	}

	public void delStorage(DelStorageDTO dto) throws SQLException {
		String sql = "delete from sb_storage where name=?";
		dao.update(sql, dto.getName());
	}

	public Integer delSurfboard(SurfboardDTO dto) throws SQLException {
		String sql = "delete from sb_surfboard where id=?";
		return dao.update(sql, dto.getId());
	}

	public CommonList<SurfboardStateItemDTO> surfboardStateList(SurfboardStateListDTO dto) throws Exception {
		Search search = new Search();
		search.setSearchSql("select * from sb_surfboard s,sb_surfboard_status ss where s.id=ss.id");
		search.setCountSql("select count(*) from sb_surfboard s,sb_surfboard_status ss where s.id=ss.id");
		search.setPageNo(dto.getPageNo());
		search.setPageSize(dto.getPageSize());
		if (ORDER_KEY_SET.contains(dto.getOrderKey())) {
			search.addOrder(dto.getAscFlag() ? Search.ASC : Search.DESC, dto.getOrderKey());
		}
		if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
			search.addWhere(Search.AND, "ss.rent_status in ('" + StringUtils.join(dto.getStatus(), "','") + "')");
		}
		if (dto.getSize() != null && !dto.getSize().isEmpty()) {
			search.addWhere(Search.AND, "s.size in ('" + StringUtils.join(dto.getSize(), "','") + "')");
		}
		if (dto.getType() != null && !dto.getType().isEmpty()) {
			search.addWhere(Search.AND, "s.type in ('" + StringUtils.join(dto.getType(), "','") + "')");
		}
		if (dto.getStorage() != null && !dto.getStorage().isEmpty()) {
			search.addWhere(Search.AND, "s.storage in ('" + StringUtils.join(dto.getStorage(), "','") + "')");
		}
		return dao.pagination(search, SurfboardStateItemDTO.class);
	}

	public CommonList<SurfboardReturnLogEntity> dataList(Integer pageNo, Integer pageSize, String startDate, String endDate) throws Exception {
		Search search = new Search();
		search.setSearchSql("select * from sb_surfboard_return_log");
		search.setCountSql("select count(*) from sb_surfboard_return_log");
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		search.addOrder(Order.DESC, "create_time");
		if (!StringUtils.isEmpty(startDate)) {
			search.addWhere(Search.AND, "create_date>=?", startDate);
		}
		if (!StringUtils.isEmpty(endDate)) {
			search.addWhere(Search.AND, "create_date<=?", endDate);
		}
		CommonList<SurfboardReturnLogEntity> rtnList = dao.pagination(search, SurfboardReturnLogEntity.class);
		return rtnList;
	}
	
	
	public FileDTO exportDataList(String startDate, String endDate) throws IOException {
		String language = CookieUtils.getCookie("language");
		if (StringUtils.isEmpty(language)) {
			language = "zh";
		}
		final String curLang = language;
		String filePath = ExcelUtils.generateExcel2007(SurfboardReturnLogEntity.class, tempFilePath, UUID.randomUUID().toString(), new ExcelDataSource<SurfboardReturnLogEntity>() {

			@Override
			public List<SurfboardReturnLogEntity> getDataList(int pageNo, int pageSize) {
				try {
					CommonList<SurfboardReturnLogEntity> list = dataList(pageNo, pageSize, startDate, endDate);
					return list.getRecords(); 
				} catch (Exception e) {
					log.error("Exception", e);
				}
				return null;
			}

			@Override
			public String getValue(SurfboardReturnLogEntity dto, ExcelHeader header) {
				header.getField().setAccessible(true);
				Object obj;
				try {
					obj = header.getField().get(dto);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return "";
				}
				String value = obj == null ? "" : obj.toString();
				Properties locales = "zh".equals(curLang) ? ZH_LOCALES : EN_LOCALES;
				String tmpValue = locales.getProperty(value);
				return StringUtils.isEmpty(tmpValue) ? value : tmpValue;
			}
			
		}, "zh".equals(curLang) ? ZH_LOCALES : EN_LOCALES);
		return new FileDTO("surfboard_return_log.xlsx", filePath);
	}

	public BigDecimal totalIncome(String startDate, String endDate) throws SQLException {
		String sql = "select sum(income) from sb_surfboard_return_log where 1=1";
		int paramCount = 0;
		if (!StringUtils.isEmpty(startDate)) {
			sql += " and create_date>=?";
			paramCount++;
		}
		if (!StringUtils.isEmpty(endDate)) {
			sql += " and create_date<=?";
			paramCount++;
		}
		Object[] params = new Object[paramCount];
		paramCount = 0;
		if (!StringUtils.isEmpty(startDate)) {
			params[paramCount++] = startDate;
		}
		if (!StringUtils.isEmpty(endDate)) {
			params[paramCount++] = endDate;
		}
		BigDecimal totalIncome = dao.queryScalar(sql, params);
		return totalIncome;
	}

}
