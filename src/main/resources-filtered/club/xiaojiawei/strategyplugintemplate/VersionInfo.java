//todo 包名换成自己的
package club.xiaojiawei.strategyplugintemplate;

/**
 * @author 肖嘉威
 * @date 2025/7/19 23:22
 */

public class VersionInfo {
    public static final String VERSION = "${project.version}";
    public static final String CARD_SDK_VERSION_USED = "${card-sdk-version}".endsWith("}") ? null : "${card-sdk-version}";
    public static final String STRATEGY_SDK_VERSION_USED = "${strategy-sdk-version}".endsWith("}") ? null : "${strategy-sdk-version}";
}
