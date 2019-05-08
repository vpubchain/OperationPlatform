package com.vpubchain.core.uri;

/**
 * <p>This exception occurs when an optional field is detected (under the Suqa URI scheme) and fails
 * to pass the associated test (such as {@code amount} not being a valid number).</p>
 *当检测到可选字段（在suqa uri方案下）并失败时，会发生此异常。
 *
 * *通过相关测试（如@code amount不是有效数字）。
 *
 * @since 0.3.0
 *         
 */
public class OptionalFieldValidationException extends CoinURIParseException {

    public OptionalFieldValidationException(String s) {
        super(s);
    }

    public OptionalFieldValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
