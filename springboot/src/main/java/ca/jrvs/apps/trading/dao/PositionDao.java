package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

  private static final String VIEW_NAME = "position";
  private static final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PositionDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
        .withTableName(VIEW_NAME).usingGeneratedKeyColumns(ID_COLUMN);
  }

  public List<Position> findByAccountId(Integer id) {
    String selectSql = "SELECT account_id, ticker, position AS sum FROM "
        + VIEW_NAME + " WHERE account_id=?";

    try {
      return jdbcTemplate.query(selectSql,
          BeanPropertyRowMapper.newInstance(Position.class), id);
    } catch (DataAccessException ex) {
      throw new IllegalArgumentException("No record for account: " + id, ex);
    }
  }

  public List<Position> findByTickerId(String ticker) {
    String selectSql = "SELECT account_id, ticker, position AS sum FROM "
        + VIEW_NAME + " WHERE ticker=?";

    try {
      return jdbcTemplate.query(selectSql,
          BeanPropertyRowMapper.newInstance(Position.class), ticker);
    } catch (DataAccessException ex) {
      throw new IllegalArgumentException("No record for ticker: " + ticker, ex);
    }
  }

  public List<Position> findAll() {
    String selectSql = "SELECT account_id, ticker, position AS sum FROM " + VIEW_NAME;

    return jdbcTemplate.query(selectSql,
        BeanPropertyRowMapper.newInstance(Position.class));
  }

  public long count() {
    String selectSql = "SELECT COUNT(*) FROM " + VIEW_NAME;

    return jdbcTemplate.queryForObject(selectSql, Long.class);
  }
}
