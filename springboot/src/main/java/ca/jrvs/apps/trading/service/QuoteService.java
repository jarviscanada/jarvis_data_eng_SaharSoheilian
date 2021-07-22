package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from db
     * - foreach ticker get iexQuote
     * - convert iexQuote to quote entity
     * - persist quote to db
     *
     * @return saved quotes
     */
    public List<Quote> updateMarketData() {
        List<Quote> quoteList = findAllQuotes();
        List<String> tickers = new ArrayList<>();

        for (Quote quote : quoteList)
            tickers.add(quote.getTicker());

        return saveQuotes(tickers);
    }

    /**
     * helper method that maps a IexQuote to a Quote entity
     * iexQuote.getLatestPrice() == null: if the stock market is closed
     * @param iexQuote
     * @return Quote
     */
    //TODO: logger.info? or error?
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        if (iexQuote.getLatestPrice() == null) {
            throw new IllegalArgumentException("Stock market is closed");
        }

        Quote quote = new Quote();

        quote.setTicker(iexQuote.getSymbol());
        quote.setBidSize(iexQuote.getIexBidSize() == null ? 0 : iexQuote.getIexBidSize());
        quote.setBidPrice(iexQuote.getIexBidPrice() == null ? 0d :iexQuote.getIexBidPrice());
        quote.setAskSize(iexQuote.getIexAskSize() == null ? 0 : iexQuote.getIexAskSize());
        quote.setAskPrice(iexQuote.getIexAskPrice() == null ? 0d : iexQuote.getIexAskPrice());
        quote.setLastPrice(iexQuote.getLatestPrice() == null ? 0d : iexQuote.getLatestPrice());

        return quote;
    }

    /**
     * Validate against IEX and save given tickers to quote table
     * - Get IexQuotes
     * - convert to Quote entity
     * - persist the quote to db
     *
     * @param tickers
     * @throws IllegalArgumentException is ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quoteList = new ArrayList<>();

        tickers.forEach(ticker -> quoteList.add(saveQuote(ticker)));

        return quoteList;
    }

    /**
     * helper method
     */
    public Quote saveQuote(String ticker) {
        return saveQuote(buildQuoteFromIexQuote(findIexQuoteByTicker(ticker)));
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
            .orElseThrow(() ->
                new IllegalArgumentException(ticker + " is invalid"));
    }

    /**
     * Update a given quote to quote table without validation
     * @param quote entity
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     * @return
     */
    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}
