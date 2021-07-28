package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  abstract public JdbcTemplate getJdbcTemplate();

  abstract public SimpleJdbcInsert getSimpleJdbcInsert();

  abstract public String getTableName();

  abstract public String getIdColumnName();

  abstract public Class<T> getEntityClass();

  /**
   * Save an entity and update auto-generated integer ID
   * @param entity to be saved
   * @return saved entity
   */
  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1)
        throw new DataRetrievalFailureException("Unable to update entity");
    } else
      addOne(entity);

    return entity;
  }

  /**
   * helper method that saves one entity
   */
  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    Number row = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setId(row.intValue());
  }

  /**
   * helper method that updates one entity
   */
  abstract public int updateOne(T entity);

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    entities.forEach(this::save);

    return entities;
  }

  @Override
  public Optional<T> findById(Integer id) {
    Optional<T> entity = Optional.empty();
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " +
        getIdColumnName() + "=?";

    try {
      entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(selectSql,
          BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (EmptyResultDataAccessException ex) {
      logger.debug("Can't find entity: " + id, ex);
    }

    return entity;
  }

  @Override
  public boolean existsById(Integer id) {
    return findById(id).isPresent();
  }

  @Override
  public List<T> findAll() {
    String selectSql = "SELECT * FROM " + getTableName();

    return getJdbcTemplate().query(selectSql,
        BeanPropertyRowMapper.newInstance(getEntityClass()));
  }

  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    List<T> result = new ArrayList<>();

    for (Integer id : ids) {
      if (findById(id).isPresent()) {
        T entity = findById(id).get();
        result.add(entity);
      }
    }

    return result;
  }

  @Override
  public long count() {
    String selectSql = "SELECT COUNT(*) FROM " + getTableName();

    return getJdbcTemplate().queryForObject(selectSql, Long.class);
  }

  @Override
  public void deleteById(Integer id) {
    if (id == null)
      throw new IllegalArgumentException("ID can't be null");

    String deleteSql = "DELETE FROM " + getTableName() + " WHERE "
        + getIdColumnName() + "=?";
    getJdbcTemplate().update(deleteSql, id);
  }

  @Override
  public void deleteAll() {
    String deleteAllSql = "DELETE FROM " + getTableName();
    getJdbcTemplate().update(deleteAllSql);
  }
}
