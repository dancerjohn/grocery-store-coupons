package org.butler.johnj;

import static org.hamcrest.MatcherAssert.*;

import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

@Ignore
public class TestBase {

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	/**
	 * @param reason
	 * @param actual
	 * @param matcher
	 */
	public <T> void checkThat(String reason, T actual,
			Matcher<? super T> matcher) {
		try {
			assertThat(reason, actual, matcher);
		} catch (Throwable t) {
			errorCollector.addError(t);
		}
	}
}
