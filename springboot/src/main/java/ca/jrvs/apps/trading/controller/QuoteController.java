package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import com.sun.org.apache.xpath.internal.operations.Quo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {

    private QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @ApiOperation(value = "Show iexQuote", notes = " SAAR Show iexQuote for a given ticker/symbol")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "ticker is not found")})
    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            return quoteService.findIexQuoteByTicker(ticker);
        } catch (Exception ex) {
            throw ResponseExceptionUtil.getResponseStatusException(ex);
        }
    }

    @ApiOperation(value = "Update quote table using iex data",
        notes ="Update all quotes in the quote table. Use IEX trading API as market data source.")
    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> updateMarketData() {
        try{
            return quoteService.updateMarketData();
        } catch (Exception ex) {
            throw ResponseExceptionUtil.getResponseStatusException(ex);
        }
    }

    @ApiOperation(value = "Update a given quote in the quote table",
        notes = "Manually update a quote in the quote table using IEX trading API.")
    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote) {
        try {
            return quoteService.saveQuote(quote);
        } catch (Exception ex) {
            throw ResponseExceptionUtil.getResponseStatusException(ex);
        }
    }

    @ApiOperation(value = "Add a new ticker to the dailyList (quote table)",
        notes = "Add a new ticker/symbol to the quote table, to enable trader to trade this security.")
    @PostMapping(path = "/ticker/{ticker}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Quote createQuote(@PathVariable String ticker) {
        try {
            return quoteService.saveQuote(ticker);
        } catch (Exception ex) {
            throw ResponseExceptionUtil.getResponseStatusException(ex);
        }
    }

    @ApiOperation(value = "Show dailyList", notes = "Show daily list for this trading system.")
    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteService.findAllQuotes();
        } catch (Exception ex) {
            throw ResponseExceptionUtil.getResponseStatusException(ex);
        }
    }

}
