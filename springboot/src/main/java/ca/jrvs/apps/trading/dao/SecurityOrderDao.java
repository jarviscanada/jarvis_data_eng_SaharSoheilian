package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

  private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);

  private static final String TABLE_NAME = "security_order";
  private static final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
        .withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  public Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
  }

  @Override
  public int updateOne(SecurityOrder securityOrder) {
    String updateSql = "UPDATE " + TABLE_NAME
        + " SET account_id=?, status=?, ticker=?, \"size\"=?, price=?, notes=?"
    + " WHERE " + ID_COLUMN + "=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(securityOrder));
  }

  private Object[] makeUpdateValues(SecurityOrder securityOrder) {
    return new Object[]{securityOrder.getAccountId(), securityOrder.getStatus(),
        securityOrder.getTicker(), securityOrder.getSize(), securityOrder.getPrice(),
        securityOrder.getNotes(), securityOrder.getId()};
  }

  @Override
  public void delete(SecurityOrder integer) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
