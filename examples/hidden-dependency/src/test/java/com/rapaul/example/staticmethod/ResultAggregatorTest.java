package com.rapaul.example.staticmethod;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class ResultAggregatorTest {

    private Patient patient = new Patient("123");
    private ResultAggregator aggregator = spy(new ResultAggregator());

    @Test(expected = IllegalStateException.class)
    public void cantCreateAnAggregateSummaryIfNoResultsExist() throws Exception {
        aggregator.aggregateSummary(patient);
    }

    @Test
    public void calculatesTheMeanOfAllResults() {
        doReturn(Arrays.asList(new Result(0), new Result(2))).
                when(aggregator).getPendingResults(patient);
        AggregateSummary aggregateSummary = aggregator.aggregateSummary(patient);
        assertThat(aggregateSummary.getMean(), is(new BigDecimal(1)));
    }

}