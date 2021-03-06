package com.noshio.loging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Maskarator plugin.
 * Should be able to mask the following sensitive information
 * <p>
 * JWT tokens
 * Visa card Numbers
 * Visa 13 digit card numbers
 * American Express card numbers
 * MasterCard card numbers
 * DinersClub card numbers
 * Discovery card numbers
 * JCB 15 digit card numbers
 * <p>
 * Does not work yet with
 * JCB 16 digits card numbers
 * Voyager card numbers
 * enRoute card numbers
 */
@Plugin(name = "LogMaskarator", category = "Converter")
@ConverterKeys({"sensitive"})
public class LogMaskarator extends LogEventPatternConverter {

	private static final String CREDIT_CARD_NUMBER_REGEX = "\\b(?:4[ -]*(?:\\d[ -]*){11}(?:(?:\\d[ -]*){3})?\\d|"
			+ "(?:5[ -]*[1-5](?:[ -]*\\d){2}|(?:2[ -]*){3}[1-9]|(?:2[ -]*){2}[3-9][ -]*"
			+ "\\d|2[ -]*[3-6](?:[ -]*\\d){2}|2[ -]*7[ -]*[01][ -]*\\d|2[ -]*7[ -]*2[ -]*0)(?:[ -]*"
			+ "\\d){12}|3[ -]*[47](?:[ -]*\\d){13}|3[ -]*(?:0[ -]*[0-5]|[68][ -]*\\d)(?:[ -]*"
			+ "\\d){11}|6[ -]*(?:0[ -]*1[ -]*1|5[ -]*\\d[ -]*\\d)(?:[ -]*"
			+ "\\d){12}|(?:2[ -]*1[ -]*3[ -]*1|1[ -]*8[ -]*0[ -]*0|3[ -]*5(?:[ -]*"
			+ "\\d){3})(?:[ -]*\\d){11})\\b";
	private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_NUMBER_REGEX);
	private static final String CREDIT_CARD_NUMBER_MASK = "**hidden cc data***";

	private static final String JWT_REGEX = "Bearer [A-Za-z0-9\\-\\._~\\+\\/]+=*";
	private static final Pattern JWT_PATTERN = Pattern.compile(JWT_REGEX);
	private static final String JWT_REPLACEMENT_MASK = "xxx.xxx.xxx";


	private static final String CVV_REGEX = "CVV:[0-9]{3}";
	private static final Pattern CVV_PATTERN = Pattern.compile(CVV_REGEX);
	private static final String CVV_REPLACEMENT_REGEX = "**hiden cvv**";

	protected LogMaskarator(String name, String style) {
		super(name, style);
	}

	/**
	 * as per documentation we need this method.
	 *
	 * @param options
	 * @return LogMaskarator
	 */
	public static LogMaskarator newInstance(final String[] options) {
		return new LogMaskarator("sensitive", Thread.currentThread().getName());
	}

	@Override
	public void format(final LogEvent event, final StringBuilder outputMsg) {
		String msg = event.getMessage().getFormattedMessage() != null
				? event.getMessage().getFormattedMessage()
				: "";
		String maskedMessage;
		try {
			maskedMessage = maskarate(msg);
		} catch (final Exception e) {
			maskedMessage = msg;
			// we should log this, as we cannot throw, as extended class does not throws exceptions
		}
		outputMsg.append(maskedMessage);
	}

	/**
	 * masks the occurrences that match regex's in msg string
	 *
	 * @param msg
	 * @return
	 */
	private String maskarate(String msg) {
		Matcher matcher;
		final StringBuffer buffer = new StringBuffer();

		matcher = JWT_PATTERN.matcher(msg);
		maskMatcher(matcher, buffer, JWT_REPLACEMENT_MASK);
		msg = buffer.toString();
		buffer.setLength(0);

		matcher = CREDIT_CARD_PATTERN.matcher(msg);
		maskMatcher(matcher, buffer, CREDIT_CARD_NUMBER_MASK);
		msg = buffer.toString();
		buffer.setLength(0);

		matcher = CVV_PATTERN.matcher(msg);
		maskMatcher(matcher, buffer, CVV_REPLACEMENT_REGEX);

		return buffer.toString();
	}

	/**
	 * keeps adding the masked strings to msg.
	 *
	 * @param matcher
	 * @param buffer
	 * @param maskStr
	 * @return StringBuffer
	 */
	private StringBuffer maskMatcher(final Matcher matcher, final StringBuffer buffer, final String maskStr) {
		while (matcher.find()) {
			matcher.appendReplacement(buffer, maskStr);
		}
		matcher.appendTail(buffer);
		return buffer;
	}
}
