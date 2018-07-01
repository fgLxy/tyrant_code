package org.tyrant.core.utils.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	
	static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	private static final String EXCEL2003_TYPE = ".xls";

	private static final String EXCEL2007_TYPE = ".xlsx";
	
	public static <T> String generateExcel2007(Class<T> clazz, String path, String title, ExcelDataSource<T> dataSource) throws IOException {
		return generateExcel2007(clazz, path, title, dataSource, null);
	}
	
	public static <T> String generateExcel2007(Class<T> clazz, String path, String title, ExcelDataSource<T> dataSource, Properties locales) throws IOException {
		List<ExcelHeader> headers = generateHeaders(clazz);
		ExcelHelper<T> excelHelper = new ExcelHelper<>(headers, dataSource, 1000);
		excelHelper.setProperties(locales);
		SXSSFWorkbook wb = excelHelper.exportExcel2007(title);
		String filePath = path + title + EXCEL2007_TYPE;
		createFile(filePath);
		try (OutputStream stream = Files.newOutputStream(Paths.get(filePath))) {
			wb.write(stream);
		} catch (FileNotFoundException e) {
			log.error("OnError save excel file fail.{}", e);
		} catch (IOException e) {
			log.error("OnError save excel file fail.{}", e);
		}
		return filePath;
	}

	private static void createFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		if (!path.getParent().toFile().exists()) {
			Files.createDirectories(path.getParent());
		}
		if (!path.toFile().exists()) {
			Files.createFile(path);
		}
	}

	public static <T> String generateExcel2003(Class<T> clazz, String path, String title, ExcelDataSource<T> dataSource) throws IOException {
		return generateExcel2003(clazz, path, title, dataSource, null);
	}
	
	public static <T> String generateExcel2003(Class<T> clazz,String path, String title, ExcelDataSource<T> dataSource, Properties locales) throws IOException {
		List<ExcelHeader> headers = generateHeaders(clazz);
		ExcelHelper<T> excelHelper = new ExcelHelper<>(headers, dataSource, 1000);
		excelHelper.setProperties(locales);
		HSSFWorkbook wb = excelHelper.exportExcel2003(title);
		String filePath = path + title + EXCEL2003_TYPE;
		createFile(filePath);
		try (OutputStream stream = Files.newOutputStream(Paths.get(filePath))) {
			wb.write(stream);
		} catch (FileNotFoundException e) {
			log.error("OnError save excel file fail.{}", e);
		} catch (IOException e) {
			log.error("OnError save excel file fail.{}", e);
		}
		return filePath;
	}

	private static List<ExcelHeader> generateHeaders(Class<? extends Object> clazz) {
		Class<?> currentClass = clazz;
		List<ColumnFieldPack> columns = new ArrayList<>();
		//收集所有打了ExcelColumn注解的属性
		while (currentClass != Object.class) {
			columns.addAll(Arrays.asList(currentClass.getDeclaredFields()).stream()
				.filter(field -> field.getDeclaredAnnotation(ExcelColumn.class) != null)
				.map(field -> new ColumnFieldPack(field.getDeclaredAnnotation(ExcelColumn.class), field))
				.collect(Collectors.toList()));
			currentClass = currentClass.getSuperclass();
		}
		//根据column的columnIndex进行升序排序
		columns.sort(new Comparator<ColumnFieldPack>() {
			@Override
			public int compare(ColumnFieldPack obj1, ColumnFieldPack obj2) {
				return obj1.column.columnIndex() < obj2.column.columnIndex() ? -1 : 1;
			}
		});
		//转为ExcelHeader返回
		return columns.stream().map(column -> new ExcelHeader(column.column.title(), column.field)).collect(Collectors.toList());
	}

	static class ColumnFieldPack {
		ExcelColumn column;
		Field field;
		ColumnFieldPack(ExcelColumn column, Field field) {
			this.column = column;
			this.field = field;
		}
	}
	
}
