package org.tyrant.surfboard.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyrant.dao.Idao;
import org.tyrant.surfboard.dto.QryMenuResponseDTO;
import org.tyrant.surfboard.entity.ProductEntity;
import org.tyrant.surfboard.entity.TransactionEntity;

@Service
public class MenuService {
	@Autowired
	Idao dao;

	public QryMenuResponseDTO qryMenu(String userId) throws SQLException {
		String qryProductSql = "select p.* from sb_product p,sb_user u,sb_user_product up"
				+ " where u.id=? and up.user_id=u.id and p.name=up.product_name";
		String qryTransSql = "select t.* from sb_trans t,sb_product p,sb_user_product up,sb_user u "
				+ "where u.id=? and up.user_id=u.id and p.name=up.product_name and t.product_name=p.name";
		List<ProductEntity> products = dao.queryObjects(ProductEntity.class, qryProductSql, userId);
		List<TransactionEntity> trans = dao.queryObjects(TransactionEntity.class, qryTransSql, userId);
		QryMenuResponseDTO dto = new QryMenuResponseDTO();
		dto.setProducts(products);
		dto.setTrans(trans);
		return dto;
	}

}
