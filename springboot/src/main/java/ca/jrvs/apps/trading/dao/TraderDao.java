package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private static final String TABLE_NAME = "trader";
  private static final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private  SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public TraderDao(DataSource dataSource) {
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
  public Class<Trader> getEntityClass() {
    return Trader.class;
  }

  @Override
  public int updateOne(Trader trader) {
    String updateSql = "UPDATE " + TABLE_NAME
        + " SET first_name=?, last_name=?, dob=?, "
        + "country=?, email=? WHERE " + ID_COLUMN + "=?";
    return jdbcTemplate.update(updateSql, makeUpdateValues(trader));
  }

  private Object[] makeUpdateValues(Trader trader) {
    return new Object[]{trader.getFirstName(), trader.getLastName(), trader.getDob(),
    trader.getCountry(), trader.getEmail(), trader.getId()};
  }

  @Override
  public void delete(Trader trader) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Trader> traders) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
