package com.vpubchain.core.uri;

/**
 * <p>This exception occurs when a required field is detected (under the BIP21 rules) and fails
 * to pass the associated test (such as {@code req-expires} being out of date), or the required field is unknown
 * to this version of the client in which case it should fail for security reasons.</p>
 *p>当检测到所需字段（在bip21规则下）并失败时，会发生此异常
 *
 * *要通过相关测试（如@code req expires过期），或所需字段未知
 *
 * *对于此版本的客户端，在这种情况下，由于安全原因，它应该失败。<p>
 * @since 0.3.0
 *         
 */
public class RequiredFieldValidationException extends CoinURIParseException {

    public RequiredFieldValidationException(String s) {
        super(s);
    }

    public RequiredFieldValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
