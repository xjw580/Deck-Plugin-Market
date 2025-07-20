package club.xiaojiawei.strategyplugintemplate

import club.xiaojiawei.hsscriptstrategysdk.StrategyPlugin

/**
 * @author 肖嘉威
 * @date 2024/9/29 16:56
 */
class TemplatePlugin: StrategyPlugin {
    override fun description(): String {
//        插件的描述
        return "模板插件描述"
    }

    override fun author(): String {
//        插件的作者
        return "XiaoJiawei"
    }

    override fun version(): String {
//        插件的版本号
        return "1.0.0-template"
    }

    override fun id(): String {
        return "插件的唯一id"
    }

    override fun name(): String {
//        插件的名字
        return "模板策略插件"
    }

    override fun homeUrl(): String {
        return "https://github.com/xjw580/Deck-Plugin-Market/tree/master/Deck-Plugin-Template"
    }

    override fun cardSDKVersion(): String? = VersionInfo.CARD_SDK_VERSION_USED

    override fun strategySDKVersion(): String? = VersionInfo.STRATEGY_SDK_VERSION_USED

}