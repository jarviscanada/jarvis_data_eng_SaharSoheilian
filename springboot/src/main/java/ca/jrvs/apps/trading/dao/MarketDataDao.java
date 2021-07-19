package ca.jrvs.apps.trading.dao;

import static ca.jrvs.apps.trading.util.JsonUtil.toJson;
import static ca.jrvs.apps.trading.util.JsonUtil.toObjectFromJson;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  @Autowired
  public MarketDataDao(MarketDataConfig marketDataConfig,
      HttpClientConnectionManager httpClientConnectionManager) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  /**
   * Get an IexQuote by ticker as ID
   *
   * @param ticker
   * @return IexQuote
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0)
      return Optional.empty();
    else if (quotes.size() == 1)
      iexQuote = Optional.of(quotes.get(0));
    else
      throw new DataRetrievalFailureException("Unexpected number of quotes");

    return iexQuote;
  }

  /**
   * Get quotes for a list of tickers (ID) from IEX
   *
   * @param tickers a list of tickers
   * @return a list of IexQuote
   * @throws IllegalArgumentException if any tickers is invalid or list is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    List<IexQuote> iexQuotes = new ArrayList<>();
    StringBuilder symbols = new StringBuilder();

    for (String ticker : tickers)
      symbols.append(ticker).append(',');

    String url = String.format(IEX_BATCH_URL, symbols);

    String response = executeHttpGet(url)
        .orElseThrow(()->new IllegalArgumentException("Invalid tickers"));

    // Array of JSON documents
    JSONArray iexQuotesJson = new JSONArray(response);

    if (iexQuotesJson.length() == 0)
      throw new IllegalArgumentException("Invalid ticker");

    for (int i = 0; i < iexQuotesJson.length(); i++) {
      try {
        iexQuotes.add(
            toObjectFromJson(toJson(iexQuotesJson.get(i), true, false)
            , IexQuote.class));
      } catch (IOException ex) {
        logger.error("Error on parsing response", ex);
        throw new DataRetrievalFailureException("Error on parsing response", ex);
      }
    }

    return iexQuotes;
  }

  /**
   * Execute a GET and return http entity/body as a String
   *
   * @param url
   * @return http response body or Optional.empty() for 404 response
   * @throws DataRetrievalFailureException if Http failed or status code is unexpected
   */
  private Optional<String> executeHttpGet(String url) {
    Optional<String> responseBody;
    HttpClient httpClient = getHttpClient();
    HttpResponse response;
    HttpGet request = new HttpGet(url);

    try {
      response = httpClient.execute(request);
    } catch (IOException ex) {
      logger.error("Error on http execution", ex);
      throw new DataRetrievalFailureException("Http request failed");
    }

    //TODO or check if the status is 404
    if (response.getStatusLine().getStatusCode() != 200)
      return Optional.empty();

    try {
      responseBody = Optional.of(EntityUtils.toString(response.getEntity()));
    } catch (IOException ex) {
      logger.error("Error on parsing response", ex);
      throw new DataRetrievalFailureException("Parsing http response failed");
    }

    return responseBody;
  }

  /**
   * Borrow a HTTP client from the httpClientConnectionManager
   * @return a httpClient
   */
  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
        // prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
